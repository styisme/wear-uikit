# Uikit 源码级 API 字典 (v1.7.1)

本文档详尽到了“函数签名”级别。包含所有公开类、方法、常量、XML 属性以及事件回调。

---

## 一、 初始化与核心配置

### 1.1 `UikitConfig`
全局配置管理器。
- **`public static void init(String prefsName)`** [Since v1.0.0]
  - `prefsName`: SharedPreferences 文件名，建议传入 `"my_app_prefs"`。
  - 说明：必须在 `Application` 中调用。
- **`public static void setCustomPrimaryColor(Context, int color)`** [Since v1.2.0]
- **`public static void setCustomAccentColor(Context, int color)`** [Since v1.2.0]
- **`public static void clearCustomColors(Context)`** [Since v1.2.0]
- **`public static void setThemeMode(Context, String mode)`** [Since v1.0.0]
  - `mode`: 可选 `"auto"`, `"light"`, `"dark"`。

### 1.2 `UikitUi`
UI 核心渲染引擎。
- **`public static Context wrapScale(Context newBase)`** [Since v1.0.0]
- **`public static void applyEntryAnimation(Activity activity)`** [Since v1.1.0]
- **`public static void applyRoundPadding(Context ctx, View root)`** [Since v1.0.0]
- **`public static void setupWheelScroll(ScrollView sv, LinearLayout container)`** [Since v1.1.0, 回弹 v1.6.0]
  - 说明：内部自动处理 3D 滚轮与边缘阻尼回弹（Bouncing）。

---

## 二、 颜色与主题

### 2.1 `UikitThemePresets`
预设主题色工具类。
- **`public static void applyPreset(Context ctx, int color)`** [Since v1.7.0]
- **常量列表**：`GREEN`, `BLUE`, `RED`, `PURPLE`, `YELLOW`, `ORANGE`, `TEAL`。

### 2.2 `UikitColor`
- **`public static int getPrimaryColor(Context)`**
- **`public static int getOnPrimaryColor(Context)`**
- **`public static int getAccentColor(Context, boolean isDark)`**

---

## 三、 基础控件与表单

### 3.1 `UikitButton` (胶囊按钮)
*底层为 `LinearLayout`，内部包含 `ImageView` 与 `TextView`。*

**XML 属性**：
| 属性名 | 类型 | 默认值 | 说明 |
|---|---|---|---|
| `app:uikit_radius` | Dimension | 32dp | 圆角 |
| `app:uikit_backgroundColor` | Color | 动态主色 | 背景色 |
| `app:uikit_textColor` | Color | 动态反色 | 文字色 |

**库原生 API**：
- **`public void setText(CharSequence text)`**
- **`public CharSequence getText()`**
- **`public void setIcon(Drawable drawable)`**
- **`public void setIconResource(int resId)`**

**原生代理 API（完美无缝替换）**：
- **`public void setTextColor(int color)`**
- **`public void setTextColor(ColorStateList colors)`**
- **`public ColorStateList getTextColors()`**
- **`public void setTextSize(float size)`**
- **`public void setTextSize(int unit, float size)`**
- **`public void setGravity(int gravity)`**
- **`public void setTypeface(Typeface tf)`**
- **`public void setAllCaps(boolean allCaps)`**
- **`public void setEnabled(boolean enabled)`** (禁用时自动变半透明)

### 3.2 `UikitCompatButton` [Since v1.7.1]
专用于无缝平替原生 `<Button>` 的空壳子类，直接继承自 `UikitButton`。

### 3.3 `UikitIconButton` (纯图标按钮) [Since v1.7.0]
**XML 属性**：
| 属性名 | 类型 | 默认值 |
|---|---|---|
| `app:uikit_icon_radius` | Dimension | 48dp (圆形) |
| `app:uikit_icon_bg` | Color | 动态主色 |
| `app:uikit_icon_tint` | Color | 动态反色 |

**方法**：
- **`public void setIconResource(int resId)`**
- **`public void setIconTint(int color)`**

### 3.4 `UikitCheckBox` & `UikitRadioButton`
直接继承自原生类。自带 Canvas 越界防御。
- 直接调用原生 `setChecked(boolean)` 和 `setOnCheckedChangeListener(...)`。

### 3.5 `UikitStepper` (步进器)
- **`public void setMinValue(int min)`**
- **`public void setMaxValue(int max)`**
- **`public void setCurrentValue(int val)`**
- **`public int getCurrentValue()`**
- **`public void setOnValueChangeListener(OnValueChangeListener listener)`**

### 3.6 `UikitLinearList` (轻量列表容器)
- **`public void addItem(UikitListItem item, boolean showDivider)`**
- **`public void clear()`**

---

## 四、 进度、加载与微交互

### 4.1 `UikitProgressBar` (横向进度条)
**原生兼容 API**：
- **`public void setMax(int max)`**
- **`public int getMax()`**
- **`public void setProgress(int progress)`**
- **`public int getProgress()`**

**库原生 API**：
- **`public void setProgress(float progress)`** (范围 0.0f ~ 1.0f)

### 4.2 `UikitSlider` (可拖动进度条)
**原生兼容 API (SeekBar)**：
- **`public void setMax(int max)`**
- **`public void setProgress(int progress)`**
- **`public void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener listener)`**

**库原生 API**：
- **`public void setMaxProgress(float max)`**
- **`public void setProgress(float progress)`**
- **`public interface OnProgressChangedListener { void onProgressChanged(float progress); }`**
- **`public void setOnProgressChangedListener(...)`**

### 4.3 `UikitCircularProgressTimer` (倒计时环)
- **`public void start(long millis)`**
- **`public void stop()`**
- **`public void setOnTimerFinishListener(OnTimerFinishListener listener)`**

### 4.4 `UikitMarqueeTextView` (跑马灯)
- 自带无限滚动动画，直接 `setText()` 即可。

---

## 五、 全屏交互组件

所有全屏组件通过 `startActivityForResult` 启动。

### 5.1 `UikitFullListActivity`
- **`public static void show(Activity, String title, ArrayList<UikitListItemModel> items, int requestCode)`**
- **返回值**：
  - `int index = data.getIntExtra(UikitFullListActivity.EXTRA_RESULT_INDEX, -1);`
  - `String text = data.getStringExtra(UikitFullListActivity.EXTRA_RESULT_TEXT);`

### 5.2 `UikitFullInputActivity`
- **`public static void show(Activity, String title, String defaultText, String hint, String positiveText, String negativeText, int requestCode)`**
- **返回值**：
  - `String text = data.getStringExtra(UikitFullInputActivity.EXTRA_RESULT_TEXT);`

### 5.3 `UikitFullTextActivity`
- **`public static void show(Activity, String title, String message, String positiveText, boolean autoSplit, int requestCode)`**
  - `autoSplit`: 是否按句号拆分文本实现 3D 滚轮。智能断句已排除 `2.3` 和 `.com`。

### 5.4 `UikitFullConfirmActivity`
- **`public static void show(Activity, String title, String message, int requestCode)`**
- **返回值**：点击 ✓ 返回 `RESULT_OK`，点击 × 返回 `RESULT_CANCELED`。

### 5.5 `UikitPickerActivity`
- **`public static void show(Activity, String title, String[] items, int requestCode)`**
- **返回值**：`EXTRA_RESULT_INDEX` 和 `EXTRA_RESULT_TEXT`。

### 5.6 `UikitDatePickerActivity`
- **`public static void show(Activity, int requestCode)`**
- **返回值**：`EXTRA_RESULT_YEAR`, `EXTRA_RESULT_MONTH`, `EXTRA_RESULT_DAY`。

---

## 六、 废弃 API 说明

| 废弃 API | 状态 | 替代方案 |
|---|---|---|
| `UikitUi.showList()` | 可用，但在圆屏上边缘会被切。 | `UikitFullListActivity` |
| `UikitUi.showInput()` | 可用，但键盘弹起会遮挡输入框。 | `UikitFullInputActivity` |
| `UikitUi.showConfirm()` | 可用，但长文本留白过大。 | `UikitFullConfirmActivity` |

---

## 七、 内置图标库 (36 个)

前缀为 `ic_uikit_`。
- **文件与系统**：`home`, `settings`, `search`, `folder`, `image`, `time`, `menu`, `more_vert`
- **媒体与播放**：`play`, `pause`, `stop`, `next`, `prev`, `music`, `volume_up`, `volume_down`
- **操作与编辑**：`add`, `edit`, `delete`, `check`, `close`, `arrow_back`, `arrow_forward`
- **网络与状态**：`wifi`, `bluetooth`, `battery`, `lock`, `unlock`, `alarm`
- **通讯与社交**：`message`, `email`, `notifications`, `heart`, `star`, `cloud_download`, `cloud_upload`


## 8. 内置 36 个 MD3 矢量图标 [Since v1.5.0]

自 v1.5.0 起，库中直接内置了 **36 个纯正的 MD3 风格矢量图标**，无任何第三方依赖。
所有图标统一存放在 `res/drawable` 中，资源前缀为 **`ic_uikit_`**。

### 💡 基本用法
**在 XML 中使用：**
```xml
<!-- 配合原生 ImageView -->
<ImageView
    android:layout_width="24dp"
    android:layout_height="24dp"
    android:src="@drawable/ic_uikit_wifi"
    app:tint="#FFFFFF" /> 

<!-- 配合库的纯图标按钮 UikitIconButton -->
<org.uikit.UikitIconButton
    android:layout_width="48dp"
    android:layout_height="48dp"
    android:src="@drawable/ic_uikit_play" />
```

**在 Java 中动态使用与染色：**
```java
ImageView iv = new ImageView(this);
// 1. 设置图标
iv.setImageResource(R.drawable.ic_uikit_heart);
// 2. 动态跟随主题染色
iv.setColorFilter(UikitColor.getPrimaryColor(this));
```

---

### 🗂️ 图标图鉴 (36 个)

#### 📁 文件与系统 (Files & System)
| 资源名 | 含义 | 适用场景 |
|---|---|---|
| `ic_uikit_home` | 主页 / 房子 | 底部导航、返回首页 |
| `ic_uikit_settings` | 齿轮 / 设置 | 设置页面入口 |
| `ic_uikit_search` | 放大镜 / 搜索 | 搜索栏占位、搜索按钮 |
| `ic_uikit_folder` | 文件夹 | 文件管理、目录选择 |
| `ic_uikit_image` | 图片 / 相册 | 图片浏览、相册入口 |
| `ic_uikit_time` | 时钟 / 历史 | 历史记录、时间轴 |
| `ic_uikit_menu` | 汉堡菜单 (三横线) | 侧边栏呼出、抽屉菜单 |
| `ic_uikit_more_vert` | 竖排三点 | 更多操作、溢出菜单 |

#### 🎵 媒体与播放 (Media & Playback)
| 资源名 | 含义 | 适用场景 |
|---|---|---|
| `ic_uikit_play` | 播放 (三角形) | 播放器控制栏、继续 |
| `ic_uikit_pause` | 暂停 (双竖线) | 播放器控制栏 |
| `ic_uikit_stop` | 停止 (方块) | 播放器控制栏、取消任务 |
| `ic_uikit_next` | 下一首 (右箭头带竖线) | 播放器控制栏 |
| `ic_uikit_prev` | 上一首 (左箭头带竖线) | 播放器控制栏 |
| `ic_uikit_music` | 音符 | 音乐列表、歌曲占位 |
| `ic_uikit_volume_up` | 喇叭带加号 | 音量增加、取消静音 |
| `ic_uikit_volume_down` | 喇叭带减号 | 音量减少、静音 |

#### 🛠️ 操作与编辑 (Actions & Editing)
| 资源名 | 含义 | 适用场景 |
|---|---|---|
| `ic_uikit_add` | 加号 | 新建、添加项目 |
| `ic_uikit_edit` | 铅笔 | 编辑资料、修改状态 |
| `ic_uikit_delete` | 垃圾桶 | 删除、清空记录 |
| `ic_uikit_check` | 对勾 | 确认、完成、选中状态 |
| `ic_uikit_close` | 叉号 | 取消、关闭弹窗、清除输入 |
| `ic_uikit_arrow_back` | 左箭头 | 返回上一页、折叠 |
| `ic_uikit_arrow_forward` | 右箭头 | 进入下一页、展开 |

#### 📶 网络与状态 (Network & Status)
| 资源名 | 含义 | 适用场景 |
|---|---|---|
| `ic_uikit_wifi` | Wi-Fi 信号 | 网络状态、局域网传输 |
| `ic_uikit_bluetooth` | 蓝牙标志 | 蓝牙配对、连接状态 |
| `ic_uikit_battery` | 电池 | 电量显示、省电模式 |
| `ic_uikit_lock` | 闭合的锁 | 隐私保护、加密、已锁定 |
| `ic_uikit_unlock` | 打开的锁 | 公开访问、已解锁 |
| `ic_uikit_alarm` | 闹钟 / 铃铛 | 定时提醒、闹钟设置 |

#### 💬 通讯与社交 (Communication & Social)
| 资源名 | 含义 | 适用场景 |
|---|---|---|
| `ic_uikit_message` | 对话气泡 | 聊天、消息列表、通知 |
| `ic_uikit_email` | 信封 | 邮件、反馈、联系作者 |
| `ic_uikit_notifications` | 铃铛 | 消息推送、通知中心 |
| `ic_uikit_heart` | 爱心 | 点赞、收藏、喜欢 |
| `ic_uikit_star` | 星标 | 收藏、评分、特别关注 |
| `ic_uikit_cloud_download` | 云下载箭头 | 从云端获取文件 |
| `ic_uikit_cloud_upload` | 云上传箭头 | 备份至云端、上传文件 |

---

### 🎨 为什么内置这些图标？
1. **极低版本完美兼容**：所有图标均为 `VectorDrawable` 格式，在 Android 2.3 (API 10) 及以上完美渲染，绝不模糊。
2. **完美跟随动态取色**：图标本身为纯黑色，在使用 `app:tint` 或代码中的 `setColorFilter` 时，可瞬间变成主题色、白色或任何你想要的颜色。
3. **体积客观增加**：虽然纯文本的 SVG 路径本身很小，但经过 AAPT2 编译、资源索引生成后，36 个图标及相应的多密度适配确实会使 `.aar` 体积增加约几百 KB。如果你的项目对包体积极度敏感，且不需要这些图标，可以手动删除 `uikit/src/main/res/drawable/ic_uikit_*` 相关文件。
