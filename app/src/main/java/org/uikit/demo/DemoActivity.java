package org.uikit.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.CompoundButton;

import org.uikit.*;

import java.util.ArrayList;
import java.util.Locale;

public class DemoActivity extends Activity {

    private final String[] colorNames = {"Default Green", "Ocean Blue", "Sunset Red", "Deep Purple", "Cyber Yellow"};
    private final int[] colorValues = {
            0, 0xFF1976D2, 0xFFD32F2F, 0xFF7B1FA2, 0xFFFBC02D
    };

    @Override
    protected void attachBaseContext(Context newBase) {
        String lang = UikitConfig.getAppLang(newBase);
        if (!"system".equals(lang)) {
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.setLocale(locale);
            newBase = newBase.createConfigurationContext(config);
        }
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
        title.setText(R.string.title_main);
        title.setTextSize(20);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(UikitColor.getAccentColor(this, dark));
        LinearLayout.LayoutParams titleLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleLp.topMargin = UikitScreenUtils.dp2px(this, 16);
        titleLp.bottomMargin = UikitScreenUtils.dp2px(this, 16);
        title.setLayoutParams(titleLp);
        root.addView(title);

        // ================= 1. 基础组件区 =================
        addCodeTip(root, getString(R.string.api_list_switch));
        UikitListItem listItem1 = new UikitListItem(this, getString(R.string.label_wifi), getString(R.string.sub_wifi));
        listItem1.setSwitch(true, new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton b, boolean c) {}
        });
        addViewWithMargin(root, listItem1);

        addCodeTip(root, getString(R.string.api_list_arrow));
        UikitListItem listItem2 = new UikitListItem(this, getString(R.string.label_device), getString(R.string.sub_device));
        listItem2.setArrow();
        listItem2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UikitToast.show(DemoActivity.this, getString(R.string.toast_clicked));
            }
        });
        addViewWithMargin(root, listItem2);

        addCodeTip(root, getString(R.string.api_progress_bar));
        UikitProgressBar progressBar = new UikitProgressBar(this);
        progressBar.setProgress(0.7f);
        LinearLayout.LayoutParams pbLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UikitScreenUtils.dp2px(this, 12));
        pbLp.leftMargin = UikitScreenUtils.dp2px(this, 16);
        pbLp.rightMargin = UikitScreenUtils.dp2px(this, 16);
        pbLp.bottomMargin = UikitScreenUtils.dp2px(this, 16);
        progressBar.setLayoutParams(pbLp);
        root.addView(progressBar);

        addCodeTip(root, getString(R.string.api_progress_ring));
        UikitProgressRing ring = new UikitProgressRing(this);
        LinearLayout.LayoutParams ringLp = new LinearLayout.LayoutParams(UikitScreenUtils.dp2px(this, 60), UikitScreenUtils.dp2px(this, 60));
        ringLp.gravity = Gravity.CENTER_HORIZONTAL;
        ringLp.bottomMargin = UikitScreenUtils.dp2px(this, 16);
        ring.setLayoutParams(ringLp);
        ring.setProgress(0.45f);
        root.addView(ring);

        addCodeTip(root, getString(R.string.api_slider));
        UikitSlider slider = new UikitSlider(this);
        slider.setMaxProgress(100);
        slider.setProgress(30);
        LinearLayout.LayoutParams sliderLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UikitScreenUtils.dp2px(this, 48));
        sliderLp.leftMargin = UikitScreenUtils.dp2px(this, 16);
        sliderLp.rightMargin = UikitScreenUtils.dp2px(this, 16);
        slider.setLayoutParams(sliderLp);
        root.addView(slider);

        addCodeTip(root, getString(R.string.api_loading_bar));
        UikitLoadingBar loadingBar = new UikitLoadingBar(this);
        LinearLayout.LayoutParams loadingLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UikitScreenUtils.dp2px(this, 12));
        loadingLp.leftMargin = UikitScreenUtils.dp2px(this, 16);
        loadingLp.rightMargin = UikitScreenUtils.dp2px(this, 16);
        loadingLp.bottomMargin = UikitScreenUtils.dp2px(this, 16);
        loadingBar.setLayoutParams(loadingLp);
        root.addView(loadingBar);

        addCodeTip(root, getString(R.string.api_skeleton));
        UikitSkeleton skeleton = new UikitSkeleton(this);
        LinearLayout.LayoutParams skLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UikitScreenUtils.dp2px(this, 40));
        skLp.leftMargin = UikitScreenUtils.dp2px(this, 16);
        skLp.rightMargin = UikitScreenUtils.dp2px(this, 16);
        skLp.bottomMargin = UikitScreenUtils.dp2px(this, 16);
        skeleton.setLayoutParams(skLp);
        root.addView(skeleton);

        // ================= 2. 全屏组件区 =================
        addCodeTip(root, getString(R.string.api_full_list));
        addBtn(root, getString(R.string.btn_full_list), new View.OnClickListener() {
            public void onClick(View v) {
                ArrayList<UikitListItemModel> items = new ArrayList<>();
                items.add(new UikitListItemModel(getString(R.string.item_normal), getString(R.string.item_normal_sub)));
                items.add(new UikitListItemModel(getString(R.string.item_success), getString(R.string.item_success_sub), 0xFF4CAF50));
                items.add(new UikitListItemModel(getString(R.string.item_custom1), getString(R.string.item_custom1_sub), 0, 0xFFF44336));
                items.add(new UikitListItemModel(getString(R.string.item_custom2), getString(R.string.item_custom2_sub), 0, 0xFF2196F3));
                items.add(new UikitListItemModel(getString(R.string.item_all_custom), getString(R.string.item_all_custom_sub), 0xFF388E3C, 0xFFFFEB3B));
                UikitFullListActivity.show(DemoActivity.this, getString(R.string.list_advanced_title), items, 1001);
            }
        });

        addCodeTip(root, getString(R.string.api_full_input));
        addBtn(root, getString(R.string.btn_full_input), new View.OnClickListener() {
            public void onClick(View v) {
                UikitFullInputActivity.show(DemoActivity.this, getString(R.string.full_input_title), "", getString(R.string.full_input_hint), "提交", "放弃", 1002);
            }
        });

        addCodeTip(root, getString(R.string.api_full_text));
        addBtn(root, getString(R.string.btn_full_text), new View.OnClickListener() {
            public void onClick(View v) {
                UikitFullTextActivity.show(DemoActivity.this, getString(R.string.full_text_title), getString(R.string.long_text), "知道了", true, 1003);
            }
        });

        addCodeTip(root, getString(R.string.api_picker));
        addBtn(root, getString(R.string.btn_picker), new View.OnClickListener() {
            public void onClick(View v) {
                UikitPickerActivity.show(DemoActivity.this, getString(R.string.picker_title), new String[]{"Option A", "Option B", "Option C"}, 1005);
            }
        });

        // ================= 3. 对话框区 =================
        addCodeTip(root, getString(R.string.api_show_input));
        addBtn(root, getString(R.string.btn_input), new View.OnClickListener() {
            public void onClick(View v) {
                UikitToast.show(DemoActivity.this, getString(R.string.toast_deprecated));
                UikitUi.showInput(DemoActivity.this, getString(R.string.dlg_input_title), "", new UikitUi.OnInputListener() {
                    public void onInput(String s) {
                        UikitUi.showConfirm(DemoActivity.this, getString(R.string.dlg_echo_title), s, null).show();
                    }
                }).show();
            }
        });

        addCodeTip(root, getString(R.string.api_show_list));
        addBtn(root, getString(R.string.btn_list), new View.OnClickListener() {
            public void onClick(View v) {
                UikitToast.show(DemoActivity.this, getString(R.string.toast_deprecated));
                UikitUi.showList(DemoActivity.this, "Legacy List", new String[]{"One", "Two", "Three"},
                    new UikitUi.OnItemSelectListener() {
                        public void onItemSelect(int p) {
                            UikitToast.show(DemoActivity.this, "Picked: " + p);
                        }
                    }).show();
            }
        });

        addCodeTip(root, getString(R.string.api_show_confirm));
        addBtn(root, getString(R.string.btn_confirm), new View.OnClickListener() {
            public void onClick(View v) {
                UikitToast.show(DemoActivity.this, getString(R.string.toast_deprecated));
                UikitUi.showConfirm(DemoActivity.this, "Legacy Confirm", "This is a legacy dialog.", null).show();
            }
        });

        addCodeTip(root, getString(R.string.api_bottom_sheet));
        addBtn(root, getString(R.string.btn_sheet), new View.OnClickListener() {
            public void onClick(View v) {
                UikitEmptyView empty = new UikitEmptyView(DemoActivity.this);
                new UikitBottomSheet(DemoActivity.this, getString(R.string.sheet_options_title), empty).show();
            }
        });

        addCodeTip(root, getString(R.string.api_toast));
        addBtn(root, getString(R.string.btn_toast), new View.OnClickListener() {
            public void onClick(View v) {
                UikitToast.show(DemoActivity.this, getString(R.string.toast_clicked));
            }
        });

        // ================= 4. 色彩与震动 =================
        addCodeTip(root, getString(R.string.api_change_color));
        addBtn(root, getString(R.string.btn_color), new View.OnClickListener() {
            public void onClick(View v) {
                ArrayList<UikitListItemModel> colorItems = new ArrayList<>();
                colorItems.add(new UikitListItemModel("Default Green"));
                colorItems.add(new UikitListItemModel("Ocean Blue"));
                colorItems.add(new UikitListItemModel("Sunset Red"));
                colorItems.add(new UikitListItemModel("Deep Purple"));
                colorItems.add(new UikitListItemModel("Cyber Yellow"));
                UikitFullListActivity.show(DemoActivity.this, getString(R.string.dlg_pick_color), colorItems, 1004);
            }
        });

        addCodeTip(root, getString(R.string.api_vibrate));
        addBtn(root, getString(R.string.btn_vibrate), new View.OnClickListener() {
            public void onClick(View v) {
                WearHardwareUtils.vibrate(DemoActivity.this, WearHardwareUtils.HAPTIC_SUCCESS);
                UikitToast.show(DemoActivity.this, getString(R.string.toast_vibrate));
            }
        });

        // ================= 5. 新增终极组件 =================
        addCodeTip(root, getString(R.string.api_tab_badge_divider));
        UikitTabView tabView = new UikitTabView(this, new String[]{"Tab 1", "Tab 2"});
        LinearLayout.LayoutParams tabLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tabLp.leftMargin = UikitScreenUtils.dp2px(this, 16);
        tabLp.rightMargin = UikitScreenUtils.dp2px(this, 16);
        tabLp.bottomMargin = UikitScreenUtils.dp2px(this, 8);
        tabView.setLayoutParams(tabLp);
        root.addView(tabView);

        UikitListItem listItemWithBadge = new UikitListItem(this, getString(R.string.label_unread), getString(R.string.sub_unread));
        UikitBadge badge = new UikitBadge(this);
        badge.setBadge("99+");
        LinearLayout.LayoutParams badgeLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        badgeLp.rightMargin = UikitScreenUtils.dp2px(this, 16);
        badgeLp.gravity = Gravity.CENTER_VERTICAL;
        badge.setLayoutParams(badgeLp);
        listItemWithBadge.addView(badge);
        addViewWithMargin(root, listItemWithBadge);
        root.addView(new UikitDivider(this));

        addCodeTip(root, getString(R.string.api_loading_view));
        UikitLoadingView loadingView = new UikitLoadingView(this);
        LinearLayout.LayoutParams loadLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UikitScreenUtils.dp2px(this, 40));
        loadingView.setLayoutParams(loadLp);
        root.addView(loadingView);

        addCodeTip(root, getString(R.string.api_full_confirm));
        addBtn(root, getString(R.string.btn_full_confirm), new View.OnClickListener() {
            public void onClick(View v) {
                if (UikitClickUtils.isFastDoubleClick()) return;
                UikitFullConfirmActivity.show(DemoActivity.this, getString(R.string.dlg_confirm_title), getString(R.string.dlg_confirm_msg), 2001);
            }
        });

        addCodeTip(root, getString(R.string.api_image_viewer));
        addBtn(root, getString(R.string.btn_image_viewer), new View.OnClickListener() {
            public void onClick(View v) {
                if (UikitClickUtils.isFastDoubleClick()) return;
                UikitImageViewerActivity.show(DemoActivity.this, "demo_image.png");
            }
        });

        addCodeTip(root, getString(R.string.api_date_picker));
        addBtn(root, getString(R.string.btn_date_picker), new View.OnClickListener() {
            public void onClick(View v) {
                if (UikitClickUtils.isFastDoubleClick()) return;
                UikitDatePickerActivity.show(DemoActivity.this, 2003);
            }
        });

        addCodeTip(root, getString(R.string.api_third_party));
        addBtn(root, getString(R.string.btn_third_party), new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(DemoActivity.this, ThirdPartyActivity.class));
            }
        });

        // ================= 6. 设置开关区 =================
        addCodeTip(root, "XML 属性演示");
        addBtn(root, "测试 XML 布局", new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(DemoActivity.this, XmlDemoActivity.class));
            }
        });

        addCodeTip(root, getString(R.string.api_switch));
        addSwitch(root, getString(R.string.sw_dark), dark, new OnChecked() {
            public void onChecked(boolean v) {
                UikitConfig.setThemeMode(DemoActivity.this, v ? UikitConfig.THEME_DARK : UikitConfig.THEME_LIGHT);
                recreate();
            }
        });

        addSwitch(root, getString(R.string.sw_dynamic), UikitConfig.isDynamicColorEnabled(this), new OnChecked() {
            public void onChecked(boolean v) {
                UikitConfig.setDynamicColorEnabled(DemoActivity.this, v);
                recreate();
            }
        });

        addSwitch(root, getString(R.string.sw_round), UikitConfig.getScreenShape(this) == UikitConfig.SHAPE_ROUND, new OnChecked() {
            public void onChecked(boolean v) {
                UikitConfig.setScreenShape(DemoActivity.this, v ? UikitConfig.SHAPE_ROUND : UikitConfig.SHAPE_AUTO);
                recreate();
            }
        });

        addSwitch(root, getString(R.string.sw_lang), "zh".equals(UikitConfig.getAppLang(this)), new OnChecked() {
            public void onChecked(boolean v) {
                UikitConfig.setAppLang(DemoActivity.this, v ? "zh" : "en");
                recreate();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 1001) {
                String result = data.getStringExtra(UikitFullListActivity.EXTRA_RESULT_TEXT);
                UikitToast.show(this, getString(R.string.toast_selected, result));
            } else if (requestCode == 1002) {
                String result = data.getStringExtra(UikitFullInputActivity.EXTRA_RESULT_TEXT);
                UikitToast.show(this, getString(R.string.toast_selected, result));
            } else if (requestCode == 1004) {
                int index = data.getIntExtra(UikitFullListActivity.EXTRA_RESULT_INDEX, -1);
                if (index >= 0 && index < colorValues.length) {
                    if (colorValues[index] == 0) {
                        UikitConfig.clearCustomColors(this);
                    } else {
                        UikitConfig.setCustomPrimaryColor(this, colorValues[index]);
                        UikitConfig.setCustomAccentColor(this, UikitColorMixer.blend(colorValues[index], 0xFFFFFFFF, 0.3f));
                    }
                    recreate();
                }
            } else if (requestCode == 1005) {
                String result = data.getStringExtra(UikitPickerActivity.EXTRA_RESULT_TEXT);
                UikitToast.show(this, getString(R.string.toast_selected, result));
            } else if (requestCode == 2001) {
                UikitToast.show(this, getString(R.string.toast_confirmed));
            } else if (requestCode == 2003) {
                String y = data.getStringExtra(UikitDatePickerActivity.EXTRA_RESULT_YEAR);
                String m = data.getStringExtra(UikitDatePickerActivity.EXTRA_RESULT_MONTH);
                String d = data.getStringExtra(UikitDatePickerActivity.EXTRA_RESULT_DAY);
                UikitToast.show(this, y + "-" + m + "-" + d);
            }
        }
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

    private void addCodeTip(LinearLayout root, String code) {
        TextView tv = new TextView(this);
        tv.setText(code);
        tv.setTextSize(11);
        tv.setTextColor(code.contains("Deprecated") || code.contains("废弃") ? 0xFFFF5252 : 0xFF888888);
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
