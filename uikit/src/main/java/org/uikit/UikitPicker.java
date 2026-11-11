package org.uikit;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class UikitPicker extends FrameLayout {

    public interface OnItemSelectedListener {
        void onItemSelected(int index, String item);
    }

    private ScrollView scrollView;
    private LinearLayout container;
    private String[] items;
    private OnItemSelectedListener listener;
    private int lastSelectedIndex = -1;

    public UikitPicker(Context context) {
        super(context);

        // 底层垂直滚动的文字列表
        scrollView = new ScrollView(context);
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setClipToPadding(false);
        scrollView.setOverScrollMode(OVER_SCROLL_NEVER);
        
        scrollView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, android.view.MotionEvent event) {
                if (event.getAction() == android.view.MotionEvent.ACTION_UP || 
                    event.getAction() == android.view.MotionEvent.ACTION_CANCEL) {
                    snapToNearest();
                }
                return false;
            }
        });
        
        scrollView.setOnScrollChangeListener(new OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                updateScale(scrollY);
            }
        });
        
        container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setGravity(Gravity.CENTER_HORIZONTAL);
        scrollView.addView(container);
        addView(scrollView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    public void setItems(String[] items) {
        this.items = items;
        container.removeAllViews();
        
        if (items == null || items.length == 0) return;

        for (String item : items) {
            TextView tv = new TextView(getContext());
            tv.setText(item);
            tv.setTextSize(22);
            tv.setGravity(Gravity.CENTER);
            // 设定固定高度，防止缩放导致布局跳动
            tv.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, dp(64)));
            tv.setPadding(dp(32), 0, dp(32), 0);
            container.addView(tv);
        }

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                if (scrollView.getHeight() > 0) {
                    // 上下留白，让首尾项能居中
                    scrollView.setPadding(0, scrollView.getHeight() / 2 - dp(32), 0, scrollView.getHeight() / 2 - dp(32));
                    updateScale(0);
                }
            }
        });
    }

    private void updateScale(int scrollY) {
        if (scrollView.getHeight() == 0 || items == null || container.getChildCount() == 0) return;

        int centerY = scrollView.getHeight() / 2 + scrollY;
        int minDistance = Integer.MAX_VALUE;
        int nearestIndex = 0;

        for (int i = 0; i < container.getChildCount(); i++) {
            TextView child = (TextView) container.getChildAt(i);
            if (child.getHeight() == 0) continue;

            int childCenterY = child.getTop() + child.getHeight() / 2;
            int distance = Math.abs(centerY - childCenterY);
            
            float maxDistance = scrollView.getHeight() / 2f;
            float fraction = distance / maxDistance;
            if (fraction > 1f) fraction = 1f;
            if (Float.isNaN(fraction)) fraction = 1f; 

            // 直上直下，仅仅做缩放和透明度变化，无旋转
            float scale = 1.0f - 0.35f * fraction;
            float alpha = 1.0f - 0.7f * fraction;
            
            child.setScaleX(scale);
            child.setScaleY(scale);
            child.setAlpha(alpha);
            
            // 寻找最中间的一项
            if (distance < minDistance) {
                minDistance = distance;
                nearestIndex = i;
            }
        }

        // 统一更新背景和文字颜色（将圆圈背景直接嵌在选中的项上）
        for (int i = 0; i < container.getChildCount(); i++) {
            TextView child = (TextView) container.getChildAt(i);
            if (i == nearestIndex) {
                // 选中项：加上圆角背景（药丸/圆环）
                GradientDrawable bg = new GradientDrawable();
                bg.setShape(GradientDrawable.RECTANGLE);
                bg.setCornerRadius(dp(32)); // 全圆角
                bg.setColor(UikitColor.getPrimaryColor(getContext()));
                child.setBackground(bg);
                child.setTextColor(UikitColor.getOnPrimaryColor(getContext()));
            } else {
                // 未选中项：清除背景，文字变灰
                child.setBackground(null);
                child.setTextColor(UikitTheme.isDarkMode(getContext()) ? 0xFFAAAAAA : 0xFF666666);
            }
        }

        if (lastSelectedIndex != nearestIndex) {
            lastSelectedIndex = nearestIndex;
            if (listener != null) {
                listener.onItemSelected(nearestIndex, items[nearestIndex]);
            }
        }
    }

    private void snapToNearest() {
        if (scrollView.getHeight() == 0 || items == null || container.getChildCount() == 0) return;
        
        int centerY = scrollView.getHeight() / 2 + scrollView.getScrollY();
        int minDistance = Integer.MAX_VALUE;
        int nearestIndex = 0;

        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            if (child.getHeight() == 0) continue;
            
            int childCenterY = child.getTop() + child.getHeight() / 2;
            int distance = Math.abs(centerY - childCenterY);
            if (distance < minDistance) {
                minDistance = distance;
                nearestIndex = i;
            }
        }

        View target = container.getChildAt(nearestIndex);
        int targetScrollY = target.getTop() - (scrollView.getHeight() - target.getHeight()) / 2;
        scrollView.smoothScrollTo(0, targetScrollY);
    }

    private int dp(int val) {
        return (int) (val * getResources().getDisplayMetrics().density);
    }
}
