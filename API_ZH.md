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

### `UikitUi.handleSwipeBack(Activity activity, MotionEvent ev)`
处理滑动返回手势。在 `dispatchTouchEvent` 中调用。
```java
@Override
public boolean dispatchTouchEvent(MotionEvent ev) {
    if (UikitUi.handleSwipeBack(this, ev)) return true;
    return super.dispatchTouchEvent(ev);
}
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

### `UikitUi.showList()`
```java
UikitUi.showList(this, "选择", new String[]{"A", "B"}, new OnItemSelectListener() {
    public void onItemSelect(int pos) { }
}).show();
```

### `UikitBottomSheet`
```java
new UikitBottomSheet(this, "标题", new UikitEmptyView(this)).show();
```

---

## 4. 硬件交互

### `WearHardwareUtils.vibrate(Context, int type)`
```java
WearHardwareUtils.vibrate(this, WearHardwareUtils.HAPTIC_SUCCESS);
```

### `UikitFullListActivity.show()`
全屏选择列表，适合选项较多且需要完整滚动体验的场景。
```java
String[] items = {"Option 1", "Option 2", "Option 3"};
UikitFullListActivity.show(this, "选择选项", items, 1001);

// 接收结果 (在 Activity 中重写)
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == 1001 && resultCode == RESULT_OK) {
        int index = data.getIntExtra(UikitFullListActivity.EXTRA_RESULT_INDEX, -1);
        String text = data.getStringExtra(UikitFullListActivity.EXTRA_RESULT_TEXT);
    }
}
```
