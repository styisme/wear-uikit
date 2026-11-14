# 贡献指南

**简体中文** | [English](CONTRIBUTING_EN.md)

首先，感谢你愿意为 **Uikit** 贡献代码！无论你是修复了一个 Bug，还是新增了一个精巧的 MD3 组件，你的参与都让这个项目变得更好。

作为一个主打 **极低版本兼容** 与 **极致手表体验** 的 UI 库，我们对代码质量和适配标准有着严格的要求。请在提交 PR 前，务必仔细阅读以下指南。

---

## 📑 目录
1. [项目架构与源码结构](#1-项目架构与源码结构)
2. [本地开发环境搭建](#2-本地开发环境搭建)
3. [UI 组件开发规范（极度重要）](#3-ui-组件开发规范极度重要)
4. [代码与提交规范](#4-代码与提交规范)
5. [分支模型与发版流程](#5-分支模型与发版流程)
6. [Issue 与 Pull Request 规范](#6-issue-与-pull-request-规范)
7. [常见问题排查（FAQ）](#7-常见问题排查faq)

---

## 1. 项目架构与源码结构

本项目由两个核心模块组成：`uikit`（主库）与 `app`（Demo 测试大厅）。

```text
wear-uikit/
├── uikit/                     # 主库模块 (Library)
│   ├── src/main/java/org/uikit/
│   │   ├── UikitConfig.java   # 全局配置
│   │   ├── UikitColor.java    # 动态取色与颜色计算
│   │   ├── UikitButton.java   # 核心基础控件
│   │   └── ...                # 其他控件与 Activity
│   └── src/main/res/          # 库内部的 XML 资源与图标
├── app/                       # Demo 模块 (Application)
│   └── src/main/java/org/uikit/demo/ # 各个测试页面
├── .github/workflows/         # CI/CD 自动发版流水线
└── gradle/wrapper/            # 锁定版本的 Gradle Wrapper
```

**注意**：所有的组件逻辑只能写在 `uikit` 模块中。`app` 模块仅用于验证和展示。

---

## 2. 本地开发环境搭建

由于本库需兼容 Android 2.3 (API 10)，我们**永久锁死**了极其古老的构建工具链。**如果你使用新版 Android Studio（自带 JDK 17+），将无法直接编译本项目。**

### 必备环境
| 组件 | 版本要求 | 说明 |
|---|---|---|
| **JDK** | `Java 8` | 必须为 JDK 8，不支持 11 或 17 |
| **Gradle** | `5.6.4` | 已在 `gradle-wrapper.properties` 锁定 |
| **AGP** | `3.6.3` | Android Gradle Plugin 版本 |
| **SDK** | `compileSdk 30` / `buildTools 30.0.3` | - |

### 编译命令
不要使用 Android Studio 的一键 Run，请使用终端执行：
```bash
# 确保 JAVA_HOME 指向 JDK 8
export JAVA_HOME=/path/to/jdk8

# 编译库与 Demo
./gradlew :uikit:assembleRelease :app:assembleDebug
```

---

## 3. UI 组件开发规范（极度重要）

作为 UI 库，我们的每一行代码都会直接影响接入方的 App 体积与运行稳定性。请务必遵守：

### 3.1 颜色规范：严禁硬编码
- **禁止**在 Java 或 XML 中写死颜色值（如 `#FF0000`）。
- **必须**通过 `UikitColor.getPrimaryColor(ctx)` 或 `UikitColor.getAccentColor(ctx, isDark)` 获取颜色。
- 如果需要深浅色，请结合 `UikitTheme.isDarkMode(ctx)` 进行判断。
- 若需自定义 XML 属性，请在 `attrs.xml` 中提供 `format="color"` 的属性。

### 3.2 尺寸规范：严禁使用固定像素
- **禁止**使用 `px` 或固定的大数字。所有的边距、宽高必须通过 `UikitScreenUtils.dp2px(ctx, dp)` 转换。
- 对于圆屏，布局必须考虑 `UikitUi.applyRoundPadding(activity, rootView)` 适配。

### 3.3 兼容性与防御规范
- **防御性绘制**：所有重写 `onDraw` 的自绘控件，必须在第一行加上 `if (getWidth() == 0 || getHeight() == 0) return;`，以防止在极低版本上因为测量未完成导致底层 C++ 渲染崩溃。
- **动画限制**：禁止在 API 12 以下的设备调用 `view.animate()`，会引发 NPE 闪退。
- **原生代理**：如果你新增的基础控件对应原生控件（如 `ProgressBar`），**必须**在类中提供原生的高频方法代理（如 `setMax`、`setProgress` 等），以便老项目无缝替换。

### 3.4 响应式与交互规范
- 所有点击事件必须支持 `UikitClickUtils.isFastDoubleClick()` 防抖。
- 所有可点击控件必须支持按压缩放反馈效果。

---

## 4. 代码与提交规范

### 4.1 代码风格
- 遵循标准的 Java 命名规范（类名大驼峰，方法/变量小驼峰，常量全大写）。
- 所有类、公开方法必须包含 Javadoc 注释。
- **严禁使用 Lombok、AndroidX 库**以及任何第三方依赖（零依赖是本库的底线）。

### 4.2 Commit Message 规范
使用 [Conventional Commits](https://www.conventionalcommits.org/zh-hans/) 规范：
```
<type>(<scope>): <subject>
```
**允许的 type**：
- `feat`: 新功能
- `fix`: 修复 Bug
- `docs`: 文档修改
- `style`: 代码格式（不影响逻辑）
- `refactor`: 重构
- `perf`: 性能优化
- `ci`: CI 配置修改
- `chore`: 杂项（版本号、依赖等）

**常用 scope**：`ui`、`button`、`form`、`progress`、`theme`、`docs`、`ci`。

**示例**：
```
feat(ui): 新增 UikitIconButton 纯图标按钮
fix(button): 修复 UikitButton 在 API 23 下的空指针闪退
docs: 补全 API_ZH.md 中 36 个内置图标的图鉴说明
```

---

## 5. 分支模型与发版流程

### 5.1 分支模型
- `main`：稳定分支（保护分支，禁止直接 Push）。
- `feat/xxx`：新功能开发。
- `fix/xxx`：Bug 修复。

### 5.2 自动发版流程
本项目已经实现了全自动的 CI/CD 发版流水线。
**切勿手动修改 `app/build.gradle` 里的 versionCode 和 versionName！**

发版步骤：
1. 修改 `CHANGELOG.md`，在顶部加上新的版本号（如 `## [1.8.0] - 2026-12-01`）。
2. 提交 PR 并合并到 `main` 分支。
3. CI 会自动提取版本号、编译出 `.aar` 和 `.apk`、打上 `v1.8.0` 的 Tag，并将产物发布到 GitHub Releases 页面。

---

## 6. Issue 与 Pull Request 规范

### 6.1 Issue 模板
- **Bug 报告**：请附带测试机型的 **Android 版本、芯片型号**，以及崩溃日志（Logcat）。如果是 UI 错位，请附上截图。
- **功能建议**：请描述你的使用场景（例如：在手表上做音乐播放器需要什么控件），最好能提供草图或设计参考。

### 6.2 PR 规范
- 每个 PR 尽量只做一件事（一个功能或一个 Bug 修复）。
- PR 描述必须包含：变更内容、关联的 Issue（如果有）、本地测试截图、以及是否使用了 AI 辅助。
- CI 编译不通过的 PR 会被直接拒绝。

---

## 7. 常见问题排查（FAQ）

**Q: 编译报错 `tools.jar` 缺失？**
A: 请检查你的 JDK 版本，必须是 JDK 8。

**Q: 跑 Demo 时，图标显示为白色方块或不见了？**
A: 请确保在布局文件中使用了正确的 `android:src="@drawable/ic_uikit_xxx"`。

**Q: 新增的控件为什么在低版本上闪退？**
A: 请检查你是否在 `onDraw` 中使用了可能产生负数的矩形坐标，或者在 API 12 以下调用了属性动画。

**Q: PR 时提示找不到 `uikit-release.aar`？**
A: 因为你合并进 main 后，CI 正在后台跑，需要等 Actions 页面出现绿勾后，去 Releases 页面下载最新的产物。
