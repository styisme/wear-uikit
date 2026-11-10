package org.uikit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(bgColor);
        scroll.setFillViewport(true);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setLayoutParams(new ScrollView.LayoutParams(
                ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.WRAP_CONTENT));
        root.setPadding(16 * density, 16 * density, 16 * density, 16 * density);
        
        scroll.addView(root);
        setContentView(scroll);

        UikitUi.applyRoundPadding(this, root);

        // 1. 标题
        TextView tvTitle = new TextView(this);
        tvTitle.setText(title);
        tvTitle.setTextSize(20);
        tvTitle.setGravity(Gravity.CENTER);
        tvTitle.setTextColor(UikitColor.getAccentColor(this, dark));
        LinearLayout.LayoutParams titleLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleLp.bottomMargin = 16 * density;
        tvTitle.setLayoutParams(titleLp);
        root.addView(tvTitle);

        // 2. 文本容器（用于应用 3D 滚轮）
        LinearLayout textContainer = new LinearLayout(this);
        textContainer.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams containerLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        containerLp.bottomMargin = 24 * density;
        textContainer.setLayoutParams(containerLp);

        // 3. 按句号/问号/叹号/换行符拆分长文本
        // 正则解释：匹配任意标点后紧跟的位置，保留标点
        String[] sentences = message.split("(?<=。)|(?<=！)|(?<=？)|(?<=\\.)|(?<=\\n)");
        
        for (String sentence : sentences) {
            if (sentence.trim().isEmpty()) continue;
            
            TextView tvSentence = new TextView(this);
            tvSentence.setText(sentence.trim());
            tvSentence.setTextSize(16);
            tvSentence.setTextColor(dark ? 0xFFE0E0E0 : 0xFF1C1B1F);
            tvSentence.setGravity(Gravity.CENTER);
            tvSentence.setLineSpacing(0, 1.2f);
            tvSentence.setPadding(16, 16, 16, 16);
            // 给每句话加个卡片背景，看起来像独立的字幕块
            tvSentence.setBackground(UikitUi.getPillBackground(this));
            
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.bottomMargin = 12 * density;
            tvSentence.setLayoutParams(lp);
            
            textContainer.addView(tvSentence);
        }

        root.addView(textContainer);

        // 【核心修复】应用 3D 滚轮特效（针对拆分后的 textContainer 生效）
        UikitUi.setupWheelScroll(scroll, textContainer);

        // 4. 确定按钮 (放在容器外，确保滑动时按钮不会跟着变小)
        UikitButton btnOk = new UikitButton(this);
        btnOk.setText(posText);
        LinearLayout.LayoutParams btnLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 48 * density);
        btnOk.setLayoutParams(btnLp);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
            }
        });
        root.addView(btnOk);
    }

    @Override
    public boolean dispatchTouchEvent(android.view.MotionEvent ev) {
        if (UikitUi.handleSwipeBack(this, ev)) return true;
        return super.dispatchTouchEvent(ev);
    }
}
