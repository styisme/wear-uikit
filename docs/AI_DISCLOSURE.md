# AI 生成说明

本库（**Uikit**）从 [WatchSend](https://github.com/styisme/watchsend) 抽取通用 UI 能力，剥离业务耦合后独立发布。抽取、重构、文档撰写过程中大量使用大型语言模型（LLM）辅助。

本说明遵循 [AI 开发规范](https://github.com/styisme/ai-development-guidelines)，透明界定 AI 与人类的实际贡献比例与责任边界。

相关文档：[README](../README.md) | [CHANGELOG](../CHANGELOG.md) | [贡献指南](../CONTRIBUTING.md) | [开发约束](DEVELOPMENT_CONSTRAINTS.md)。

---

## AI 深度参与的部分

- **组件抽取与重构**：
  - 将原 `WatchUiUtils` 拆分为 `UikitUi` + `UikitTheme` + `WearHardwareUtils` + `UikitColor`，降低耦合。
  - 将原 `Md3Switch` 硬编码颜色改为 `UikitColor.getPrimaryColor()`，随动态取色变化。
  - 将原 `I18nManager.get()` 硬编码多语言替换为 Android `R.string.uikit_*`。
  - 将原 `"oldsend"` SharedPreferences 硬编码改为 `UikitConfig.init()` 可注入。
- **MD3 动态取色三层兜底**：`colorAccent` → `system_accent1_*` 反射 → 固定色。
- **物理级缩放**：`createConfigurationContext` 绕过 Android 11+ 对 `updateConfiguration` 的拦截。
- **API 降级封装**：`VibrationEffect`（API 26+）、`ViewPropertyAnimator`（API 12+）、`createConfigurationContext`（API 17+）、`isRound`（API 28+）的版本判断与 fallback。
- **CI/CD 流水线**：`build.yml` 实现 push/PR 自动编译、`v*` tag 自动发 Release。

---

## 人类参与的部分

- **需求定义**：抽取哪些组件、保留哪些、剥离哪些。
- **API 兼容性取舍**：minSdk 10 与动态取色（API 31+）的共存策略。
- **真机验证**：在真实手表与手机上验证缩放、滑动返回、表冠、震动、深色模式。
- **文档审校**：确保 AI 生成的文档与代码一致，避免夸大。
- **发布决策**：版本号、tag、Release 时机。

---

## 风险提示

1. **反射脆弱性**：`UikitColor` 依赖 `system_accent1_*` 资源名，非标准 ROM 可能取不到，已做 fallback。
2. **宿主主题依赖**：动态取色要求宿主使用 `DeviceDefault` 主题，否则退化为固定色。
3. **API 代沟**：表冠、`ViewPropertyAnimator`、`createConfigurationContext` 均需特定 API 版本，低版本已降级但功能受限。
4. **性能**：3D 滚轮、入场动画、滑动返回在低端设备上可能掉帧，提供 `LowPerf` 模式降级。

---

## 许可

本项目代码使用 [MIT 协议](../LICENSE)。
