# Uikit 保姆级 API 字典 (v1.7.0)

本文档涵盖了 `org.uikit` 库中所有公开 API、XML 属性、返回值处理以及底层交互原理。请配合左侧目录按需查阅。

---

## 一、 初始化与核心配置

### 1.1 `UikitConfig.init(String prefsName)`
- **版本溯源**：`[Since v1.0.0]`
- **作用**：初始化库的 SharedPreferences，库中所有的主题、缩放、语言配置都会存入此文件。
- **参数**：
  - `prefsName` (String)：建议传入宿主 App 的名字，如 `"my_app_prefs"`。
- **调用时机**：必须在 `Application.onCreate()` 最早期调用。
```java
UikitConfig.init("my_app_prefs");
```

### 1.2 `UikitUi.wrapScale(Context newBase)`
- **版本溯源**：`[Since v1.0.0]`
- **作用**：应用物理级 UI 缩放（0.3x ~ 2.5x），彻底绕过 Android 11+ 对 `updateConfiguration` 的拦截。
- **参数**：`newBase` (Context) - 宿主 Activity 原生的 Context。
- **返回值**：包装后的 Context，**必须传给 super**。
```java
@Override
protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(UikitUi.wrapScale(newBase));
}
```

### 1.3 `UikitTheme.applyTheme(Activity activity)`
- **版本溯源**：`[Since v1.0.0]`
- **作用**：应用深色/浅色主题，递归修复白底白字。
- **调用时机**：`setContentView()` 之后调用。

---

## 二、 颜色、主题与预设

### 2.1 `UikitThemePresets` (预设主题)
- **版本溯源**：`[Since v1.7.0]`
- **作用**：专为没有动态取色的老设备（Android 12 以下）提供 MD3 配色。
- **内置常量**：
  - `UikitThemePresets.GREEN` (`#FF388E3C`)
  - `UikitThemePresets.BLUE` (`#FF1976D2`)
  - `UikitThemePresets.RED` (`#FFD32F2F`)
  - `UikitThemePresets.PURPLE` (`#FF7B1FA2`)
  - `UikitThemePresets.YELLOW` (`#FFFBC02D`)
  - `UikitThemePresets.ORANGE` (`#FFF57C00`)
  - `UikitThemePresets.TEAL` (`#FF00796B`)
```java
// 一键应用蓝色主题
UikitThemePresets.applyPreset(this, UikitThemePresets.BLUE);
```

### 2.2 `UikitColor`
- **版本溯源**：`[Since v1.0.0]`
- `getPrimaryColor(Context)`：获取当前主色。
- `getOnPrimaryColor(Context)`：自动计算与主色对比度最高的文字色（黑或白）。
- `getAccentColor(Context, boolean isDark)`：获取强调色。

---

## 三、 基础控件与表单

### 3.1 `UikitButton`
- **版本溯源**：`[Since v1.0.0, 架构重构 Since v1.6.0]`
- **特性**：底层由 `TextView` 重构为 `LinearLayout`。完美支持内嵌图标，低版本完美居中。
- **XML 属性表**：
  | 属性名 | 类型 | 默认值 | 说明 |
  |---|---|---|---|
  | `app:uikit_radius` | Dimension | `32dp` | 圆角大小 |
  | `app:uikit_backgroundColor` | Color | 动态主色 | 背景色 |
  | `app:uikit_textColor` | Color | 自动计算 | 文字颜色 |
- **代码示例**：
```xml
<org.uikit.UikitButton
    android:layout_width="match_parent"
    android:layout_height="48dp"
    android:text="自定义"
    android:drawableStart="@drawable/ic_uikit_wifi"
    app:uikit_radius="16dp"
    app:uikit_backgroundColor="#F44336" />
```

### 3.2 `UikitIconButton`
- **版本溯源**：`[Since v1.7.0]`
- **特性**：纯图标按钮，无文字，自带按压缩放动画，完美适配播放器控制栏。
- **XML 属性表**：
  | 属性名 | 类型 | 默认值 | 说明 |
  |---|---|---|---|
  | `app:uikit_icon_radius` | Dimension | `48dp` | 圆角（默认正圆） |
  | `app:uikit_icon_bg` | Color | 动态主色 | 背景色 |
  | `app:uikit_icon_tint` | Color | 自动计算 | 图标颜色 |
- **代码示例**：
```xml
<org.uikit.UikitIconButton
    android:layout_width="48dp"
    android:layout_height="48dp"
    android:src="@drawable/ic_uikit_play"
    app:uikit_icon_bg="#FF388E3C" />
```

### 3.3 `UikitCheckBox` & `UikitRadioButton`
- **版本溯源**：`[Since v1.6.0]`
- **无缝替换**：完美继承了原生 `CheckBox` 和 `RadioButton`。你只需在 XML 里把标签名换掉，Java 里的 `setOnCheckedChangeListener` 逻辑完全不用改。

### 3.4 `UikitStepper`
- **版本溯源**：`[Since v1.6.0]`
- **作用**：`- 30 +` 步进器。
- **API**：`setMinValue(int)`、`setMaxValue(int)`、`setCurrentValue(int)`、`setOnValueChangeListener(Listener)`。

### 3.5 `UikitLinearList`
- **版本溯源**：`[Since v1.7.0]`
- **作用**：轻量级列表容器。如果你想在普通的 `ScrollView` 中嵌入标准列表项，用它。
- **API**：`addItem(UikitListItem item, boolean showDivider)`、`clear()`。

---

## 四、 进度、加载与微交互

### 4.1 `UikitCircularProgressTimer`
- **版本溯源**：`[Since v1.6.0]`
- **作用**：倒计时环形进度条。
- **API**：
  - `start(long millis)`：开始倒计时。
  - `stop()`：停止。
  - `setOnTimerFinishListener(Listener)`：结束时回调，并且自动触发震动。

### 4.2 `UikitMarqueeTextView`
- **版本溯源**：`[Since v1.6.0]`
- **作用**：自动无限滚动的跑马灯文字。

---

## 五、 全屏交互组件（推荐）

所有的全屏组件都必须通过 `startActivityForResult` 启动。以下示例假设 `requestCode = 1001`。

### 5.1 `UikitFullListActivity` (全屏列表)
- **版本溯源**：`[Since v1.3.0]`
- **启动**：
```java
ArrayList<UikitListItemModel> items = new ArrayList<>();
// 参数：主标题, 副标题, 文字强调色(0为默认), 卡片背景色(0为默认)
items.add(new UikitListItemModel("普通选项", "副标题", 0, 0));
items.add(new UikitListItemModel("错误", null, 0, 0xFFF44336));
UikitFullListActivity.show(this, "高级列表", items, 1001);
```
- **接收返回值**（重写 `onActivityResult`）：
```java
if (requestCode == 1001 && resultCode == RESULT_OK) {
    int index = data.getIntExtra(UikitFullListActivity.EXTRA_RESULT_INDEX, -1);
    String text = data.getStringExtra(UikitFullListActivity.EXTRA_RESULT_TEXT);
}
```

### 5.2 `UikitFullInputActivity` (全屏输入)
- **版本溯源**：`[Since v1.3.0]`
- **启动**：`UikitFullInputActivity.show(this, "输入名称", "", "请输入...", "提交", "取消", 1002);`
- **接收返回值**：
```java
String text = data.getStringExtra(UikitFullInputActivity.EXTRA_RESULT_TEXT);
```

### 5.3 `UikitFullTextActivity` (全屏长文本)
- **版本溯源**：`[Since v1.3.0, 智能断句 Since v1.6.0]`
- **启动**：`UikitFullTextActivity.show(this, "用户协议", longText, "我知道了", true, 1003);`
  - 最后那个 `true` 表示开启智能断句，精准排除数字点（如 2.3）和网址（如 .com）。

### 5.4 `UikitPickerActivity` (3D 选择器)
- **版本溯源**：`[Since v1.5.0]`
- **启动**：`UikitPickerActivity.show(this, "请选择", new String[]{"A", "B", "C"}, 1004);`
- **接收返回值**：`EXTRA_RESULT_INDEX` (int) 和 `EXTRA_RESULT_TEXT` (String)。

### 5.5 `UikitDatePickerActivity` (年月日选择)
- **版本溯源**：`[Since v1.4.0]`
- **启动**：`UikitDatePickerActivity.show(this, 1005);`
- **接收返回值**：
```java
String year = data.getStringExtra(UikitDatePickerActivity.EXTRA_RESULT_YEAR);
String month = data.getStringExtra(UikitDatePickerActivity.EXTRA_RESULT_MONTH);
String day = data.getStringExtra(UikitDatePickerActivity.EXTRA_RESULT_DAY);
```

### 5.6 `UikitFullConfirmActivity` (极简确认)
- **版本溯源**：`[Since v1.4.0]`
- **启动**：`UikitFullConfirmActivity.show(this, "删除项目？", "此操作无法撤销", 1006);`
- **返回值**：点击 ✓ 返回 `RESULT_OK`，点击 × 返回 `RESULT_CANCELED`。

### 5.7 `UikitImageViewerActivity` (全屏看图)
- **版本溯源**：`[Since v1.4.0]`
- **启动**：`UikitImageViewerActivity.show(this, "demo_image.png");` （文件名需放在 `assets/` 下）。

---

## 六、 废弃 API 与迁移指南

> ⚠️ **状态说明**：以下 API 已标记 `@Deprecated`（自 v1.3.0 起）。
> **它们在底层逻辑上依然完全可用，能够正常弹出、正常回调。** 
> 被标记废弃的唯一原因是：受限于早期的非全屏 Dialog 架构，它们在手表的圆屏和软键盘弹起场景下，**视觉表现不如全屏组件**。为了给用户提供最完美的 UI 体验，强烈建议新项目使用全屏替代方案。

| 废弃 API | 体验差异（为什么推荐迁移） | 替代方案 |
|---|---|---|
| `UikitUi.showList()` | 在圆屏设备上，弹窗边缘可能被遮挡，列表过长时滑动不够顺畅。 | `UikitFullListActivity` |
| `UikitUi.showInput()` | 手表键盘弹起时，弹窗容易被挤压或发生遮挡，输入体验不及全屏。 | `UikitFullInputActivity` |
| `UikitUi.showConfirm()` | 长文本时内部留白较大，排版不够美观。 | `UikitFullConfirmActivity` / `UikitFullTextActivity` |

---

## 七、 视觉特效与低配优化

### 7.1 3D 滚轮与边缘回弹
只要你调用了 `UikitUi.setupWheelScroll(scrollView, container)`，你的页面就会自动获得：
1. **3D 滚轮特效**：边缘视图缩小、变透明（仅圆屏触发）。
2. **边缘阻尼回弹**：滑到顶部/底部继续拖拽，松手物理复位（全设备触发）。

### 7.2 极低配设备降级
如果设备性能极低（如 512MB 内存、单核 CPU）导致掉帧，可以强制关闭硬件加速：
```java
scrollView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
```

---

## 八、 内置 36 个 MD3 图标 [Since v1.5.0]

前缀 `ic_uikit_`。所有图标均可在 Java 或 XML 中调用，且自动适配深色模式。
```java
imageView.setImageResource(R.drawable.ic_uikit_wifi);
```

### 📁 文件与系统
- `ic_uikit_home`：主页
- `ic_uikit_settings`：设置齿轮
- `ic_uikit_search`：放大镜搜索
- `ic_uikit_folder`：文件夹
- `ic_uikit_image`：图片
- `ic_uikit_time`：时钟
- `ic_uikit_menu`：汉堡菜单
- `ic_uikit_more_vert`：竖排三点

### 🎵 媒体与播放
- `ic_uikit_play`：播放
- `ic_uikit_pause`：暂停
- `ic_uikit_stop`：停止
- `ic_uikit_next`：下一首
- `ic_uikit_prev`：上一首
- `ic_uikit_music`：音符
- `ic_uikit_volume_up`：音量加
- `ic_uikit_volume_down`：音量减

### 🛠️ 操作与编辑
- `ic_uikit_add`：加号
- `ic_uikit_edit`：铅笔编辑
- `ic_uikit_delete`：垃圾桶
- `ic_uikit_check`：勾选
- `ic_uikit_close`：关闭
- `ic_uikit_arrow_back`：左箭头
- `ic_uikit_arrow_forward`：右箭头

### 📶 网络与状态
- `ic_uikit_wifi`：Wi-Fi
- `ic_uikit_bluetooth`：蓝牙
- `ic_uikit_battery`：电池
- `ic_uikit_lock`：锁定
- `ic_uikit_unlock`：解锁
- `ic_uikit_alarm`：闹钟

### 💬 通讯与社交
- `ic_uikit_message`：消息气泡
- `ic_uikit_email`：邮件
- `ic_uikit_notifications`：通知铃铛
- `ic_uikit_heart`：爱心
- `ic_uikit_star`：星标
- `ic_uikit_cloud_download`：云下载
- `ic_uikit_cloud_upload`：云上传
