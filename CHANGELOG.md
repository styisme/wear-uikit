# Changelog

本文件记录版本更新历史，格式基于 [Keep a Changelog](https://keepachangelog.com/zh-CN/1.0.0/)，版本号遵循 [Semantic Versioning](https://semver.org/lang/zh-CN/)。

## [Unreleased]

### Planned
- 暂无。

---

## [1.7.0] - 2026-11-13

吸收社区 Feedback，针对老旧项目适配、现代 UI 扩展与极低配设备优化做出重大更新。

### Added
- **`UikitIconButton`**：纯图标按钮。完美契合播放器控制栏。
- **`UikitLinearList`**：轻量级列表容器。方便在 ScrollView 中嵌套标准列表项。
- **`UikitThemePresets`**：提供 6 种 MD3 预设主题色（Ocean Blue, Sunset Red 等），一键解决老设备无动态取色的痛点。
- **GitHub Release 附带 AAR**：CI 现在会直接把编译好的 `.aar` 挂在 Releases，方便无外网或 JitPack 抽风时手动下载依赖。

### Fixed
- **彻底修正文档包名**：全面清洗文档中的 `com.styisme.uikit`，统一为实际源码包名 `org.uikit`。**（绝对保证代码兼容，无需修改任何代码）**
- **补充原生继承说明**：在文档中明确 `UikitCheckBox` / `UikitRadioButton` 继承自原生类，支持无缝替换。

### Docs
- 补充了极低配置设备的性能优化建议（`setLayerType` 降级）。

---

## [1.6.0] - 2026-11-12

质感飞跃更新。为滚动视图加入了物理级边缘阻尼回弹，彻底重构了 UikitButton 的底层架构，并补齐了表单与微交互组件。

### Added
- **边缘阻尼回弹特效 (Bouncing)**：当上下滚动到极限时继续拖拽，会产生物理级的阻尼拉伸，松手弹性恢复。
- **表单组件**：`UikitCheckBox`、`UikitRadioButton`、`UikitStepper`。
- **微交互组件**：`UikitMarqueeTextView`（跑马灯）、`UikitCircularProgressTimer`（倒计时环）。

### Fixed
- **UikitButton 架构重构**：由 `TextView` 重构为 `LinearLayout`，解决了低版本设置图标无法居中、以及事件丢失导致动画卡死的 Bug。
- **全屏长文本智能断句**：精准排除数字小数点（如 2.3）和网址（如 .com）。

---

## [1.5.0] - 2026-11-12

开箱即用的图标库与 XML 支持。

### Added
- **36 个内置 MD3 矢量图标**。
- **UikitButton 支持完整 XML 属性** 与内嵌图标染色。
- **全新 `UikitPicker`**。
- **全量中英双语**。

### Fixed
- 修复全屏长文本网址断裂问题。
- 修复图片查看器拖拽与居中。

---

## [1.4.1] - 2026-11-12

修复 JitPack 发布流程。

### Fixed
- **JitPack 构建失败**：补全 `gradle-wrapper.properties`，锁定 Gradle 5.6.4，并新增 `jitpack.yml` 指定 `openjdk8`。

---

## [1.4.0] - 2026-11-12

高阶组件爆发。

### Added
- `UikitFullConfirmActivity`、`UikitImageViewerActivity`、`UikitDatePickerActivity`、`UikitTabView`、`UikitLoadingView`、`UikitBadge`、`UikitDivider`、`UikitClickUtils`。

---

## [1.3.0] - 2026-11-10

全屏交互新时代。

### Added
- `UikitFullTextActivity`、`UikitFullInputActivity`、`UikitFullListActivity`。

### Deprecated
- 标记 `showList()` / `showInput()` / `showConfirm()` 为废弃状态。

### Fixed
- 修复底部抽屉圆屏卡死问题。

---

## [1.2.0] - 2026-11-10

高频组件扩充。

### Added
- `UikitListItem`、`UikitProgressBar`。
- 自定义 MD3 颜色 API。

---

## [1.1.0] - 2026-11-08

### Added
- `UikitProgressRing`、`UikitSkeleton`、`UikitToast`、`UikitBottomSheet` 等。

---

## [1.0.0] - 2026-11-08

首个正式版本。从 WatchSend 抽取通用 UI 能力，剥离业务耦合。

### Added
- `UikitConfig`、`UikitColor`、`UikitTheme`、`UikitUi`、`WearHardwareUtils`。
- 物理级 UI 缩放、滑动返回、3D 滚轮特效。

---

## 版本对比链接

[Unreleased]: https://github.com/styisme/wear-uikit/compare/v1.7.0...HEAD
[1.7.0]: https://github.com/styisme/wear-uikit/compare/v1.6.0...v1.7.0
[1.6.0]: https://github.com/styisme/wear-uikit/compare/v1.5.0...v1.6.0
[1.5.0]: https://github.com/styisme/wear-uikit/compare/v1.4.1...v1.5.0
[1.4.1]: https://github.com/styisme/wear-uikit/compare/v1.4.0...v1.4.1
[1.4.0]: https://github.com/styisme/wear-uikit/compare/v1.3.0...v1.4.0
[1.3.0]: https://github.com/styisme/wear-uikit/compare/v1.2.0...v1.3.0
[1.2.0]: https://github.com/styisme/wear-uikit/compare/v1.1.0...v1.2.0
[1.1.0]: https://github.com/styisme/wear-uikit/compare/v1.0.0...v1.1.0
[1.0.0]: https://github.com/styisme/wear-uikit/releases/tag/v1.0.0
