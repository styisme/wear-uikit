# Uikit API 调用指南

本文档介绍 `org.uikit` 库中核心 API 的调用方式。

## 1. 初始化与生命周期

### `UikitConfig.init(String prefsName)`
初始化配置。建议在 `Application` 早期调用。
```java
UikitConfig.init("my_app_prefs");
```

### `UikitUi.wrapScale(Context base)`
应用物理级 UI 缩放。必须在 Activity 的 `attachBaseContext` 中调用。
```java
@Override
protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(UikitUi.wrapScale(newBase));
}
```

### `UikitTheme.applyTheme(Activity activity)`
应用深色/浅色主题。
```java
UikitTheme.applyTheme(this);
```

---

## 2. 颜色与自定义

### `UikitColor.getPrimaryColor(Context)`
获取当前主色（优先级：自定义色 > MD3 动态色 > 默认绿）。

### `UikitConfig.setCustomPrimaryColor(Context, int color)`
覆盖默认主色，全局生效。
```java
UikitConfig.setCustomPrimaryColor(this, 0xFF1976D2); // 蓝色
```

---

## 3. UI 组件

### `UikitListItem`
标准列表项，一行代码生成。
```java
UikitListItem item = new UikitListItem(this, "Wi-Fi", "允许局域网发现");
item.setSwitch(true, listener); // 添加开关
// 或者
item.setArrow(); // 添加箭头
```

### `UikitProgressBar`
横向进度条。
```java
UikitProgressBar pb = new UikitProgressBar(this);
pb.setProgress(0.75f);
```

---

## 4. 全屏交互组件 (推荐)

> 以下组件替代了旧版的弹窗 API，完美适配手表的圆屏和滚动特效。

### `UikitFullTextActivity.show()` - 全屏长文本
按句子拆分长文本，应用 3D 滚轮特效，中间字大，上下字小。
```java
UikitFullTextActivity.show(this, "用户协议", "这是一段很长的文本...", "我知道了", 1001);
```

### `UikitFullInputActivity.show()` - 全屏输入
完美适配手表键盘，支持自定义按钮文字。
```java
UikitFullInputActivity.show(this, "输入名称", "", "请输入...", "提交", "取消", 1002);
```

### `UikitFullListActivity.show()` - 全屏列表
支持双行显示和自定义卡片颜色。
```java
ArrayList<UikitListItemModel> items = new ArrayList<>();
items.add(new UikitListItemModel("普通选项", "这是副标题"));
items.add(new UikitListItemModel("成功状态", "绿色强调", 0xFF4CAF50));
items.add(new UikitListItemModel("红色卡片", null, 0, 0xFFF44336));

UikitFullListActivity.show(this, "请选择", items, 1003);
```

---

## 5. 旧版弹窗 API (已废弃)

> **⚠️ 警告**：以下 API 已标记 `@Deprecated`，将在未来的版本中移除，请尽快迁移至全屏版本。

- `UikitUi.showList()` -> 请用 `UikitFullListActivity`
- `UikitUi.showInput()` -> 请用 `UikitFullInputActivity`
- `UikitUi.showConfirm()` -> 请用 `UikitFullTextActivity`

---

## 6. 硬件交互

### `WearHardwareUtils.vibrate(Context, int type)`
```java
WearHardwareUtils.vibrate(this, WearHardwareUtils.HAPTIC_SUCCESS);
```

### `UikitSlider`
可拖动的进度条。常用于音量调节、亮度调节或进度控制。
```java
UikitSlider slider = new UikitSlider(this);
slider.setMaxProgress(100);
slider.setProgress(50);
slider.setOnProgressChangedListener(new UikitSlider.OnProgressChangedListener() {
    @Override
    public void onProgressChanged(float progress) {
        // 处理拖动事件
    }
});
```

### `UikitLoadingBar`
不确定进度的加载条（自带无限循环的动画）。
```java
UikitLoadingBar loadingBar = new UikitLoadingBar(this);
// 添加到布局后会自动开始动画
// 如果想手动控制，可以调用：
loadingBar.startLoading();
loadingBar.stopLoading();
```
