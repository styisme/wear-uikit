# Uikit API Guide

This document describes how to use the core APIs of the `org.uikit` library.

## 1. Initialization & Lifecycle

### `UikitConfig.init(String prefsName)`
Initializes the library configuration. Should be called in `Application` or early in `onCreate`. The parameter is the SharedPreferences file name; you can reuse the host's existing file.
```java
UikitConfig.init("my_app_prefs");
```

### `UikitUi.wrapScale(Context base)`
Applies physical UI scaling (0.3x - 2.5x). Must be called in every Activity's `attachBaseContext`.
```java
@Override
protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(UikitUi.wrapScale(newBase));
}
```

### `UikitTheme.applyTheme(Activity activity)`
Applies dark/light theme. It recursively traverses the view tree to fix white-on-white text or leftover light backgrounds.
```java
UikitTheme.applyTheme(this);
```

### `UikitUi.applyEntryAnimation(Activity activity)`
Applies system-level scale entry animation (Predictive Back style). Call it in `onCreate`.
```java
UikitUi.applyEntryAnimation(this);
```

### `UikitUi.handleSwipeBack(Activity activity, MotionEvent ev)`
Intercepts and handles swipe-back gestures. Call it in the Activity's `dispatchTouchEvent`.
```java
@Override
public boolean dispatchTouchEvent(MotionEvent ev) {
    if (UikitUi.handleSwipeBack(this, ev)) return true;
    return super.dispatchTouchEvent(ev);
}
```

---

## 2. Color & Screen Utils

### `UikitColor`
- `UikitColor.getPrimaryColor(Context)`: Gets the current MD3 dynamic primary color (works on API 31+; falls back to fixed green on older versions).
- `UikitColor.getOnPrimaryColor(Context)`: Automatically calculates contrasting text color (black or white) on the primary color.
- `UikitColor.getAccentColor(Context, boolean isDark)`: Gets the accent color.

### `UikitScreenUtils`
- `UikitScreenUtils.dp2px(Context, float dp)`: Converts dp to px.
- `UikitScreenUtils.isSquareScreen(View root)`: Checks if the current screen is square.

---

## 3. UI Widgets

### `UikitButton` (Standard Pill Button)
Automatically follows dynamic primary color, rounded corners, and contrasting text.
```java
UikitButton btn = new UikitButton(this);
btn.setText("Click Me");
// Set layout params...
```

### `UikitSwitch`
Custom-drawn MD3 switch with smooth animation.
```java
UikitSwitch sw = new UikitSwitch(this);
sw.setChecked(true);
```

### `UikitProgressRing`
Custom-drawn circular progress bar optimized for round screens.
```java
UikitProgressRing ring = new UikitProgressRing(this);
ring.setProgress(0.75f); // 0.0f - 1.0f
```

### `UikitSkeleton`
Shimmer loading placeholder.
```java
UikitSkeleton skeleton = new UikitSkeleton(this);
// Animation starts automatically when attached to window
```

---

## 4. Popups & Bottom Sheet

### `UikitUi.showList()`
Shows an option list dialog.
```java
UikitUi.showList(this, "Choose Action", new String[]{"Option A", "Option B"}, 
    new UikitUi.OnItemSelectListener() {
        public void onItemSelect(int position) {
            // Handle selection
        }
    }).show();
```

### `UikitUi.showInput()`
Shows a text input dialog.
```java
UikitUi.showInput(this, "Enter Name", "Default", new UikitUi.OnInputListener() {
    public void onInput(String text) {
        // Handle input
    }
}).show();
```

### `UikitBottomSheet`
A bottom sheet panel. Supports drag-to-dismiss and back-button dismissal.
```java
View content = new UikitEmptyView(this); // Custom content
UikitBottomSheet sheet = new UikitBottomSheet(this, "More Options", content);
sheet.show();
```

### `UikitToast.show()`
Replacement for system Toast, optimized for round screens to prevent clipping.
```java
UikitToast.show(this, "Success");
```

---

## 5. Hardware Interactions

### `WearHardwareUtils.vibrate(Context, int type)`
Triggers haptic feedback.
```java
WearHardwareUtils.vibrate(this, WearHardwareUtils.HAPTIC_SUCCESS);
// Available: HAPTIC_LIGHT, HAPTIC_ERROR
```

### `WearHardwareUtils.bindRotaryEncoder(ScrollView sv)`
Binds the Wear OS rotary encoder (crown scroll) events.
```java
WearHardwareUtils.bindRotaryEncoder(scrollView);
```

---

## 6. Custom MD3 Colors

If you do not want to use system dynamic color (or if the device is below Android 12), you can force specific primary and accent colors via the API:

### `UikitConfig.setCustomPrimaryColor(Context, int color)`
Sets a custom primary color. Pass a standard `Color` int (e.g., `0xFF1976D2`). Once set, all `UikitButton`, `UikitSwitch`, and `UikitProgressRing` will immediately use this color.

### `UikitConfig.setCustomAccentColor(Context, int color)`
Sets a custom accent color (used for titles, highlighted text, etc.).

### `UikitConfig.clearCustomColors(Context)`
Clears custom colors and restores default dynamic color (or fallback green).

```java
// Set blue theme
UikitConfig.setCustomPrimaryColor(this, 0xFF1976D2);
recreate(); // Recommended to reload Activity to refresh UI
```
