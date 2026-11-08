package org.uikit.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.uikit.UikitColor;
import org.uikit.UikitSwitch;
import org.uikit.UikitTheme;
import org.uikit.UikitUi;

public class SubMenuActivity extends Activity {

    @Override
    protected void attachBaseContext(android.content.Context newBase) {
        super.attachBaseContext(UikitUi.wrapScale(newBase));
    }

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        UikitTheme.applyTheme(this);
        UikitUi.applyEntryAnimation(this); // 测试进入动画

        ScrollView scroll = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        scroll.addView(root);
        setContentView(scroll);

        UikitUi.applyRoundPadding(this, root);
        UikitUi.setupWheelScroll(scroll, root); // 测试二级页面的 3D 滚轮

        boolean dark = UikitTheme.isDarkMode(this);
        root.setBackgroundColor(dark ? 0xFF000000 : 0xFFF4F6F8);

        TextView title = new TextView(this);
        title.setText("Sub Menu");
        title.setTextSize(22);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(UikitColor.getAccentColor(this, dark));
        LinearLayout.LayoutParams titleLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleLp.topMargin = dp(16);
        titleLp.bottomMargin = dp(16);
        title.setLayoutParams(titleLp);
        root.addView(title);

        // 模拟列表项（长列表测试滚轮）
        for (int i = 1; i <= 8; i++) {
            addListItem(root, "Sub Item " + i);
        }

        // 底部返回按钮
        Button btnBack = new Button(this);
        btnBack.setText("Go Back");
        btnBack.setAllCaps(false);
        btnBack.setTextSize(16);
        btnBack.setMinHeight(0);
        btnBack.setPadding(0, 0, 0, 0);
        btnBack.setBackground(null);
        
        android.graphics.drawable.GradientDrawable bg = new android.graphics.drawable.GradientDrawable();
        bg.setCornerRadius(dp(32));
        bg.setColor(UikitColor.getPrimaryColor(this));
        btnBack.setBackground(bg);
        btnBack.setTextColor(UikitColor.getOnPrimaryColor(this));

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, dp(48));
        lp.topMargin = dp(16);
        lp.bottomMargin = dp(32);
        lp.leftMargin = dp(16);
        lp.rightMargin = dp(16);
        btnBack.setLayoutParams(lp);
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0); // 取消退出动画白屏
            }
        });
        root.addView(btnBack);
    }

    private void addListItem(LinearLayout root, String text) {
        boolean dark = UikitTheme.isDarkMode(this);
        LinearLayout row = new LinearLayout(this);
        row.setGravity(Gravity.CENTER);
        row.setPadding(dp(16), dp(20), dp(16), dp(20));
        row.setBackground(UikitUi.getPillBackground(this));
        
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.bottomMargin = dp(12);
        lp.leftMargin = dp(16);
        lp.rightMargin = dp(16);
        row.setLayoutParams(lp);

        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextSize(16);
        tv.setTextColor(dark ? 0xFFE0E0E0 : 0xFF1C1B1F);
        row.addView(tv);
        
        root.addView(row);
    }

    private int dp(int v) { return (int)(v * getResources().getDisplayMetrics().density); }

    @Override
    public boolean dispatchTouchEvent(android.view.MotionEvent ev) {
        // 测试滑动返回
        if (UikitUi.handleSwipeBack(this, ev)) return true;
        return super.dispatchTouchEvent(ev);
    }
}
