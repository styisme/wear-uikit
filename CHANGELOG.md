# Changelog

本文件记录版本更新历史，格式基于 [Keep a Changelog](https://keepachangelog.com/zh-CN/1.0.0/)，版本号遵循 [Semantic Versioning](https://semver.org/lang/zh-CN/)。

## [Unreleased]

### Planned
- 暂无。

---

## [1.4.0] - 2026-11-12

史诗级扩充。一次性补齐了 8 个高阶组件，正式成为一个功能完备的 Wear OS 生态级 UI 库。

### Added
- **`UikitFullConfirmActivity`**：极简全屏确认框，底部两个大圆按钮。
- **`UikitImageViewerActivity`**：全屏图片查看器，支持 FitCenter 居中、双指缩放与单指拖拽。
- **`UikitDatePickerActivity`**：年月日三列滚轮日期选择器，支持前后 50 年。
- **`UikitTabView`**：分段控制器（Tab 切换）。
- **`UikitLoadingView`**：三点呼吸 Loading 动画。
- **`UikitBadge`**：小红点/数字角标。
- **`UikitDivider`**：标准分割线。
- **`UikitClickUtils`**：防手抖连击工具。

### Fixed
- 彻底重构了 Demo，所有文案全面支持中英双语动态切换。
- 修复了图片查看器无法拖拽和不居中的问题。
- 修复了日期选择器年份只能选未来的硬伤。

---

## [1.3.0] - 2026-11-10

### Added
- `UikitFullTextActivity` 全屏长文本（3D 滚轮字幕）。
- `UikitFullInputActivity` 全屏输入。
- `UikitFullListActivity` 全屏列表（双行、自定义卡片色）。

### Deprecated
- 废弃 `UikitUi.showList()`、`showInput()`、`showConfirm()`。

### Fixed
- 修复圆屏弹窗卡死与输入框黑底问题。

---

## 版本对比链接

[Unreleased]: https://github.com/styisme/wear-uikit/compare/v1.4.0...HEAD
[1.4.0]: https://github.com/styisme/wear-uikit/compare/v1.3.0...v1.4.0
[1.3.0]: https://github.com/styisme/wear-uikit/compare/v1.2.1...v1.3.0
