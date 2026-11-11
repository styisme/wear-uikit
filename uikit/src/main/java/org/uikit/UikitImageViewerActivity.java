package org.uikit;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
public class UikitImageViewerActivity extends Activity {
    public static final String EXTRA_IMAGE_NAME = "uikit_image_name";
    public static void show(Activity activity, String assetName) {
        android.content.Intent intent = new android.content.Intent(activity, UikitImageViewerActivity.class);
        intent.putExtra(EXTRA_IMAGE_NAME, assetName);
        activity.startActivity(intent);
    }
    @Override protected void attachBaseContext(Context newBase) { super.attachBaseContext(UikitUi.wrapScale(newBase)); }
    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(0xFF000000));
        final ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        setContentView(imageView);
        String assetName = getIntent().getStringExtra(EXTRA_IMAGE_NAME);
        try {
            InputStream is = getAssets().open(assetName);
            Bitmap bmp = BitmapFactory.decodeStream(is);
            is.close();
            if (bmp != null) imageView.setImageBitmap(bmp);
            else { Toast.makeText(this, "Image load failed", Toast.LENGTH_SHORT).show(); finish(); }
        } catch (IOException e) { Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show(); finish(); return; }
        final Matrix matrix = new Matrix();
        final float[] matrixValues = new float[9];
        final ScaleGestureDetector scaleDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override public boolean onScale(ScaleGestureDetector detector) {
                float scale = detector.getScaleFactor();
                matrix.getValues(matrixValues);
                float currentScale = matrixValues[Matrix.MSCALE_X];
                scale = Math.max(0.5f, Math.min(scale * currentScale, 3.0f)) / currentScale;
                matrix.postScale(scale, scale, detector.getFocusX(), detector.getFocusY());
                imageView.setImageMatrix(matrix);
                return true;
            }
        });
        imageView.setOnTouchListener(new View.OnTouchListener() { public boolean onTouch(View v, MotionEvent event) { scaleDetector.onTouchEvent(event); return true; } });
        imageView.setOnClickListener(new View.OnClickListener() { public void onClick(View v) { finish(); overridePendingTransition(0,0); } });
    }
    @Override public boolean dispatchTouchEvent(android.view.MotionEvent ev) { if (UikitUi.handleSwipeBack(this, ev)) return true; return super.dispatchTouchEvent(ev); }
}
