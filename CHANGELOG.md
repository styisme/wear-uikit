# Changelog

本文件记录版本更新历史，格式基于 [Keep a Changelog](https://keepachangelog.com/zh-CN/1.0.0/)，版本号遵循 [Semantic Versioning](https://semver.org/lang/zh-CN/)。

## [Unreleased]

### Planned
- `UikitDatePicker` 手表专属日期/时间滚轮选择器。
- `UikitChart` 极简柱状图/折线图。

---

## [1.3.0] - 2026-11-10

本次更新全面重构了弹窗交互，推出了全新的全屏交互体系，并彻底废弃了旧版弹窗。

### Added
- **`UikitFullTextActivity`**：全屏长文本展示。通过句子拆分与 3D 滚轮特效，实现如科幻电影字幕般的沉浸式阅读体验。
- **`UikitFullInputActivity`**：全屏文本输入。完美适配 Wear OS 键盘弹起，支持自定义“确定/取消”按钮文案。
- **`UikitFullListActivity`**：全屏列表选择。支持双行显示（标题+副标题）、每个卡片独立自定义背景色，并自动计算文字对比度。
- **GitHub 社区模板**：加入了标准的 Issue 模板（Bug 与 Feature）以及 PR 模板。

### Deprecated
- 标记 `UikitUi.showList()`、`UikitUi.showInput()`、`UikitUi.showConfirm()` 为废弃状态（`@Deprecated`）。在 Demo 与 IDE 中会显示废弃警告，引导开发者迁移至全屏组件。

### Fixed
- 彻底修复了底部抽屉在圆屏上因全屏遮挡导致无法点击关闭的致命 Bug。
- 修复了英文长文本中撇号（`'`）导致 AAPT 编译崩溃的问题。
- 修复了全屏输入页面底部漏出黑色背景的问题。

---

## [1.2.0] - 2026-11-10

本次更新大幅扩充了高频 UI 组件，加入了自定义颜色 API 与多语言支持。

### Added
- `UikitListItem`、`UikitProgressBar`。
- 自定义 MD3 颜色 API：`UikitConfig.setCustomPrimaryColor()` 与 `setCustomAccentColor()`。
- Demo 支持中英双语动态切换。

### Fixed
- 弹窗输入框背景与确认/取消按钮全面适配深色模式与自定义主题色。
- 增加圆屏专属弹窗排版（缩窄宽度，按钮改为竖向排列）。
- 修复了 `UikitListItem` 缺失 `CompoundButton` 导入导致的编译失败。

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

[Unreleased]: https://github.com/styisme/wear-uikit/compare/v1.3.0...HEAD
[1.3.0]: https://github.com/styisme/wear-uikit/compare/v1.2.0...v1.3.0
[1.2.0]: https://github.com/styisme/wear-uikit/compare/v1.1.0...v1.2.0
[1.1.0]: https://github.com/styisme/wear-uikit/compare/v1.0.0...v1.1.0
[1.0.0]: https://github.com/styisme/wear-uikit/releases/tag/v1.0.0
