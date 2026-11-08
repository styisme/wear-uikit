package org.uikit.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.uikit.UikitColor;
import org.uikit.UikitConfig;
import org.uikit.UikitSwitch;
import org.uikit.UikitTheme;
import org.uikit.UikitUi;
import org.uikit.WearHardwareUtils;

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

        // 1. 应用圆盘边距
        UikitUi.applyRoundPadding(this, root);
        // 2. 开启 3D 滚轮特效
        UikitUi.setupWheelScroll(scroll, root);

        boolean dark = UikitTheme.isDarkMode(this);
        root.setBackgroundColor(dark ? 0xFF000000 : 0xFFF4F6F8);

        TextView title = new TextView(this);
        title.setText("Uikit Demo");
        title.setTextSize(22);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(UikitColor.getAccentColor(this, dark));
        // 给标题加点上下边距
        LinearLayout.LayoutParams titleLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleLp.topMargin = dp(16);
        titleLp.bottomMargin = dp(16);
        title.setLayoutParams(titleLp);
        root.addView(title);

        addSwitch(root, "Dark Mode", dark, new OnChecked() {
            public void onChecked(boolean v) {
                UikitConfig.setThemeMode(DemoActivity.this, v ? UikitConfig.THEME_DARK : UikitConfig.THEME_LIGHT);
                recreate();
            }
        });
        
        addSwitch(root, "Dynamic Color (API31+)", UikitConfig.isDynamicColorEnabled(this), new OnChecked() {
            public void onChecked(boolean v) {
                UikitConfig.setDynamicColorEnabled(DemoActivity.this, v);
                recreate();
            }
        });
        
        addSwitch(root, "Low Performance", UikitConfig.isLowPerf(this), new OnChecked() {
            public void onChecked(boolean v) {
                UikitConfig.setLowPerf(DemoActivity.this, v);
            }
        });

        addSwitch(root, "Force Round Screen", UikitConfig.getScreenShape(this) == UikitConfig.SHAPE_ROUND, new OnChecked() {
            public void onChecked(boolean v) {
                UikitConfig.setScreenShape(DemoActivity.this, v ? UikitConfig.SHAPE_ROUND : UikitConfig.SHAPE_AUTO);
                recreate();
            }
        });

        addButton(root, "Show List", new View.OnClickListener() {
            public void onClick(View v) {
                UikitUi.showList(DemoActivity.this, "Choose", new String[]{"One","Two","Three"},
                    new UikitUi.OnItemSelectListener() {
                        public void onItemSelect(int p) {
                            UikitUi.showConfirm(DemoActivity.this, "Picked", "You chose #" + (p+1), null).show();
                        }
                    }).show();
            }
        });
        
        addButton(root, "Show Input", new View.OnClickListener() {
            public void onClick(View v) {
                UikitUi.showInput(DemoActivity.this, "Input", "", new UikitUi.OnInputListener() {
                    public void onInput(String s) {
                        UikitUi.showConfirm(DemoActivity.this, "Echo", s, null).show();
                    }
                }).show();
            }
        });
        
        addButton(root, "Vibrate", new View.OnClickListener() {
            public void onClick(View v) {
                WearHardwareUtils.vibrate(DemoActivity.this, WearHardwareUtils.HAPTIC_SUCCESS);
                Toast.makeText(DemoActivity.this, "Vibrate", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private interface OnChecked { void onChecked(boolean v); }

    private void addSwitch(LinearLayout root, String label, boolean checked, final OnChecked cb) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(dp(16), dp(12), dp(16), dp(12));
        row.setBackground(UikitUi.getPillBackground(this));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.bottomMargin = dp(12);
        lp.leftMargin = dp(16);
        lp.rightMargin = dp(16);
        row.setLayoutParams(lp);
        
        TextView tv = new TextView(this);
        tv.setText(label);
        tv.setTextSize(16);
        tv.setTextColor(UikitTheme.isDarkMode(this) ? 0xFFE0E0E0 : 0xFF1C1B1F);
        tv.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        row.addView(tv);
        
        UikitSwitch sw = new UikitSwitch(this);
        sw.setLayoutParams(new LinearLayout.LayoutParams(dp(44), dp(26)));
        sw.setChecked(checked);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton b, boolean c) { cb.onChecked(c); }
        });
        row.addView(sw);
        root.addView(row);
    }

    private void addButton(LinearLayout root, String label, View.OnClickListener l) {
        Button b = new Button(this);
        b.setText(label);
        b.setAllCaps(false);
        b.setTextSize(16);
        b.setMinHeight(0);
        b.setPadding(0, 0, 0, 0);

        // 【修复】移除原生灰色方块背景，改为动态主题色胶囊按钮
        b.setBackground(null);
        android.graphics.drawable.GradientDrawable bg = new android.graphics.drawable.GradientDrawable();
        bg.setCornerRadius(dp(32));
        bg.setColor(UikitColor.getPrimaryColor(this)); // 跟随动态取色或默认绿
        b.setBackground(bg);
        b.setTextColor(UikitColor.getOnPrimaryColor(this)); // 自动计算白字或黑字

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, dp(48));
        lp.bottomMargin = dp(12);
        lp.leftMargin = dp(16);
        lp.rightMargin = dp(16);
        b.setLayoutParams(lp);
        b.setOnClickListener(l);
        root.addView(b);
    }

    private int dp(int v) { return (int)(v * getResources().getDisplayMetrics().density); }

    @Override
    public boolean dispatchTouchEvent(android.view.MotionEvent ev) {
        if (UikitUi.handleSwipeBack(this, ev)) return true;
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {
        if (UikitUi.isSystemBackBlocked(this)) {
            Toast.makeText(this, "Swipe to go back", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }
}
