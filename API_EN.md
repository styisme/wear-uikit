# Uikit Detailed API Guide (v1.4.0)

This document details all components, parameters, return types, and examples of the `org.uikit` library.

---

## 1. Initialization & Lifecycle

### `UikitConfig.init(String prefsName)`
Initializes the library's SharedPreferences file.
* **Parameters**: `prefsName` - e.g., `"my_app_prefs"`.
* **When to call**: In `Application.onCreate()` or early in `MainActivity`.
```java
UikitConfig.init("my_app_prefs");
```

### `UikitUi.wrapScale(Context newBase)`
Applies physical UI scaling (0.3x - 2.5x).
* **Parameters**: `newBase` - The original Context.
* **Returns**: The wrapped `Context`.
* **When to call**: Must override `attachBaseContext` in every Activity.
```java
@Override
protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(UikitUi.wrapScale(newBase));
}
```

### `UikitTheme.applyTheme(Activity activity)`
Applies dark/light theme and recursively fixes light backgrounds.
```java
UikitTheme.applyTheme(this);
```

### `UikitUi.applyEntryAnimation(Activity activity)`
Applies predictive back entry animation.
```java
UikitUi.applyEntryAnimation(this);
```

### `UikitUi.handleSwipeBack(Activity activity, MotionEvent ev)`
Handles global swipe-back gestures.
* **Returns**: `true` if consumed.
* **When to call**: In `dispatchTouchEvent`.
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
* `UikitColor.getPrimaryColor(Context)`: Gets primary color.
* `UikitColor.getOnPrimaryColor(Context)`: Auto-calculates black/white text contrast.
* `UikitColor.getAccentColor(Context, boolean isDark)`: Gets accent color.

### `UikitConfig` Custom Colors
```java
UikitConfig.setCustomPrimaryColor(this, 0xFF1976D2);
UikitConfig.setCustomAccentColor(this, 0xFF64B5F6);
UikitConfig.clearCustomColors(this);
```

### `UikitScreenUtils`
* `UikitScreenUtils.dp2px(Context, float dp)`
* `UikitScreenUtils.isSquareScreen(View root)`

---

## 3. Basic Widgets

### `UikitButton`
Standard pill button with press-scale animation.
```java
UikitButton btn = new UikitButton(this);
btn.setText("OK");
```

### `UikitSwitch`
Custom MD3 switch.
```java
UikitSwitch sw = new UikitSwitch(this);
sw.setChecked(true);
```

### `UikitListItem`
Standard settings list item.
```java
UikitListItem item = new UikitListItem(this, "Wi-Fi", "Allow LAN");
item.setSwitch(true, listener);
// Or
item.setArrow();
```

### `UikitBadge`
Unread message badge.
```java
UikitBadge badge = new UikitBadge(this);
badge.setBadge("99+");
// Or
badge.setDot();
listItem.addView(badge);
```

### `UikitDivider`
```java
root.addView(new UikitDivider(this));
```

### `UikitEmptyView`
```java
UikitEmptyView empty = new UikitEmptyView(this);
```

---

## 4. Progress & Loading

### `UikitProgressBar`
```java
UikitProgressBar pb = new UikitProgressBar(this);
pb.setProgress(0.75f);
```

### `UikitProgressRing`
```java
UikitProgressRing ring = new UikitProgressRing(this);
ring.setProgress(0.5f);
```

### `UikitSlider`
```java
UikitSlider slider = new UikitSlider(this);
slider.setMaxProgress(100);
slider.setProgress(30);
slider.setOnProgressChangedListener(progress -> {});
```

### `UikitLoadingBar`
```java
UikitLoadingBar loading = new UikitLoadingBar(this);
```

### `UikitLoadingView`
```java
UikitLoadingView loadingView = new UikitLoadingView(this);
```

### `UikitSkeleton`
```java
UikitSkeleton skeleton = new UikitSkeleton(this);
```

---

## 5. Fullscreen Components (Recommended)

### `UikitFullListActivity.show()`
* **Parameters**: `(Activity, String title, ArrayList<UikitListItemModel> items, int requestCode)`
* **Returns**: `EXTRA_RESULT_INDEX` (int), `EXTRA_RESULT_TEXT` (String) via `onActivityResult`.
```java
ArrayList<UikitListItemModel> items = new ArrayList<>();
items.add(new UikitListItemModel("Normal", "Default", 0, 0));
items.add(new UikitListItemModel("Error", "Red bg", 0, 0xFFF44336));
UikitFullListActivity.show(this, "List", items, 1001);
```

### `UikitFullInputActivity.show()`
* **Parameters**: `(Activity, String title, String defaultText, String hint, String positiveText, String negativeText, int requestCode)`
* **Returns**: `EXTRA_RESULT_TEXT` (String).
```java
UikitFullInputActivity.show(this, "Input", "", "Type...", "Submit", "Cancel", 1002);
```

### `UikitFullTextActivity.show()`
* **Parameters**: `(Activity, String title, String message, String positiveText, int requestCode)`
```java
UikitFullTextActivity.show(this, "Agreement", longText, "OK", 1003);
```

### `UikitFullConfirmActivity.show()`
* **Parameters**: `(Activity, String title, String message, int requestCode)`
```java
UikitFullConfirmActivity.show(this, "Delete?", "Cannot be undone", 1004);
```

### `UikitPickerActivity.show()`
* **Parameters**: `(Activity, String title, String[] items, int requestCode)`
* **Returns**: `EXTRA_RESULT_INDEX` (int), `EXTRA_RESULT_TEXT` (String).
```java
UikitPickerActivity.show(this, "Select", new String[]{"A", "B", "C"}, 1005);
```

### `UikitDatePickerActivity.show()`
* **Parameters**: `(Activity, int requestCode)`
* **Returns**: `EXTRA_RESULT_YEAR`, `EXTRA_RESULT_MONTH`, `EXTRA_RESULT_DAY` (String).
```java
UikitDatePickerActivity.show(this, 1006);
```

### `UikitImageViewerActivity.show()`
* **Parameters**: `(Activity, String assetName)`
```java
UikitImageViewerActivity.show(this, "demo_image.png");
```

---

## 6. Deprecated APIs (Legacy Popups)

> ⚠️ **Warning**: The following APIs are marked `@Deprecated` and will be removed in future versions.
> Migrate to fullscreen components for better round-screen and 3D scrolling support.

### `UikitUi.showList()` - [Deprecated]
* **Alternative**: `UikitFullListActivity.show()`
```java
// Deprecated
UikitUi.showList(this, "Select", new String[]{"A", "B"}, listener).show();
```

### `UikitUi.showInput()` - [Deprecated]
* **Alternative**: `UikitFullInputActivity.show()`
```java
// Deprecated
UikitUi.showInput(this, "Input", "", listener).show();
```

### `UikitUi.showConfirm()` - [Deprecated]
* **Alternative**: `UikitFullConfirmActivity.show()`
```java
// Deprecated
UikitUi.showConfirm(this, "Confirm", "Are you sure?", null).show();
```

---

## 7. Popups & Hardware Utils

### `UikitToast.show()`
```java
UikitToast.show(this, "Saved");
```

### `UikitBottomSheet`
```java
new UikitBottomSheet(this, "Options", new UikitEmptyView(this)).show();
```

### `WearHardwareUtils`
* `WearHardwareUtils.vibrate(Context, int type)`
* `WearHardwareUtils.keepScreenOn(Activity, boolean)`
* `WearHardwareUtils.bindRotaryEncoder(ScrollView)`

### `UikitClickUtils`
```java
if (UikitClickUtils.isFastDoubleClick()) return;
```

### `UikitFullTextActivity.show() (Split Control)`
When displaying long texts containing URLs or version numbers, forced splitting ruins the reading experience. Therefore, an `autoSplit` parameter is provided.
- `autoSplit = false` (Default): Displays the entire text as a single card with natural line breaks (Recommended).
- `autoSplit = true`: Splits by punctuation and enables the 3D wheel subtitle effect.

```java
// Default whole text (Recommended)
UikitFullTextActivity.show(this, "Agreement", longText, "OK", 1003);

// Enable 3D wheel split
UikitFullTextActivity.show(this, "Features", "Feature 1. Feature 2. Feature 3.", "OK", true, 1004);
```
