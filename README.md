**简体中文** | [English](README_EN.md)

# Uikit

> 从 [WatchSend](https://github.com/styisme/watchsend) 抽取的通用 Android/Wear OS UI 组件库。纯 Java 动态渲染 + XML 资源，无 AndroidX、无第三方依赖，最低支持 Android 2.3（API 10）。

[![Build Uikit](https://github.com/styisme/wear-uikit/actions/workflows/build.yml/badge.svg)](https://github.com/styisme/wear-uikit/actions/workflows/build.yml)
[![Version](https://img.shields.io/badge/version-1.0.0-512BD4)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Platform](https://img.shields.io/badge/platform-Android%202.3%2B-brightgreen)]()

---

## AI 生成声明

> **本库由 AI 辅助生成。**
>
> - **AI 深度参与**：`UikitSwitch` 自绘开关动画、`UikitUi` 物理级缩放与滑动返回、`UikitColor` 动态取色反射、`WearHardwareUtils` 硬件降级封装、`UikitTheme` 递归着色、CI 流水线。
> - **人类负责**：需求定义、真机验证（手表 / 手机）、API 兼容性取舍、最终发布决策。
>
> 详细范围见 [docs/AI_DISCLOSURE.md](docs/AI_DISCLOSURE.md)。

---

## 版本与溯源

当前版本：**v1.0.0**。

本库从 [WatchSend](https://github.com/styisme/watchsend) 抽取通用 UI 能力，剥离业务耦合（`I18nManager`、`OldSendService`、设备模型等），改为可独立复用的 library。业务层协议栈不随本库发布。

完整版本历史见 [CHANGELOG.md](CHANGELOG.md)。

---

## 核心组件

| 组件 | 文件 | 作用 |
|---|---|---|
| `UikitConfig` | `UikitConfig.java` | 配置层，可注入宿主 SharedPreferences |
| `UikitColor` | `UikitColor.java` | MD3 动态取色，API 31+ 反射 `system_accent1_*`，低版本自动降级 |
| `UikitTheme` | `UikitTheme.java` | 三态深色模式（auto/light/dark）+ 递归着色兜底 |
| `UikitSwitch` | `UikitSwitch.java` | MD3 自绘开关，颜色随 `UikitColor` 变化 |
| `UikitUi` | `UikitUi.java` | 物理级 UI 缩放、药丸卡片、对话框、滑动返回、3D 滚轮、入场动画 |
| `WearHardwareUtils` | `WearHardwareUtils.java` | 震动、表冠、屏幕常亮、圆屏判断、下巴避让 |

通用 XML 资源：`res/drawable/uikit_*.xml`、`res/layout/dialog_uikit_*.xml`。

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

### 2. 初始化

```java
// Application 或首个 Activity
UikitConfig.init("myapp_prefs");   // 可选，复用已有 SharedPreferences
```

### 3. 每个 Activity 接入

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
    // ... 你的布局
}

@Override
public boolean dispatchTouchEvent(MotionEvent ev) {
    if (UikitUi.handleSwipeBack(this, ev)) return true;
    return super.dispatchTouchEvent(ev);
}
```

### 4. 使用组件

```xml
<org.uikit.UikitSwitch
    android:layout_width="44dp"
    android:layout_height="26dp" />
```

```java
UikitUi.showList(this, "选择", new String[]{"A", "B"}, new UikitUi.OnItemSelectListener() {
    public void onItemSelect(int p) { /* ... */ }
}).show();

UikitUi.showInput(this, "输入", "", new UikitUi.OnInputListener() {
    public void onInput(String s) { /* ... */ }
}).show();
```

---

## MD3 动态取色

`UikitColor` 三层兜底：

1. `targetSdk 30` + 宿主主题为 `Theme.DeviceDefault.NoActionBar` → `ctx.getTheme().resolveAttribute(android.R.attr.colorAccent)` 拿到动态色
2. 失败 → 反射 `getIdentifier("system_accent1_600", "color", "android")`（API 31+）
3. 再失败 → 固定色 `0xFF388E3C / 0xFF1B5E20 / 0xFF81C784`

**生效条件**：宿主 `AndroidManifest.xml` 的 `<application>` 主题必须是系统 `DeviceDefault` 系列（不要用死板的 `Theme.Light`），且 `targetSdkVersion` 不能太低（本库设为 30）。低版本设备自动走固定色兜底，不会崩。

---

## 编译

工具链与原项目保持一致：

| 组件 | 版本 |
|---|---|
| AGP | 3.6.3 |
| Gradle | 5.6.4 |
| JDK | 8 |
| compileSdk / targetSdk | 30 |
| buildTools | 30.0.3 |
| minSdk | 10 |

本地：

```bash
gradle :uikit:assembleRelease
gradle :app:assembleDebug
```

云端：推送到 GitHub 的 `main`/`master` 自动触发 `.github/workflows/build.yml`，产出 `uikit-aar` 与 `uikit-demo-apk` 两个 artifact。推送 `v*` tag 自动发 Release。

---

## 架构局限性与风险提示

1. **API 代沟**：表冠事件需 API 26+，`ViewPropertyAnimator` 需 API 12+，`createConfigurationContext` 需 API 17+，代码已做版本判断与降级。
2. **动态取色依赖宿主主题**：宿主若使用非 `DeviceDefault` 主题，MD3 取色会退化为固定色。
3. **纯手写反射**：`UikitColor` 依赖 `system_accent1_*` 资源名，非标准 ROM 可能取不到，已做 fallback。
4. **低性能模式**：3D 滚轮、入场动画、滑动返回动画在低端设备上可能掉帧，可调用 `UikitConfig.setLowPerf(ctx, true)` 降级。

---

## 关联文档

- [CHANGELOG.md](CHANGELOG.md) —— 版本更新记录
- [CONTRIBUTING.md](CONTRIBUTING.md) —— 贡献指南与提交规范
- [docs/AI_DISCLOSURE.md](docs/AI_DISCLOSURE.md) —— AI 生成声明与参与细节
- [docs/DEVELOPMENT_CONSTRAINTS.md](docs/DEVELOPMENT_CONSTRAINTS.md) —— 开发约束与测试覆盖说明
- [WatchSend 原项目](https://github.com/styisme/watchsend) —— 本库的来源

---

## 协议

本项目使用 [MIT License](LICENSE)。
