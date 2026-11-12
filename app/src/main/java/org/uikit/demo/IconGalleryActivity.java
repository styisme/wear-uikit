package org.uikit.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import org.uikit.UikitColor;
import org.uikit.UikitTheme;
import org.uikit.UikitToast;
import org.uikit.UikitUi;

public class IconGalleryActivity extends Activity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(UikitUi.wrapScale(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        UikitTheme.applyTheme(this);

        boolean dark = UikitTheme.isDarkMode(this);
        int bgColor = dark ? 0xFF000000 : 0xFFF4F6F8;

        getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(bgColor));

        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(bgColor);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(bgColor);
        scroll.addView(root);
        setContentView(scroll);

        // 【修复】应用圆屏边距与 3D 滚轮
        UikitUi.applyRoundPadding(this, root);
        UikitUi.setupWheelScroll(scroll, root);

        TextView title = new TextView(this);
        title.setText("MD3 Icons");
        title.setTextSize(20);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(UikitColor.getAccentColor(this, dark));
        LinearLayout.LayoutParams titleLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleLp.topMargin = 16 * (int)getResources().getDisplayMetrics().density;
        titleLp.bottomMargin = 16 * (int)getResources().getDisplayMetrics().density;
        root.addView(title, titleLp);

        GridLayout grid = new GridLayout(this);
        grid.setColumnCount(3);
        grid.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        LinearLayout.LayoutParams gridLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int density = (int) getResources().getDisplayMetrics().density;
        gridLp.leftMargin = 8 * density;
        gridLp.rightMargin = 8 * density;
        gridLp.bottomMargin = 24 * density;
        grid.setLayoutParams(gridLp);
        root.addView(grid);

        String[] iconNames = {
            "home", "settings", "search", "delete", "edit", "add", 
            "close", "check", "arrow_back", "arrow_forward", "play", "pause"
        };
        int iconColor = UikitColor.getAccentColor(this, dark);

        for (final String name : iconNames) {
            int resId = getResources().getIdentifier("ic_uikit_" + name, "drawable", getPackageName());
            if (resId == 0) continue;

            LinearLayout itemBox = new LinearLayout(this);
            itemBox.setOrientation(LinearLayout.VERTICAL);
            itemBox.setGravity(Gravity.CENTER);
            itemBox.setPadding(4, 16, 4, 16);

            ImageView iv = new ImageView(this);
            iv.setImageResource(resId);
            iv.setColorFilter(iconColor);
            int size = (int) (28 * density);
            LinearLayout.LayoutParams ivLp = new LinearLayout.LayoutParams(size, size);
            ivLp.gravity = Gravity.CENTER_HORIZONTAL;
            itemBox.addView(iv, ivLp);

            TextView tvName = new TextView(this);
            tvName.setText(name);
            tvName.setTextSize(10);
            tvName.setTextColor(dark ? 0xFF888888 : 0xFF999999);
            tvName.setGravity(Gravity.CENTER);
            tvName.setPadding(0, 8, 0, 0);
            itemBox.addView(tvName);

            itemBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    UikitToast.show(IconGalleryActivity.this, "Tapped: " + name);
                }
            });

            GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
            lp.width = 0;
            lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            lp.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            itemBox.setLayoutParams(lp);

            grid.addView(itemBox);
        }
    }

    @Override
    public boolean dispatchTouchEvent(android.view.MotionEvent ev) {
        if (UikitUi.handleSwipeBack(this, ev)) return true;
        return super.dispatchTouchEvent(ev);
    }
}
