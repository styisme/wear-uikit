# Contributing Guidelines

[简体中文](CONTRIBUTING.md) | **English**

Thank you for contributing to **Uikit**! As a UI library focused on **legacy compatibility (API 10)** and **Wear OS native experience**, we have strict requirements for code quality and adaptation standards.

---

## 📑 Table of Contents
1. [Architecture & Structure](#1-architecture--structure)
2. [Local Development Setup](#2-local-development-setup)
3. [UI Component Guidelines (CRITICAL)](#3-ui-component-guidelines-critical)
4. [Code & Commit Conventions](#4-code--commit-conventions)
5. [Branching & Release Flow](#5-branching--release-flow)
6. [Issue & PR Guidelines](#6-issue--pr-guidelines)

---

## 1. Architecture & Structure

The project consists of two core modules: `uikit` (Library) and `app` (Demo).

```text
wear-uikit/
├── uikit/                     # Library module
│   ├── src/main/java/org/uikit/
│   │   ├── UikitConfig.java   # Global config
│   │   ├── UikitColor.java    # Dynamic color engine
│   │   └── ...                # Other widgets & Activities
│   └── src/main/res/          # XML resources & icons
├── app/                       # Demo module
└── .github/workflows/         # CI/CD
```

---

## 2. Local Development Setup

To support Android 2.3 (API 10), we have **locked** the toolchain. **You cannot use the new Android Studio's default JDK 17.**

| Component | Version | Note |
|---|---|---|
| **JDK** | `Java 8` | Strictly required |
| **Gradle** | `5.6.4` | Locked in wrapper |
| **AGP** | `3.6.3` | Bound to Gradle 5.6.4 |

### Build Command
```bash
export JAVA_HOME=/path/to/jdk8
./gradlew :uikit:assembleRelease :app:assembleDebug
```

---

## 3. UI Component Guidelines (CRITICAL)

### 3.1 Color Rules: NO Hardcoding
- **Do NOT** hardcode colors (e.g., `#FF0000`).
- **Must** use `UikitColor.getPrimaryColor(ctx)` or `getAccentColor()`.
- For custom XML attributes, declare them in `attrs.xml`.

### 3.2 Size Rules: NO Pixels
- **Do NOT** use `px` or magic numbers.
- **Must** use `UikitScreenUtils.dp2px(ctx, dp)`.
- Layouts must account for round screens via `UikitUi.applyRoundPadding()`.

### 3.3 Compatibility & Defense Rules
- **Defensive Drawing**: Any custom `onDraw` must start with `if (getWidth() == 0 || getHeight() == 0) return;` to prevent native rendering crashes on legacy devices.
- **Animation Limits**: Never call `view.animate()` below API 12 (causes NPE).
- **Native Proxies**: If your new widget replaces a native one (e.g., `ProgressBar`), you **MUST** proxy high-frequency methods (like `setMax`, `setProgress`) for drop-in compatibility.

---

## 4. Code & Commit Conventions

### 4.1 Code Style
- Standard Java naming conventions.
- **Do NOT** use Lombok, AndroidX, or any third-party dependencies (Zero-dependency is mandatory).

### 4.2 Commit Message
Use [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/):
```
<type>(<scope>): <subject>
```
**Allowed types**: `feat`, `fix`, `docs`, `style`, `refactor`, `perf`, `ci`, `chore`.

**Common scopes**: `ui`, `button`, `form`, `progress`, `theme`.

**Example**:
```
feat(ui): add UikitIconButton
fix(button): resolve NPE crash on API 23
```

---

## 5. Branching & Release Flow

- `main`: Stable branch (protected).
- `feat/xxx`, `fix/xxx`: Feature and fix branches.

### Auto-Release
**NEVER edit `versionCode` or `versionName` in `build.gradle`!**
1. Update `CHANGELOG.md` with the new version (e.g., `## [1.8.0] - 2026-12-01`).
2. Merge to `main`.
3. CI will build the AAR, tag `v1.8.0`, and publish to GitHub Releases automatically.

---

## 6. Issue & PR Guidelines

### 6.1 Issues
- **Bug**: Include Android version, chipset, Logcat, and screenshots.
- **Feature**: Describe the use case and provide design references if possible.

### 6.2 PRs
- Keep PRs focused on a single topic.
- PR description must include: Changes, related issues, test screenshots, and AI-assistance disclosure.
- PRs that fail CI will be rejected.
