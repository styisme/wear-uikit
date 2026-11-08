package org.uikit;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UikitBottomSheet extends Dialog {

    public UikitBottomSheet(Context ctx, String title, View content) {
        super(ctx);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        // 1. 允许点击外部区域关闭
        setCanceledOnTouchOutside(true);
        
        boolean dark = UikitTheme.isDarkMode(ctx);
        
        LinearLayout root = new LinearLayout(ctx);
        root.setOrientation(LinearLayout.VERTICAL);
        
        // 2. 加大底部 padding (64dp) 防止在圆屏底部被切割
        int padH = UikitScreenUtils.dp2px(ctx, 24);
        int padTop = UikitScreenUtils.dp2px(ctx, 16);
        int padBottom = UikitScreenUtils.dp2px(ctx, 64);
        root.setPadding(padH, padTop, padH, padBottom);
        
        // 3. 圆角背景（只设顶部圆角）
        android.graphics.drawable.GradientDrawable bg = new android.graphics.drawable.GradientDrawable();
        bg.setColor(dark ? 0xFF1E1E1E : 0xFFFFFFFF);
        bg.setCornerRadii(new float[]{48, 48, 48, 48, 0, 0, 0, 0});
        root.setBackground(bg);
        
        // 4. 顶部拖动把手（支持点击关闭）
        View handle = new View(ctx);
        LinearLayout.LayoutParams handleLp = new LinearLayout.LayoutParams(
                UikitScreenUtils.dp2px(ctx, 32), UikitScreenUtils.dp2px(ctx, 4));
        handleLp.gravity = Gravity.CENTER_HORIZONTAL;
        handleLp.bottomMargin = UikitScreenUtils.dp2px(ctx, 16);
        handle.setLayoutParams(handleLp);
        
        android.graphics.drawable.GradientDrawable hBg = new android.graphics.drawable.GradientDrawable();
        hBg.setCornerRadius(UikitScreenUtils.dp2px(ctx, 2));
        hBg.setColor(dark ? 0xFF555555 : 0xFFDDDDDD);
        handle.setBackground(hBg);
        
        // 点击把手直接关闭
        handle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { dismiss(); }
        });
        root.addView(handle);
        
        // 5. 标题
        if (title != null) {
            TextView tvTitle = new TextView(ctx);
            tvTitle.setText(title);
            tvTitle.setTextSize(18);
            tvTitle.setTextColor(dark ? 0xFFE0E0E0 : 0xFF1C1B1F);
            tvTitle.setGravity(Gravity.CENTER);
            tvTitle.setPadding(0, 0, 0, 16);
            root.addView(tvTitle);
        }
        
        // 6. 内容
        if (content != null) {
            root.addView(content);
        }
        
        setContentView(root);
        
        Window w = getWindow();
        if (w != null) {
            w.setBackgroundDrawableResource(android.R.color.transparent);
            w.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(w.getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            w.setAttributes(lp);
        }
    }
}
