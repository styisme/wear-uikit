# Uikit 字典级 API 手册 (v1.5.0)

本文档详尽列出了 `org.uikit` 库中所有组件的调用方式、参数说明、返回类型及代码示例。
建议开发者通读一遍，以便了解各个组件的适用场景。

---

## 1. 初始化与生命周期

### `UikitConfig.init(String prefsName)` [Since v1.0.0]
初始化库的 SharedPreferences 存储文件名。如果不调用，默认使用 `"uikit_prefs"`。
* **参数**：`prefsName` (String) - 建议传宿主 App 的名字，方便统一管理配置。
* **调用时机**：必须在 `Application.onCreate()` 或首个 Activity 的 `onCreate()` 最早期调用。

### `UikitUi.wrapScale(Context newBase)` [Since v1.0.0]
应用物理级 UI 缩放（支持 0.3x ~ 2.5x）。
* **参数**：`newBase` (Context) - 宿主 Activity 原本的 Context。
* **返回值**：包装后的 `Context`，**必须传给 super**。
* **调用时机**：重写 Activity 的 `attachBaseContext`。
```java
@Override
protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(UikitUi.wrapScale(newBase));
}
```

### `UikitTheme.applyTheme(Activity activity)` [Since v1.0.0]
应用深色/浅色主题，并递归修复界面中可能残留的浅色背景。
* **参数**：`activity` (Activity) - 当前 Activity。
* **调用时机**：在 `setContentView()` 之后调用。

### `UikitUi.applyEntryAnimation(Activity activity)` [Since v1.1.0]
应用系统级的缩放进入动画（类似 Predictive Back）。自动处理低性能模式。
* **参数**：`activity` (Activity) - 当前 Activity。

### `UikitUi.handleSwipeBack(Activity activity, MotionEvent ev)` [Since v1.0.0]
处理全屏滑动返回手势。
* **参数**：`activity` (Activity), `ev` (MotionEvent) - 触摸事件。
* **返回值**：`boolean` (true 表示已消费该事件，不再向下传递)。
* **调用时机**：重写 Activity 的 `dispatchTouchEvent`。
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
* `UikitScreenUtils.dp2px(Context context, float dp)`: dp 转 px。
* `UikitScreenUtils.isSquareScreen(View root)`: 判断当前是否方屏。

---

## 3. 基础控件

### `UikitButton` [Since v1.0.0, XML支持 Since v1.5.0]
标准胶囊按钮，自带按压缩放微动效。

**XML 属性表**：
| 属性名 | 类型 | 默认值 | 说明 |
|---|---|---|---|
| `app:uikit_radius` | Dimension | 32dp | 圆角大小 |
| `app:uikit_backgroundColor` | Color | 动态主色 | 背景颜色 |
| `app:uikit_textColor` | Color | 自动计算 | 文字颜色（深色底白字，浅色底黑字） |

```xml
<org.uikit.UikitButton
    android:layout_width="match_parent"
    android:layout_height="48dp"
    android:text="自定义"
    android:drawableStart="@drawable/ic_uikit_wifi"
    app:uikit_radius="16dp"
    app:uikit_backgroundColor="#F44336" />
```
* **内嵌图标**：自 v1.5.0 起，如果在 XML 中使用了 `android:drawableStart`，库会自动将其缩放至 18dp，并自动跟随文字颜色染色（Tint）。

### `UikitSwitch` [Since v1.0.0]
MD3 风格自绘开关。
```java
UikitSwitch sw = new UikitSwitch(this);
sw.setChecked(true);
sw.setOnCheckedChangeListener((buttonView, isChecked) -> {});
```

### `UikitListItem` [Since v1.2.0]
标准设置页列表项。
* **构造参数**：`(Context context, String title, String subtitle)`。
  * `title`：主标题。
  * `subtitle`：副标题。如果不需要，传 `null`。
```java
UikitListItem item = new UikitListItem(this, "Wi-Fi", "允许局域网");
// 右侧加开关
item.setSwitch(true, listener);
// 或右侧加箭头 (自带点击事件)
item.setArrow();
```

### `UikitBadge` [Since v1.4.0]
未读消息角标。
```java
UikitBadge badge = new UikitBadge(this);
badge.setBadge("99+"); // 显示数字
// 或者仅显示小红点
badge.setDot(); 
listItem.addView(badge);
```

### `UikitDivider` [Since v1.4.0]
自动适配深浅色的分割线。无需传参，直接 addView 即可。
```java
root.addView(new UikitDivider(this));
```

### `UikitEmptyView` [Since v1.1.0]
自带 👻 图标的空状态视图。
```java
UikitEmptyView empty = new UikitEmptyView(this);
```

---

## 4. 进度与加载

### `UikitProgressBar` [Since v1.2.0]
横向进度条。
```java
UikitProgressBar pb = new UikitProgressBar(this);
pb.setProgress(0.75f); // 范围 0.0f ~ 1.0f
```

### `UikitProgressRing` [Since v1.1.0]
环形进度条，适合圆屏。
```java
UikitProgressRing ring = new UikitProgressRing(this);
ring.setProgress(0.5f); // 范围 0.0f ~ 1.0f
```

### `UikitSlider` [Since v1.3.0]
可拖动进度条。
```java
UikitSlider slider = new UikitSlider(this);
slider.setMaxProgress(100); // 设置最大值
slider.setProgress(30);     // 设置当前值
slider.setOnProgressChangedListener(progress -> {
    // 拖动时的回调
});
```

### `UikitLoadingBar` [Since v1.3.0]
循环播放的加载条。
```java
UikitLoadingBar loading = new UikitLoadingBar(this);
// 添加到布局后自动开始动画，销毁时自动停止
```

### `UikitLoadingView` [Since v1.4.0]
三点呼吸跳动的 Loading 动画。
```java
UikitLoadingView loadingView = new UikitLoadingView(this);
```

### `UikitSkeleton` [Since v1.1.0]
骨架屏占位。
```java
UikitSkeleton skeleton = new UikitSkeleton(this);
```

---

## 5. 全屏交互组件 (推荐)

所有全屏 Activity 都会通过 `startActivityForResult` 启动。

### `UikitFullListActivity.show()` [Since v1.3.0]
全屏列表，支持双行显示和自定义卡片背景色。
* **参数**：`(Activity activity, String title, ArrayList<UikitListItemModel> items, int requestCode)`
* **数据模型 `UikitListItemModel` 构造参数**：
  1. `String title`: 主标题。
  2. `String subtitle`: 副标题，可为 null。
  3. `int accentColor`: 文字强调色。传 0 表示默认。
  4. `int cardColor`: 卡片背景色。传 0 表示默认。
* **返回值提取** (在 `onActivityResult` 中)：
  * `data.getIntExtra(UikitFullListActivity.EXTRA_RESULT_INDEX, -1)`
  * `data.getStringExtra(UikitFullListActivity.EXTRA_RESULT_TEXT)`

```java
ArrayList<UikitListItemModel> items = new ArrayList<>();
// 普通样式
items.add(new UikitListItemModel("普通选项", "默认样式", 0, 0));
// 自定义红色卡片
items.add(new UikitListItemModel("错误", "红色背景", 0, 0xFFF44336));

UikitFullListActivity.show(this, "高级列表", items, 1001);
```

### `UikitFullInputActivity.show()` [Since v1.3.0]
全屏文本输入，完美适配手表键盘。
* **参数**：`(Activity activity, String title, String defaultText, String hint, String positiveText, String negativeText, int requestCode)`
  * `defaultText`: 输入框的默认内容，可为 ""。
  * `hint`: 占位提示文字。
  * `positiveText` / `negativeText`: 底部按钮文字。传 null 则使用系统默认的 "确定"/"取消"。
* **返回值提取**：`data.getStringExtra(UikitFullInputActivity.EXTRA_RESULT_TEXT)`

```java
UikitFullInputActivity.show(this, "输入名称", "", "请输入...", "提交", "取消", 1002);
```

### `UikitFullTextActivity.show()` [Since v1.3.0, autoSplit Since v1.5.0]
全屏长文本阅读，自带 3D 滚轮字幕。
* **参数**：`(Activity activity, String title, String message, String positiveText, int requestCode)` 或带 `boolean autoSplit` 的重载方法。
* **autoSplit 参数说明**：
  * `false` (默认)：整段文字作为一个卡片展示，支持自然换行。
  * `true`：按句号拆分，并启用 3D 滚轮字幕特效。
```java
UikitFullTextActivity.show(this, "用户协议", longText, "我知道了", 1003);
// 开启 3D 滚轮切割
UikitFullTextActivity.show(this, "特性列表", "特性一。特性二。", "知道了", true, 1004);
```

### `UikitFullConfirmActivity.show()` [Since v1.4.0]
极简全屏确认框。
* **参数**：`(Activity activity, String title, String message, int requestCode)`
* **返回值**：无需提取数据。点击 ✓ 返回 `RESULT_OK`，点击 × 返回 `RESULT_CANCELED`。
```java
UikitFullConfirmActivity.show(this, "删除项目？", "此操作无法撤销", 1004);
```

### `UikitPickerActivity.show()` [Since v1.5.0]
上下滚动的居中 3D 选择器。
* **参数**：`(Activity activity, String title, String[] items, int requestCode)`
* **返回值提取**：
  * `data.getIntExtra(UikitPickerActivity.EXTRA_RESULT_INDEX, -1)`
  * `data.getStringExtra(UikitPickerActivity.EXTRA_RESULT_TEXT)`
```java
UikitPickerActivity.show(this, "请选择", new String[]{"A", "B", "C"}, 1005);
```

### `UikitDatePickerActivity.show()` [Since v1.4.0]
年月日三列滚轮日期选择器。
* **参数**：`(Activity activity, int requestCode)`
* **返回值提取**：均为 String 类型。
  * `data.getStringExtra(UikitDatePickerActivity.EXTRA_RESULT_YEAR)`
  * `data.getStringExtra(UikitDatePickerActivity.EXTRA_RESULT_MONTH)`
  * `data.getStringExtra(UikitDatePickerActivity.EXTRA_RESULT_DAY)`
```java
UikitDatePickerActivity.show(this, 1006);
```

### `UikitImageViewerActivity.show()` [Since v1.4.0]
全屏图片查看器（支持单指拖拽、双指缩放）。
* **参数**：`(Activity activity, String assetName)`。`assetName` 为放在 `src/main/assets/` 下的文件名。
```java
UikitImageViewerActivity.show(this, "demo_image.png");
```

---

## 6. 废弃 API (旧版非全屏弹窗)

> ⚠️ **警告**：以下 API 已标记 `@Deprecated`（自 v1.3.0 起废弃）。
> 在未来的版本中可能会被移除，请务必迁移至全屏版本。

| 废弃 API | 替代方案 |
|---|---|
| `UikitUi.showList()` | `UikitFullListActivity.show()` |
| `UikitUi.showInput()` | `UikitFullInputActivity.show()` |
| `UikitUi.showConfirm()` | `UikitFullConfirmActivity.show()` |

---

## 7. 弹窗与硬件辅助

### `UikitToast.show()` [Since v1.1.0]
居中显示的轻量级提示。
```java
UikitToast.show(this, "已保存");
```

### `UikitBottomSheet` [Since v1.1.0]
底部抽屉，支持下滑关闭。
* **构造参数**：`(Context, String title, View contentView)`
```java
new UikitBottomSheet(this, "选项", new UikitEmptyView(this)).show();
```

### `WearHardwareUtils` [Since v1.0.0]
* `WearHardwareUtils.vibrate(Context, int type)`: 触发震动。`type` 可选 `HAPTIC_LIGHT`, `HAPTIC_SUCCESS`, `HAPTIC_ERROR`。
* `WearHardwareUtils.keepScreenOn(Activity, boolean)`: 屏幕常亮。
* `WearHardwareUtils.bindRotaryEncoder(ScrollView)`: 绑定表冠滑动事件。

### `UikitClickUtils` [Since v1.4.0]
防止用户手抖连续点击按钮。
```java
btn.setOnClickListener(v -> {
    if (UikitClickUtils.isFastDoubleClick()) return;
});
```

---

## 8. 内置图标资源清单 [Since v1.5.0]

自 v1.5.0 起，库中包含 35 个 MD3 矢量图标，资源前缀为 `ic_uikit_`。直接使用 `R.drawable.ic_uikit_xxx` 即可调用。

**完整图标列表**：
`home` (首页), `settings` (设置), `search` (搜索), `delete` (删除), `edit` (编辑), `add` (添加), `close` (关闭), `check` (勾选), `arrow_back` (左箭头), `arrow_forward` (右箭头), `play` (播放), `pause` (暂停), `stop` (停止), `next` (下一首), `prev` (上一首), `volume_up` (音量加), `volume_down` (音量减), `music` (音乐), `wifi` (无线网), `bluetooth` (蓝牙), `battery` (电池), `message` (消息), `email` (邮件), `notifications` (通知), `alarm` (闹钟), `lock` (锁定), `unlock` (解锁), `heart` (爱心), `star` (星标), `cloud_download` (下载), `cloud_upload` (上传), `folder` (文件夹), `image` (图片), `time` (时间), `menu` (菜单), `more_vert` (更多)。

```java
imageView.setImageResource(R.drawable.ic_uikit_wifi);
```
