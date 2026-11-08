package org.uikit.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.CompoundButton;

import org.uikit.*;

public class DemoActivity extends Activity {

    @Override
    protected void attachBaseContext(android.content.Context newBase) {
        super.attachBaseContext(UikitUi.wrapScale(newBase));
    }

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        UikitTheme.applyTheme(this);
        UikitUi.applyEntryAnimation(this);

        ScrollView scroll = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        scroll.addView(root);
        setContentView(scroll);

        UikitUi.applyRoundPadding(this, root);
        UikitUi.setupWheelScroll(scroll, root);

        boolean dark = UikitTheme.isDarkMode(this);
        root.setBackgroundColor(dark ? 0xFF000000 : 0xFFF4F6F8);

        TextView title = new TextView(this);
        title.setText("Uikit Features");
        title.setTextSize(22);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(UikitColor.getAccentColor(this, dark));
        LinearLayout.LayoutParams titleLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleLp.topMargin = UikitScreenUtils.dp2px(this, 16);
        titleLp.bottomMargin = UikitScreenUtils.dp2px(this, 16);
        title.setLayoutParams(titleLp);
        root.addView(title);

        // --- 1. 环形进度条 ---
        final UikitProgressRing ring = new UikitProgressRing(this);
        LinearLayout.LayoutParams ringLp = new LinearLayout.LayoutParams(
                UikitScreenUtils.dp2px(this, 80), UikitScreenUtils.dp2px(this, 80));
        ringLp.gravity = Gravity.CENTER_HORIZONTAL;
        ringLp.bottomMargin = UikitScreenUtils.dp2px(this, 16);
        ring.setLayoutParams(ringLp);
        root.addView(ring);
        
        ring.setProgress(0.65f);
        ring.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ring.setProgress((float) Math.random());
            }
        });

        // --- 2. 骨架屏 ---
        UikitSkeleton skeleton = new UikitSkeleton(this);
        LinearLayout.LayoutParams skLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, UikitScreenUtils.dp2px(this, 48));
        skLp.bottomMargin = UikitScreenUtils.dp2px(this, 16);
        skLp.leftMargin = UikitScreenUtils.dp2px(this, 16);
        skLp.rightMargin = UikitScreenUtils.dp2px(this, 16);
        skeleton.setLayoutParams(skLp);
        root.addView(skeleton);

        // --- 3. 工具按钮 ---
        addBtn(root, "Show Toast", new View.OnClickListener() {
            public void onClick(View v) {
                UikitToast.show(DemoActivity.this, "Hello Watch!");
            }
        });

        addBtn(root, "Show Bottom Sheet", new View.OnClickListener() {
            public void onClick(View v) {
                UikitEmptyView empty = new UikitEmptyView(DemoActivity.this);
                new UikitBottomSheet(DemoActivity.this, "Options", empty).show();
            }
        });

        addBtn(root, "Open Sub Menu", new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(DemoActivity.this, SubMenuActivity.class));
            }
        });

        // --- 4. 开关控制 ---
        addSwitch(root, "Force Round Screen", UikitConfig.getScreenShape(this) == UikitConfig.SHAPE_ROUND, new OnChecked() {
            public void onChecked(boolean v) {
                UikitConfig.setScreenShape(DemoActivity.this, v ? UikitConfig.SHAPE_ROUND : UikitConfig.SHAPE_AUTO);
                recreate();
            }
        });
        
        addSwitch(root, "Dark Mode", dark, new OnChecked() {
            public void onChecked(boolean v) {
                UikitConfig.setThemeMode(DemoActivity.this, v ? UikitConfig.THEME_DARK : UikitConfig.THEME_LIGHT);
                recreate();
            }
        });
    }

    private interface OnChecked { void onChecked(boolean v); }

    private void addSwitch(LinearLayout root, String label, boolean checked, final OnChecked cb) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(UikitScreenUtils.dp2px(this,16), UikitScreenUtils.dp2px(this,12), UikitScreenUtils.dp2px(this,16), UikitScreenUtils.dp2px(this,12));
        row.setBackground(UikitUi.getPillBackground(this));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.bottomMargin = UikitScreenUtils.dp2px(this,12);
        lp.leftMargin = UikitScreenUtils.dp2px(this,16);
        lp.rightMargin = UikitScreenUtils.dp2px(this,16);
        row.setLayoutParams(lp);
        
        TextView tv = new TextView(this);
        tv.setText(label);
        tv.setTextSize(16);
        tv.setTextColor(UikitTheme.isDarkMode(this) ? 0xFFE0E0E0 : 0xFF1C1B1F);
        tv.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        row.addView(tv);
        
        UikitSwitch sw = new UikitSwitch(this);
        sw.setLayoutParams(new LinearLayout.LayoutParams(UikitScreenUtils.dp2px(this,44), UikitScreenUtils.dp2px(this,26)));
        sw.setChecked(checked);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton b, boolean c) { cb.onChecked(c); }
        });
        row.addView(sw);
        root.addView(row);
    }

    private void addBtn(LinearLayout root, String label, View.OnClickListener l) {
        UikitButton b = new UikitButton(this);
        b.setText(label);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, UikitScreenUtils.dp2px(this,48));
        lp.bottomMargin = UikitScreenUtils.dp2px(this,12);
        lp.leftMargin = UikitScreenUtils.dp2px(this,16);
        lp.rightMargin = UikitScreenUtils.dp2px(this,16);
        b.setLayoutParams(lp);
        b.setOnClickListener(l);
        root.addView(b);
    }

    @Override
    public boolean dispatchTouchEvent(android.view.MotionEvent ev) {
        if (UikitUi.handleSwipeBack(this, ev)) return true;
        return super.dispatchTouchEvent(ev);
    }
}
