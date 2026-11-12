# Contributing Guidelines

[简体中文](CONTRIBUTING.md) | **English**

---

## ⚠️ Critical Build Environment Constraints

To support legacy Android (API 10), we have **locked** the following toolchain:

| Component | Version | Note |
|---|---|---|
| **Gradle** | `5.6.4` | **DO NOT upgrade to 6.0+** |
| **AGP** | `3.6.3` | Strictly bound to Gradle 5.6.4 |
| **JDK** | `8` | Required for compilation |

If you see errors like `Could not create an instance of type com.android.build...`, your Gradle version is likely too high.

---

## Local Development

```bash
git clone https://github.com/styisme/wear-uikit.git
cd wear-uikit
gradle :uikit:assembleRelease :app:assembleDebug
```
