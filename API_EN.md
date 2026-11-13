# Uikit Master API Dictionary (v1.7.0)

This document serves as a comprehensive dictionary for all public APIs, XML attributes, return value handling, and underlying principles of the `org.uikit` library.

---

## 1. Initialization & Configuration

### 1.1 `UikitConfig.init(String prefsName)`
- **Since**: `v1.0.0`
- **Usage**: Initializes SharedPreferences. All theme, scaling, and language settings are stored here.
- **Parameters**:
  - `prefsName` (String): e.g., `"my_app_prefs"`.
- **When to call**: In `Application.onCreate()`.

### 1.2 `UikitUi.wrapScale(Context newBase)`
- **Since**: `v1.0.0`
- **Usage**: Applies physical UI scaling (0.3x - 2.5x), bypassing Android 11+ restrictions.
```java
@Override
protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(UikitUi.wrapScale(newBase));
}
```

### 1.3 `UikitTheme.applyTheme(Activity activity)`
- **Since**: `v1.0.0`
- **Usage**: Applies dark/light theme. Call after `setContentView()`.

---

## 2. Colors, Themes & Presets

### 2.1 `UikitThemePresets`
- **Since**: `v1.7.0`
- **Usage**: Preset themes for legacy devices without Dynamic Color.
- **Constants**: `GREEN`, `BLUE`, `RED`, `PURPLE`, `YELLOW`, `ORANGE`, `TEAL`.
```java
UikitThemePresets.applyPreset(this, UikitThemePresets.BLUE);
```

### 2.2 `UikitColor`
- **Since**: `v1.0.0`
- `getPrimaryColor(Context)`, `getOnPrimaryColor(Context)`, `getAccentColor(Context, boolean)`.

---

## 3. Basic & Form Widgets

### 3.1 `UikitButton`
- **Since**: `v1.0.0, Refactored v1.6.0`
- **Features**: Refactored to `LinearLayout`. Perfect for nested views and centered icons on low APIs.
- **XML Attributes**:
  | Attribute | Type | Default | Description |
  |---|---|---|---|
  | `app:uikit_radius` | Dimension | `32dp` | Corner radius |
  | `app:uikit_backgroundColor` | Color | Dynamic | Background color |
  | `app:uikit_textColor` | Color | Auto | Text color |

```xml
<org.uikit.UikitButton
    android:layout_width="match_parent"
    android:layout_height="48dp"
    android:text="Custom"
    android:drawableStart="@drawable/ic_uikit_wifi"
    app:uikit_radius="16dp" />
```

### 3.2 `UikitIconButton`
- **Since**: `v1.7.0`
- **Features**: Icon-only button, perfect for media controls.
- **XML Attributes**:
  | Attribute | Type | Default | Description |
  |---|---|---|---|
  | `app:uikit_icon_radius` | Dimension | `48dp` | Corner radius |
  | `app:uikit_icon_bg` | Color | Dynamic | Background color |
  | `app:uikit_icon_tint` | Color | Auto | Icon color |

```xml
<org.uikit.UikitIconButton
    android:layout_width="48dp"
    android:layout_height="48dp"
    android:src="@drawable/ic_uikit_play" />
```

### 3.3 `UikitCheckBox` & `UikitRadioButton`
- **Since**: `v1.6.0`
- **Usage**: **Perfectly inherit from native classes**. Drop-in replacement without modifying Java listeners.

### 3.4 `UikitStepper`
- **Since**: `v1.6.0`
- **API**: `setMinValue`, `setMaxValue`, `setCurrentValue`, `setOnValueChangeListener`.

### 3.5 `UikitLinearList`
- **Since**: `v1.7.0`
- **API**: `addItem(UikitListItem item, boolean showDivider)`, `clear()`.

---

## 4. Progress & Micro-interactions

### 4.1 `UikitCircularProgressTimer`
- **Since**: `v1.6.0`
- **API**: `start(long millis)`, `stop()`, `setOnTimerFinishListener(...)` (Triggers vibration on finish).

### 4.2 `UikitMarqueeTextView`
- **Since**: `v1.6.0`
- **Usage**: Auto-scrolling marquee text.

---

## 5. Fullscreen Components (Recommended)

All fullscreen components are launched via `startActivityForResult`. (e.g., `requestCode = 1001`).

### 5.1 `UikitFullListActivity` (Full List)
- **Since**: `v1.3.0`
- **Launch**:
```java
ArrayList<UikitListItemModel> items = new ArrayList<>();
items.add(new UikitListItemModel("Normal", "Subtitle", 0, 0));
UikitFullListActivity.show(this, "List", items, 1001);
```
- **Receive Result**:
```java
if (requestCode == 1001 && resultCode == RESULT_OK) {
    int index = data.getIntExtra(UikitFullListActivity.EXTRA_RESULT_INDEX, -1);
    String text = data.getStringExtra(UikitFullListActivity.EXTRA_RESULT_TEXT);
}
```

### 5.2 `UikitFullInputActivity`
- **Since**: `v1.3.0`
- **Launch**: `UikitFullInputActivity.show(this, "Input", "", "Type...", "Submit", "Cancel", 1002);`
- **Receive Result**: `data.getStringExtra(UikitFullInputActivity.EXTRA_RESULT_TEXT)`

### 5.3 `UikitFullTextActivity`
- **Since**: `v1.3.0, Smart Split v1.6.0`
- **Launch**: `UikitFullTextActivity.show(this, "Agreement", longText, "OK", true, 1003);`
  - The boolean `true` enables smart splitting, preserving URLs and decimals.

### 5.4 `UikitPickerActivity`
- **Since**: `v1.5.0`
- **Launch**: `UikitPickerActivity.show(this, "Select", new String[]{"A", "B"}, 1004);`
- **Receive Result**: `EXTRA_RESULT_INDEX`, `EXTRA_RESULT_TEXT`.

### 5.5 `UikitDatePickerActivity`
- **Since**: `v1.4.0`
- **Launch**: `UikitDatePickerActivity.show(this, 1005);`
- **Receive Result**: `EXTRA_RESULT_YEAR`, `EXTRA_RESULT_MONTH`, `EXTRA_RESULT_DAY`.

### 5.6 `UikitFullConfirmActivity`
- **Since**: `v1.4.0`
- **Launch**: `UikitFullConfirmActivity.show(this, "Delete?", "Cannot be undone", 1006);`
- **Receive Result**: `RESULT_OK` for ✓, `RESULT_CANCELED` for ×.

### 5.7 `UikitImageViewerActivity`
- **Since**: `v1.4.0`
- **Launch**: `UikitImageViewerActivity.show(this, "demo_image.png");`

---

## 6. Deprecated APIs & Migration

> ⚠️ **Status Note**: Deprecated since `v1.3.0`.
> **These APIs still function perfectly**. They are marked deprecated solely because their non-fullscreen architecture offers inferior visual experiences on round screens and when the keyboard pops up.

| Deprecated | UX Difference | Alternative |
|---|---|---|
| `showList()` | Might be clipped on round screens. | `UikitFullListActivity` |
| `showInput()` | Obscured when the watch keyboard appears. | `UikitFullInputActivity` |
| `showConfirm()` | Less optimal spacing for long texts. | `UikitFullConfirmActivity` |

---

## 7. Visual Effects & Performance

### 7.1 Bouncing Effect & 3D Wheel
Calling `UikitUi.setupWheelScroll(scrollView, container)` automatically activates:
1. **3D Wheel**: Edge views scale down (Round screen only).
2. **Edge Bouncing**: Physical damped bounce at edges (All screens).

### 7.2 Low-Performance Devices
If you experience frame drops:
```java
scrollView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
```

---

## 8. Built-in 36 MD3 Icons [Since v1.5.0]

Prefix: `ic_uikit_`.
```java
imageView.setImageResource(R.drawable.ic_uikit_wifi);
```

### Files & System
`home`, `settings`, `search`, `folder`, `image`, `time`, `menu`, `more_vert`.

### Media & Playback
`play`, `pause`, `stop`, `next`, `prev`, `music`, `volume_up`, `volume_down`.

### Actions & Editing
`add`, `edit`, `delete`, `check`, `close`, `arrow_back`, `arrow_forward`.

### Network & Status
`wifi`, `bluetooth`, `battery`, `lock`, `unlock`, `alarm`.

### Communication & Social
`message`, `email`, `notifications`, `heart`, `star`, `cloud_download`, `cloud_upload`.
