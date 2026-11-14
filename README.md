**简体中文** | [English](README_EN.md)

# Uikit

> 从 [WatchSend](https://github.com/styisme/watchsend) 抽取的通用 Android/Wear OS UI 组件库。纯 Java 动态渲染 + XML 资源，无 AndroidX、无第三方依赖，最低支持 Android 2.3（API 10）。

[![Build Uikit](https://github.com/styisme/wear-uikit/actions/workflows/build.yml/badge.svg)](https://github.com/styisme/wear-uikit/actions/workflows/build.yml)
[![Version](https://img.shields.io/badge/version-1.7.1-512BD4)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![JitPack](https://jitpack.io/v/styisme/wear-uikit.svg)](https://jitpack.io/#styisme/wear-uikit)
[![Platform](https://img.shields.io/badge/Platform-Android%202.3%2B-brightgreen)]()

---

## 📑 目录

1. [⚠️ 核心声明与版本选择建议](#-核心声明与版本选择建议)
2. [📦 引入方式](#-引入方式)
3. [🛠️ 构建环境限制（极度重要）](#️-构建环境限制极度重要)
4. [✨ 全景组件库分类](#-全景组件库分类)
5. [🔄 原生控件无缝替换方案](#-原生控件无缝替换方案)
6. [🎨 核心特性解析](#-核心特性解析)
7. [📱 Demo 体验](#-demo-体验)
8. [📚 关联文档](#-关联文档)

---

## ⚠️ 核心声明与版本选择建议

### 1. AI 生成声明
本项目绝大部分 UI 代码（如 3D 滚轮、物理级缩放、自绘表单控件）均由 **AI 辅助生成**。人类开发者负责需求定义、API 架构设计、真机适配与最终发布。详见 [AI_DISCLOSURE.md](docs/AI_DISCLOSURE.md)。

### 2. 体积与版本选择
从 `v1.5.0` 起，库内置了 36 个 MD3 矢量图标；`v1.7.0` 起，新增了表单与原生代理层。这导致 `.aar` 体积相比初始版本增加了约 **几百 KB**。

> **如何选择版本？**
> - **推荐：`v1.7.1`（全量安全版）**。包含所有最新组件、图标、防闪退修复与原生代理，适合所有新建项目和绝大多数升级项目。
> - **精简替代：`v1.4.1`**。体积小巧，无内置图标和表单组件，适合对包体积极度敏感且仅需基础 UI 的项目。

---

## 📦 引入方式

### 方式一：JitPack（推荐）
```gradle
// settings.gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
// app/build.gradle
dependencies {
    implementation 'com.github.styisme:wear-uikit:v1.7.1'
}
```

### 方式二：本地 AAR 引入
由于 JitPack 对老旧 Gradle 编译可能有延迟，你可以前往 [GitHub Releases](https://github.com/styisme/wear-uikit/releases) 下载 `uikit-release.aar`，放入 `app/libs/` 目录：
```gradle
dependencies {
    implementation files('libs/uikit-release.aar')
}
```

---

## 🛠️ 构建环境限制（极度重要）

由于本库需要兼容极低版本的 Android (API 10)，我们**强制锁死**了以下工具链。**绝对不要随意升级**，否则会导致 AGP 编译灾难。

| 组件 | 锁定版本 | 说明 |
|---|---|---|
| **JDK** | `8` | 编译与运行均需 JDK 8 |
| **Gradle** | `5.6.4` | 已在 `gradle-wrapper.properties` 锁定 |
| **AGP** | `3.6.3` | 与 Gradle 5.6.4 强绑定 |
| **SDK** | `compileSdk 30` / `buildTools 30.0.3` | - |

---

## ✨ 全景组件库分类

### 一、架构与配置
*   **`UikitConfig`**：初始化 SharedPreferences、全局配置注入。
*   **`UikitColor`**：MD3 动态取色引擎，低版本自动降级。
*   **`UikitTheme`**：深色模式渲染与视图递归修复。
*   **`UikitThemePresets`**：提供 6 种 MD3 预设色，一键解决老设备无动态取色痛点。
*   **`UikitClickUtils`**：防手抖连击工具。

### 二、基础与表单控件
*   **`UikitButton`**：胶囊按钮。底层重构为 `LinearLayout`，全量代理原生方法（如 `setTextColor`）。
*   **`UikitCompatButton`**：专为平替原生 `<Button>` 的空壳类。
*   **`UikitIconButton`**：纯图标按钮，完美契合播放器控制栏。
*   **`UikitSwitch`**：丝滑动画的 MD3 开关。
*   **`UikitCheckBox`** & **`UikitRadioButton`**：纯手绘控件，完美继承原生类。
*   **`UikitStepper`**：`- 30 +` 步进器。
*   **`UikitListItem`**：标准设置页列表项，支持副标题与右侧控件。
*   **`UikitLinearList`**：轻量化列表容器，方便嵌套在 ScrollView 中。
*   **`UikitDivider`**：优雅的分割线。

### 三、进度与加载
*   **`UikitProgressBar`**：横向进度条（兼容原生 `setMax`/`setProgress`）。
*   **`UikitProgressRing`**：环形进度条（圆屏首选）。
*   **`UikitSlider`**：可拖动调节条（兼容原生 `SeekBar` 监听器）。
*   **`UikitCircularProgressTimer`**：带结束震动的倒计时环。
*   **`UikitLoadingBar`**：不确定进度循环加载条。
*   **`UikitLoadingView`**：三点呼吸 Loading 动画。
*   **`UikitSkeleton`**：骨架屏加载占位。

### 四、全屏交互组件（推荐）
*   **`UikitFullTextActivity`**：全屏长文本，支持智能断句与 3D 滚轮字幕。
*   **`UikitFullListActivity`**：全屏列表，支持双行显示与自定义卡片背景色。
*   **`UikitFullInputActivity`**：全屏输入，完美适配手表键盘。
*   **`UikitFullConfirmActivity`**：极简全屏确认框（× / ✓）。
*   **`UikitPickerActivity`**：上下滚动的居中 3D 选择器。
*   **`UikitDatePickerActivity`**：年月日三列滚轮日期选择。
*   **`UikitImageViewerActivity`**：全屏图片查看（支持单指拖拽、双指缩放）。

### 五、视觉与微交互
*   **`UikitMarqueeTextView`**：自动滚动的跑马灯文字。
*   **`UikitBadge`**：未读消息小红点/数字角标。
*   **边缘阻尼回弹 (Bouncing)**：滑到顶/底继续拖拽，松手物理复位。

### 六、内置资源
*   **36 个 MD3 矢量图标**：前缀 `ic_uikit_`，直接调用。

---

## 🔄 原生控件无缝替换方案

为了让你现有的巨型老项目**零代码修改**完成 UI 升级，我们提供了完美的平替方案：

| 原生控件 | Uikit 平替类 | 说明 |
|---|---|---|
| `<Button>` | `<org.uikit.UikitCompatButton>` | 代理了 `setTextColor` 等所有高频方法。 |
| `<CheckBox>` | `<org.uikit.UikitCompatCheckBox>` | 天然继承，无需修改 Listener。 |
| `<RadioButton>` | `<org.uikit.UikitCompatRadioButton>` | 天然继承。 |
| `<Switch>` | `<org.uikit.UikitCompatSwitch>` | 天然继承。 |
| `<ProgressBar>` | `<org.uikit.UikitProgressBar>` | 兼容 `setMax` 与 `setProgress(int)`。 |
| `<SeekBar>` | `<org.uikit.UikitSlider>` | 兼容 `OnSeekBarChangeListener`。 |

---

## 🎨 核心特性解析

1.  **物理级 UI 缩放**：通过 `createConfigurationContext` 绕过 Android 11+ 限制，支持 0.3x ~ 2.5x 实时调整。
2.  **3D 滚轮特效**：边缘视图自动缩小变透明，中间视图高亮。
3.  **边缘阻尼回弹**：模拟 iOS 的 Bouncing 效果，配有 `OvershootInterpolator` 弹性恢复。
4.  **智能断句**：全屏长文本拆分时，精准排除数字小数点与网址。
5.  **极低版本防御**：针对老旧 ARM 芯片加入了严密的 Canvas 尺寸越界与除零防御。

---

## 📱 Demo 体验

源码中包含一个极度完整的测试大厅 `app` 模块。
在此页面中，你可以测试：明暗主题切换、动态取色、强制圆屏、全屏组件、原生兼容层、表单控件、回弹特效以及 36 个图标的展示。

---

## 📚 关联文档

- [CHANGELOG.md](CHANGELOG.md) —— 完整版本更新历史
- [API_ZH.md](API_ZH.md) / [API_EN.md](API_EN.md) —— 字典级 API 说明
- [CONTRIBUTING.md](CONTRIBUTING.md) —— 贡献与发版指南
- [OldSend 原项目](https://github.com/styisme/oldsend) —— 核心协议来源

## 📜 协议
本项目使用 [MIT License](LICENSE)。
