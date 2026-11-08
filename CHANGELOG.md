# Changelog

本文件记录版本更新历史，格式基于 [Keep a Changelog](https://keepachangelog.com/zh-CN/1.0.0/)，版本号遵循 [Semantic Versioning](https://semver.org/lang/zh-CN/)。

> **关于溯源**：本库从 [WatchSend](https://github.com/styisme/watchsend) 抽取通用 UI 能力，剥离业务耦合后独立发布。版本号从 `v1.0.0` 重新开始计算。

## [Unreleased]

### Planned
- `UikitBottomSheet` 底部抽屉组件。
- `UikitToast` 统一 Toast 封装。
- `UikitProgressRing` 圆屏环形进度组件。

---

## [1.0.0] - 2026-11-08

首个正式版本。从 WatchSend 抽取通用 UI 能力，剥离 `I18nManager`、`OldSendService`、设备模型等业务耦合，改为可独立复用的 library。

### Added
- **`UikitConfig`**：配置层，可注入宿主 SharedPreferences，统一管理主题、缩放、动态取色、低性能、圆屏形状、下巴避让等开关。
- **`UikitColor`**：MD3 动态取色，API 31+ 反射 `system_accent1_*`，低版本自动降级为固定色。
- **`UikitTheme`**：三态深色模式（auto/light/dark）+ 递归着色兜底，兼容旧数据。
- **`UikitSwitch`**：MD3 自绘开关，`ValueAnimator` 动画，颜色随 `UikitColor` 变化，兼容 API 10+。
- **`UikitUi`**：物理级 UI 缩放（`createConfigurationContext`，绕过 Android 11+ 限制）、药丸卡片背景、`createBaseDialog` / `showList` / `showInput` / `showConfirm` 对话框、滑动返回、3D 滚轮、入场动画。
- **`WearHardwareUtils`**：震动（`VibrationEffect` 降级）、表冠旋转事件、屏幕常亮、圆屏判断、下巴避让。
- **通用 XML 资源**：卡片、按钮、输入框、开关、对话框布局。
- **Demo 模块**：`app/` 演示全部组件与开关。
- **GitHub Actions**：push/PR 自动编译，`v*` tag 自动发 Release。

### Fixed
- 剥离原项目 `I18nManager.get()` 硬编码多语言，改用 Android `R.string.uikit_*`。
- 剥离原项目 `"oldsend"` SharedPreferences 硬编码，改为 `UikitConfig.init()` 可注入。

### Changed
- 原 `Md3Switch` 颜色硬编码改为 `UikitColor.getPrimaryColor()`。
- 原 `WatchUiUtils` 拆分为 `UikitUi` + `UikitTheme` + `WearHardwareUtils` + `UikitColor`，互相低耦合。

---

## 版本对比链接

[Unreleased]: https://github.com/styisme/wear-uikit/compare/v1.0.0...HEAD
[1.0.0]: https://github.com/styisme/wear-uikit/releases/tag/v1.0.0
