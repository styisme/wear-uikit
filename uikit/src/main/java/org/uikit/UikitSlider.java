package org.uikit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

public class UikitSlider extends View {
    public interface OnProgressChangedListener {
        void onProgressChanged(float progress);
    }

    private Paint trackBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint trackFgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint thumbPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF rect = new RectF();

    private float maxProgress = 100f;
    private float currentProgress = 0f;
    private float trackHeight;
    private float thumbRadius;
    
    private OnProgressChangedListener listener;

    public UikitSlider(Context context) {
        super(context);
        trackHeight = UikitScreenUtils.dp2px(context, 8);
        thumbRadius = UikitScreenUtils.dp2px(context, 12);
        
        trackBgPaint.setStyle(Paint.Style.FILL);
        trackFgPaint.setStyle(Paint.Style.FILL);
        thumbPaint.setStyle(Paint.Style.FILL);
        
        boolean dark = UikitTheme.isDarkMode(context);
        trackBgPaint.setColor(dark ? 0xFF3E3E3E : 0xFFE0E0E0);
        trackFgPaint.setColor(UikitColor.getPrimaryColor(context));
        thumbPaint.setColor(UikitColor.getPrimaryColor(context));
    }

    public void setMaxProgress(float max) {
        this.maxProgress = max;
        invalidate();
    }

    public float getProgress() {
        return currentProgress;
    }

    public void setProgress(float progress) {
        if (progress < 0) progress = 0;
        if (progress > maxProgress) progress = maxProgress;
        this.currentProgress = progress;
        invalidate();
    }

    public void setOnProgressChangedListener(OnProgressChangedListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        float width = getWidth();
        float height = getHeight();
        float cy = height / 2f;
        
        // 1. 绘制背景轨道
        rect.set(0, cy - trackHeight / 2f, width, cy + trackHeight / 2f);
        canvas.drawRoundRect(rect, trackHeight / 2f, trackHeight / 2f, trackBgPaint);

        // 2. 绘制进度轨道
        float progressRatio = currentProgress / maxProgress;
        float progressX = width * progressRatio;
        rect.set(0, cy - trackHeight / 2f, progressX, cy + trackHeight / 2f);
        canvas.drawRoundRect(rect, trackHeight / 2f, trackHeight / 2f, trackFgPaint);

        // 3. 绘制拖拽圆点 (限制在左右边界内)
        float thumbX = progressX;
        if (thumbX < thumbRadius) thumbX = thumbRadius;
        if (thumbX > width - thumbRadius) thumbX = width - thumbRadius;
        canvas.drawCircle(thumbX, cy, thumbRadius, thumbPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float width = getWidth();
                float ratio = x / width;
                if (ratio < 0) ratio = 0;
                if (ratio > 1) ratio = 1;
                
                currentProgress = ratio * maxProgress;
                invalidate();
                
                if (listener != null) {
                    listener.onProgressChanged(currentProgress);
                }
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                return true;
        }
        return super.onTouchEvent(event);
    }
}
