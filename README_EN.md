[简体中文](README.md) | **English**

# Uikit

> A reusable Android/Wear OS UI library extracted from [WatchSend](https://github.com/styisme/watchsend). Pure Java dynamic rendering + XML resources.

[![Build Uikit](https://github.com/styisme/wear-uikit/actions/workflows/build.yml/badge.svg)](https://github.com/styisme/wear-uikit/actions/workflows/build.yml)
[![Version](https://img.shields.io/badge/version-1.4.1-512BD4)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![JitPack](https://jitpack.io/v/styisme/wear-uikit.svg)](https://jitpack.io/#styisme/wear-uikit)

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
    implementation 'com.github.styisme:wear-uikit:v1.4.1'
}
```

---

## ⚠️ Build Environment (Important)

To maintain compatibility with API 10, this library uses an older toolchain:
- **AGP**: `3.6.3`
- **Gradle**: `5.6.4` (Locked in Wrapper)
- **JDK**: `8`

**You must use Gradle 5.6.4 to build this project.** Do not upgrade to Gradle 7.x or 8.x, as it will break AGP 3.6.3 (which caused JitPack build failures previously).

---

## Core Components Overview

### Architecture
- **`UikitConfig`**: Init, theme, scale, custom colors, language.
- **`UikitColor`**: MD3 dynamic color.
- **`UikitTheme`**: Dark mode.

### Basic Widgets
- **`UikitButton`**: Pill button with press animation.
- **`UikitSwitch`**: MD3 switch.
- **`UikitListItem`**: Settings list item.
- **`UikitDivider`**: Divider.
- **`UikitBadge`**: Badge.

### Progress & Loading
- **`UikitProgressBar`**: Horizontal progress.
- **`UikitProgressRing`**: Circular progress.
- **`UikitSlider`**: Draggable slider.
- **`UikitLoadingBar`**: Indeterminate bar.
- **`UikitLoadingView`**: Breathing dots.
- **`UikitSkeleton`**: Skeleton placeholder.

### Fullscreen Interactions (Recommended)
- **`UikitFullListActivity`**: Full list with 2-line items.
- **`UikitFullInputActivity`**: Full input.
- **`UikitFullTextActivity`**: Full text with 3D wheel.
- **`UikitFullConfirmActivity`**: Minimal confirm dialog.
- **`UikitPickerActivity`**: Vertical 3D picker.
- **`UikitDatePickerActivity`**: Date picker.
- **`UikitImageViewerActivity`**: Image viewer.

### Popups & Utils
- **`UikitBottomSheet`**: Bottom sheet.
- **`UikitToast`**: Centered toast.
- **`WearHardwareUtils`**: Hardware utils.
- **`UikitClickUtils`**: Anti-debounce.

---

## Related Docs

- [CHANGELOG.md](CHANGELOG.md)
- [API_ZH.md](API_ZH.md) / [API_EN.md](API_EN.md)

## License

[MIT License](LICENSE).
