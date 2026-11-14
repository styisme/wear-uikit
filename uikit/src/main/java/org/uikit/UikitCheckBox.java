package org.uikit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.CheckBox;

public class UikitCheckBox extends CheckBox {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint checkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF rect = new RectF();

    public UikitCheckBox(Context context) { super(context); init(); }
    public UikitCheckBox(Context context, AttributeSet attrs) { super(context, attrs); init(); }

    private void init() {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(UikitScreenUtils.dp2px(getContext(), 2));
        checkPaint.setStyle(Paint.Style.STROKE);
        checkPaint.setStrokeWidth(UikitScreenUtils.dp2px(getContext(), 2.5f));
        checkPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int size = getHeight();
        // 【防御】宽高为0不绘制
        if (size == 0) return; 
        
        int cx = getWidth() / 2;
        int padding = UikitScreenUtils.dp2px(getContext(), 8);
        rect.set(cx - size / 2f + padding, padding, cx + size / 2f - padding, size - padding);

        int primaryColor = UikitColor.getPrimaryColor(getContext());
        int unCheckColor = UikitTheme.isDarkMode(getContext()) ? 0xFF888888 : 0xFFCCCCCC;

        if (isChecked()) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(primaryColor);
            canvas.drawRoundRect(rect, UikitScreenUtils.dp2px(getContext(), 4), UikitScreenUtils.dp2px(getContext(), 4), paint);
            
            checkPaint.setColor(UikitColor.getOnPrimaryColor(getContext()));
            Path path = new Path();
            path.moveTo(rect.left + rect.width() * 0.25f, rect.centerY());
            path.lineTo(rect.left + rect.width() * 0.45f, rect.bottom - rect.height() * 0.25f);
            path.lineTo(rect.right - rect.width() * 0.2f, rect.top + rect.height() * 0.3f);
            canvas.drawPath(path, checkPaint);
        } else {
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(unCheckColor);
            canvas.drawRoundRect(rect, UikitScreenUtils.dp2px(getContext(), 4), UikitScreenUtils.dp2px(getContext(), 4), paint);
        }
    }
}
