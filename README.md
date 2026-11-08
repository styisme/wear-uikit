**简体中文** | [English](README_EN.md)

# Uikit

> 从 [WatchSend](https://github.com/styisme/watchsend) 抽取的通用 Android/Wear OS UI 组件库。纯 Java 动态渲染 + XML 资源，无 AndroidX、无第三方依赖，最低支持 Android 2.3（API 10）。

[![Build Uikit](https://github.com/styisme/wear-uikit/actions/workflows/build.yml/badge.svg)](https://github.com/styisme/wear-uikit/actions/workflows/build.yml)
[![Version](https://img.shields.io/badge/version-1.2.0-512BD4)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Platform](https://img.shields.io/badge/platform-Android%202.3%2B-brightgreen)]()

---

## AI 生成声明

> **本库由 AI 辅助生成。**
>
> - **AI 深度参与**：3D 滚轮算法、物理级缩放、滑动返回、动态取色反射、自绘组件（开关/进度环/进度条/列表项/骨架屏）、CI 流水线。
> - **人类负责**：需求定义、真机验证（手表 / 手机）、API 兼容性取舍、最终发布决策。
>
> 详细范围见 [docs/AI_DISCLOSURE.md](docs/AI_DISCLOSURE.md)。

---

## 版本与溯源

当前版本：**v1.2.0**。

本库从 [WatchSend](https://github.com/styisme/watchsend) 抽取通用 UI 能力，剥离业务耦合，改为可独立复用的 library。

完整版本历史见 [CHANGELOG.md](CHANGELOG.md)。

---

## 核心组件

| 组件 | 文件 | 作用 |
|---|---|---|
| **基础架构** | | |
| `UikitConfig` | `UikitConfig.java` | 配置层，可注入宿主 SharedPreferences，管理主题/语言/颜色 |
| `UikitColor` | `UikitColor.java` | MD3 动态取色，优先返回自定义颜色，支持 API 31+ 反射 |
| `UikitTheme` | `UikitTheme.java` | 三态深色模式 + 递归着色兜底 |
| `UikitScreenUtils` | `UikitScreenUtils.java` | 屏幕尺寸、dp 转换、方/圆屏判断 |
| `WearHardwareUtils` | `WearHardwareUtils.java` | 震动、表冠、屏幕常亮、下巴避让 |
| `UikitUi` | `UikitUi.java` | 物理级缩放、药丸卡片、弹窗（圆屏专属适配）、滑动返回、3D 滚轮 |
| **控件与列表** | | |
| `UikitSwitch` | `UikitSwitch.java` | MD3 自绘开关，带平滑动画 |
| `UikitButton` | `UikitButton.java` | 标准 MD3 胶囊按钮，自动适配主题色 |
| `UikitListItem` | `UikitListItem.java` | 标准设置页列表项，支持副标题、开关或箭头 |
| `UikitProgressRing` | `UikitProgressRing.java` | 环形进度条，适配圆屏 |
| `UikitProgressBar` | `UikitProgressBar.java` | 横向进度条，支持动态主题色 |
| `UikitSkeleton` | `UikitSkeleton.java` | 微光闪烁骨架屏占位 |
| `UikitEmptyView` | `UikitEmptyView.java` | 统一的空状态占位图 |
| **弹窗与抽屉** | | |
| `UikitToast` | `UikitToast.java` | 防切边的圆角气泡提示 |
| `UikitBottomSheet` | `UikitBottomSheet.java` | 底部抽屉（支持下拉手势拖拽关闭） |

---

## 引入方式

### 方式一：JitPack（推荐，无需下载源码）

在根目录 `build.gradle` 添加：
```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
在 `app/build.gradle` 引入：
```gradle
dependencies {
    implementation 'com.github.styisme:wear-uikit:v1.2.0'
}
```

### 方式二：本地 Module

```gradle
// settings.gradle
include ':uikit'

// app/build.gradle
dependencies {
    implementation project(':uikit')
}
```

---

## 快速开始

```java
// 初始化
UikitConfig.init("myapp_prefs");

// 每个 Activity 接入
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

## 自定义 MD3 颜色

如果你不想使用系统动态取色，可以通过以下 API 强制覆盖：

```java
// 设置自定义主色（蓝色）
UikitConfig.setCustomPrimaryColor(this, 0xFF1976D2);
// 设置强调色
UikitConfig.setCustomAccentColor(this, 0xFF64B5F6);
// 清除自定义颜色，恢复默认
UikitConfig.clearCustomColors(this);
```

---

## 关联文档

- [CHANGELOG.md](CHANGELOG.md)
- [API_ZH.md](API_ZH.md) / [API_EN.md](API_EN.md)
- [CONTRIBUTING.md](CONTRIBUTING.md)
- [docs/AI_DISCLOSURE.md](docs/AI_DISCLOSURE.md)
- [docs/DEVELOPMENT_CONSTRAINTS.md](docs/DEVELOPMENT_CONSTRAINTS.md)

## 协议

本项目使用 [MIT License](LICENSE)。
