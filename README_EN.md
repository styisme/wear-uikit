[简体中文](README.md) | **English**

# Uikit

> A reusable Android/Wear OS UI library extracted from [WatchSend](https://github.com/styisme/watchsend). Pure Java dynamic rendering + XML resources.

[![Build Uikit](https://github.com/styisme/wear-uikit/actions/workflows/build.yml/badge.svg)](https://github.com/styisme/wear-uikit/actions/workflows/build.yml)
[![Version](https://img.shields.io/badge/version-1.7.1-512BD4)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![JitPack](https://jitpack.io/v/styisme/wear-uikit.svg)](https://jitpack.io/#styisme/wear-uikit)

---

## 📑 Table of Contents

1. [⚠️ Core Statement & Version Advice](#-core-statement--version-advice)
2. [📦 Usage](#-usage)
3. [🛠️ Build Environment Constraints](#️-build-environment-constraints)
4. [✨ Component Overview](#-component-overview)
5. [🔄 Native Drop-in Replacements](#-native-drop-in-replacements)
6. [🎨 Core Features](#-core-features)
7. [📱 Demo](#-demo)
8. [📚 Related Docs](#-related-docs)

---

## ⚠️ Core Statement & Version Advice

### 1. AI Generation Disclosure
Most UI code (3D wheel, scaling, custom widgets) is **AI-assisted**. Human developers define requirements, design APIs, verify on real devices, and make release decisions.

### 2. Size & Version Selection
Starting from `v1.5.0`, 36 built-in icons were added. `v1.7.0` added forms and native proxies. This objectively increases the `.aar` size by a few hundred KB.

> **How to choose?**
> - **Recommended: `v1.7.1` (Full & Safe)**. Includes all latest components, fixes, and proxies.
> - **Lite Alternative: `v1.4.1`**. Small size, no built-in icons or form widgets.

---

## 📦 Usage

### JitPack (Recommended)
```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
dependencies {
    implementation 'com.github.styisme:wear-uikit:v1.7.1'
}
```

### Local AAR
Download `uikit-release.aar` from [GitHub Releases](https://github.com/styisme/wear-uikit/releases) and put it in `app/libs/`.

---

## 🛠️ Build Environment Constraints

Locked to support API 10. **DO NOT upgrade.**

| Component | Version |
|---|---|
| **JDK** | `8` |
| **Gradle** | `5.6.4` |
| **AGP** | `3.6.3` |

---

## ✨ Component Overview

### Architecture & Config
`UikitConfig`, `UikitColor`, `UikitTheme`, `UikitThemePresets`, `UikitClickUtils`.

### Basic & Form Widgets
`UikitButton`, `UikitCompatButton`, `UikitIconButton`, `UikitSwitch`, `UikitCheckBox`, `UikitRadioButton`, `UikitStepper`, `UikitListItem`, `UikitLinearList`, `UikitDivider`.

### Progress & Loading
`UikitProgressBar`, `UikitProgressRing`, `UikitSlider`, `UikitCircularProgressTimer`, `UikitLoadingBar`, `UikitLoadingView`, `UikitSkeleton`.

### Fullscreen Interactions
`UikitFullTextActivity`, `UikitFullListActivity`, `UikitFullInputActivity`, `UikitFullConfirmActivity`, `UikitPickerActivity`, `UikitDatePickerActivity`, `UikitImageViewerActivity`.

### Visual & Micro-interactions
`UikitMarqueeTextView`, `UikitBadge`, **Bouncing Effect**.

### Built-in Resources
**36 MD3 Vector Icons** (Prefix `ic_uikit_`).

---

## 🔄 Native Drop-in Replacements

| Native | Uikit Replacement | Note |
|---|---|---|
| `<Button>` | `<org.uikit.UikitCompatButton>` | Proxies `setTextColor`, etc. |
| `<CheckBox>` | `<org.uikit.UikitCompatCheckBox>` | Inherits native. |
| `<RadioButton>` | `<org.uikit.UikitCompatRadioButton>` | Inherits native. |
| `<Switch>` | `<org.uikit.UikitCompatSwitch>` | Inherits native. |
| `<ProgressBar>` | `<org.uikit.UikitProgressBar>` | Compatible with `setMax`, `setProgress`. |
| `<SeekBar>` | `<org.uikit.UikitSlider>` | Compatible with `OnSeekBarChangeListener`. |

---

## 🎨 Core Features

1.  **Physical UI Scaling**: Bypasses Android 11+ restrictions.
2.  **3D Wheel Effect**: Edge views scale down and fade out.
3.  **Edge Bouncing**: iOS-like elastic bounce at edges.
4.  **Smart Split**: Preserves numbers and URLs in text splitting.
5.  **Legacy Defenses**: Canvas boundary and zero-division protections.

---

## 📱 Demo

The `app` module includes a comprehensive playground to test all themes, components, and features.

---

## 📚 Related Docs

- [CHANGELOG.md](CHANGELOG.md)
- [API_ZH.md](API_ZH.md) / [API_EN.md](API_EN.md)
- [CONTRIBUTING_EN.md](CONTRIBUTING_EN.md)

## 📜 License
[MIT License](LICENSE).
