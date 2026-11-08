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
        title.setText("Uikit API 测试大厅");
        title.setTextSize(20);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(UikitColor.getAccentColor(this, dark));
        LinearLayout.LayoutParams titleLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleLp.topMargin = UikitScreenUtils.dp2px(this, 16);
        titleLp.bottomMargin = UikitScreenUtils.dp2px(this, 16);
        title.setLayoutParams(titleLp);
        root.addView(title);

        // ================= 1. 新增组件测试 =================
        addCodeTip(root, "UikitListItem (带开关)");
        UikitListItem listItem1 = new UikitListItem(this, "Wi-Fi 传输", "允许局域网发现");
        listItem1.setSwitch(true, new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton b, boolean c) {}
        });
        addViewWithMargin(root, listItem1);

        addCodeTip(root, "UikitListItem (带箭头)");
        UikitListItem listItem2 = new UikitListItem(this, "关于设备", "查看版本与存储");
        listItem2.setArrow();
        listItem2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UikitToast.show(DemoActivity.this, "点击了列表");
            }
        });
        addViewWithMargin(root, listItem2);

        addCodeTip(root, "UikitProgressBar");
        UikitProgressBar progressBar = new UikitProgressBar(this);
        progressBar.setProgress(0.7f);
        LinearLayout.LayoutParams pbLp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, UikitScreenUtils.dp2px(this, 12));
        pbLp.leftMargin = UikitScreenUtils.dp2px(this, 16);
        pbLp.rightMargin = UikitScreenUtils.dp2px(this, 16);
        pbLp.bottomMargin = UikitScreenUtils.dp2px(this, 16);
        progressBar.setLayoutParams(pbLp);
        root.addView(progressBar);

        // ================= 2. 弹窗与对话框测试 =================
        addCodeTip(root, "UikitUi.showInput()");
        addBtn(root, "Show Input", new View.OnClickListener() {
            public void onClick(View v) {
                UikitUi.showInput(DemoActivity.this, "Input", "", new UikitUi.OnInputListener() {
                    public void onInput(String s) {
                        UikitUi.showConfirm(DemoActivity.this, "Echo", s, null).show();
                    }
                }).show();
            }
        });

        addCodeTip(root, "UikitUi.showList()");
        addBtn(root, "Show List", new View.OnClickListener() {
            public void onClick(View v) {
                UikitUi.showList(DemoActivity.this, "Choose", new String[]{"One","Two","Three"},
                    new UikitUi.OnItemSelectListener() {
                        public void onItemSelect(int p) {
                            UikitUi.showConfirm(DemoActivity.this, "Picked", "You chose #" + (p+1), null).show();
                        }
                    }).show();
            }
        });

        addCodeTip(root, "new UikitBottomSheet().show()");
        addBtn(root, "Show Bottom Sheet", new View.OnClickListener() {
            public void onClick(View v) {
                UikitEmptyView empty = new UikitEmptyView(DemoActivity.this);
                new UikitBottomSheet(DemoActivity.this, "Options", empty).show();
            }
        });

        // ================= 3. 硬件与跳转测试 =================
        addCodeTip(root, "WearHardwareUtils.vibrate()");
        addBtn(root, "Vibrate", new View.OnClickListener() {
            public void onClick(View v) {
                WearHardwareUtils.vibrate(DemoActivity.this, WearHardwareUtils.HAPTIC_SUCCESS);
                UikitToast.show(DemoActivity.this, "Vibrate");
            }
        });

        addCodeTip(root, "startActivity(Intent)");
        addBtn(root, "Open Sub Menu", new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(DemoActivity.this, SubMenuActivity.class));
            }
        });
    }

    private void addCodeTip(LinearLayout root, String code) {
        TextView tv = new TextView(this);
        tv.setText(code);
        tv.setTextSize(11);
        tv.setTextColor(0xFF888888);
        tv.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.topMargin = UikitScreenUtils.dp2px(this, 16);
        lp.bottomMargin = UikitScreenUtils.dp2px(this, 4);
        tv.setLayoutParams(lp);
        root.addView(tv);
    }

    private void addViewWithMargin(LinearLayout root, View view) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = UikitScreenUtils.dp2px(this, 16);
        lp.rightMargin = UikitScreenUtils.dp2px(this, 16);
        view.setLayoutParams(lp);
        root.addView(view);
    }

    private void addBtn(LinearLayout root, String label, View.OnClickListener l) {
        UikitButton b = new UikitButton(this);
        b.setText(label);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, UikitScreenUtils.dp2px(this, 48));
        lp.leftMargin = UikitScreenUtils.dp2px(this, 16);
        lp.rightMargin = UikitScreenUtils.dp2px(this, 16);
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
