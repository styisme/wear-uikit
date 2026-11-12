[简体中文](README.md) | **English**

# Uikit

> A reusable Android/Wear OS UI library extracted from [WatchSend](https://github.com/styisme/watchsend). Pure Java dynamic rendering + XML resources.

[![Build Uikit](https://github.com/styisme/wear-uikit/actions/workflows/build.yml/badge.svg)](https://github.com/styisme/wear-uikit/actions/workflows/build.yml)
[![Version](https://img.shields.io/badge/version-1.6.0-512BD4)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![JitPack](https://jitpack.io/v/styisme/wear-uikit.svg)](https://jitpack.io/#styisme/wear-uikit)

---

## ⚖️ Version Selection Advice

Starting from `v1.5.0`, the library includes **35 built-in MD3 vector icons**. In `v1.6.0`, 5 advanced form/micro-interaction components and physical bounce effects were added. This caused an increase in the `.aar` size.

> **How to choose?**
> - **Recommended: `v1.6.0` (Full)**. Includes everything. Suitable for most new projects.
> - **Lite Alternative: `v1.4.1`**. If you don't need built-in icons or bounce effects.

```gradle
// Full (Recommended)
implementation 'com.github.styisme:wear-uikit:v1.6.0'

// Lite
// implementation 'com.github.styisme:wear-uikit:v1.4.1'
```

---

## Usage

```gradle
// settings.gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
// app/build.gradle
dependencies {
    implementation 'com.github.styisme:wear-uikit:v1.6.0'
}
```

---

## ⚠️ Build Environment (Important)

To maintain compatibility with API 10, this library uses an older toolchain:
- **AGP**: `3.6.3`
- **Gradle**: `5.6.4` (Locked in Wrapper)
- **JDK**: `8`

**You must use Gradle 5.6.4 to build this project.** Do not upgrade to Gradle 7.x or 8.x.

---

## Core Components Overview

### Architecture
- **`UikitConfig`**, **`UikitColor`**, **`UikitTheme`**.

### Basic Widgets
- **`UikitButton`**: Pill button. Refactored to a `LinearLayout` architecture, perfectly supports arbitrary nested views and centered compound drawables.
- **`UikitSwitch`**, **`UikitListItem`**, **`UikitDivider`**, **`UikitBadge`**.
- **`UikitCheckBox` & `UikitRadioButton`**: MD3 hand-drawn controls.
- **`UikitStepper`**: `- 30 +` stepper control.

### Progress & Loading
- **`UikitProgressBar`**, **`UikitProgressRing`**, **`UikitSlider`**, **`UikitLoadingBar`**, **`UikitLoadingView`**, **`UikitSkeleton`**, **`UikitCircularProgressTimer`**.

### Fullscreen Interactions (Recommended)
- **`UikitFullListActivity`**, **`UikitFullInputActivity`**, **`UikitFullTextActivity`**, **`UikitFullConfirmActivity`**, **`UikitPickerActivity`**, **`UikitDatePickerActivity`**, **`UikitImageViewerActivity`**.

### Visual & Micro-interactions
- **`UikitMarqueeTextView`**: Auto-scrolling marquee text.
- **Bouncing Effect**: Physical damped bounce when over-scrolling any `ScrollView`.

### Built-in Resources
- **35 MD3 Vector Icons** (e.g., `ic_uikit_home`, `ic_uikit_wifi`).

---

## Related Docs

- [CHANGELOG.md](CHANGELOG.md)
- [API_ZH.md](API_ZH.md) / [API_EN.md](API_EN.md)

## License

[MIT License](LICENSE).
