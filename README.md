**简体中文** | [English](README_EN.md)

# Uikit

> 从 [WatchSend](https://github.com/styisme/watchsend) 抽取的通用 Android/Wear OS UI 组件库。纯 Java 动态渲染 + XML 资源，无 AndroidX、无第三方依赖，最低支持 Android 2.3（API 10）。

[![Build Uikit](https://github.com/styisme/wear-uikit/actions/workflows/build.yml/badge.svg)](https://github.com/styisme/wear-uikit/actions/workflows/build.yml)
[![Version](https://img.shields.io/badge/version-1.3.0-512BD4)]()
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

## 版本与溯源

当前版本：**v1.3.0**。

本库从 [WatchSend](https://github.com/styisme/watchsend) 抽取通用 UI 能力，剥离业务耦合，改为可独立复用的 library。

完整版本历史见 [CHANGELOG.md](CHANGELOG.md)。

---

## 核心组件

| 组件 | 文件 | 作用 |
|---|---|---|
| **基础架构** | | |
| `UikitConfig` | `UikitConfig.java` | 配置层，可注入宿主 SharedPreferences |
| `UikitColor` | `UikitColor.java` | MD3 动态取色，支持 API 31+ 反射与自定义色 |
| `UikitTheme` | `UikitTheme.java` | 三态深色模式 + 递归着色兜底 |
| `UikitUi` | `UikitUi.java` | 物理级缩放、药丸卡片、旧版弹窗、滑动返回 |
| **高频控件** | | |
| `UikitSwitch` | `UikitSwitch.java` | MD3 自绘开关 |
| `UikitButton` | `UikitButton.java` | 标准 MD3 胶囊按钮 |
| `UikitListItem` | `UikitListItem.java` | 标准设置页列表项（支持开关/箭头） |
| `UikitProgressBar` | `UikitProgressBar.java` | 横向进度条 |
| `UikitProgressRing` | `UikitProgressRing.java` | 环形进度条 |
| `UikitSkeleton` | `UikitSkeleton.java` | 骨架屏加载占位 |
| **全屏交互** | | |
| `UikitFullListActivity` | `UikitFullListActivity.java` | **全屏列表**（支持双行与自定义卡片色） |
| `UikitFullInputActivity` | `UikitFullInputActivity.java` | **全屏输入**（完美适配手表键盘） |
| `UikitFullTextActivity` | `UikitFullTextActivity.java` | **全屏长文本**（3D 滚轮字幕效果） |
| **辅助组件** | | |
| `UikitToast` | `UikitToast.java` | 防切边圆角气泡提示 |
| `UikitBottomSheet` | `UikitBottomSheet.java` | 底部抽屉（支持下拉关闭） |

> **⚠️ 废弃警告**：`UikitUi.showList()`、`UikitUi.showInput()`、`UikitUi.showConfirm()` 已废弃，请尽快迁移至 `UikitFullListActivity`、`UikitFullInputActivity`、`UikitFullTextActivity`。

---

## 引入方式

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
    implementation 'com.github.styisme:wear-uikit:v1.3.0'
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

## 关联文档

- [CHANGELOG.md](CHANGELOG.md)
- [API_ZH.md](API_ZH.md) / [API_EN.md](API_EN.md)
- [CONTRIBUTING.md](CONTRIBUTING.md)
- [docs/AI_DISCLOSURE.md](docs/AI_DISCLOSURE.md)
- [docs/DEVELOPMENT_CONSTRAINTS.md](docs/DEVELOPMENT_CONSTRAINTS.md)

## 协议

本项目使用 [MIT License](LICENSE)。
