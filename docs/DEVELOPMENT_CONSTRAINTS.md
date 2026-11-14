# 开发环境约束说明

本文档说明本仓库（**wear-uikit**）的**实际开发条件**与**责任边界**。

作为一款专为极低版本 Android 与 Wear OS 打造的纯 UI 组件库，本文件的作用是：**让外部能看清“我们做了什么、没条件做什么”**。

相关文档：[README](../README.md) | [CHANGELOG](../CHANGELOG.md) | [AI 披露](AI_DISCLOSURE.md)。

---

## 一、开发环境（按实际勾选）

- [ ] 有本地 IDE / SDK，可本地编译
- [ ] 有本地 diff 工具，可逐行审阅
- [ ] 可跑 lint / 单元测试
- [ ] 走标准 PR 流程（fork → PR → review）
- [x] **仅靠云端 CI 编译（无本地工具链）** —— *（依赖 GitHub Actions + JDK 8 + Gradle 5.6.4）*
- [x] **仅靠移动端 / 手表端开发** —— *（主要使用手表端的 MGit 及手机端的 Pydroid 3 编写与运行 Python 修复脚本）*
- [x] **走直推 master（无 PR 流程）**
- [x] **借助私有仓库生成签名** —— *（由于本地无 JDK 环境，通过专门的私有 Actions 仓库生成 Keystore，并转换为 Base64 注入主仓库 Secrets）*

> 未勾选的条目，代表**本仓库物理上无法执行**，不是"忽略了"。

---

## 二、实际执行的机制（做了什么就勾什么）

### 披露

- [x] 提供 `AI_DISCLOSURE.md`，说明 AI 参与范围与比例
- [x] README 顶部链接本文件
- [ ] Commit message 标注 `[ai-assisted]` —— *（目前使用 Conventional Commits，虽未强制标注，但绝大多数代码由 AI 辅助生成）*

### UI 兼容性与防御机制

- [x] **双轨制兼容**：通过 `UikitCompatButton` 等平替类，保证老项目零代码修改即可升级。
- [x] **底层绘制防御**：在 `onDraw` 中加入坐标与尺寸校验，防止低版本底层渲染崩溃。
- [x] **物理级缩放重置**：强制废弃 `updateConfiguration`，改用 `attachBaseContext` 规避 Android 11+ 拦截。

### 门禁与全自动发版

- [x] **CI 编译通过才发版**（这是本项目唯一的自动化门禁）
- [x] **版本号自动化约束** —— *（强制读取 `CHANGELOG.md`，开发者无需也无法手动修改 Gradle 版本号）*
- [x] **自动化签名与发布** —— *（CI 自动解析 Secrets 中的密钥，编译 Release AAR 并自动打 Tag、创建 GitHub Release）*
- [x] **UI 风格与多语言代码级强审查** —— *（人工严格把关了所有卡片必须使用 `UikitUi.getPillBackground`，防止出现白底白字）*

### 测试

- [x] **真机测试**（真实的物理设备） —— *（在 HM_Watch_A (展锐芯片, API 27) 及 Redmi 3X 等设备上进行了极限测试）*
- [x] **交互与动画测试** —— *（重点验证了 3D 滚轮滑动、卡片预测性推拉返回手势、圆屏边距避让）*
- [x] **网页端跨浏览器测试** —— *（针对 Demo 中的 WebView 渲染进行了验证）*
- [ ] **表冠真机测试** —— *（受限于测试设备，部分无表冠设备仅验证了代码兼容性）*
- [ ] 模拟器测试

---

## 三、诚实声明

**以下情况如实标注，不得美化：**

- **关于真机测试**：本仓库的核心 UI 已在数台老旧安卓设备（如 HM_Watch_A）上通过真机验证。但受限于物理设备数量，部分冷门 API（如 Wear OS 3+ 的表冠阻尼）的边缘场景尚未完全覆盖。
- **关于云端编译**：CI 编译通过仅代表语法与依赖正确，不代表所有功能在您的特定设备上绝对可用。
- **关于 AI 生成与正则补丁**：本项目属于极端的 AI 辅助驱动开发。在 v1.1.0 到 v1.7.0 的迭代中，AI 使用了大量 Python 正则脚本直接修改源码，导致了多次灾难性错误（如误删 XML 的 `layout_width` 导致 App 闪退，大括号吞噬导致 CI 彻底崩溃）。最终完全依赖云端 CI 报错日志和人肉排查才修复。
- **关于 AI 系统适配的滞后性 (v1.7.1 新增)**：在重构 `UikitButton` 时，AI 误在 `init()` 阶段调用了尚未实例化的 `textView.setGravity()`，导致在 API 27 真机上直接闪退。这再次证明人类开发者的真机反馈与报错纠偏是不可替代的。

---

## 四、敏感模块清单（按实际填写）

以下是 `wear-uikit` 库中涉及底层绘制、硬件交互、系统 API 的核心模块。请按**实际验证状态**标注：

| 模块 | 验证状态 |
|---|---|
| `UikitUi.java`（3D滚轮、圆屏避让、**物理级缩放**） | ✅ 真机测试（滑动阻尼感良好，高版本缩放完美生效） |
| `UikitButton.java`（底层重构与原生代理层） | ✅ 真机测试（API 27 HM_Watch_A 上完美运行，无 NPE 闪退） |
| `UikitCheckBox.java` & `UikitRadioButton.java`（自绘表单控件） | ✅ 真机测试（已加入 Canvas 越界与除零防御） |
| `UikitFullTextActivity.java`（全屏长文本与正则断句） | ✅ 真机测试（已验证不会切断 URL 和数字小数点） |
| `UikitSlider.java`（可拖动进度条与除法防御） | ✅ 真机测试（已加入 `maxProgress == 0` 的除零防御） |
| `UikitCircularProgressTimer.java`（倒计时与动画取消） | ✅ 真机测试（已加入 `onDetachedFromWindow` 强制停止） |

> 标注 `⚠️ 仅 CI` 或 `❌ 未验证` 的模块，**不代表不能发布**，但使用前请评估风险。
