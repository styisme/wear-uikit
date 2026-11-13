**简体中文** | [English](README_EN.md)

# Uikit

> 从 [WatchSend](https://github.com/styisme/watchsend) 抽取的通用 Android/Wear OS UI 组件库。纯 Java 动态渲染 + XML 资源，无 AndroidX、无第三方依赖，最低支持 Android 2.3（API 10）。

[![Build Uikit](https://github.com/styisme/wear-uikit/actions/workflows/build.yml/badge.svg)](https://github.com/styisme/wear-uikit/actions/workflows/build.yml)
[![Version](https://img.shields.io/badge/version-1.7.0-512BD4)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![JitPack](https://jitpack.io/v/styisme/wear-uikit.svg)](https://jitpack.io/#styisme/wear-uikit)

---

## ⚖️ 版本选择建议（关于体积与兼容性）

从 `v1.5.0` 开始，本库内置了 **36 个 MD3 矢量图标** 与完整的 **XML 属性支持**。在 `v1.7.0` 中，新增了纯图标按钮、轻量列表容器与主题预设。这导致 `.aar` 体积有所增加。

> **如何选择版本？**
> - **推荐：`v1.7.0`（全量版）**。包含所有最新组件、图标、回弹特效，适合绝大多数新建项目。
> - **精简替代：`v1.4.1`**。体积小巧，无内置图标和表单组件，适合对包体积极度敏感的项目。

```gradle
// 全量版 (推荐)
implementation 'org.uikit:wear-uikit:v1.7.0'

// 精简版
// implementation 'org.uikit:wear-uikit:v1.4.1'
```

---

## 引入方式

### 方式一：JitPack（推荐）
在根目录 `build.gradle` 或 `settings.gradle` 中添加仓库：
```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
在 `app/build.gradle` 中引入：
```gradle
dependencies {
    implementation 'com.github.styisme:wear-uikit:v1.7.0'
}
```

### 方式二：本地 AAR 引入（推荐极低版本项目使用）
由于 JitPack 对老旧 Gradle 编译可能有延迟，你可以直接前往 [GitHub Releases](https://github.com/styisme/wear-uikit/releases) 下载最新编译好的 `uikit-release.aar`，放入 `app/libs/` 目录，并在 `build.gradle` 中引入：
```gradle
dependencies {
    implementation files('libs/uikit-release.aar')
}
```

---

## ⚠️ 构建环境说明 (重要)

为了保证对极低版本 Android (API 10) 的兼容，本库使用了较老的构建工具链：
- **AGP**: `3.6.3`
- **Gradle**: `5.6.4` (已在 Wrapper 中锁定)
- **JDK**: `8`

**请务必使用 Gradle 5.6.4**。不要升级到 7.x 或 8.x，否则会导致 AGP 3.6.3 彻底崩溃。

---

## 核心组件速览

### 🎨 预设主题 (针对无动态取色的老设备)
老设备不支持 Android 12 动态取色？一键应用 MD3 预设颜色：
```java
UikitThemePresets.applyPreset(this, UikitThemePresets.BLUE);
```

### 架构与配置
- **`UikitConfig`**: 初始化配置、管理主题、UI缩放、自定义颜色。
- **`UikitColor`**: MD3 动态取色，低版本降级兜底。
- **`UikitTheme`**: 深色模式渲染与视图递归着色。

### 基础与表单控件
- **`UikitButton`**: 胶囊按钮。底层重构为 `LinearLayout`，完美支持内嵌图标与任意嵌套。
- **`UikitIconButton`**: 纯图标按钮，完美契合播放器控制栏或工具栏。
- **`UikitSwitch`**: 丝滑动画的 MD3 开关。
- **`UikitCheckBox` & `UikitRadioButton`**: 纯手绘的 MD3 控件。**完美继承原生类，无缝替换，无需修改原有 Listener 逻辑。**
- **`UikitStepper`**: `- 30 +` 步进器。
- **`UikitLinearList`**: 轻量化列表容器，方便在 `ScrollView` 中嵌入标准列表。

### 进度、加载与微交互
- **`UikitProgressBar`** / **`UikitProgressRing`**: 横向与环形进度条。
- **`UikitSlider`**: 可拖动调节条。
- **`UikitCircularProgressTimer`**: 带结束震动的倒计时环。
- **`UikitMarqueeTextView`**: 自动滚动的跑马灯文字。

### 全屏交互与物理特效
- **`UikitFullTextActivity`** / **`FullListActivity`** / **`FullInputActivity`** / **`FullConfirmActivity`**: 全屏全家桶。
- **边缘阻尼回弹 (Bouncing)**: 滚动到顶/底继续拖拽，松手弹性复位。

### 内置资源
- **36 个 MD3 矢量图标**：前缀 `ic_uikit_`，直接调用。

---

## 关联文档

- [CHANGELOG.md](CHANGELOG.md) —— 完整版本历史
- [API_ZH.md](API_ZH.md) / [API_EN.md](API_EN.md) —— 字典级组件调用指南
- [CONTRIBUTING.md](CONTRIBUTING.md)

## 协议
本项目使用 [MIT License](LICENSE)。
