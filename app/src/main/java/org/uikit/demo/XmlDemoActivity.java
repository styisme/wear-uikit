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
        UikitTheme.applyTheme(this);
        setContentView(R.layout.activity_xml_demo);

        View root = findViewById(R.id.xml_root);
        // 【修复】应用圆屏边距
        UikitUi.applyRoundPadding(this, root);

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
