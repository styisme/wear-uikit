[简体中文](README.md) | **English**

# Uikit

> A reusable Android/Wear OS UI library extracted from [WatchSend](https://github.com/styisme/watchsend). Pure Java dynamic rendering + XML resources. No AndroidX, no third-party dependencies.

[![Build Uikit](https://github.com/styisme/wear-uikit/actions/workflows/build.yml/badge.svg)](https://github.com/styisme/wear-uikit/actions/workflows/build.yml)
[![Version](https://img.shields.io/badge/version-1.2.0-512BD4)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Platform](https://img.shields.io/badge/platform-Android%202.3%2B-brightgreen)]()

---

## AI Generation Disclosure

> **This library is AI-assisted.** See [docs/AI_DISCLOSURE.md](docs/AI_DISCLOSURE.md) for details.

---

## Version & Origin

Current version: **v1.2.0**.

Extracted from [WatchSend](https://github.com/styisme/watchsend), removing business coupling and turning it into a reusable library.

---

## Core Components

| Component | File | Purpose |
|---|---|---|
| **Core** | | |
| `UikitConfig` | `UikitConfig.java` | Config layer, theme/language/color management |
| `UikitColor` | `UikitColor.java` | MD3 dynamic color & custom color override |
| `UikitTheme` | `UikitTheme.java` | Tri-state dark mode + recursive tinting |
| `UikitScreenUtils` | `UikitScreenUtils.java` | Screen size, dp2px, square/round detection |
| `WearHardwareUtils` | `WearHardwareUtils.java` | Haptics, rotary, screen-on, chin padding |
| `UikitUi` | `UikitUi.java` | Physical scaling, dialogs (round-screen adapted), swipe-back |
| **Widgets** | | |
| `UikitSwitch` | `UikitSwitch.java` | MD3 custom switch |
| `UikitButton` | `UikitButton.java` | Standard MD3 pill button |
| `UikitListItem` | `UikitListItem.java` | Settings list item with switch/arrow |
| `UikitProgressRing` | `UikitProgressRing.java` | Circular progress bar |
| `UikitProgressBar` | `UikitProgressBar.java` | Horizontal progress bar |
| `UikitSkeleton` | `UikitSkeleton.java` | Shimmer loading placeholder |
| `UikitEmptyView` | `UikitEmptyView.java` | Unified empty state |
| **Popups** | | |
| `UikitToast` | `UikitToast.java` | Edge-safe rounded toast |
| `UikitBottomSheet` | `UikitBottomSheet.java` | Bottom sheet with drag-to-dismiss |

---

## Usage

### JitPack (Recommended)

```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
dependencies {
    implementation 'com.github.styisme:wear-uikit:v1.2.0'
}
```

### Local Module

```gradle
include ':uikit'
implementation project(':uikit')
```

---

## Quick Start

```java
UikitConfig.init("myapp_prefs");

@Override
protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(UikitUi.wrapScale(newBase));
}

@Override
protected void onCreate(Bundle b) {
    super.onCreate(b);
    UikitTheme.applyTheme(this);
    UikitUi.applyEntryAnimation(this);
}
```

---

## Custom MD3 Colors

```java
UikitConfig.setCustomPrimaryColor(this, 0xFF1976D2);
UikitConfig.setCustomAccentColor(this, 0xFF64B5F6);
UikitConfig.clearCustomColors(this);
```

---

## Related Docs

- [CHANGELOG.md](CHANGELOG.md)
- [API_ZH.md](API_ZH.md) / [API_EN.md](API_EN.md)
- [CONTRIBUTING_EN.md](CONTRIBUTING_EN.md)

## License

[MIT License](LICENSE).
