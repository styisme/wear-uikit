# Contributing Guidelines

[简体中文](CONTRIBUTING.md) | **English**

Thank you for contributing to **Uikit**! Follow **Conventional Commits + SemVer 2.0.0 + CHANGELOG**.

---

## Local Development

```bash
git clone https://github.com/styisme/wear-uikit.git
cd wear-uikit
gradle :uikit:assembleRelease
gradle :app:assembleDebug
```

## Commit Convention

```
<type>(<scope>): <subject>
```

Allowed types: `feat`, `fix`, `docs`, `style`, `refactor`, `ci`.
Common scopes: `ui`, `color`, `theme`, `widget`, `wear`.

**Important**: Versioning relies on `CHANGELOG.md`. Tag with the same version.

## Troubleshooting

**Q: Dynamic color not working on Android 12+?**
A: The host `<application>` theme must be `Theme.DeviceDefault.NoActionBar`.

**Q: Bottom sheet cannot be dismissed?**
A: It supports clicking outside, tapping the `Close` button, dragging down, and the back button.

**Q: CI release job returns 403?**
A: Enable **Read and write permissions** under Settings → Actions → General.
