# 贡献指南

**简体中文** | [English](CONTRIBUTING_EN.md)

感谢参与 **Uikit** 的开发！

## ⚠️ 构建环境限制
本项目**锁死**了以下工具链版本，请勿随意升级：
- **Gradle**: `5.6.4`
- **AGP**: `3.6.3`
- **JDK**: `8`

## 本地开发
```bash
git clone https://github.com/styisme/wear-uikit.git
cd wear-uikit
gradle :uikit:assembleRelease :app:assembleDebug
```

## 提交规范
使用 Conventional Commits (`feat`, `fix`, `docs` 等)。

## 发版说明
1. 更新 `CHANGELOG.md` 顶部的版本号。
2. Push 到 main 分支。
3. CI 会自动打 Tag，并将 AAR 与 APK 附在 Release 中。
