[简体中文](README.md) | **English**

# Uikit

> A reusable Android/Wear OS UI library extracted from [WatchSend](https://github.com/styisme/watchsend). Pure Java dynamic rendering + XML resources. No AndroidX, no third-party dependencies.

[![Build Uikit](https://github.com/styisme/wear-uikit/actions/workflows/build.yml/badge.svg)](https://github.com/styisme/wear-uikit/actions/workflows/build.yml)
[![Version](https://img.shields.io/badge/version-1.3.0-512BD4)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![JitPack](https://jitpack.io/v/styisme/wear-uikit.svg)](https://jitpack.io/#styisme/wear-uikit)

---

## AI Generation Disclosure

> **This library is AI-assisted.** See [docs/AI_DISCLOSURE.md](docs/AI_DISCLOSURE.md) for details.

---

## Version & Origin

Current version: **v1.3.0**.

Extracted from [WatchSend](https://github.com/styisme/watchsend), removing business coupling and turning it into a reusable library.

---

## Core Components

| Component | File | Purpose |
|---|---|---|
| **Core** | | |
| `UikitConfig` | `UikitConfig.java` | Config layer, injectable SharedPreferences |
| `UikitColor` | `UikitColor.java` | MD3 dynamic color & custom color override |
| `UikitTheme` | `UikitTheme.java` | Tri-state dark mode + recursive tinting |
| `UikitUi` | `UikitUi.java` | Physical scaling, pill cards, legacy dialogs |
| **Widgets** | | |
| `UikitSwitch` | `UikitSwitch.java` | MD3 custom switch |
| `UikitButton` | `UikitButton.java` | Standard MD3 pill button |
| `UikitListItem` | `UikitListItem.java` | Settings list item with switch/arrow |
| `UikitProgressBar` | `UikitProgressBar.java` | Horizontal progress bar |
| `UikitProgressRing` | `UikitProgressRing.java` | Circular progress bar |
| `UikitSkeleton` | `UikitSkeleton.java` | Shimmer loading placeholder |
| **Fullscreen** | | |
| `UikitFullListActivity` | `UikitFullListActivity.java` | **Full list** (2-line & custom card color) |
| `UikitFullInputActivity` | `UikitFullInputActivity.java` | **Full input** (Wear OS keyboard optimized) |
| `UikitFullTextActivity` | `UikitFullTextActivity.java` | **Full text** (3D wheel subtitle effect) |
| **Popups** | | |
| `UikitToast` | `UikitToast.java` | Edge-safe rounded toast |
| `UikitBottomSheet` | `UikitBottomSheet.java` | Bottom sheet with drag-to-dismiss |

> **⚠️ Deprecated**: `UikitUi.showList()`, `UikitUi.showInput()`, and `UikitUi.showConfirm()` are deprecated. Please migrate to `UikitFullListActivity`, `UikitFullInputActivity`, and `UikitFullTextActivity`.

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
    implementation 'com.github.styisme:wear-uikit:v1.3.0'
}
```

```java
// Initialization
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

## Related Docs

- [CHANGELOG.md](CHANGELOG.md)
- [API_EN.md](API_EN.md)
- [CONTRIBUTING_EN.md](CONTRIBUTING_EN.md)

## License

[MIT License](LICENSE).
