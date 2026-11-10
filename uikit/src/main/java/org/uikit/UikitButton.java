package org.uikit;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class UikitButton extends TextView {

    public UikitButton(Context context) {
        super(context);
        init();
    }

    public UikitButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setGravity(android.view.Gravity.CENTER);
        setTextSize(16);
        setClickable(true);
        setFocusable(true);
        
        GradientDrawable bg = new GradientDrawable();
        bg.setCornerRadius(UikitScreenUtils.dp2px(getContext(), 32));
        bg.setColor(UikitColor.getPrimaryColor(getContext()));
        setBackground(bg);
        setTextColor(UikitColor.getOnPrimaryColor(getContext()));

        // 绑定触感反馈
        setupTouchFeedback();
    }

    private void setupTouchFeedback() {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (Build.VERSION.SDK_INT < 11) return false; // 低版本不支持属性动画，直接跳过

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 按下：缩小到 92%，透明度降到 70%
                        v.animate()
                         .scaleX(0.92f).scaleY(0.92f).alpha(0.7f)
                         .setDuration(100)
                         .start();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        // 抬起/取消：恢复原状
                        v.animate()
                         .scaleX(1f).scaleY(1f).alpha(1f)
                         .setDuration(150)
                         .start();
                        break;
                }
                // 返回 false 让原本的 onClick 事件能够正常触发
                return false; 
            }
        });
    }
}
