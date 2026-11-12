# Uikit Dictionary-Level API Guide (v1.6.0)

This document details all components, parameters, return types, and underlying interaction principles of the `org.uikit` library.

---

## 1. Initialization & Lifecycle

### `UikitConfig.init(String prefsName)` [Since v1.0.0]
Initializes SharedPreferences. All theme, scaling, custom colors, and language settings are stored here.

### `UikitUi.wrapScale(Context newBase)` [Since v1.0.0]
Applies physical UI scaling (0.3x - 2.5x).
* **Under the hood**: Uses `createConfigurationContext` to override the `densityDpi` of the host's `Resources`, bypassing Android 11+ restrictions on `updateConfiguration`.

### `UikitTheme.applyTheme(Activity activity)` [Since v1.0.0]
Applies dark/light theme. Recursively fixes white-on-white text issues on native controls.

---

## 2. Basic Widgets & Forms

### `UikitButton` [Since v1.0.0, Refactored Since v1.6.0]
**Core Refactor**: Refactored from `TextView` to a `LinearLayout` in v1.6.0. It now contains an internal `ImageView` and `TextView`.
Advantages:
1. Fixed low-API (10-23) `setCompoundDrawables` centering bugs.
2. Supports arbitrary nested views.
3. Uses `onTouchEvent` instead of `OnTouchListener`, preventing animation freezes when wrapped in a `FrameLayout`.

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
    app:uikit_radius="16dp" />
```

### `UikitCheckBox` & `UikitRadioButton` [Since v1.6.0]
Hand-drawn MD3 controls. Adapts to dark mode and dynamic colors.

### `UikitStepper` [Since v1.6.0]
```java
UikitStepper stepper = new UikitStepper(this);
stepper.setMaxValue(100);
```

---

## 3. Progress, Loading & Micro-interactions

### `UikitCircularProgressTimer` [Since v1.6.0]
A circular countdown timer.
* **Under the hood**: Uses `CountDownTimer` to refresh the canvas arc every 100ms. Triggers a long vibration on finish.
```java
UikitCircularProgressTimer timer = new UikitCircularProgressTimer(this);
timer.start(10000); // 10s
```

### `UikitMarqueeTextView` [Since v1.6.0]
Auto-scrolling marquee text.
* **Under the hood**: Overrides `isFocused()` to return `true` so it scrolls infinitely without requiring focus.

---

## 4. Fullscreen Components (Recommended)

### `UikitFullTextActivity.show()` [Since v1.3.0, Smart Split Since v1.6.0]
* **Parameters**: `(Activity, String title, String message, String positiveText, boolean autoSplit, int requestCode)`
* **Smart Split**: The regex explicitly excludes number decimals (e.g., 2.3) and URLs (e.g., .com).

### `UikitFullListActivity.show()` [Since v1.3.0]
* **Parameters**: `(Activity, String title, ArrayList<UikitListItemModel> items, int requestCode)`

### `UikitFullInputActivity.show()` [Since v1.3.0]
### `UikitFullConfirmActivity.show()` [Since v1.4.0]
### `UikitPickerActivity.show()` [Since v1.5.0]

---

## 5. Visual Effects: 3D Wheel & Bouncing

Calling `UikitUi.setupWheelScroll(scrollView, container)` automatically activates:
1. **3D Wheel Effect**: Dynamically adjusts `scaleX`, `scaleY`, and `alpha` based on the distance from the screen center. (Round screen only).
2. **Edge Bouncing**: Since v1.6.0. Intercepts `OnTouchListener` to apply a `translationY` offset when over-scrolling (damping 0.35). Elastic recovery via `OvershootInterpolator` on release. (Works on all screens).

---

## 6. Deprecated APIs & Migration

> ⚠️ **Status Note**: Deprecated since v1.3.0.
> **These APIs still function perfectly** (they can pop up and return callbacks normally). 
> They are marked deprecated solely because their non-fullscreen Dialog architecture offers inferior visual experiences on round screens and when the watch keyboard pops up. For the best UI experience, migrating to the fullscreen alternatives is highly recommended.

| Deprecated | UX Difference (Why migrate) | Alternative |
|---|---|---|
| `UikitUi.showList()` | Might be clipped on round screens; scrolling is less smooth for long lists. | `UikitFullListActivity` |
| `UikitUi.showInput()` | Prone to being squeezed/obscured when the watch keyboard appears. | `UikitFullInputActivity` |
| `UikitUi.showConfirm()` | Less optimal spacing for long texts. | `UikitFullConfirmActivity` / `UikitFullTextActivity` |

---

## 7. Built-in 36 MD3 Vector Icons [Since v1.5.0]

36 pure MD3 vector icons are built-in. Use `R.drawable.ic_uikit_xxx`.

### 📁 Files & System
`home`, `settings`, `search`, `folder`, `image`, `time`, `menu`, `more_vert`.

### 🎵 Media & Playback
`play`, `pause`, `stop`, `next`, `prev`, `music`, `volume_up`, `volume_down`.

### 🛠️ Actions & Editing
`add`, `edit`, `delete`, `check`, `close`, `arrow_back`, `arrow_forward`.

### 📶 Network & Status
`wifi`, `bluetooth`, `battery`, `lock`, `unlock`, `alarm`.

### 💬 Communication & Social
`message`, `email`, `notifications`, `heart`, `star`, `cloud_download`, `cloud_upload`.

```java
imageView.setImageResource(R.drawable.ic_uikit_wifi);
```
