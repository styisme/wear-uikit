# Uikit API 调用指南

本文档介绍 `org.uikit` 库中核心 API 的调用方式。

## 1. 初始化与生命周期

### `UikitConfig.init(String prefsName)`
初始化库配置。建议在 `Application` 或 `MainActivity` 的 `onCreate` 最早期调用。参数为 SharedPreferences 的文件名，可复用宿主已有的文件。
```java
UikitConfig.init("my_app_prefs");
```

### `UikitUi.wrapScale(Context base)`
应用物理级 UI 缩放（0.3x - 2.5x）。必须在每个 Activity 的 `attachBaseContext` 中调用。
```java
@Override
protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(UikitUi.wrapScale(newBase));
}
```

### `UikitTheme.applyTheme(Activity activity)`
应用深色/浅色主题。会自动递归遍历视图树，修复部分白底白字或残留浅色背景问题。
```java
UikitTheme.applyTheme(this);
```

### `UikitUi.applyEntryAnimation(Activity activity)`
应用系统级的缩放入场动画（Predictive Back 风格）。在 `onCreate` 中调用。
```java
UikitUi.applyEntryAnimation(this);
```

### `UikitUi.handleSwipeBack(Activity activity, MotionEvent ev)`
拦截并处理滑动返回手势。在 Activity 的 `dispatchTouchEvent` 中调用。
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
- `UikitColor.getPrimaryColor(Context)`：获取当前 MD3 动态主色（API 31+ 生效，低版本回退为固定绿）。
- `UikitColor.getOnPrimaryColor(Context)`：自动计算主色上的对比文字色（黑或白）。
- `UikitColor.getAccentColor(Context, boolean isDark)`：获取强调色。

### `UikitScreenUtils`
- `UikitScreenUtils.dp2px(Context, float dp)`：dp 转 px。
- `UikitScreenUtils.isSquareScreen(View root)`：判断当前是否方屏。

---

## 3. UI 组件

### `UikitButton` (标准胶囊按钮)
自动跟随动态主色、圆角与反色文字。
```java
UikitButton btn = new UikitButton(this);
btn.setText("Click Me");
// 设置布局参数...
```

### `UikitSwitch` (开关)
自绘 MD3 开关，自带平滑动画。
```java
UikitSwitch sw = new UikitSwitch(this);
sw.setChecked(true);
```

### `UikitProgressRing` (环形进度条)
适配圆屏的手绘进度条。
```java
UikitProgressRing ring = new UikitProgressRing(this);
ring.setProgress(0.75f); // 0.0f - 1.0f
```

### `UikitSkeleton` (骨架屏)
微光闪烁的加载占位符。
```java
UikitSkeleton skeleton = new UikitSkeleton(this);
// 添加到布局后自动开始动画
```

---

## 4. 弹窗与抽屉

### `UikitUi.showList()`
弹出选项列表。
```java
UikitUi.showList(this, "选择操作", new String[]{"选项 A", "选项 B"}, 
    new UikitUi.OnItemSelectListener() {
        public void onItemSelect(int position) {
            // 处理点击
        }
    }).show();
```

### `UikitUi.showInput()`
弹出文本输入框。
```java
UikitUi.showInput(this, "输入名称", "默认值", new UikitUi.OnInputListener() {
    public void onInput(String text) {
        // 处理输入
    }
}).show();
```

### `UikitBottomSheet` (底部抽屉)
从底部滑出的面板。支持下滑手势和返回键关闭。
```java
View content = new UikitEmptyView(this); // 自定义内容
UikitBottomSheet sheet = new UikitBottomSheet(this, "更多选项", content);
sheet.show();
```

### `UikitToast.show()`
替代系统 Toast，适配圆屏防切边。
```java
UikitToast.show(this, "操作成功");
```

---

## 5. 硬件交互

### `WearHardwareUtils.vibrate(Context, int type)`
触发震动反馈。
```java
WearHardwareUtils.vibrate(this, WearHardwareUtils.HAPTIC_SUCCESS);
// 可选：HAPTIC_LIGHT, HAPTIC_ERROR
```

### `WearHardwareUtils.bindRotaryEncoder(ScrollView sv)`
绑定手表表冠（旋转表盘）滑动事件。
```java
WearHardwareUtils.bindRotaryEncoder(scrollView);
```

---

## 6. 自定义 MD3 颜色

如果不想使用系统的动态取色（或设备低于 Android 12），可以通过 API 强制指定主色和强调色：

### `UikitConfig.setCustomPrimaryColor(Context, int color)`
设置自定义主色。传入标准的 `Color` int 值（如 `0xFF1976D2`）。设置后，所有 `UikitButton`、`UikitSwitch`、`UikitProgressRing` 将立即使用该颜色。

### `UikitConfig.setCustomAccentColor(Context, int color)`
设置自定义强调色（用于标题、高亮文字等）。

### `UikitConfig.clearCustomColors(Context)`
清除自定义颜色，恢复为默认动态取色（或默认极客绿）。

```java
// 设置为蓝色主题
UikitConfig.setCustomPrimaryColor(this, 0xFF1976D2);
recreate(); // 建议重新加载 Activity 以刷新 UI
```
