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

public class UikitFullListActivity extends Activity {
    public static final String EXTRA_TITLE = "uikit_title";
    public static final String EXTRA_ITEMS = "uikit_items";
    public static final String EXTRA_RESULT_INDEX = "uikit_result_index";
    public static final String EXTRA_RESULT_TEXT = "uikit_result_text";

    public static void show(Activity activity, String title, String[] items, int requestCode) {
        Intent intent = new Intent(activity, UikitFullListActivity.class);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_ITEMS, items);
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
        String[] items = getIntent().getStringArrayExtra(EXTRA_ITEMS);
        if (items == null) items = new String[0];

        ScrollView scroll = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        scroll.addView(root);
        setContentView(scroll);

        UikitUi.applyRoundPadding(this, root);
        UikitUi.setupWheelScroll(scroll, root);

        boolean dark = UikitTheme.isDarkMode(this);
        root.setBackgroundColor(dark ? 0xFF000000 : 0xFFF4F6F8);

        // 标题
        TextView tvTitle = new TextView(this);
        tvTitle.setText(title);
        tvTitle.setTextSize(20);
        tvTitle.setGravity(Gravity.CENTER);
        tvTitle.setTextColor(UikitColor.getAccentColor(this, dark));
        LinearLayout.LayoutParams titleLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleLp.topMargin = UikitScreenUtils.dp2px(this, 16);
        titleLp.bottomMargin = UikitScreenUtils.dp2px(this, 16);
        tvTitle.setLayoutParams(titleLp);
        root.addView(tvTitle);

        // 列表项
        for (int i = 0; i < items.length; i++) {
            final int index = i;
            TextView tv = new TextView(this);
            tv.setText(items[i]);
            tv.setTextSize(16);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(dark ? 0xFFE0E0E0 : 0xFF1C1B1F);
            tv.setPadding(UikitScreenUtils.dp2px(this, 16), UikitScreenUtils.dp2px(this, 20),
                    UikitScreenUtils.dp2px(this, 16), UikitScreenUtils.dp2px(this, 20));
            tv.setBackground(UikitUi.getPillBackground(this));

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.leftMargin = UikitScreenUtils.dp2px(this, 16);
            lp.rightMargin = UikitScreenUtils.dp2px(this, 16);
            lp.bottomMargin = UikitScreenUtils.dp2px(this, 12);
            tv.setLayoutParams(lp);

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent data = new Intent();
                    data.putExtra(EXTRA_RESULT_INDEX, index);
                    data.putExtra(EXTRA_RESULT_TEXT, items[index]);
                    setResult(RESULT_OK, data);
                    finish();
                    overridePendingTransition(0, 0);
                }
            });
            root.addView(tv);
        }
        
        // 底部留白防切边
        View bottomSpace = new View(this);
        bottomSpace.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, UikitScreenUtils.dp2px(this, 32)));
        root.addView(bottomSpace);
    }

    @Override
    public boolean dispatchTouchEvent(android.view.MotionEvent ev) {
        if (UikitUi.handleSwipeBack(this, ev)) return true;
        return super.dispatchTouchEvent(ev);
    }
}
