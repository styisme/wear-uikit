# Uikit API Guide

This document describes how to use the core APIs of the `org.uikit` library.

## 1. Initialization & Lifecycle

### `UikitConfig.init(String prefsName)`
```java
UikitConfig.init("my_app_prefs");
```

### `UikitUi.wrapScale(Context base)`
```java
@Override
protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(UikitUi.wrapScale(newBase));
}
```

### `UikitTheme.applyTheme(Activity activity)`
```java
UikitTheme.applyTheme(this);
```

---

## 2. Color & Customization

### `UikitColor.getPrimaryColor(Context)`
Gets the current primary color.

### `UikitConfig.setCustomPrimaryColor(Context, int color)`
```java
UikitConfig.setCustomPrimaryColor(this, 0xFF1976D2); // Blue
```

---

## 3. UI Widgets

### `UikitListItem`
```java
UikitListItem item = new UikitListItem(this, "Wi-Fi", "Allow LAN discovery");
item.setSwitch(true, listener);
```

### `UikitProgressBar`
```java
UikitProgressBar pb = new UikitProgressBar(this);
pb.setProgress(0.75f);
```

---

## 4. Fullscreen Components (Recommended)

> These components replace the legacy dialog APIs, perfectly adapting to round screens and 3D wheel effects.

### `UikitFullTextActivity.show()`
```java
UikitFullTextActivity.show(this, "Agreement", "Long text here...", "OK", 1001);
```

### `UikitFullInputActivity.show()`
```java
UikitFullInputActivity.show(this, "Input", "", "Type...", "Submit", "Cancel", 1002);
```

### `UikitFullListActivity.show()`
```java
ArrayList<UikitListItemModel> items = new ArrayList<>();
items.add(new UikitListItemModel("Normal", "Subtitle"));
items.add(new UikitListItemModel("Success", "Green accent", 0xFF4CAF50));
items.add(new UikitListItemModel("Red Card", null, 0, 0xFFF44336));

UikitFullListActivity.show(this, "Choose", items, 1003);
```

---

## 5. Legacy Dialog APIs (Deprecated)

> **⚠️ Warning**: The following APIs are marked `@Deprecated` and will be removed in a future version.

- `UikitUi.showList()` -> Use `UikitFullListActivity`
- `UikitUi.showInput()` -> Use `UikitFullInputActivity`
- `UikitUi.showConfirm()` -> Use `UikitFullTextActivity`

---

## 6. Hardware Interactions

### `WearHardwareUtils.vibrate(Context, int type)`
```java
WearHardwareUtils.vibrate(this, WearHardwareUtils.HAPTIC_SUCCESS);
```
