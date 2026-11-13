package org.uikit;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class UikitCompatButton extends UikitButton {

    public UikitCompatButton(Context context) {
        super(context);
    }

    public UikitCompatButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UikitCompatButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // ================= 代理所有原生的 TextView / Button 方法 =================
    // 旧项目无需修改任何 Java 代码，直接改 XML 标签为 <org.uikit.UikitCompatButton> 即可。

    public void setTextColor(int color) {
        textColor = color;
        textView.setTextColor(color);
        if (iconView.getDrawable() != null) {
            applyIconTint();
        }
    }

    public void setTextColor(ColorStateList colors) {
        if (colors != null) {
            textColor = colors.getDefaultColor();
            textView.setTextColor(colors);
        }
    }

    public void setTextSize(float size) {
        textView.setTextSize(size);
    }

    public void setTextSize(int unit, float size) {
        textView.setTextSize(unit, size);
    }

    public void setGravity(int gravity) {
        textView.setGravity(gravity);
    }

    public void setTypeface(Typeface tf) {
        textView.setTypeface(tf);
    }

    public void setAllCaps(boolean allCaps) {
        textView.setAllCaps(allCaps);
    }

    public ColorStateList getTextColors() {
        return textView.getTextColors();
    }
}
