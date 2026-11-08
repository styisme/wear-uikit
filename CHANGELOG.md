# Changelog

本文件记录版本更新历史，格式基于 [Keep a Changelog](https://keepachangelog.com/zh-CN/1.0.0/)，版本号遵循 [Semantic Versioning](https://semver.org/lang/zh-CN/)。

## [Unreleased]

### Planned
- `UikitDatePicker` 手表专属日期/时间滚轮选择器。
- `UikitChart` 极简柱状图/折线图。

---

## [1.1.0] - 2026-11-08

本次更新补齐了完整的 UI 基建能力，新增 8 个高阶组件与工具类，并彻底解决了弹窗卡死痛点。

### Added
- **`UikitButton`**：标准 MD3 胶囊按钮，自动适配动态主题色与反色文字。
- **`UikitProgressRing`**：手绘环形进度条，完美适配圆屏，支持圆角线帽。
- **`UikitSkeleton`**：纯代码实现微光闪烁骨架屏，自带加载动画。
- **`UikitToast`**：替代系统 Toast，解决手表端被切边、不跟随主题的问题。
- **`UikitBottomSheet`**：底部抽屉弹窗，支持下拉拖拽关闭。
- **`UikitEmptyView`**：标准空状态占位视图（👻 图标 + 提示语）。
- **`UikitColorMixer`**：提供 `withAlpha` 与 `blend` 颜色工具。
- **`UikitScreenUtils`**：提供屏幕宽高获取、`dp2px`、方/圆屏判断。

### Fixed
- **`UikitBottomSheet` 圆屏卡死**：修复了在手表圆盘上弹窗撑满全屏导致无法点击外部关闭的问题。增加 64dp 底部安全区，并补全了全局 `Close` 按钮、下拉拖拽手势、系统返回键拦截。

### Changed
- `DemoActivity` 重构为测试大厅，集成所有新老组件演示。
- 替换 Demo 默认机器人图标，更新为 Uikit 专属矢量 Logo。

---

## [1.0.0] - 2026-11-08

首个正式版本。从 WatchSend 抽取通用 UI 能力，剥离业务耦合，改为可独立复用的 library。

### Added
- `UikitConfig`、`UikitColor`、`UikitTheme`、`UikitUi`、`UikitSwitch`、`WearHardwareUtils`。
- 物理级 UI 缩放（绕过 Android 11+ 限制）。
- 滑动返回与预测性返回动画。
- 3D 滚轮特效与圆屏边距避让。
- GitHub Actions 自动编译与发版流水线。

---

## 版本对比链接

[Unreleased]: https://github.com/styisme/wear-uikit/compare/v1.1.0...HEAD
[1.1.0]: https://github.com/styisme/wear-uikit/compare/v1.0.0...v1.1.0
[1.0.0]: https://github.com/styisme/wear-uikit/releases/tag/v1.0.0
