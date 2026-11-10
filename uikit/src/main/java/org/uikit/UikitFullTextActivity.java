package org.uikit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class UikitFullTextActivity extends Activity {
    public static final String EXTRA_TITLE = "uikit_title";
    public static final String EXTRA_MESSAGE = "uikit_message";
    public static final String EXTRA_POSITIVE_TEXT = "uikit_positive_text";

    public static void show(Activity activity, String title, String message, String positiveText, int requestCode) {
        Intent intent = new Intent(activity, UikitFullTextActivity.class);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_POSITIVE_TEXT, positiveText);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(UikitUi.wrapScale(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        UikitTheme.applyTheme(this);
        UikitUi.applyEntryAnimation(this);

        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String message = getIntent().getStringExtra(EXTRA_MESSAGE);
        String posText = getIntent().getStringExtra(EXTRA_POSITIVE_TEXT);
        if (posText == null) posText = getString(android.R.string.ok);

        boolean dark = UikitTheme.isDarkMode(this);
        int density = (int) getResources().getDisplayMetrics().density;
        int bgColor = dark ? 0xFF000000 : 0xFFF4F6F8;
        
        // 【修复】设置 Window 默认背景色
        getWindow().setBackgroundDrawable(new ColorDrawable(bgColor));

        // 1. 铺满全屏的 ScrollView
        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(bgColor);
        // 关键：让内部容器铺满整个可视区域，确保首尾元素能滚动到绝对中心
        scroll.setFillViewport(true);
        scroll.setVerticalScrollBarEnabled(false);

        // 2. 滚轮容器 (所有元素放在这里)
        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setLayoutParams(new ScrollView.LayoutParams(
                ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.WRAP_CONTENT));
        // 应用圆屏左右避让
        UikitUi.applyRoundPadding(this, container);
        scroll.addView(container);
        setContentView(scroll);

        // ================= 第一部分：标题 =================
        TextView tvTitle = new TextView(this);
        tvTitle.setText(title);
        tvTitle.setTextSize(20);
        tvTitle.setGravity(Gravity.CENTER);
        tvTitle.setTextColor(UikitColor.getAccentColor(this, dark));
        LinearLayout.LayoutParams titleLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleLp.bottomMargin = 16 * density;
        tvTitle.setLayoutParams(titleLp);
        container.addView(tvTitle);

        // ================= 第二部分：拆分后的文本卡片 =================
        String[] sentences = message.split("(?<=。)|(?<=！)|(?<=？)|(?<=\\.)|(?<=\\n)");
        for (String sentence : sentences) {
            if (sentence.trim().isEmpty()) continue;
            
            TextView tvSentence = new TextView(this);
            tvSentence.setText(sentence.trim());
            tvSentence.setTextSize(16);
            tvSentence.setTextColor(dark ? 0xFFE0E0E0 : 0xFF1C1B1F);
            tvSentence.setGravity(Gravity.CENTER);
            tvSentence.setLineSpacing(0, 1.3f);
            tvSentence.setPadding(16 * density, 16 * density, 16 * density, 16 * density);
            tvSentence.setBackground(UikitUi.getPillBackground(this));
            
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.bottomMargin = 12 * density;
            tvSentence.setLayoutParams(lp);
            
            container.addView(tvSentence);
        }

        // ================= 第三部分：底部操作按钮 =================
        UikitButton btnOk = new UikitButton(this);
        btnOk.setText(posText);
        LinearLayout.LayoutParams btnLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 48 * density);
        btnLp.topMargin = 12 * density;
        btnLp.bottomMargin = 12 * density;
        btnOk.setLayoutParams(btnLp);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
            }
        });
        container.addView(btnOk);

        // ================= 核心：将整个 container 作为 3D 滚轮列表 =================
        // 内部会自动给 container 添加半个屏幕高度的上下 padding，
        // 保证默认情况下标题在中间，滑动到底部时按钮在中间。
        UikitUi.setupWheelScroll(scroll, container);
    }

    @Override
    public boolean dispatchTouchEvent(android.view.MotionEvent ev) {
        if (UikitUi.handleSwipeBack(this, ev)) return true;
        return super.dispatchTouchEvent(ev);
    }
}
