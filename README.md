**简体中文** | [English](README_EN.md)

# Uikit

> 从 [WatchSend](https://github.com/styisme/watchsend) 抽取的通用 Android/Wear OS UI 组件库。纯 Java 动态渲染 + XML 资源，无 AndroidX、无第三方依赖，最低支持 Android 2.3（API 10）。

[![Build Uikit](https://github.com/styisme/wear-uikit/actions/workflows/build.yml/badge.svg)](https://github.com/styisme/wear-uikit/actions/workflows/build.yml)
[![Version](https://img.shields.io/badge/version-1.1.0-512BD4)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Platform](https://img.shields.io/badge/platform-Android%202.3%2B-brightgreen)]()

---

## AI 生成声明

> **本库由 AI 辅助生成。**
>
> - **AI 深度参与**：3D 滚轮算法、物理级缩放、滑动返回、动态取色反射、自绘组件（开关/进度环/骨架屏）、CI 流水线。
> - **人类负责**：需求定义、真机验证（手表 / 手机）、API 兼容性取舍、最终发布决策。
>
> 详细范围见 [docs/AI_DISCLOSURE.md](docs/AI_DISCLOSURE.md)。

---

## 版本与溯源

当前版本：**v1.1.0**。

本库从 [WatchSend](https://github.com/styisme/watchsend) 抽取通用 UI 能力，剥离业务耦合，改为可独立复用的 library。

完整版本历史见 [CHANGELOG.md](CHANGELOG.md)。

---

## 核心组件

| 组件 | 文件 | 作用 |
|---|---|---|
| **基础架构** | | |
| `UikitConfig` | `UikitConfig.java` | 配置层，可注入宿主 SharedPreferences |
| `UikitColor` | `UikitColor.java` | MD3 动态取色，API 31+ 反射，低版本降级 |
| `UikitColorMixer` | `UikitColorMixer.java` | 颜色混合、透明度计算工具 |
| `UikitTheme` | `UikitTheme.java` | 三态深色模式 + 递归着色兜底 |
| `UikitScreenUtils` | `UikitScreenUtils.java` | 屏幕尺寸、dp 转换、方/圆屏判断 |
| `WearHardwareUtils` | `WearHardwareUtils.java` | 震动、表冠、屏幕常亮、下巴避让 |
| `UikitUi` | `UikitUi.java` | 物理级缩放、药丸卡片、对话框、滑动返回、3D 滚轮、入场动画 |
| **自绘控件** | | |
| `UikitSwitch` | `UikitSwitch.java` | MD3 自绘开关，带平滑动画 |
| `UikitButton` | `UikitButton.java` | 标准 MD3 胶囊按钮，自动适配主题色 |
| `UikitProgressRing` | `UikitProgressRing.java` | 环形进度条，适配圆屏 |
| `UikitSkeleton` | `UikitSkeleton.java` | 微光闪烁骨架屏占位 |
| `UikitEmptyView` | `UikitEmptyView.java` | 统一的空状态占位图 |
| **弹窗与抽屉** | | |
| `UikitToast` | `UikitToast.java` | 防切边的圆角气泡提示 |
| `UikitBottomSheet` | `UikitBottomSheet.java` | 底部抽屉（支持下拉手势拖拽关闭） |

---

## 快速开始

### 1. 引入模块

```gradle
// settings.gradle
include ':uikit'

// app/build.gradle
dependencies {
    implementation project(':uikit')
}
```

### 2. 初始化与接入

```java
// Application
UikitConfig.init("myapp_prefs");

// 每个 Activity
@Override
protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(UikitUi.wrapScale(newBase));
}

@Override
protected void onCreate(Bundle b) {
    super.onCreate(b);
    UikitTheme.applyTheme(this);
    UikitUi.applyEntryAnimation(this);
    // ... 你的布局
}

@Override
public boolean dispatchTouchEvent(MotionEvent ev) {
    if (UikitUi.handleSwipeBack(this, ev)) return true;
    return super.dispatchTouchEvent(ev);
}
```

---

## MD3 动态取色

`UikitColor` 三层兜底：

1. `targetSdk 30` + 宿主主题为 `Theme.DeviceDefault.NoActionBar` → `ctx.getTheme().resolveAttribute(android.R.attr.colorAccent)`
2. 失败 → 反射 `getIdentifier("system_accent1_600", "color", "android")`（API 31+）
3. 再失败 → 固定色 `0xFF388E3C / 0xFF1B5E20 / 0xFF81C784`

**生效条件**：宿主 `<application>` 主题必须是系统 `DeviceDefault` 系列，且 `targetSdkVersion` 不能太低（本库设为 30）。

---

## 关联文档

- [CHANGELOG.md](CHANGELOG.md)
- [CONTRIBUTING.md](CONTRIBUTING.md)
- [docs/AI_DISCLOSURE.md](docs/AI_DISCLOSURE.md)
- [docs/DEVELOPMENT_CONSTRAINTS.md](docs/DEVELOPMENT_CONSTRAINTS.md)

## 协议

本项目使用 [MIT License](LICENSE)。

---

## 如何通过 JitPack 引入（推荐）

不需要下载源码，只需在项目的 `build.gradle` 中添加：

```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

然后在 `app/build.gradle` 中引入（请将 `v1.1.0` 替换为你发布的 Release 版本）：

```gradle
dependencies {
    implementation 'com.github.styisme:wear-uikit:v1.1.0'
}
```
