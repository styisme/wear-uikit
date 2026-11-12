package org.uikit;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class UikitButton extends TextView {

    public UikitButton(Context context) {
        super(context);
        init(context, null);
    }

    public UikitButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public UikitButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setGravity(android.view.Gravity.CENTER);
        setTextSize(16);
        setClickable(true);
        setFocusable(true);

        int defaultRadius = UikitScreenUtils.dp2px(context, 32);
        int defaultBgColor = UikitColor.getPrimaryColor(context);
        int defaultTextColor = UikitColor.getOnPrimaryColor(context);
        
        float radius = defaultRadius;
        int bgColor = defaultBgColor;
        int textColor = defaultTextColor;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.UikitButton);
            if (a != null) {
                radius = a.getDimension(R.styleable.UikitButton_uikit_radius, defaultRadius);
                bgColor = a.getColor(R.styleable.UikitButton_uikit_backgroundColor, defaultBgColor);
                textColor = a.getColor(R.styleable.UikitButton_uikit_textColor, defaultTextColor);
                a.recycle();
            }
        }

        GradientDrawable bg = new GradientDrawable();
        bg.setCornerRadius(radius);
        bg.setColor(bgColor);
        setBackground(bg);
        setTextColor(textColor);

        setupTouchFeedback();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        // 每次设置文字后，重新校正图标的尺寸和颜色
        fixCompoundDrawables();
    }

    private void fixCompoundDrawables() {
        Drawable[] drawables = getCompoundDrawables();
        int size = UikitScreenUtils.dp2px(getContext(), 18); // 统一强制图标大小为 18dp
        int color = getCurrentTextColor(); // 强制图标颜色跟随文字

        for (Drawable d : drawables) {
            if (d != null) {
                d.setBounds(0, 0, size, size);
                if (Build.VERSION.SDK_INT >= 21) {
                    d.setTint(color);
                } else {
                    d.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                }
            }
        }
        setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

    private void setupTouchFeedback() {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (Build.VERSION.SDK_INT < 11) return false;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.animate().scaleX(0.92f).scaleY(0.92f).alpha(0.7f).setDuration(100).start();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        v.animate().scaleX(1f).scaleY(1f).alpha(1f).setDuration(150).start();
                        break;
                }
                return false; 
            }
        });
    }
}
