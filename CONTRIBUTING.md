# 贡献指南

**简体中文** | [English](CONTRIBUTING_EN.md)

感谢参与 **Uikit** 的开发！本项目采用 **Conventional Commits + SemVer 2.0.0 + CHANGELOG** 规范。

---

## 本地开发

```bash
git clone https://github.com/styisme/wear-uikit.git
cd wear-uikit
gradle :uikit:assembleRelease
gradle :app:assembleDebug
```

## 提交规范

```
<type>(<scope>): <subject>
```
常用 type：`feat` / `fix` / `docs` / `style` / `refactor` / `ci`。
常用 scope：`ui` / `color` / `theme` / `widget` / `wear`。

**重要**：版本号以 `CHANGELOG.md` 为准。

## 常见问题排查

**Q: 动态取色在 Android 12+ 取不到？**
A: 宿主 `<application>` 主题必须是 `Theme.DeviceDefault.NoActionBar`。

**Q: 底部弹窗无法关闭？**
A: 支持点击外部、`Close` 按钮、下拉手势和返回键退出。

**Q: CI Release job 报 403？**
A: 仓库 Settings → Actions → General → Workflow permissions 选 **Read and write permissions**。
