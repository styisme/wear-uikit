[简体中文](README.md) | **English**

# Uikit

> A reusable Android/Wear OS UI library extracted from [WatchSend](https://github.com/styisme/watchsend). Pure Java dynamic rendering + XML resources. No AndroidX, no third-party dependencies, min SDK 10 (Android 2.3).

[![Build Uikit](https://github.com/styisme/wear-uikit/actions/workflows/build.yml/badge.svg)](https://github.com/styisme/wear-uikit/actions/workflows/build.yml)
[![Version](https://img.shields.io/badge/version-1.0.0-512BD4)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Platform](https://img.shields.io/badge/platform-Android%202.3%2B-brightgreen)]()

---

## AI Generation Disclosure

> **This library is AI-assisted.**
>
> - **AI-heavy parts**: `UikitSwitch` custom switch animation, `UikitUi` physical scaling & swipe-back, `UikitColor` dynamic color reflection, `WearHardwareUtils` hardware degradation, `UikitTheme` recursive tinting, CI pipeline.
> - **Human parts**: requirement definition, real-device verification, API compatibility trade-offs, release decisions.
>
> See [docs/AI_DISCLOSURE.md](docs/AI_DISCLOSURE.md) for details.

---

## Version & Origin

Current version: **v1.0.0**.

Extracted from [WatchSend](https://github.com/styisme/watchsend), removing business coupling (`I18nManager`, `OldSendService`, device models) and turning it into a reusable library. The transfer protocol stack is not shipped with this library.

See [CHANGELOG.md](CHANGELOG.md) for full history.

---

## Core Components

| Component | File | Purpose |
|---|---|---|
| `UikitConfig` | `UikitConfig.java` | Config layer, injectable SharedPreferences |
| `UikitColor` | `UikitColor.java` | MD3 dynamic color, API 31+ reflection on `system_accent1_*`, auto fallback |
| `UikitTheme` | `UikitTheme.java` | Tri-state dark mode (auto/light/dark) + recursive tinting |
| `UikitSwitch` | `UikitSwitch.java` | MD3 custom switch, color follows `UikitColor` |
| `UikitUi` | `UikitUi.java` | Physical UI scaling, pill cards, dialogs, swipe-back, 3D wheel, entry animation |
| `WearHardwareUtils` | `WearHardwareUtils.java` | Haptics, rotary input, screen-on, round screen, chin padding |

Shared XML resources: `res/drawable/uikit_*.xml`, `res/layout/dialog_uikit_*.xml`.

---

## Quick Start

### 1. Add the module

```gradle
// settings.gradle
include ':uikit'

// app/build.gradle
dependencies {
    implementation project(':uikit')
}
```

### 2. Initialize

```java
// Application or first Activity
UikitConfig.init("myapp_prefs");   // optional, reuse existing prefs
```

### 3. Hook into each Activity

```java
@Override
protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(UikitUi.wrapScale(newBase));
}

@Override
protected void onCreate(Bundle b) {
    super.onCreate(b);
    UikitTheme.applyTheme(this);
    UikitUi.applyEntryAnimation(this);
    // ... your layout
}

@Override
public boolean dispatchTouchEvent(MotionEvent ev) {
    if (UikitUi.handleSwipeBack(this, ev)) return true;
    return super.dispatchTouchEvent(ev);
}
```

### 4. Use components

```xml
<org.uikit.UikitSwitch
    android:layout_width="44dp"
    android:layout_height="26dp" />
```

```java
UikitUi.showList(this, "Choose", new String[]{"A", "B"}, new UikitUi.OnItemSelectListener() {
    public void onItemSelect(int p) { /* ... */ }
}).show();
```

---

## MD3 Dynamic Color

`UikitColor` uses three layers:

1. `targetSdk 30` + host theme `Theme.DeviceDefault.NoActionBar` → `ctx.getTheme().resolveAttribute(android.R.attr.colorAccent)`
2. Fallback → reflect `getIdentifier("system_accent1_600", "color", "android")` (API 31+)
3. Fallback → fixed `0xFF388E3C / 0xFF1B5E20 / 0xFF81C784`

**Requirements**: host `<application>` theme must be a system `DeviceDefault` variant, and `targetSdkVersion` not too low (this lib uses 30). Older devices fall back to fixed colors safely.

---

## Build

Same toolchain as the original project:

| Component | Version |
|---|---|
| AGP | 3.6.3 |
| Gradle | 5.6.4 |
| JDK | 8 |
| compileSdk / targetSdk | 30 |
| buildTools | 30.0.3 |
| minSdk | 10 |

Local:

```bash
gradle :uikit:assembleRelease
gradle :app:assembleDebug
```

CI: push to `main`/`master` triggers `.github/workflows/build.yml`, producing `uikit-aar` and `uikit-demo-apk` artifacts. Push a `v*` tag to auto-release.

---

## Limitations

1. **API gaps**: rotary input needs API 26+, `ViewPropertyAnimator` needs 12+, `createConfigurationContext` needs 17+. Version checks and fallbacks are in place.
2. **Dynamic color depends on host theme**: non-`DeviceDefault` themes degrade to fixed colors.
3. **Reflection-based**: `UikitColor` relies on `system_accent1_*` resource names; non-standard ROMs may fall back.
4. **Low performance mode**: 3D wheel, entry animation, swipe-back may drop frames on low-end devices. Call `UikitConfig.setLowPerf(ctx, true)` to degrade.

---

## Related Docs

- [CHANGELOG.md](CHANGELOG.md)
- [CONTRIBUTING_EN.md](CONTRIBUTING_EN.md)
- [docs/AI_DISCLOSURE.md](docs/AI_DISCLOSURE.md)
- [docs/DEVELOPMENT_CONSTRAINTS.md](docs/DEVELOPMENT_CONSTRAINTS.md)
- [WatchSend](https://github.com/styisme/watchsend)

---

## License

[MIT License](LICENSE).
