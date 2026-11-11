# Changelog

本文件记录版本更新历史，格式基于 [Keep a Changelog](https://keepachangelog.com/zh-CN/1.0.0/)。

## [Unreleased]

### Planned
- 暂无。

---

## [1.4.1] - 2026-11-12

本次更新重点修复了 JitPack 的发布流程，补齐了缺失的构建环境说明文档。

### Fixed
- **JitPack 构建失败**：补全了 `gradle/wrapper/gradle-wrapper.properties`，强制 JitPack 使用 Gradle 5.6.4 编译，彻底解决了因 JitPack 默认使用 Gradle 8.9 导致 AGP 3.6.3 崩溃的问题。
- 新增了 `jitpack.yml` 明确指定 `openjdk8`。

### Docs
- `README.md` 与 `README_EN.md` 中补充了「构建环境限制」章节，明确警告开发者不要升级 Gradle。
- `CONTRIBUTING.md` 中同步了环境限制。

---

## [1.4.0] - 2026-11-12

史诗级扩充。一次性补齐了 8 个高阶组件，正式成为一个功能完备的 Wear OS 生态级 UI 库。

### Added
- **`UikitFullConfirmActivity`**：极简全屏确认框。
- **`UikitImageViewerActivity`**：全屏图片查看器（支持缩放+拖拽）。
- **`UikitDatePickerActivity`**：年月日三列滚轮选择器。
- **`UikitTabView`**：分段控制器。
- **`UikitLoadingView`**：三点呼吸 Loading 动画。
- **`UikitBadge`**：小红点/数字角标。
- **`UikitDivider`**：标准分割线。
- **`UikitClickUtils`**：防手抖连击工具。

### Fixed
- Demo 全面支持中英双语。
- 修复了图片查看器无法拖拽和不居中的问题。
- 修复了日期选择器年份只能选未来的硬伤。

---

## [1.3.0] - 2026-11-10

### Added
- `UikitFullTextActivity`、`UikitFullInputActivity`、`UikitFullListActivity`。

### Deprecated
- 废弃 `UikitUi.showList()`、`showInput()`、`showConfirm()`。

### Fixed
- 修复圆屏弹窗卡死与输入框黑底问题。

---

## 版本对比链接

[Unreleased]: https://github.com/styisme/wear-uikit/compare/v1.4.1...HEAD
[1.4.1]: https://github.com/styisme/wear-uikit/compare/v1.4.0...v1.4.1
[1.4.0]: https://github.com/styisme/wear-uikit/compare/v1.3.0...v1.4.0
