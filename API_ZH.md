# Uikit 字典级 API 手册 (v1.6.0)

本文档详尽列出了 `org.uikit` 库中所有组件的调用方式、参数说明、返回类型及底层交互原理。
建议开发者通读一遍，以便了解各个组件的适用场景与最佳实践。

---

## 1. 初始化与生命周期

### `UikitConfig.init(String prefsName)` [Since v1.0.0]
初始化库的 SharedPreferences 存储文件名。
* **参数**：`prefsName` (String) - 建议传宿主 App 的名字，如 `"my_app_prefs"`。如果不传，默认使用 `"uikit_prefs"`。
* **底层原理**：库中所有的主题配置、UI缩放比例、自定义颜色、语言设置都会存在这里。调用此方法会改变后续所有 `prefs(ctx)` 获取的实例。
* **调用时机**：必须在 `Application.onCreate()` 或首个 Activity 的 `onCreate()` 最早期调用。

### `UikitUi.wrapScale(Context newBase)` [Since v1.0.0]
应用物理级 UI 缩放（支持 0.3x ~ 2.5x）。
* **底层原理**：通过 `createConfigurationContext` 重写了宿主的 `Resources` 的 `densityDpi`。这绕过了 Android 11+ 系统对 `updateConfiguration` 的拦截。
* **调用时机**：必须重写 Activity 的 `attachBaseContext`。
```java
@Override
protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(UikitUi.wrapScale(newBase));
}
```

### `UikitTheme.applyTheme(Activity activity)` [Since v1.0.0]
应用深色/浅色主题。内部会递归遍历根视图，强制修复一些原生控件在深色模式下的“白底白字”残留。

---

## 2. 基础控件与表单

### `UikitButton` [Since v1.0.0, 架构重构 Since v1.6.0]
**核心重构说明**：在 v1.6.0 中，底层由 `TextView` 重构为 `LinearLayout`。现在它本质上是一个容器，内部包含一个 `ImageView` 和一个 `TextView`。
这带来的巨大优势是：
1. 彻底解决了低版本（API 10-23）使用 `setCompoundDrawables` 时图标死活无法居中的 Bug。
2. 允许开发者直接将任意 View 塞入按钮，不用再受限于原生的 `CompoundDrawables`。
3. 触控反馈改为了 `onTouchEvent` 重写，避免了被外层 `FrameLayout` 包裹时 `ACTION_UP` 丢失导致动画卡死的问题。

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
    app:uikit_radius="16dp" />
```

### `UikitCheckBox` & `UikitRadioButton` [Since v1.6.0]
纯手绘的 MD3 控件。选中状态使用主色填充，并利用 Canvas 绘制对勾或圆点。完全自适应深色模式和动态取色。
```java
UikitCheckBox cb = new UikitCheckBox(this);
cb.setChecked(true);
```

### `UikitStepper` [Since v1.6.0]
步进器（+ / -）。适合调音量、计时、设定目标值。
```java
UikitStepper stepper = new UikitStepper(this);
stepper.setMinValue(0);
stepper.setMaxValue(100);
stepper.setCurrentValue(30);
stepper.setOnValueChangeListener(value -> { /* 处理变化 */ });
```

---

## 3. 进度、加载与微交互

### `UikitCircularProgressTimer` [Since v1.6.0]
带倒计时动画的环形进度条。
* **底层原理**：内部使用了 `CountDownTimer`，每隔 100ms 刷新一次 `Canvas.drawArc` 的弧度。倒计时结束时，自动调用 `WearHardwareUtils.vibrate` 触发长震动。
```java
UikitCircularProgressTimer timer = new UikitCircularProgressTimer(this);
timer.setOnTimerFinishListener(() -> { /* 倒计时结束回调 */ });
timer.start(10000); // 开始 10 秒倒计时
```

### `UikitMarqueeTextView` [Since v1.6.0]
自动滚动的跑马灯文字。
* **底层原理**：重写了 `isFocused()` 并强制返回 `true`，并结合 `setEllipsize(TextUtils.TruncateAt.MARQUEE)` 和 `setMarqueeRepeatLimit(-1)`，确保没有焦点也能无限滚动。
```java
UikitMarqueeTextView marquee = new UikitMarqueeTextView(this);
marquee.setText("很长很长的一段文字...");
```

---

## 4. 全屏交互组件 (推荐)

所有全屏 Activity 都会通过 `startActivityForResult` 启动。

### `UikitFullTextActivity.show()` [Since v1.3.0, 智能断句 Since v1.6.0]
全屏长文本阅读，自带 3D 滚轮字幕。
* **参数**：`(Activity, String title, String message, String positiveText, int requestCode)` 或带 `boolean autoSplit` 的重载。
* **智能断句**：`autoSplit=true` 时，我们的正则不仅支持中文标点，而且**精准排除了数字小数点（如 2.3）和网址（如 .com）**，不再误伤英文长句。

### `UikitFullListActivity.show()` [Since v1.3.0]
全屏列表。支持双行显示和自定义卡片背景色。
* **参数**：`(Activity, String title, ArrayList<UikitListItemModel> items, int requestCode)`
* **返回值**：通过 `onActivityResult` 获取 `EXTRA_RESULT_INDEX` (int) 和 `EXTRA_RESULT_TEXT` (String)。

### `UikitFullInputActivity.show()` [Since v1.3.0]
全屏文本输入。键盘弹起时自动 `SOFT_INPUT_ADJUST_RESIZE`，内部自适应滚动。支持自定义确定/取消按钮文字。

### `UikitFullConfirmActivity.show()` [Since v1.4.0]
极简全屏确认框。点击 ✓ 返回 `RESULT_OK`，点击 × 返回 `RESULT_CANCELED`。

### `UikitPickerActivity.show()` [Since v1.5.0]
上下滚动的居中 3D 选择器。中间文字放大，上下文字缩小。松手自动吸附到最近选项。

---

## 5. 视觉特效：3D 滚轮与边缘回弹

只要调用了 `UikitUi.setupWheelScroll(scrollView, container)`，就会自动激活两大特效：

1. **3D 滚轮 (Wheel Effect)**：根据子 View 距离屏幕中心的距离，动态调整 `scaleX`、`scaleY` 和 `alpha`。距离越远，比例越小，透明度越低。
2. **边缘阻尼回弹 (Bouncing)**：自 v1.6.0 引入。底层拦截了 `OnTouchListener`，当列表滑到顶部/底部极限并继续拖拽时，施加 `translationY` 偏移（阻尼系数 0.35）。松手后使用 `OvershootInterpolator` 弹性归位。
   * **注意**：3D 滚轮特效仅在**圆屏模式下**生效，但**回弹特效在任何屏幕下均生效**。

---

## 6. 废弃 API 与迁移指南

> ⚠️ **状态说明**：以下 API 已标记 `@Deprecated`（自 v1.3.0 起）。
> **它们在底层逻辑上依然完全可用，能够正常弹出、正常回调。** 
> 被标记废弃的唯一原因是：受限于早期的非全屏 Dialog 架构，它们在手表的圆屏和软键盘弹起场景下，**视觉表现不如全屏组件**。为了给用户提供最完美的 UI 体验，强烈建议新项目使用全屏替代方案。

| 废弃 API | 体验差异（为什么推荐迁移） | 替代方案 |
|---|---|---|
| `UikitUi.showList()` | 在圆屏设备上，弹窗边缘可能被遮挡，列表过长时滑动不够顺畅。 | `UikitFullListActivity` |
| `UikitUi.showInput()` | 手表键盘弹起时，弹窗容易被挤压或发生遮挡，输入体验不及全屏。 | `UikitFullInputActivity` |
| `UikitUi.showConfirm()` | 长文本时内部留白较大，排版不够美观。 | `UikitFullConfirmActivity` / `UikitFullTextActivity` |

---

## 7. 内置 36 个 MD3 矢量图标清单 [Since v1.5.0]

自 v1.5.0 起，库中直接内置了 **36 个纯正的 MD3 风格矢量图标**。你不需要引入任何第三方库，直接通过 `R.drawable.ic_uikit_xxx` 即可调用。
所有图标均自动适配系统的深色/浅色主题（借助 `setTint` 或 `ColorFilter`）。

为了方便查阅，我们将它们分为 5 个类别：

### 📁 文件与系统
* `ic_uikit_home`：主页
* `ic_uikit_settings`：设置齿轮
* `ic_uikit_search`：放大镜搜索
* `ic_uikit_folder`：文件夹
* `ic_uikit_image`：图片占位
* `ic_uikit_time`：时钟
* `ic_uikit_menu`：汉堡菜单
* `ic_uikit_more_vert`：竖排三点（更多）

### 🎵 媒体与播放
* `ic_uikit_play`：播放
* `ic_uikit_pause`：暂停
* `ic_uikit_stop`：停止
* `ic_uikit_next`：下一首
* `ic_uikit_prev`：上一首
* `ic_uikit_music`：音符
* `ic_uikit_volume_up`：音量增加
* `ic_uikit_volume_down`：音量减少

### 🛠️ 操作与编辑
* `ic_uikit_add`：加号（添加）
* `ic_uikit_edit`：铅笔（编辑）
* `ic_uikit_delete`：垃圾桶（删除）
* `ic_uikit_check`：勾号（完成）
* `ic_uikit_close`：叉号（关闭）
* `ic_uikit_arrow_back`：左箭头（返回）
* `ic_uikit_arrow_forward`：右箭头（前进）

### 📶 网络与状态
* `ic_uikit_wifi`：Wi-Fi 信号
* `ic_uikit_bluetooth`：蓝牙
* `ic_uikit_battery`：电池（满电）
* `ic_uikit_lock`：锁定
* `ic_uikit_unlock`：解锁
* `ic_uikit_alarm`：闹钟

### 💬 通讯与社交
* `ic_uikit_message`：消息气泡
* `ic_uikit_email`：信封邮件
* `ic_uikit_notifications`：通知铃铛
* `ic_uikit_heart`：爱心（喜欢）
* `ic_uikit_star`：星标（收藏）
* `ic_uikit_cloud_download`：云下载
* `ic_uikit_cloud_upload`：云上传

```java
// 使用示例
imageView.setImageResource(R.drawable.ic_uikit_wifi);
imageView.setColorFilter(UikitColor.getPrimaryColor(this)); // 手动染色
```
