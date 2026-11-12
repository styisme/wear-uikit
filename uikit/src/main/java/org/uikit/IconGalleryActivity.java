package org.uikit;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
        UikitUi.applyEntryAnimation(this);

        boolean dark = UikitTheme.isDarkMode(this);
        int density = (int) getResources().getDisplayMetrics().density;
        int bgColor = dark ? 0xFF000000 : 0xFFF4F6F8;

        getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(bgColor));

        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(bgColor);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(bgColor);
        scroll.addView(root);
        setContentView(scroll);

        UikitUi.applyRoundPadding(this, root);
        // 对 GridLayout 应用滚轮特效会导致所有图标排版错乱，因此这里不启用 setupWheelScroll

        TextView title = new TextView(this);
        title.setText("MD3 Icons");
        title.setTextSize(20);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(UikitColor.getAccentColor(this, dark));
        LinearLayout.LayoutParams titleLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleLp.topMargin = 16 * density;
        titleLp.bottomMargin = 16 * density;
        root.addView(title, titleLp);

        // 使用 GridLayout 展示图标，列数自适应
        GridLayout grid = new GridLayout(this);
        grid.setColumnCount(3); // 手表上 3 列比较合适
        grid.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        grid.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        root.addView(grid);

        List<String> iconNames = getIconNames();
        
        // 图标主色调（统一使用动态主题色，如果是深色模式则偏亮）
        int iconColor = UikitColor.getAccentColor(this, dark);

        for (final String name : iconNames) {
            int resId = getResources().getIdentifier(name, "drawable", getPackageName());
            if (resId == 0) continue;

            LinearLayout itemBox = new LinearLayout(this);
            itemBox.setOrientation(LinearLayout.VERTICAL);
            itemBox.setGravity(Gravity.CENTER);
            itemBox.setPadding(8, 16, 8, 16);

            ImageView iv = new ImageView(this);
            iv.setImageResource(resId);
            iv.setColorFilter(iconColor);
            int size = (int) (32 * density);
            LinearLayout.LayoutParams ivLp = new LinearLayout.LayoutParams(size, size);
            ivLp.gravity = Gravity.CENTER_HORIZONTAL;
            itemBox.addView(iv, ivLp);

            TextView tvName = new TextView(this);
            tvName.setText(name.replace("ic_uikit_", ""));
            tvName.setTextSize(10);
            tvName.setTextColor(dark ? 0xFF888888 : 0xFF999999);
            tvName.setGravity(Gravity.CENTER);
            tvName.setPadding(0, 8, 0, 0);
            itemBox.addView(tvName);

            // 点击测试动态变色与长按查看名字
            itemBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    UikitToast.show(IconGalleryActivity.this, "Tapped: " + name);
                }
            });
            itemBox.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    Toast.makeText(IconGalleryActivity.this, name, Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

            // 设置宽高权重，均分屏幕宽度
            GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
            lp.width = 0;
            lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            lp.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            lp.setMargins(4, 4, 4, 4);
            itemBox.setLayoutParams(lp);

            grid.addView(itemBox);
        }
    }

    private List<String> getIconNames() {
        List<String> list = new ArrayList<>();
        list.add("ic_uikit_home");
        list.add("ic_uikit_settings");
        list.add("ic_uikit_search");
        list.add("ic_uikit_delete");
        list.add("ic_uikit_edit");
        list.add("ic_uikit_add");
        list.add("ic_uikit_close");
        list.add("ic_uikit_check");
        list.add("ic_uikit_arrow_back");
        list.add("ic_uikit_arrow_forward");
        list.add("ic_uikit_play");
        list.add("ic_uikit_pause");
        list.add("ic_uikit_stop");
        list.add("ic_uikit_next");
        list.add("ic_uikit_prev");
        list.add("ic_uikit_volume_up");
        list.add("ic_uikit_volume_down");
        list.add("ic_uikit_music");
        list.add("ic_uikit_wifi");
        list.add("ic_uikit_bluetooth");
        list.add("ic_uikit_battery");
        list.add("ic_uikit_message");
        list.add("ic_uikit_email");
        list.add("ic_uikit_notifications");
        list.add("ic_uikit_alarm");
        list.add("ic_uikit_lock");
        list.add("ic_uikit_unlock");
        list.add("ic_uikit_heart");
        list.add("ic_uikit_star");
        list.add("ic_uikit_cloud_download");
        list.add("ic_uikit_cloud_upload");
        list.add("ic_uikit_folder");
        list.add("ic_uikit_image");
        list.add("ic_uikit_time");
        list.add("ic_uikit_menu");
        list.add("ic_uikit_more_vert");
        return list;
    }

    @Override
    public boolean dispatchTouchEvent(android.view.MotionEvent ev) {
        if (UikitUi.handleSwipeBack(this, ev)) return true;
        return super.dispatchTouchEvent(ev);
    }
}
