package org.uikit;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
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
    private View indicatorRing;
    private String[] items;
    private OnItemSelectedListener listener;
    private int lastSelectedIndex = -1;

    public UikitPicker(Context context) {
        super(context);

        // 1. 底部滚动层
        scrollView = new ScrollView(context);
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setClipToPadding(false);
        scrollView.setOverScrollMode(OVER_SCROLL_NEVER);
        
        scrollView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
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

        // 2. 顶层固定圆环
        indicatorRing = new View(context);
        int ringSize = dp(72);
        LayoutParams ringLp = new LayoutParams(ringSize, ringSize);
        ringLp.gravity = Gravity.CENTER;
        
        GradientDrawable ringBg = new GradientDrawable();
        ringBg.setShape(GradientDrawable.OVAL);
        ringBg.setColor(Color.TRANSPARENT);
        ringBg.setStroke(dp(2), UikitColor.getPrimaryColor(context));
        indicatorRing.setBackground(ringBg);
        
        addView(indicatorRing, ringLp);
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
            tv.setTextSize(24);
            tv.setGravity(Gravity.CENTER);
            tv.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, dp(72)));
            tv.setTextColor(UikitColor.getPrimaryColor(getContext()));
            container.addView(tv);
        }

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                if (scrollView.getHeight() > 0) {
                    scrollView.setPadding(0, scrollView.getHeight() / 2 - dp(36), 0, scrollView.getHeight() / 2 - dp(36));
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
            View child = container.getChildAt(i);
            if (child.getHeight() == 0) continue;

            int childCenterY = child.getTop() + child.getHeight() / 2;
            int distance = Math.abs(centerY - childCenterY);
            
            float maxDistance = scrollView.getHeight() / 2f;
            float fraction = distance / maxDistance;
            if (fraction > 1f) fraction = 1f;
            if (Float.isNaN(fraction)) fraction = 1f; 

            // 灵动微特效：缩放 + 旋转
            float scale = 1.0f - 0.3f * fraction;
            float alpha = 1.0f - 0.6f * fraction;
            float rotation = fraction * 15f; 
            if (childCenterY < centerY) rotation = -rotation;

            child.setScaleX(scale);
            child.setScaleY(scale);
            child.setAlpha(alpha);
            child.setRotation(rotation); 
            
            if (distance < minDistance) {
                minDistance = distance;
                nearestIndex = i;
                ((TextView) child).setTextColor(UikitColor.getAccentColor(getContext(), UikitTheme.isDarkMode(getContext())));
            } else {
                ((TextView) child).setTextColor(UikitTheme.isDarkMode(getContext()) ? 0xFFAAAAAA : 0xFF666666);
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
