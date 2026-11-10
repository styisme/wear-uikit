# Uikit API Guide

This document describes how to use the core APIs of the `org.uikit` library.

## 1. Initialization & Lifecycle

### `UikitConfig.init(String prefsName)`
Initializes config. Call in `Application`.
```java
UikitConfig.init("my_app_prefs");
```

### `UikitUi.wrapScale(Context base)`
Applies physical UI scaling. Must be called in `attachBaseContext`.
```java
@Override
protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(UikitUi.wrapScale(newBase));
}
```

### `UikitTheme.applyTheme(Activity activity)`
Applies dark/light theme.
```java
UikitTheme.applyTheme(this);
```

### `UikitUi.handleSwipeBack(Activity activity, MotionEvent ev)`
Handles swipe-back gestures.
```java
@Override
public boolean dispatchTouchEvent(MotionEvent ev) {
    if (UikitUi.handleSwipeBack(this, ev)) return true;
    return super.dispatchTouchEvent(ev);
}
```

---

## 2. Color & Customization

### `UikitColor.getPrimaryColor(Context)`
Gets the current primary color (Priority: Custom > MD3 Dynamic > Default Green).

### `UikitConfig.setCustomPrimaryColor(Context, int color)`
Overrides the default primary color globally.
```java
UikitConfig.setCustomPrimaryColor(this, 0xFF1976D2); // Blue
```

---

## 3. UI Widgets

### `UikitListItem`
Standard list item.
```java
UikitListItem item = new UikitListItem(this, "Wi-Fi", "Allow LAN discovery");
item.setSwitch(true, listener); // With switch
// Or
item.setArrow(); // With arrow
```

### `UikitProgressBar`
Horizontal progress bar.
```java
UikitProgressBar pb = new UikitProgressBar(this);
pb.setProgress(0.75f);
```

### `UikitUi.showList()`
```java
UikitUi.showList(this, "Choose", new String[]{"A", "B"}, new OnItemSelectListener() {
    public void onItemSelect(int pos) { }
}).show();
```

### `UikitBottomSheet`
```java
new UikitBottomSheet(this, "Title", new UikitEmptyView(this)).show();
```

---

## 4. Hardware Interactions

### `WearHardwareUtils.vibrate(Context, int type)`
```java
WearHardwareUtils.vibrate(this, WearHardwareUtils.HAPTIC_SUCCESS);
```

### `UikitFullListActivity.show()`
Full-screen selection list, ideal for scenarios with many options or a complete scrolling experience.
```java
String[] items = {"Option 1", "Option 2", "Option 3"};
UikitFullListActivity.show(this, "Select Item", items, 1001);

// Receive result (Override in Activity)
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == 1001 && resultCode == RESULT_OK) {
        int index = data.getIntExtra(UikitFullListActivity.EXTRA_RESULT_INDEX, -1);
        String text = data.getStringExtra(UikitFullListActivity.EXTRA_RESULT_TEXT);
    }
}
```
