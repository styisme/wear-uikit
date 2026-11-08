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
        boolean dark = UikitTheme.isDarkMode(ctx);
        
        LinearLayout root = new LinearLayout(ctx);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(24, 24, 24, 48);
        
        android.graphics.drawable.GradientDrawable bg = new android.graphics.drawable.GradientDrawable();
        bg.setColor(dark ? 0xFF1E1E1E : 0xFFFFFFFF);
        bg.setCornerRadii(new float[]{48, 48, 48, 48, 0, 0, 0, 0});
        root.setBackground(bg);
        
        // 顶部拖动把手
        View handle = new View(ctx);
        handle.setBackgroundColor(dark ? 0xFF3E3E3E : 0xFFE0E0E0);
        LinearLayout.LayoutParams handleLp = new LinearLayout.LayoutParams(UikitScreenUtils.dp2px(ctx, 32), UikitScreenUtils.dp2px(ctx, 4));
        handleLp.gravity = Gravity.CENTER_HORIZONTAL;
        handleLp.bottomMargin = 16;
        handle.setLayoutParams(handleLp);
        handle.setBackgroundColor(dark ? 0xFF3E3E3E : 0xFFE0E0E0);
        android.graphics.drawable.GradientDrawable hBg = new android.graphics.drawable.GradientDrawable();
        hBg.setCornerRadius(4);
        hBg.setColor(dark ? 0xFF3E3E3E : 0xFFE0E0E0);
        handle.setBackground(hBg);
        root.addView(handle);
        
        if (title != null) {
            TextView tvTitle = new TextView(ctx);
            tvTitle.setText(title);
            tvTitle.setTextSize(18);
            tvTitle.setTextColor(dark ? 0xFFE0E0E0 : 0xFF1C1B1F);
            tvTitle.setGravity(Gravity.CENTER);
            tvTitle.setPadding(0, 0, 0, 16);
            root.addView(tvTitle);
        }
        
        if (content != null) root.addView(content);
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
