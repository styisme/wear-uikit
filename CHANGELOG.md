# Changelog

本文件记录版本更新历史，格式基于 [Keep a Changelog](https://keepachangelog.com/zh-CN/1.0.0/)，版本号遵循 [Semantic Versioning](https://semver.org/lang/zh-CN/)。

## [Unreleased]

### Planned
- `UikitDatePicker` 手表专属日期/时间滚轮选择器。
- `UikitChart` 极简柱状图/折线图。

---

## [1.2.0] - 2026-11-08

本次更新大幅扩充了高频 UI 组件，加入了自定义颜色 API 与多语言支持，并彻底重写了 Demo 作为测试大厅。

### Added
- **`UikitListItem`**：标准设置页列表项，支持副标题，右侧可挂载 `UikitSwitch` 或箭头图标。
- **`UikitProgressBar`**：横向进度条，自动跟随动态取色，适配小屏幕。
- **自定义 MD3 颜色**：新增 `UikitConfig.setCustomPrimaryColor()` 与 `setCustomAccentColor()`，允许宿主覆盖动态取色。
- **多语言支持**：Demo 现已支持中英双语动态切换（基于 Android 原生资源）。

### Fixed
- 弹窗输入框背景与确认/取消按钮全面适配深色模式与自定义主题色。
- 增加圆屏专属弹窗排版（缩窄宽度，按钮改为竖向排列），彻底解决圆屏被切割卡死的问题。
- 修复了 `UikitListItem` 缺失 `CompoundButton` 导入导致的编译失败。

### Changed
- `DemoActivity` 重构为 API 测试大厅，所有组件均附带对应的 API 名称提示。
- README 新增 JitPack 引入方式，支持一行依赖引入。

---

## [1.1.0] - 2026-11-08

### Added
- `UikitProgressRing` 环形进度条、`UikitSkeleton` 骨架屏、`UikitToast`、`UikitBottomSheet` 底部抽屉。
- `UikitColorMixer` 颜色混合工具、`UikitScreenUtils` 屏幕工具。

### Fixed
- 修复底部抽屉在圆屏上撑满全屏导致无法关闭的致命 Bug。

---

## [1.0.0] - 2026-11-08

首个正式版本。从 WatchSend 抽取通用 UI 能力，剥离业务耦合。

### Added
- 核心架构：`UikitConfig`、`UikitColor`、`UikitTheme`、`UikitUi`、`WearHardwareUtils`。
- 物理级 UI 缩放、滑动返回、3D 滚轮、圆屏避让。

---

## 版本对比链接

[Unreleased]: https://github.com/styisme/wear-uikit/compare/v1.2.0...HEAD
[1.2.0]: https://github.com/styisme/wear-uikit/compare/v1.1.0...v1.2.0
[1.1.0]: https://github.com/styisme/wear-uikit/compare/v1.0.0...v1.1.0
[1.0.0]: https://github.com/styisme/wear-uikit/releases/tag/v1.0.0
