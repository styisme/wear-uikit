package org.uikit;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
public class UikitProgressBar extends View {
    private Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint fgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF rect = new RectF();
    private float progress = 0f;
    public UikitProgressBar(Context context) {
        super(context);
        bgPaint.setColor(UikitTheme.isDarkMode(context) ? 0xFF3E3E3E : 0xFFE0E0E0);
        fgPaint.setColor(UikitColor.getPrimaryColor(context));
    }
    public void setProgress(float progress) {
        this.progress = Math.max(0, Math.min(1, progress));
        invalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float r = getHeight() / 2f;
        rect.set(0, 0, getWidth(), getHeight());
        canvas.drawRoundRect(rect, r, r, bgPaint);
        rect.set(0, 0, getWidth() * progress, getHeight());
        canvas.drawRoundRect(rect, r, r, fgPaint);
    }
}
