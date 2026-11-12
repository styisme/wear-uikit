# Changelog

本文件记录版本更新历史，格式基于 [Keep a Changelog](https://keepachangelog.com/zh-CN/1.0.0/)，版本号遵循 [Semantic Versioning](https://semver.org/lang/zh-CN/)。

## [Unreleased]

### Planned
- 暂无。

---

## [1.6.0] - 2026-11-12

质感飞跃更新。为滚动视图加入了物理级边缘阻尼回弹，彻底重构了 UikitButton 的底层架构，并补齐了表单与微交互组件。

### Added
- **边缘阻尼回弹特效 (Bouncing)**：集成在 `UikitUi.setupWheelScroll` 中，当上下滚动到顶部/底部极限时，继续拖拽会产生物理级的阻尼拉伸，松手后弹性恢复。
- **表单组件**：`UikitCheckBox`（复选框）、`UikitRadioButton`（单选按钮）、`UikitStepper`（步进器）。
- **微交互组件**：`UikitMarqueeTextView`（自动滚动的跑马灯文字）、`UikitCircularProgressTimer`（带结束震动的环形倒计时）。

### Fixed
- **UikitButton 架构重构**：彻底由 `TextView` 重构为 `LinearLayout`，解决了低版本（API 10-23）设置图标无法居中、以及外层包裹 FrameLayout 导致点击动画卡死（ACTION_UP 丢失）的严重 Bug。现在完美支持通过 `addView` 嵌套任意子控件。
- **全屏长文本智能断句**：重写了 `UikitFullTextActivity` 的正则表达式，现在能够精准排除数字小数点（如 2.3）和网址（如 .com），不再误伤英文长句。
- **废弃 API 标注**：旧版弹窗在 Demo 中彻底加上废弃标注与弃用警告。

---

## [1.5.0] - 2026-11-12

本次更新提供了开箱即用的图标库与 XML 支持。由于内置资源较多，导致 `.aar` 体积显著增加。

### Added
- **36 个内置 MD3 矢量图标**：如 `ic_uikit_home`, `ic_uikit_wifi` 等，直接内置在库中供开发者使用。
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
- **`UikitFullTextActivity`**：全屏长文本展示，按句子拆分，应用 3D 滚轮特效。
- **`UikitFullInputActivity`**：全屏文本输入，完美适配 Wear OS 键盘弹起。
- **`UikitFullListActivity`**：全屏列表选择，支持双行显示与自定义卡片背景色。

### Deprecated
- 标记 `UikitUi.showList()`、`UikitUi.showInput()`、`UikitUi.showConfirm()` 为废弃状态（`@Deprecated`）。

### Fixed
- 彻底修复了底部抽屉在圆屏上因全屏遮挡导致无法点击关闭的致命 Bug。
- 修复了英文长文本中撇号（`'`）导致 AAPT 编译崩溃的问题。
- 修复了全屏输入页面底部漏出黑色背景的问题。

---

## [1.2.0] - 2026-11-10

本次更新大幅扩充了高频 UI 组件，加入了自定义颜色 API 与多语言支持。

### Added
- **`UikitListItem`**：标准设置页列表项，支持副标题、开关或箭头。
- **`UikitProgressBar`**：横向进度条。
- **自定义 MD3 颜色**：新增 `UikitConfig.setCustomPrimaryColor()` 与 `setCustomAccentColor()`。
- **多语言支持**：Demo 支持中英双语动态切换。

### Fixed
- 弹窗输入框背景与确认/取消按钮全面适配深色模式与自定义主题色。
- 增加圆屏专属弹窗排版（缩窄宽度，按钮改为竖向排列）。
- 修复了 `UikitListItem` 缺失 `CompoundButton` 导入导致的编译失败。

### Changed
- `DemoActivity` 重构为 API 测试大厅，所有组件均附带对应的 API 名称提示。

---

## [1.1.0] - 2026-11-08

### Added
- **`UikitProgressRing`**：环形进度条。
- **`UikitSkeleton`**：骨架屏加载占位。
- **`UikitToast`**：防切边气泡提示。
- **`UikitBottomSheet`**：底部抽屉。
- **`UikitColorMixer`**：颜色混合工具。
- **`UikitScreenUtils`**：屏幕工具。

### Fixed
- 修复底部抽屉在圆屏上撑满全屏导致无法关闭的致命 Bug。

---

## [1.0.0] - 2026-11-08

首个正式版本。从 WatchSend 抽取通用 UI 能力，剥离业务耦合。

### Added
- 核心架构：`UikitConfig`、`UikitColor`、`UikitTheme`、`UikitUi`、`WearHardwareUtils`。
- 物理级 UI 缩放（绕过 Android 11+ 限制）。
- 滑动返回与预测性返回动画。
- 3D 滚轮特效与圆屏边距避让。
- GitHub Actions 自动编译与发版流水线。

---

## 版本对比链接

[Unreleased]: https://github.com/styisme/wear-uikit/compare/v1.6.0...HEAD
[1.6.0]: https://github.com/styisme/wear-uikit/compare/v1.5.0...v1.6.0
[1.5.0]: https://github.com/styisme/wear-uikit/compare/v1.4.1...v1.5.0
[1.4.1]: https://github.com/styisme/wear-uikit/compare/v1.4.0...v1.4.1
[1.4.0]: https://github.com/styisme/wear-uikit/compare/v1.3.0...v1.4.0
[1.3.0]: https://github.com/styisme/wear-uikit/compare/v1.2.0...v1.3.0
[1.2.0]: https://github.com/styisme/wear-uikit/compare/v1.1.0...v1.2.0
[1.1.0]: https://github.com/styisme/wear-uikit/compare/v1.0.0...v1.1.0
[1.0.0]: https://github.com/styisme/wear-uikit/releases/tag/v1.0.0
