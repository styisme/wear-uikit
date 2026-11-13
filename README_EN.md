[简体中文](README.md) | **English**

# Uikit

> A reusable Android/Wear OS UI library extracted from [WatchSend](https://github.com/styisme/watchsend). Pure Java dynamic rendering + XML resources.

[![Build Uikit](https://github.com/styisme/wear-uikit/actions/workflows/build.yml/badge.svg)](https://github.com/styisme/wear-uikit/actions/workflows/build.yml)
[![Version](https://img.shields.io/badge/version-1.7.0-512BD4)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![JitPack](https://jitpack.io/v/styisme/wear-uikit.svg)](https://jitpack.io/#styisme/wear-uikit)

---

## ⚖️ Version Selection Advice

Starting from `v1.5.0`, the library includes **36 built-in MD3 vector icons** and XML attributes. In `v1.7.0`, `UikitIconButton`, `UikitLinearList`, and theme presets were added. This increases the `.aar` size.

> **How to choose?**
> - **Recommended: `v1.7.0` (Full)**. Includes all latest components.
> - **Lite Alternative: `v1.4.1`**. Small size, no built-in icons.

```gradle
// Full (Recommended)
implementation 'com.github.styisme:wear-uikit:v1.7.0'
```

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
    implementation 'com.github.styisme:wear-uikit:v1.7.0'
}
```

### Local AAR (Recommended for legacy projects)
Download `uikit-release.aar` from [GitHub Releases](https://github.com/styisme/wear-uikit/releases), put it in `app/libs/`:
```gradle
dependencies {
    implementation files('libs/uikit-release.aar')
}
```

---

## ⚠️ Build Environment (Important)

- **AGP**: `3.6.3`
- **Gradle**: `5.6.4` (Locked in Wrapper)
- **JDK**: `8`

**You must use Gradle 5.6.4 to build this project.**

---

## Core Components Overview

### 🎨 Theme Presets (For devices without Dynamic Color)
```java
UikitThemePresets.applyPreset(this, UikitThemePresets.BLUE);
```

### Basic & Form Widgets
- **`UikitButton`**: Refactored to `LinearLayout`. Perfect for nested views and centered icons.
- **`UikitIconButton`**: Icon-only button for toolbars.
- **`UikitCheckBox` & `UikitRadioButton`**: **Perfectly inherit from native classes**. Drop-in replacement without modifying Java listeners.
- **`UikitStepper`**, **`UikitLinearList`**.

### Progress & Micro-interactions
- **`UikitSlider`**, **`UikitCircularProgressTimer`**, **`UikitMarqueeTextView`**.

### Fullscreen & Physics
- **Fullscreen Suite** & **Bouncing Effect**.

---

## Related Docs
- [CHANGELOG.md](CHANGELOG.md)
- [API_ZH.md](API_ZH.md) / [API_EN.md](API_EN.md)

## License
[MIT License](LICENSE).
