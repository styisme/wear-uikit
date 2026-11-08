# 贡献指南

**简体中文** | [English](CONTRIBUTING_EN.md)

感谢参与 **Uikit** 的开发！本项目采用轻量但明确的源代码管理规范：**Conventional Commits + SemVer 2.0.0 + CHANGELOG**。

> **关于渊源**：Uikit 从 [WatchSend](https://github.com/styisme/watchsend) 抽取通用 UI 能力，剥离业务耦合后独立发布。相关文档：[README](README.md) | [CHANGELOG](CHANGELOG.md) | [AI 披露](docs/AI_DISCLOSURE.md) | [开发约束](docs/DEVELOPMENT_CONSTRAINTS.md)。

---

## 关于 AI 生成代码

遵循 [AI 开发规范](https://github.com/styisme/ai-development-guidelines)，本库的绝大多数代码由 **AI 辅助生成**。

提交 PR 时请务必：
- 说明改动是否由 AI 生成。
- **人工验证过功能**（必须在真机或模拟器上跑过一次，不能只看编译通过）。
- 如果你也是用 AI 改的，请在 PR 描述里注明使用的模型和提示词方向。

---

## 本地开发

### 环境要求

| 组件 | 版本 |
|---|---|
| JDK | 8 |
| Gradle | 5.6.4 |
| AGP | 3.6.3 |
| Android SDK | compileSdk 30, buildTools 30.0.3 |

### 编译命令

```bash
git clone https://github.com/styisme/wear-uikit.git
cd wear-uikit
gradle :uikit:assembleRelease   # 产出 AAR
gradle :app:assembleDebug       # 产出 Demo APK
```

### 云端构建

- 推送 `main`/`master` 自动编译，产出 `uikit-aar` 与 `uikit-demo-apk`。
- 推送 `v*` tag 自动创建 GitHub Release。
- 需要仓库 Settings → Actions → General → Workflow permissions 选 **Read and write permissions**，否则 Release job 会 403。

---

## 项目结构

```text
wear-uikit/
├── uikit/
│   ├── src/main/java/org/uikit/
│   │   ├── UikitConfig.java      # 配置层
│   │   ├── UikitColor.java       # MD3 动态取色
│   │   ├── UikitTheme.java       # 三态深色模式
│   │   ├── UikitSwitch.java      # MD3 自绘开关
│   │   ├── UikitUi.java          # 缩放/卡片/对话框/滑动返回/3D滚轮
│   │   └── WearHardwareUtils.java # 硬件封装
│   └── src/main/res/             # drawable / layout / values
├── app/                          # Demo
├── .github/workflows/build.yml   # CI
├── docs/                         # AI 披露 / 开发约束
└── README.md / README_EN.md
```

---

## 提交规范

使用 [Conventional Commits](https://www.conventionalcommits.org/zh-hans/)：

```
<type>(<scope>): <subject>
```

### 允许的 type
- `feat` / `fix` / `docs` / `style` / `refactor` / `perf` / `build` / `ci` / `chore`

### 常用 scope
- `ui`：界面、样式
- `color`：动态取色
- `theme`：深色模式
- `widget`：自定义控件
- `wear`：手表硬件
- `ci`：CI 配置

### 示例
```
feat(color): 支持 Android 12+ MD3 动态取色
fix(ui): 修复滑动返回在低性能模式下的卡顿
ci: 增加 AAR 产物上传
```

---

## 分支模型

| 分支 | 用途 |
|---|---|
| `main` | 稳定分支 |
| `feat/xxx` | 功能分支 |
| `fix/xxx` | 修复分支 |
| `docs/xxx` | 文档分支 |

---

## Pull Request 流程

1. Fork 仓库
2. 新建分支：`git checkout -b feat/your-feature`
3. 修改代码并本地 / Actions 验证
4. 提交时用 Conventional Commits
5. Push 并发起 PR
6. 等 CI 通过 + Review

### PR 描述应包含

```markdown
## 变更内容
简述做了什么

## 变更类型
- [ ] feat / fix / refactor / docs / chore

## 关联 Issue
Closes #123

## 测试方式
- [ ] 本地编译通过
- [ ] 真机/模拟器验证（请注明设备型号与 Android 版本）
- [ ] Actions 编译通过

## 是否使用 AI 生成
- [ ] 是（请说明模型和人工验证内容）
- [ ] 否
```

---

## 版本号规则

遵循 [Semantic Versioning 2.0.0](https://semver.org/lang/zh-CN/)：

| 变更 | 版本号递增 |
|---|---|
| 不兼容变更 | **MAJOR** |
| 向后兼容的新功能 | **MINOR** |
| Bug 修复 | **PATCH** |

⚠️ **重要**：版本号以 `CHANGELOG.md` 顶部的最新版本为准，打 tag 时与 `CHANGELOG` 一致。CI 的 Release job 依赖 `v*` tag 触发。

---

## 常见问题排查

**Q: `wrapScale()` 在 Android 11+ 上无效？**
A: 已改用 `createConfigurationContext`，不再使用被拦截的 `updateConfiguration`。确保所有 Activity 都重写 `attachBaseContext` 并调用 `UikitUi.wrapScale(newBase)`。

**Q: 动态取色在 Android 12+ 取不到？**
A: 宿主 `AndroidManifest.xml` 的 `<application>` 主题必须是 `Theme.DeviceDefault.NoActionBar`，且 `targetSdkVersion` 不能太低（本库 30）。此外 `UikitConfig.isDynamicColorEnabled(ctx)` 必须为 `true`。

**Q: AAR 引入后 `R.id.dlg_root` 找不到？**
A: 对话框布局在 library 的 `org.uikit.R` 中。如果宿主有同名资源，会冲突。建议给宿主资源加前缀，或使用 `implementation project(':uikit')` 时通过 `org.uikit.R` 显式引用。

**Q: 表冠滑动无效？**
A: 需要 API 26+ 且设备有旋转表冠。`WearHardwareUtils.bindRotaryEncoder(scrollView)` 已做版本判断，低版本自动忽略。

**Q: 滑动返回与系统返回冲突？**
A: 在 `dispatchTouchEvent` 中优先调用 `UikitUi.handleSwipeBack()`，并在 `onBackPressed` 中通过 `UikitUi.isSystemBackBlocked()` 拦截系统返回。

**Q: CI Release job 报 403？**
A: 仓库 Settings → Actions → General → Workflow permissions 选 **Read and write permissions**。
