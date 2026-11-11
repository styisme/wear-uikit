**简体中文** | [English](README_EN.md)

# Uikit

> 从 [WatchSend](https://github.com/styisme/watchsend) 抽取的通用 Android/Wear OS UI 组件库。纯 Java 动态渲染 + XML 资源，无 AndroidX、无第三方依赖，最低支持 Android 2.3（API 10）。

[![Build Uikit](https://github.com/styisme/wear-uikit/actions/workflows/build.yml/badge.svg)](https://github.com/styisme/wear-uikit/actions/workflows/build.yml)
[![Version](https://img.shields.io/badge/version-1.4.1-512BD4)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![JitPack](https://jitpack.io/v/styisme/wear-uikit.svg)](https://jitpack.io/#styisme/wear-uikit)

---

## AI 生成声明

> **本库由 AI 辅助生成。**
>
> - **AI 深度参与**：3D 滚轮算法、物理级缩放、滑动返回、动态取色反射、自绘组件、CI 流水线。
> - **人类负责**：需求定义、真机验证（手表 / 手机）、API 兼容性取舍、最终发布决策。
>
> 详细范围见 [docs/AI_DISCLOSURE.md](docs/AI_DISCLOSURE.md)。

---

## 引入方式

### 方式一：JitPack（推荐，无需下载源码）

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
    implementation 'com.github.styisme:wear-uikit:v1.4.1'
}
```

### 方式二：本地 Module 引入
```gradle
// settings.gradle
include ':uikit'

// app/build.gradle
dependencies {
    implementation project(':uikit')
}
```

---

## ⚠️ 构建环境说明 (重要)

为了保证对极低版本 Android (API 10) 的兼容，本库使用了较老的构建工具链：
- **AGP**: `3.6.3`
- **Gradle**: `5.6.4` (已在 Wrapper 中锁定)
- **JDK**: `8`
- **Android SDK**: `compileSdk 30` / `buildTools 30.0.3`

**如果你在本地或 CI 环境编译本项目，请务必使用 Gradle 5.6.4**。不要升级 Gradle 到 7.x 或 8.x，否则会导致 AGP 3.6.3 彻底崩溃（如 JitPack 默认使用 Gradle 8.9 导致的编译失败）。

---

## 核心组件速览

### 架构与配置
- **`UikitConfig`**: 初始化配置、管理主题、UI缩放、自定义颜色、语言切换。
- **`UikitColor`**: MD3 动态取色，低版本降级兜底。
- **`UikitTheme`**: 深色模式渲染与视图递归着色。

### 基础控件
- **`UikitButton`**: 胶囊按钮，自带按压缩放微动效。
- **`UikitSwitch`**: 丝滑动画的 MD3 开关。
- **`UikitListItem`**: 标准设置页列表项，支持副标题、开关、箭头。
- **`UikitDivider`**: 优雅的分割线。
- **`UikitBadge`**: 未读消息小红点/数字角标。

### 进度与加载
- **`UikitProgressBar`**: 横向进度条。
- **`UikitProgressRing`**: 环形进度条（圆屏首选）。
- **`UikitSlider`**: 可拖动的调节条。
- **`UikitLoadingBar`**: 不确定进度循环加载条。
- **`UikitLoadingView`**: 三点呼吸 Loading 动画。
- **`UikitSkeleton`**: 骨架屏占位。

### 全屏交互组件（推荐）
- **`UikitFullListActivity`**: 全屏列表，支持双行与自定义卡片颜色。
- **`UikitFullInputActivity`**: 全屏输入，完美适配手表键盘。
- **`UikitFullTextActivity`**: 全屏长文本，自带 3D 滚轮字幕效果。
- **`UikitFullConfirmActivity`**: 极简全屏确认框（× / ✓ 两个大圆按钮）。
- **`UikitPickerActivity`**: 上下滚动的居中 3D 选择器。
- **`UikitDatePickerActivity`**: 年月日三列滚轮日期选择。
- **`UikitImageViewerActivity`**: 全屏图片查看（缩放+拖拽）。

### 弹窗与辅助
- **`UikitBottomSheet`**: 底部抽屉，带下拉回弹。
- **`UikitToast`**: 居中显示的防切边提示。
- **`WearHardwareUtils`**: 震动、表冠、屏幕常亮、圆屏判断。
- **`UikitClickUtils`**: 防手抖连击工具。

---

## 关联文档

- [CHANGELOG.md](CHANGELOG.md)
- [API_ZH.md](API_ZH.md) / [API_EN.md](API_EN.md) —— 极度详细的组件调用指南
- [CONTRIBUTING.md](CONTRIBUTING.md)

## 协议

本项目使用 [MIT License](LICENSE)。
