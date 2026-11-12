[简体中文](README.md) | **English**

# Uikit

> A reusable Android/Wear OS UI library extracted from [WatchSend](https://github.com/styisme/watchsend). Pure Java dynamic rendering + XML resources.

[![Build Uikit](https://github.com/styisme/wear-uikit/actions/workflows/build.yml/badge.svg)](https://github.com/styisme/wear-uikit/actions/workflows/build.yml)
[![Version](https://img.shields.io/badge/version-1.5.0-512BD4)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![JitPack](https://jitpack.io/v/styisme/wear-uikit.svg)](https://jitpack.io/#styisme/wear-uikit)

---

## ⚖️ Version Selection Advice (About Size Inflation)

Starting from `v1.5.0`, to provide an out-of-the-box experience, the library includes **35 built-in MD3 vector icons**, full **XML attribute support**, and extremely rich **fullscreen 3D interactive components**. This caused a significant increase in the `.aar` size.

> **How to choose?**
> - **Recommended: `v1.5.0` (Full)**. Includes icons, XML support, and the latest components. Suitable for most new watch projects.
> - **Lite Alternative: `v1.4.1`**. If your project does not need built-in icons, use this version. It is very lightweight.

```gradle
// Full (Recommended)
implementation 'com.github.styisme:wear-uikit:v1.5.0'

// Lite (No built-in icons)
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
    implementation 'com.github.styisme:wear-uikit:v1.5.0'
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
- **`UikitConfig`**: Init, theme, scale, custom colors, language.
- **`UikitColor`**: MD3 dynamic color.
- **`UikitTheme`**: Dark mode.

### Basic Widgets
- **`UikitButton`**: Pill button. Supports XML attributes and tinted compound drawables.
- **`UikitSwitch`**: MD3 switch.
- **`UikitListItem`**: Settings list item.
- **`UikitDivider`**: Divider.
- **`UikitBadge`**: Badge.

### Progress & Loading
- **`UikitProgressBar`**, **`UikitProgressRing`**, **`UikitSlider`**, **`UikitLoadingBar`**, **`UikitLoadingView`**, **`UikitSkeleton`**.

### Fullscreen Interactions (Recommended)
- **`UikitFullListActivity`**, **`UikitFullInputActivity`**, **`UikitFullTextActivity`**, **`UikitFullConfirmActivity`**, **`UikitPickerActivity`**, **`UikitDatePickerActivity`**, **`UikitImageViewerActivity`**.

### Built-in Resources
- **35 MD3 Vector Icons** (e.g., `ic_uikit_home`, `ic_uikit_wifi`).

---

## Related Docs

- [CHANGELOG.md](CHANGELOG.md)
- [API_ZH.md](API_ZH.md) / [API_EN.md](API_EN.md)

## License

[MIT License](LICENSE).
