# Uikit Dictionary-Level API Guide (v1.5.0)

This document details all components, parameters, return types, and examples of the `org.uikit` library.

---

## 1. Initialization & Lifecycle

### `UikitConfig.init(String prefsName)` [Since v1.0.0]
Initializes SharedPreferences. Default is `"uikit_prefs"`.
* **Parameters**: `prefsName` (String).
* **When to call**: In `Application.onCreate()`.

### `UikitUi.wrapScale(Context newBase)` [Since v1.0.0]
Applies physical UI scaling (0.3x - 2.5x).
* **Parameters**: `newBase` (Context).
* **Returns**: Wrapped `Context`.
* **When to call**: Override `attachBaseContext` in Activity.
```java
@Override
protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(UikitUi.wrapScale(newBase));
}
```

### `UikitTheme.applyTheme(Activity activity)` [Since v1.0.0]
Applies dark/light theme. Call after `setContentView()`.

### `UikitUi.applyEntryAnimation(Activity activity)` [Since v1.1.0]
Applies predictive back entry animation.

### `UikitUi.handleSwipeBack(Activity activity, MotionEvent ev)` [Since v1.0.0]
Handles swipe-back gestures.
* **Returns**: `boolean` (true if consumed).
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
* `UikitColor.getOnPrimaryColor(Context)`: Auto-contrast text color (black/white).
* `UikitColor.getAccentColor(Context, boolean isDark)`: Gets accent color.

### `UikitConfig` Custom Colors
```java
UikitConfig.setCustomPrimaryColor(this, 0xFF1976D2);
UikitConfig.setCustomAccentColor(this, 0xFF64B5F6);
UikitConfig.clearCustomColors(this);
```

### `UikitScreenUtils`
* `dp2px(Context, float)`
* `isSquareScreen(View)`

---

## 3. Basic Widgets

### `UikitButton` [Since v1.0.0, XML Support Since v1.5.0]
Standard pill button with press-scale animation.

**XML Attributes**:
| Attribute | Type | Default | Description |
|---|---|---|---|
| `app:uikit_radius` | Dimension | 32dp | Corner radius |
| `app:uikit_backgroundColor` | Color | Dynamic | Background color |
| `app:uikit_textColor` | Color | Auto | Text color |

```xml
<org.uikit.UikitButton
    android:layout_width="match_parent"
    android:layout_height="48dp"
    android:text="Custom"
    android:drawableStart="@drawable/ic_uikit_wifi"
    app:uikit_radius="16dp"
    app:uikit_backgroundColor="#F44336" />
```
* **Compound Drawables**: Since v1.5.0, `drawableStart` is auto-scaled to 18dp and tinted to match the text color.

### `UikitSwitch` [Since v1.0.0]
```java
UikitSwitch sw = new UikitSwitch(this);
sw.setChecked(true);
```

### `UikitListItem` [Since v1.2.0]
* **Constructor**: `(Context, String title, String subtitle)` (subtitle can be null).
```java
UikitListItem item = new UikitListItem(this, "Wi-Fi", "Allow LAN");
item.setSwitch(true, listener);
// Or
item.setArrow();
```

### `UikitBadge` [Since v1.4.0]
```java
UikitBadge badge = new UikitBadge(this);
badge.setBadge("99+");
// Or
badge.setDot();
listItem.addView(badge);
```

### `UikitDivider` [Since v1.4.0]
```java
root.addView(new UikitDivider(this));
```

### `UikitEmptyView` [Since v1.1.0]
```java
UikitEmptyView empty = new UikitEmptyView(this);
```

---

## 4. Progress & Loading

### `UikitProgressBar` [Since v1.2.0]
```java
UikitProgressBar pb = new UikitProgressBar(this);
pb.setProgress(0.75f);
```

### `UikitProgressRing` [Since v1.1.0]
```java
UikitProgressRing ring = new UikitProgressRing(this);
ring.setProgress(0.5f);
```

### `UikitSlider` [Since v1.3.0]
```java
UikitSlider slider = new UikitSlider(this);
slider.setMaxProgress(100);
slider.setProgress(30);
slider.setOnProgressChangedListener(progress -> {});
```

### `UikitLoadingBar` [Since v1.3.0]
```java
UikitLoadingBar loading = new UikitLoadingBar(this);
```

### `UikitLoadingView` [Since v1.4.0]
```java
UikitLoadingView loadingView = new UikitLoadingView(this);
```

### `UikitSkeleton` [Since v1.1.0]
```java
UikitSkeleton skeleton = new UikitSkeleton(this);
```

---

## 5. Fullscreen Components (Recommended)

All fullscreen Activities are started via `startActivityForResult`.

### `UikitFullListActivity.show()` [Since v1.3.0]
* **Parameters**: `(Activity, String title, ArrayList<UikitListItemModel> items, int requestCode)`
* **`UikitListItemModel` Constructor**:
  1. `String title`
  2. `String subtitle` (nullable)
  3. `int accentColor` (0 for default)
  4. `int cardColor` (0 for default)
* **Return Values** (`onActivityResult`):
  * `data.getIntExtra(UikitFullListActivity.EXTRA_RESULT_INDEX, -1)`
  * `data.getStringExtra(UikitFullListActivity.EXTRA_RESULT_TEXT)`

```java
ArrayList<UikitListItemModel> items = new ArrayList<>();
items.add(new UikitListItemModel("Normal", "Default", 0, 0));
items.add(new UikitListItemModel("Error", "Red bg", 0, 0xFFF44336));
UikitFullListActivity.show(this, "List", items, 1001);
```

### `UikitFullInputActivity.show()` [Since v1.3.0]
* **Parameters**: `(Activity, String title, String defaultText, String hint, String positiveText, String negativeText, int requestCode)`
* **Return Values**: `data.getStringExtra(UikitFullInputActivity.EXTRA_RESULT_TEXT)`
```java
UikitFullInputActivity.show(this, "Input", "", "Type...", "Submit", "Cancel", 1002);
```

### `UikitFullTextActivity.show()` [Since v1.3.0, autoSplit Since v1.5.0]
* **Parameters**: `(Activity, String title, String message, String positiveText, int requestCode)` or overload with `boolean autoSplit`.
* **autoSplit**: `false` (whole text), `true` (split by sentence + 3D wheel).
```java
UikitFullTextActivity.show(this, "Agreement", longText, "OK", 1003);
UikitFullTextActivity.show(this, "Features", "Feature 1. Feature 2.", "OK", true, 1004);
```

### `UikitFullConfirmActivity.show()` [Since v1.4.0]
* **Parameters**: `(Activity, String title, String message, int requestCode)`
* **Return Values**: No data. `RESULT_OK` for ✓, `RESULT_CANCELED` for ×.
```java
UikitFullConfirmActivity.show(this, "Delete?", "Cannot be undone", 1004);
```

### `UikitPickerActivity.show()` [Since v1.5.0]
* **Parameters**: `(Activity, String title, String[] items, int requestCode)`
* **Return Values**:
  * `data.getIntExtra(UikitPickerActivity.EXTRA_RESULT_INDEX, -1)`
  * `data.getStringExtra(UikitPickerActivity.EXTRA_RESULT_TEXT)`
```java
UikitPickerActivity.show(this, "Select", new String[]{"A", "B", "C"}, 1005);
```

### `UikitDatePickerActivity.show()` [Since v1.4.0]
* **Parameters**: `(Activity, int requestCode)`
* **Return Values**:
  * `EXTRA_RESULT_YEAR`, `EXTRA_RESULT_MONTH`, `EXTRA_RESULT_DAY` (all String).
```java
UikitDatePickerActivity.show(this, 1006);
```

### `UikitImageViewerActivity.show()` [Since v1.4.0]
* **Parameters**: `(Activity, String assetName)` (file in `assets/`).
```java
UikitImageViewerActivity.show(this, "demo_image.png");
```

---

## 6. Deprecated APIs (Legacy Popups)

> ⚠️ **Warning**: Deprecated since v1.3.0.

| Deprecated | Alternative |
|---|---|
| `UikitUi.showList()` | `UikitFullListActivity.show()` |
| `UikitUi.showInput()` | `UikitFullInputActivity.show()` |
| `UikitUi.showConfirm()` | `UikitFullConfirmActivity.show()` |

---

## 7. Popups & Hardware Utils

### `UikitToast.show()` [Since v1.1.0]
```java
UikitToast.show(this, "Saved");
```

### `UikitBottomSheet` [Since v1.1.0]
* **Constructor**: `(Context, String title, View contentView)`
```java
new UikitBottomSheet(this, "Options", new UikitEmptyView(this)).show();
```

### `WearHardwareUtils` [Since v1.0.0]
* `vibrate(Context, int type)` (`HAPTIC_LIGHT`, `HAPTIC_SUCCESS`, `HAPTIC_ERROR`)
* `keepScreenOn(Activity, boolean)`
* `bindRotaryEncoder(ScrollView)`

### `UikitClickUtils` [Since v1.4.0]
```java
if (UikitClickUtils.isFastDoubleClick()) return;
```

---

## 8. Built-in Icons [Since v1.5.0]

Prefix: `ic_uikit_`.

**List**:
`home`, `settings`, `search`, `delete`, `edit`, `add`, `close`, `check`, `arrow_back`, `arrow_forward`, `play`, `pause`, `stop`, `next`, `prev`, `volume_up`, `volume_down`, `music`, `wifi`, `bluetooth`, `battery`, `message`, `email`, `notifications`, `alarm`, `lock`, `unlock`, `heart`, `star`, `cloud_download`, `cloud_upload`, `folder`, `image`, `time`, `menu`, `more_vert`.

```java
imageView.setImageResource(R.drawable.ic_uikit_wifi);
```
