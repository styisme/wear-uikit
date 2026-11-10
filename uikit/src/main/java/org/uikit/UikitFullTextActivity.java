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
        root.setPadding(16 * density, 16 * density, 16 * density, 24 * density);
        
        scroll.addView(root);
        setContentView(scroll);

        // 应用圆屏边距，防止文字被切边
        UikitUi.applyRoundPadding(this, root);
        // 注：长文本页面不使用 setupWheelScroll 3D特效，因为超大的文本块被缩放透明会严重影响阅读

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

        // 2. 长文本卡片
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(16 * density, 16 * density, 16 * density, 16 * density);
        card.setBackground(UikitUi.getPillBackground(this));
        
        LinearLayout.LayoutParams cardLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        cardLp.bottomMargin = 24 * density;
        card.setLayoutParams(cardLp);

        TextView tvMessage = new TextView(this);
        tvMessage.setText(message);
        tvMessage.setTextSize(15);
        tvMessage.setTextColor(dark ? 0xFFE0E0E0 : 0xFF1C1B1F);
        tvMessage.setLineSpacing(0, 1.4f); // 增加行距，长文本阅读更舒适
        card.addView(tvMessage);
        root.addView(card);

        // 3. 确定按钮
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
