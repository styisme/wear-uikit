package org.uikit.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import org.uikit.UikitTheme;
import org.uikit.UikitUi;
import org.uikit.UikitToast;

public class XmlDemoActivity extends Activity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(UikitUi.wrapScale(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 虽然 XML 里写死了黑底，但依然应用一下主题以支持圆屏
        UikitTheme.applyTheme(this);
        setContentView(R.layout.activity_xml_demo);

        // 为了适配圆屏，应用边距
        View root = findViewById(R.id.xml_root);
        UikitUi.applyRoundPadding(this, root);

        // 给里面的按钮加个点击事件，证明是活的
        LinearLayout container = (LinearLayout) root;
        for (int i = 0; i < container.getChildCount(); i++) {
            View v = container.getChildAt(i);
            if (v instanceof org.uikit.UikitButton) {
                v.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        UikitToast.show(XmlDemoActivity.this, "XML Button Clicked!");
                    }
                });
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(android.view.MotionEvent ev) {
        if (UikitUi.handleSwipeBack(this, ev)) return true;
        return super.dispatchTouchEvent(ev);
    }
}
