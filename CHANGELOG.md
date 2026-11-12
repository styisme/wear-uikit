# Changelog

本文件记录版本更新历史，格式基于 [Keep a Changelog](https://keepachangelog.com/zh-CN/1.0.0/)。

## [Unreleased]

### Planned
- 暂无。

---

## [1.5.0] - 2026-11-12

本次更新提供了开箱即用的图标库与 XML 支持。由于内置资源较多，导致 `.aar` 体积显著增加。如有极致体积要求，建议查看 README 的「版本建议」。

### Added
- **35 个内置 MD3 矢量图标**：如 `ic_uikit_home`, `ic_uikit_wifi` 等，直接内置在库中供开发者使用。
- **UikitButton 支持完整 XML 属性**：`app:uikit_radius`, `app:uikit_backgroundColor`, `app:uikit_textColor`。
- **UikitButton 支持内嵌图标美化**：自动根据文字颜色对内嵌的 `drawableStart` 进行染色并强制缩放至 18dp。
- **全新 `UikitPicker`**：上下滚动的 3D 居中滑动选择器。
- **全量中英双语**：所有 Demo 页面、内置组件均支持完整的中英文动态切换。

### Fixed
- 修复了全屏长文本强制拆分导致断句和网址断裂的问题（现默认整段展示，可通过 `autoSplit` 参数控制）。
- 修复了图片查看器无法拖拽和不居中的问题。
- 修复了图标因生命周期错误导致丢失的 Bug。
- 优化了全屏演示页与图标页的圆屏边距和 3D 滚轮适配。

---

## [1.4.1] - 2026-11-12

本次更新重点修复了 JitPack 的发布流程，补齐了缺失的构建环境说明文档。

### Fixed
- **JitPack 构建失败**：补全了 `gradle/wrapper/gradle-wrapper.properties`，强制 JitPack 使用 Gradle 5.6.4 编译，彻底解决了因 JitPack 默认使用 Gradle 8.9 导致 AGP 3.6.3 崩溃的问题。
- 新增了 `jitpack.yml` 明确指定 `openjdk8`。

---

## [1.4.0] - 2026-11-12

史诗级扩充。一次性补齐了 8 个高阶组件。

### Added
- **`UikitFullConfirmActivity`**：极简全屏确认框。
- **`UikitImageViewerActivity`**：全屏图片查看器（支持缩放+拖拽）。
- **`UikitDatePickerActivity`**：年月日三列滚轮选择器。
- **`UikitTabView`**：分段控制器。
- **`UikitLoadingView`**：三点呼吸 Loading 动画。
- **`UikitBadge`**：小红点/数字角标。
- **`UikitDivider`**：标准分割线。
- **`UikitClickUtils`**：防手抖连击工具。

---

## 版本对比链接

[Unreleased]: https://github.com/styisme/wear-uikit/compare/v1.5.0...HEAD
[1.5.0]: https://github.com/styisme/wear-uikit/compare/v1.4.1...v1.5.0
[1.4.1]: https://github.com/styisme/wear-uikit/compare/v1.4.0...v1.4.1
[1.4.0]: https://github.com/styisme/wear-uikit/compare/v1.3.0...v1.4.0
