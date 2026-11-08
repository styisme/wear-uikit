# Contributing Guidelines

[简体中文](CONTRIBUTING.md) | **English**

Thank you for contributing to **Uikit**! This project follows **Conventional Commits + SemVer 2.0.0 + CHANGELOG**.

> **Origin**: Uikit is extracted from [WatchSend](https://github.com/styisme/watchsend). Docs: [README](README_EN.md) | [CHANGELOG](CHANGELOG.md) | [AI Disclosure](docs/AI_DISCLOSURE.md) | [Dev Constraints](docs/DEVELOPMENT_CONSTRAINTS.md).

---

## About AI-Generated Code

Following the [AI Development Guidelines](https://github.com/styisme/ai-development-guidelines), most of this codebase is **AI-assisted**.

When submitting a PR:
- State whether the change was AI-generated.
- **Manually verify functionality** on a real device or emulator (compiling is not enough).
- If you used AI, mention the model and prompt direction.

---

## Local Development

| Component | Version |
|---|---|
| JDK | 8 |
| Gradle | 5.6.4 |
| AGP | 3.6.3 |
| Android SDK | compileSdk 30, buildTools 30.0.3 |

```bash
git clone https://github.com/styisme/wear-uikit.git
cd wear-uikit
gradle :uikit:assembleRelease
gradle :app:assembleDebug
```

CI: pushes to `main`/`master` build AAR + Demo APK; `v*` tags auto-release. Enable **Read and write permissions** under Settings → Actions → General.

---

## Project Structure

```text
wear-uikit/
├── uikit/src/main/java/org/uikit/
│   ├── UikitConfig.java
│   ├── UikitColor.java
│   ├── UikitTheme.java
│   ├── UikitSwitch.java
│   ├── UikitUi.java
│   └── WearHardwareUtils.java
├── app/                          # Demo
├── .github/workflows/build.yml
└── docs/
```

---

## Commit Convention

Use [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/):

```
<type>(<scope>): <subject>
```

Allowed types: `feat` / `fix` / `docs` / `style` / `refactor` / `perf` / `build` / `ci` / `chore`.

Common scopes: `ui`, `color`, `theme`, `widget`, `wear`, `ci`.

Examples:
```
feat(color): support Android 12+ MD3 dynamic color
fix(ui): swipe-back jank under low performance mode
ci: upload AAR artifact
```

---

## Pull Request Process

1. Fork the repo
2. Create a branch: `git checkout -b feat/your-feature`
3. Modify, verify locally or via Actions
4. Use Conventional Commits
5. Push and open a PR
6. Wait for CI + review

### PR description should include

```markdown
## Changes
What was done

## Type
- [ ] feat / fix / refactor / docs / chore

## Related Issue
Closes #123

## Testing
- [ ] Local build passed
- [ ] Tested on device/emulator (model & Android version)
- [ ] Actions build passed

## AI Generated?
- [ ] Yes (model + manual verification)
- [ ] No
```

---

## Versioning

Follow [Semantic Versioning 2.0.0](https://semver.org/). The latest version in `CHANGELOG.md` is the source of truth. Tag `v*` to trigger CI release.

---

## Troubleshooting

**Q: `wrapScale()` has no effect on Android 11+?**
A: We use `createConfigurationContext` instead of the blocked `updateConfiguration`. Every Activity must override `attachBaseContext` and call `UikitUi.wrapScale(newBase)`.

**Q: Dynamic color not working on Android 12+?**
A: The host `<application>` theme must be `Theme.DeviceDefault.NoActionBar`, and `targetSdkVersion` not too low (30 here). Also ensure `UikitConfig.isDynamicColorEnabled(ctx)` is `true`.

**Q: `R.id.dlg_root` not found after adding the AAR?**
A: Dialog layouts live in `org.uikit.R`. If the host has colliding resource names, prefix host resources or reference `org.uikit.R` explicitly.

**Q: Rotary input not working?**
A: Requires API 26+ and a physical rotating crown. `WearHardwareUtils.bindRotaryEncoder()` ignores unsupported devices.

**Q: Swipe-back conflicts with system back?**
A: Call `UikitUi.handleSwipeBack()` in `dispatchTouchEvent`, and gate `onBackPressed` with `UikitUi.isSystemBackBlocked()`.

**Q: CI release job returns 403?**
A: Enable **Read and write permissions** under Settings → Actions → General → Workflow permissions.
