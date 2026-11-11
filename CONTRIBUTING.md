# 贡献指南

**简体中文** | [English](CONTRIBUTING_EN.md)

感谢参与 **Uikit** 的开发！

---

## ⚠️ 极其重要的构建环境限制

由于本项目需要在极低版本的 Android 系统上运行，我们**锁死了**以下工具链版本：

| 组件 | 版本 | 说明 |
|---|---|---|
| **Gradle** | `5.6.4` | **绝对不要升级至 6.0 及以上** |
| **AGP** | `3.6.3` | 与 Gradle 5.6.4 强绑定 |
| **JDK** | `8` | 编译与运行均需 JDK 8 |
| **Android SDK** | `30` | compileSdk 30 |

如果你在本地开发时遇到 `Could not create an instance of type com.android.build...` 错误，请立刻检查你的 Gradle 版本是否过高。**请使用项目自带的 `./gradlew` 或明确指定使用 5.6.4 版本进行编译。**

---

## 本地开发

```bash
git clone https://github.com/styisme/wear-uikit.git
cd wear-uikit
# 确保 JAVA_HOME 指向 JDK 8
gradle :uikit:assembleRelease :app:assembleDebug
```

---

## 提交规范

使用 [Conventional Commits](https://www.conventionalcommits.org/zh-hans/)：

```
<type>(<scope>): <subject>
```
常用 type：`feat` / `fix` / `docs` / `style` / `refactor` / `ci`。

---

## 发版说明

1. 更新 `CHANGELOG.md` 顶部的版本号。
2. Push 到 main 分支。
3. CI 会自动打 Tag 并发版。请确保不要手动修改 `app/build.gradle` 里的 versionCode。
