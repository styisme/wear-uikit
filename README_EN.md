[简体中文](README.md) | **English**

# Uikit

> A reusable Android/Wear OS UI library extracted from [WatchSend](https://github.com/styisme/watchsend). Pure Java dynamic rendering + XML resources. No AndroidX, no third-party dependencies.

[![Build Uikit](https://github.com/styisme/wear-uikit/actions/workflows/build.yml/badge.svg)](https://github.com/styisme/wear-uikit/actions/workflows/build.yml)
[![Version](https://img.shields.io/badge/version-1.4.0-512BD4)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![JitPack](https://jitpack.io/v/styisme/wear-uikit.svg)](https://jitpack.io/#styisme/wear-uikit)

---

## AI Generation Disclosure

> **This library is AI-assisted.** See [docs/AI_DISCLOSURE.md](docs/AI_DISCLOSURE.md) for details.

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
    implementation 'com.github.styisme:wear-uikit:v1.4.0'
}
```

---

## Core Components Overview

### Architecture & Config
- **`UikitConfig`**: Initialize config, manage theme, UI scale, custom colors, language.
- **`UikitColor`**: MD3 dynamic color with fallback.
- **`UikitTheme`**: Dark mode rendering and recursive tinting.

### Basic Widgets
- **`UikitButton`**: Pill button with press-scale animation.
- **`UikitSwitch`**: MD3 switch with smooth animation.
- **`UikitListItem`**: Standard list item with subtitle, switch, arrow, or badge support.
- **`UikitDivider`**: Elegant divider line.
- **`UikitBadge`**: Unread message dot or number badge.

### Progress & Loading
- **`UikitProgressBar`**: Horizontal progress bar.
- **`UikitProgressRing`**: Circular progress bar.
- **`UikitSlider`**: Draggable slider.
- **`UikitLoadingBar`**: Indeterminate loading bar.
- **`UikitLoadingView`**: Three-dots breathing loading animation.
- **`UikitSkeleton`**: Shimmer skeleton placeholder.

### Fullscreen Interactions (Recommended)
- **`UikitFullListActivity`**: Full list with 2-line items and custom card colors.
- **`UikitFullInputActivity`**: Full input, perfectly adapted for watch keyboards.
- **`UikitFullTextActivity`**: Full text with 3D wheel subtitle effect.
- **`UikitFullConfirmActivity`**: Minimal full confirm dialog with × / ✓ buttons.
- **`UikitPickerActivity`**: Vertical 3D wheel picker.
- **`UikitDatePickerActivity`**: Year-Month-Day 3-column date picker.
- **`UikitImageViewerActivity`**: Fullscreen image viewer (zoom + pan).

### Popups & Utils
- **`UikitBottomSheet`**: Bottom sheet with drag-to-dismiss.
- **`UikitToast`**: Centered edge-safe toast.
- **`WearHardwareUtils`**: Haptics, rotary, screen-on, round screen detection.
- **`UikitClickUtils`**: Anti-debounce utility.

---

## Related Docs

- [CHANGELOG.md](CHANGELOG.md)
- [API_ZH.md](API_ZH.md) / [API_EN.md](API_EN.md) —— **Very detailed API guides**
- [CONTRIBUTING_EN.md](CONTRIBUTING_EN.md)

## License

[MIT License](LICENSE).
