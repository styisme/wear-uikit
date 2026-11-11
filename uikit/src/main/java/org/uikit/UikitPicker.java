package org.uikit;

import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UikitPicker extends HorizontalScrollView {

    public interface OnItemSelectedListener {
        void onItemSelected(int index, String item);
    }

    private LinearLayout container;
    private String[] items;
    private OnItemSelectedListener listener;
    private int lastSelectedIndex = -1;

    public UikitPicker(Context context) {
        super(context);
        setHorizontalScrollBarEnabled(false);
        setClipToPadding(false);
        setOverScrollMode(OVER_SCROLL_NEVER);
        
        container = new LinearLayout(context);
        container.setOrientation(LinearLayout.HORIZONTAL);
        container.setGravity(Gravity.CENTER_VERTICAL);
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
            tv.setMaxLines(1);
            tv.setEllipsize(android.text.TextUtils.TruncateAt.END);
            tv.setPadding(dp(24), dp(16), dp(24), dp(16));
            tv.setTextColor(UikitColor.getPrimaryColor(getContext()));
            container.addView(tv);
        }

        post(new Runnable() {
            @Override
            public void run() {
                if (getWidth() > 0) {
                    setPadding(getWidth() / 2, 0, getWidth() / 2, 0);
                    updateScale(0);
                }
            }
        });
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        updateScale(l);
    }

    private void updateScale(int scrollX) {
        // 【修复】如果还没测量出宽度，直接返回，防止除以 0 导致 NaN 崩溃
        if (getWidth() == 0 || items == null || container.getChildCount() == 0) return;

        int centerX = getWidth() / 2 + scrollX;
        int minDistance = Integer.MAX_VALUE;
        int nearestIndex = 0;

        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            if (child.getWidth() == 0) continue; // 【修复】防御子项未测量

            int childCenterX = child.getLeft() + child.getWidth() / 2;
            int distance = Math.abs(centerX - childCenterX);
            
            float maxDistance = getWidth() / 2f;
            float fraction = distance / maxDistance;
            if (fraction > 1f) fraction = 1f;
            
            // 双重保险，如果 fraction 是 NaN，强制设为 1
            if (Float.isNaN(fraction)) fraction = 1f; 

            float scale = 1.0f - 0.4f * fraction;
            float alpha = 1.0f - 0.6f * fraction;
            
            child.setScaleX(scale);
            child.setScaleY(scale);
            child.setAlpha(alpha);
            
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
        if (getWidth() == 0 || items == null || container.getChildCount() == 0) return;
        
        int centerX = getWidth() / 2 + getScrollX();
        int minDistance = Integer.MAX_VALUE;
        int nearestIndex = 0;

        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            if (child.getWidth() == 0) continue;
            
            int childCenterX = child.getLeft() + child.getWidth() / 2;
            int distance = Math.abs(centerX - childCenterX);
            if (distance < minDistance) {
                minDistance = distance;
                nearestIndex = i;
            }
        }

        View target = container.getChildAt(nearestIndex);
        int targetScrollX = target.getLeft() + target.getWidth() / 2 - getWidth() / 2;
        smoothScrollTo(targetScrollX, 0);
    }

    private int dp(int val) {
        return (int) (val * getResources().getDisplayMetrics().density);
    }
}
