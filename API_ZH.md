# Uikit 详细 API 手册 (v1.4.0)

本文档详尽列出了 `org.uikit` 库中所有组件的调用方式、参数说明、返回类型及代码示例。
建议先阅读左侧目录，按需查找。

---

## 1. 初始化与生命周期

### `UikitConfig.init(String prefsName)`
初始化库的 SharedPreferences 存储文件名。
* **参数**：`prefsName` - 建议传宿主 App 的名字，如 `"my_app_prefs"`。
* **调用时机**：必须在 `Application.onCreate()` 或首个 Activity 的 `onCreate()` 最早期调用，否则配置无法保存。
```java
UikitConfig.init("my_app_prefs");
```

### `UikitUi.wrapScale(Context newBase)`
应用物理级 UI 缩放（支持 0.3x ~ 2.5x）。
* **参数**：`newBase` - 宿主 Activity 原本的 Context。
* **返回值**：包装后的 `Context`。
* **调用时机**：必须重写 Activity 的 `attachBaseContext`，否则缩放无效。
```java
@Override
protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(UikitUi.wrapScale(newBase));
}
```

### `UikitTheme.applyTheme(Activity activity)`
应用深色/浅色主题，并递归修复界面中可能残留的浅色背景。
* **参数**：`activity` - 当前 Activity。
* **调用时机**：在 `setContentView()` 之后调用。
```java
UikitTheme.applyTheme(this);
```

### `UikitUi.applyEntryAnimation(Activity activity)`
应用系统级的缩放进入动画（类似 Predictive Back）。
```java
UikitUi.applyEntryAnimation(this);
```

### `UikitUi.handleSwipeBack(Activity activity, MotionEvent ev)`
处理全屏滑动返回手势。
* **返回值**：`true` 表示已消费该事件，`false` 表示继续向下传递。
* **调用时机**：必须重写 Activity 的 `dispatchTouchEvent`。
```java
@Override
public boolean dispatchTouchEvent(MotionEvent ev) {
    if (UikitUi.handleSwipeBack(this, ev)) return true;
    return super.dispatchTouchEvent(ev);
}
```

---

## 2. 颜色与屏幕工具

### `UikitColor`
获取当前主题色。优先级：自定义颜色 > MD3 动态取色 > 默认绿色。
* `UikitColor.getPrimaryColor(Context)`: 获取主色。
* `UikitColor.getOnPrimaryColor(Context)`: 自动根据主色亮度返回黑色或白色，保证文字可见。
* `UikitColor.getAccentColor(Context, boolean isDark)`: 获取强调色。

### `UikitConfig` 颜色自定义
```java
// 强制覆盖主色 (例如蓝色)
UikitConfig.setCustomPrimaryColor(this, 0xFF1976D2);
// 强制覆盖强调色
UikitConfig.setCustomAccentColor(this, 0xFF64B5F6);
// 清除自定义颜色，恢复系统 MD3 取色
UikitConfig.clearCustomColors(this);
```

### `UikitScreenUtils`
* `UikitScreenUtils.dp2px(Context, float dp)`: dp 转 px。
* `UikitScreenUtils.isSquareScreen(View root)`: 判断当前是否方屏。

---

## 3. 基础控件

### `UikitButton`
标准胶囊按钮，自带按压缩放微动效。
```java
UikitButton btn = new UikitButton(this);
btn.setText("确定");
btn.setOnClickListener(v -> {});
```

### `UikitSwitch`
MD3 风格自绘开关。
```java
UikitSwitch sw = new UikitSwitch(this);
sw.setChecked(true);
sw.setOnCheckedChangeListener((buttonView, isChecked) -> {});
```

### `UikitListItem`
标准设置页列表项。
* **构造参数**：`(Context, String title, String subtitle)`。副标题可传 `null`。
```java
UikitListItem item = new UikitListItem(this, "Wi-Fi", "允许局域网");
// 右侧加开关
item.setSwitch(true, listener);
// 或右侧加箭头 (自带点击事件)
item.setArrow();
```

### `UikitBadge` (角标)
用于显示未读消息。
```java
UikitBadge badge = new UikitBadge(this);
// 显示数字
badge.setBadge("99+");
// 或者仅显示小红点
badge.setDot(); 
listItem.addView(badge);
```

### `UikitDivider`
自动适配深浅色的分割线。
```java
root.addView(new UikitDivider(this));
```

### `UikitEmptyView`
自带 👻 图标的空状态视图。
```java
UikitEmptyView empty = new UikitEmptyView(this);
```

---

## 4. 进度与加载

### `UikitProgressBar`
横向进度条。
```java
UikitProgressBar pb = new UikitProgressBar(this);
pb.setProgress(0.75f); // 0.0f ~ 1.0f
```

### `UikitProgressRing`
环形进度条，适合圆屏。
```java
UikitProgressRing ring = new UikitProgressRing(this);
ring.setProgress(0.5f);
```

### `UikitSlider` (可拖动进度条)
```java
UikitSlider slider = new UikitSlider(this);
slider.setMaxProgress(100);
slider.setProgress(30);
slider.setOnProgressChangedListener(progress -> {
    // progress 是当前进度值
});
```

### `UikitLoadingBar`
循环播放的加载条。
```java
UikitLoadingBar loading = new UikitLoadingBar(this);
// 添加到布局后自动开始，销毁时自动停止
```

### `UikitLoadingView`
三点呼吸跳动的 Loading 动画。
```java
UikitLoadingView loadingView = new UikitLoadingView(this);
```

### `UikitSkeleton`
骨架屏占位。
```java
UikitSkeleton skeleton = new UikitSkeleton(this);
```

---

## 5. 全屏交互组件 (推荐)

### `UikitFullListActivity.show()`
全屏列表。支持双行显示和自定义卡片背景色。
* **参数**：`(Activity, String title, ArrayList<UikitListItemModel> items, int requestCode)`
* **返回值**：通过 `onActivityResult` 获取 `EXTRA_RESULT_INDEX` (int) 和 `EXTRA_RESULT_TEXT` (String)。
```java
ArrayList<UikitListItemModel> items = new ArrayList<>();
// UikitListItemModel(主标题, 副标题, 文字强调色, 卡片背景色)
// 颜色传 0 表示使用系统默认
items.add(new UikitListItemModel("普通选项", "默认样式", 0, 0));
items.add(new UikitListItemModel("错误", "红色背景", 0, 0xFFF44336));

UikitFullListActivity.show(this, "高级列表", items, 1001);
```

### `UikitFullInputActivity.show()`
全屏文本输入，完美适配手表键盘。
* **参数**：`(Activity, String title, String defaultText, String hint, String positiveText, String negativeText, int requestCode)`
* **返回值**：通过 `onActivityResult` 获取 `EXTRA_RESULT_TEXT` (String)。
```java
UikitFullInputActivity.show(this, "输入名称", "", "请输入...", "提交", "取消", 1002);
```

### `UikitFullTextActivity.show()`
全屏长文本阅读，自带 3D 滚轮字幕。
* **参数**：`(Activity, String title, String message, String positiveText, int requestCode)`
```java
UikitFullTextActivity.show(this, "用户协议", longText, "我知道了", 1003);
```

### `UikitFullConfirmActivity.show()`
极简全屏确认框。
* **参数**：`(Activity, String title, String message, int requestCode)`
* **返回值**：点击 ✓ 返回 `RESULT_OK`，点击 × 返回 `RESULT_CANCELED`。
```java
UikitFullConfirmActivity.show(this, "删除项目？", "此操作无法撤销", 1004);
```

### `UikitPickerActivity.show()`
上下滚动的居中 3D 选择器。
* **参数**：`(Activity, String title, String[] items, int requestCode)`
* **返回值**：通过 `onActivityResult` 获取 `EXTRA_RESULT_INDEX` (int) 和 `EXTRA_RESULT_TEXT` (String)。
```java
UikitPickerActivity.show(this, "请选择", new String[]{"A", "B", "C"}, 1005);
```

### `UikitDatePickerActivity.show()`
年月日三列滚轮日期选择器。
* **参数**：`(Activity, int requestCode)`
* **返回值**：获取 `EXTRA_RESULT_YEAR`、`EXTRA_RESULT_MONTH`、`EXTRA_RESULT_DAY` (均为 String)。
```java
UikitDatePickerActivity.show(this, 1006);
```

### `UikitImageViewerActivity.show()`
全屏图片查看器。
* **参数**：`(Activity, String assetName)`。`assetName` 为放在 `src/main/assets/` 下的文件名。
```java
UikitImageViewerActivity.show(this, "demo_image.png");
```

---

## 6. 废弃 API (旧版非全屏弹窗)

> ⚠️ **警告**：以下 API 已标记 `@Deprecated`，在未来的版本中可能会被移除。
> 请尽快迁移至对应的全屏版本，以获得完美的圆屏适配和 3D 滚动体验。

### `UikitUi.showList()` - [已废弃]
旧版列表弹窗。在圆屏上容易导致文字截断或按钮被遮挡。
* **替代方案**：`UikitFullListActivity.show()`
```java
// 废弃的写法（不推荐）
UikitUi.showList(this, "选择", new String[]{"A", "B"}, new UikitUi.OnItemSelectListener() {
    public void onItemSelect(int position) { /* ... */ }
}).show();
```

### `UikitUi.showInput()` - [已废弃]
旧版输入弹窗。在手表上容易被软键盘完全遮挡。
* **替代方案**：`UikitFullInputActivity.show()`
```java
// 废弃的写法（不推荐）
UikitUi.showInput(this, "输入", "", new UikitUi.OnInputListener() {
    public void onInput(String text) { /* ... */ }
}).show();
```

### `UikitUi.showConfirm()` - [已废弃]
旧版确认弹窗。无法很好地处理长文本滚动。
* **替代方案**：`UikitFullConfirmActivity.show()` 或 `UikitFullTextActivity.show()`
```java
// 废弃的写法（不推荐）
UikitUi.showConfirm(this, "提示", "确定要退出吗？", null).show();
```

---

## 7. 弹窗与硬件辅助

### `UikitToast.show()`
居中显示的轻量级提示。
```java
UikitToast.show(this, "已保存");
```

### `UikitBottomSheet`
底部抽屉。支持下拉关闭。
```java
new UikitBottomSheet(this, "选项", new UikitEmptyView(this)).show();
```

### `WearHardwareUtils`
* `WearHardwareUtils.vibrate(Context, int type)`: 触发震动。`type` 可选 `HAPTIC_LIGHT`, `HAPTIC_SUCCESS`, `HAPTIC_ERROR`。
* `WearHardwareUtils.keepScreenOn(Activity, boolean)`: 屏幕常亮。
* `WearHardwareUtils.bindRotaryEncoder(ScrollView)`: 绑定表冠滑动事件。

### `UikitClickUtils` (防抖工具)
防止用户手抖连续点击按钮。
```java
btn.setOnClickListener(v -> {
    if (UikitClickUtils.isFastDoubleClick()) return;
    // 执行点击逻辑
});
```
