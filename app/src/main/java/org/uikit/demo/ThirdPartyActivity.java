package org.uikit.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.uikit.UikitColor;
import org.uikit.UikitScreenUtils;
import org.uikit.UikitTheme;
import org.uikit.UikitUi;

import java.io.IOException;
import java.io.InputStream;

public class ThirdPartyActivity extends Activity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(UikitUi.wrapScale(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UikitTheme.applyTheme(this);
        UikitUi.applyEntryAnimation(this);

        boolean dark = UikitTheme.isDarkMode(this);
        int bgColor = dark ? 0xFF000000 : 0xFFF4F6F8;
        int density = (int) getResources().getDisplayMetrics().density;

        getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(bgColor));

        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(bgColor);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(16 * density, 16 * density, 16 * density, 16 * density);
        root.setBackgroundColor(bgColor);
        scroll.addView(root);
        setContentView(scroll);

        UikitUi.applyRoundPadding(this, root);

        TextView title = new TextView(this);
        title.setText("Third-Party Resources");
        title.setTextSize(20);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(UikitColor.getAccentColor(this, dark));
        LinearLayout.LayoutParams titleLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleLp.bottomMargin = 24 * density;
        root.addView(title, titleLp);

        // ================= 1. PNG 图片加载 (原生) =================
        addCodeTip(root, "PNG Image (Native BitmapFactory)");
        ImageView pngImageView = new ImageView(this);
        LinearLayout.LayoutParams imgLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 150 * density);
        imgLp.bottomMargin = 24 * density;
        pngImageView.setLayoutParams(imgLp);
        pngImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        
        try {
            InputStream is = getAssets().open("demo_image.png");
            Bitmap bmp = BitmapFactory.decodeStream(is);
            is.close();
            if (bmp != null) {
                pngImageView.setImageBitmap(bmp);
            } else {
                pngImageView.setImageResource(android.R.drawable.ic_menu_gallery);
            }
        } catch (IOException e) {
            pngImageView.setImageResource(android.R.drawable.ic_menu_gallery);
        }
        root.addView(pngImageView);

        // ================= 2. SVG 图片加载 (WebView 渲染) =================
        addCodeTip(root, "SVG Image (via WebView)");
        try {
            WebView webView = new WebView(this);
            LinearLayout.LayoutParams webLp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 150 * density);
            webLp.bottomMargin = 24 * density;
            webView.setLayoutParams(webLp);
            // 设置透明背景，完美融入宿主环境
            webView.setBackgroundColor(Color.TRANSPARENT);
            // 缩放适配
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            // 加载 assets 里的 SVG 文件
            webView.loadUrl("file:///android_asset/demo_image.svg");
            root.addView(webView);
        } catch (Throwable t) {
            // 如果设备没有 WebView（部分极简系统），显示占位图
            ImageView svgFallback = new ImageView(this);
            LinearLayout.LayoutParams svgLp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 120 * density);
            svgLp.bottomMargin = 24 * density;
            svgFallback.setLayoutParams(svgLp);
            svgFallback.setImageResource(android.R.drawable.ic_menu_camera);
            root.addView(svgFallback);
        }

        // ================= 3. 原生按钮跳转 =================
        addCodeTip(root, "Android Native Button (Intent)");
        Button nativeBtn = new Button(this);
        nativeBtn.setText("Visit GitHub Repo");
        nativeBtn.setAllCaps(false);
        LinearLayout.LayoutParams btnLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 56 * density);
        btnLp.bottomMargin = 24 * density;
        nativeBtn.setLayoutParams(btnLp);
        nativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/styisme/wear-uikit"));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(ThirdPartyActivity.this, "Cannot open browser", Toast.LENGTH_SHORT).show();
                }
            }
        });
        root.addView(nativeBtn);
    }

    private void addCodeTip(LinearLayout root, String code) {
        TextView tv = new TextView(this);
        tv.setText(code);
        tv.setTextSize(11);
        tv.setTextColor(0xFF888888);
        tv.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.bottomMargin = UikitScreenUtils.dp2px(this, 4);
        tv.setLayoutParams(lp);
        root.addView(tv);
    }

    @Override
    public boolean dispatchTouchEvent(android.view.MotionEvent ev) {
        if (UikitUi.handleSwipeBack(this, ev)) return true;
        return super.dispatchTouchEvent(ev);
    }
}
