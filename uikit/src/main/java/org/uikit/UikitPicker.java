package org.uikit;

import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class UikitPicker extends ScrollView {

    public interface OnItemSelectedListener {
        void onItemSelected(int index, String item);
    }

    private LinearLayout container;
    private String[] items;
    private OnItemSelectedListener listener;
    private int lastSelectedIndex = -1;

    public UikitPicker(Context context) {
        super(context);
        setVerticalScrollBarEnabled(false);
        setClipToPadding(false);
        setOverScrollMode(OVER_SCROLL_NEVER);
        
        container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setGravity(Gravity.CENTER_HORIZONTAL);
        addView(container);
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
            tv.setMaxLines(1);
            tv.setEllipsize(android.text.TextUtils.TruncateAt.END);
            tv.setPadding(dp(16), dp(16), dp(16), dp(16));
            tv.setTextColor(UikitColor.getPrimaryColor(getContext()));
            container.addView(tv);
        }

        // 等布局完成后，设置上下 padding 使首尾项能滚到屏幕正中心
        post(new Runnable() {
            @Override
            public void run() {
                if (getHeight() > 0) {
                    setPadding(0, getHeight() / 2, 0, getHeight() / 2);
                    updateScale(0);
                }
            }
        });
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        updateScale(t);
    }

    private void updateScale(int scrollY) {
        if (getHeight() == 0 || items == null || container.getChildCount() == 0) return;

        int centerY = getHeight() / 2 + scrollY;
        int minDistance = Integer.MAX_VALUE;
        int nearestIndex = 0;

        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            if (child.getHeight() == 0) continue;

            int childCenterY = child.getTop() + child.getHeight() / 2;
            int distance = Math.abs(centerY - childCenterY);
            
            float maxDistance = getHeight() / 2f;
            float fraction = distance / maxDistance;
            if (fraction > 1f) fraction = 1f;
            if (Float.isNaN(fraction)) fraction = 1f; 

            // 越靠近中心越大，越远越小
            float scale = 1.0f - 0.4f * fraction; 
            float alpha = 1.0f - 0.6f * fraction;
            
            child.setScaleX(scale);
            child.setScaleY(scale);
            child.setAlpha(alpha);
            
            // 高亮居中项
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

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
            snapToNearest();
        }
        return super.onTouchEvent(ev);
    }

    private void snapToNearest() {
        if (getHeight() == 0 || items == null || container.getChildCount() == 0) return;
        
        int centerY = getHeight() / 2 + getScrollY();
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

        // 自动吸附滚动到中间的选项
        View target = container.getChildAt(nearestIndex);
        int targetScrollY = target.getTop() + target.getHeight() / 2 - getHeight() / 2;
        smoothScrollTo(0, targetScrollY);
    }

    private int dp(int val) {
        return (int) (val * getResources().getDisplayMetrics().density);
    }
}
