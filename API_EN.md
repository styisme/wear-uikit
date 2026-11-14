# Uikit Source-Level API Dictionary (v1.7.1)

This document details all public classes, method signatures, constants, XML attributes, and callbacks.

---

## 1. Initialization & Core Config

### 1.1 `UikitConfig`
- **`public static void init(String prefsName)`** [Since v1.0.0]
- **`public static void setCustomPrimaryColor(Context, int color)`** [Since v1.2.0]
- **`public static void setThemeMode(Context, String mode)`** [Since v1.0.0] (`"auto"`, `"light"`, `"dark"`)

### 1.2 `UikitUi`
- **`public static Context wrapScale(Context newBase)`** [Since v1.0.0]
- **`public static void setupWheelScroll(ScrollView sv, LinearLayout container)`** [Since v1.1.0, Bouncing v1.6.0]

---

## 2. Colors & Themes

### 2.1 `UikitThemePresets` [Since v1.7.0]
- **`public static void applyPreset(Context ctx, int color)`**
- **Constants**: `GREEN`, `BLUE`, `RED`, `PURPLE`, `YELLOW`, `ORANGE`, `TEAL`.

---

## 3. Basic & Form Widgets

### 3.1 `UikitButton`
**XML Attributes**:
| Attribute | Type | Default |
|---|---|---|
| `app:uikit_radius` | Dimension | 32dp |
| `app:uikit_backgroundColor` | Color | Dynamic |
| `app:uikit_textColor` | Color | Dynamic |

**Native Proxy APIs (Drop-in replacement)**:
- **`public void setTextColor(int color)`**
- **`public void setTextColor(ColorStateList colors)`**
- **`public void setTextSize(float size)`**
- **`public void setGravity(int gravity)`**
- **`public void setTypeface(Typeface tf)`**
- **`public void setAllCaps(boolean allCaps)`**
- **`public void setEnabled(boolean enabled)`**

**Native Library APIs**:
- **`public void setText(CharSequence text)`**
- **`public void setIcon(Drawable drawable)`**
- **`public void setIconResource(int resId)`**

### 3.2 `UikitCompatButton` [Since v1.7.1]
Drop-in alias for native `<Button>`.

### 3.3 `UikitIconButton` [Since v1.7.0]
**XML Attributes**:
| Attribute | Type | Default |
|---|---|---|
| `app:uikit_icon_radius` | Dimension | 48dp |
| `app:uikit_icon_bg` | Color | Dynamic |
| `app:uikit_icon_tint` | Color | Dynamic |

### 3.4 `UikitStepper`
- **`public void setMinValue(int)`**, **`setMaxValue(int)`**, **`setCurrentValue(int)`**
- **`public void setOnValueChangeListener(...)`**

### 3.5 `UikitLinearList`
- **`public void addItem(UikitListItem item, boolean showDivider)`**

---

## 4. Progress & Micro-interactions

### 4.1 `UikitProgressBar`
- **Native**: `setMax(int)`, `setProgress(int)`
- **Library**: `setProgress(float)` (0.0f ~ 1.0f)

### 4.2 `UikitSlider`
- **Native (SeekBar)**: `setMax(int)`, `setProgress(int)`, `setOnSeekBarChangeListener(...)`
- **Library**: `setMaxProgress(float)`, `setOnProgressChangedListener(...)`

### 4.3 `UikitCircularProgressTimer`
- **`public void start(long millis)`**, **`stop()`**
- **`public void setOnTimerFinishListener(...)`**

---

## 5. Fullscreen Components

Launch via `startActivityForResult`.

### 5.1 `UikitFullListActivity.show(Activity, String title, ArrayList<UikitListItemModel> items, int requestCode)`
- **Result**: `EXTRA_RESULT_INDEX`, `EXTRA_RESULT_TEXT`

### 5.2 `UikitFullInputActivity.show(Activity, String title, String defaultText, String hint, String positiveText, String negativeText, int requestCode)`
- **Result**: `EXTRA_RESULT_TEXT`

### 5.3 `UikitFullTextActivity.show(Activity, String title, String message, String positiveText, boolean autoSplit, int requestCode)`
- **autoSplit**: Excludes numbers (2.3) and URLs (.com) automatically.

### 5.4 `UikitFullConfirmActivity.show(Activity, String title, String message, int requestCode)`
- **Result**: `RESULT_OK` (✓), `RESULT_CANCELED` (×)

### 5.5 `UikitPickerActivity.show(Activity, String title, String[] items, int requestCode)`
- **Result**: `EXTRA_RESULT_INDEX`, `EXTRA_RESULT_TEXT`

### 5.6 `UikitDatePickerActivity.show(Activity, int requestCode)`
- **Result**: `EXTRA_RESULT_YEAR`, `EXTRA_RESULT_MONTH`, `EXTRA_RESULT_DAY`

---

## 6. Deprecated APIs
These APIs are fully functional but have inferior visual experiences on round screens. Use fullscreen alternatives.

---

## 7. Built-in Icons (36)
Prefix: `ic_uikit_`.
(Full list matches Chinese document)


## 8. Built-in 36 MD3 Vector Icons [Since v1.5.0]

36 pure MD3 vector icons are built-in with zero dependencies.
All icons are stored in `res/drawable` with the prefix **`ic_uikit_`**.

### 💡 Basic Usage
**In XML:**
```xml
<!-- With native ImageView -->
<ImageView
    android:layout_width="24dp"
    android:layout_height="24dp"
    android:src="@drawable/ic_uikit_wifi"
    app:tint="#FFFFFF" /> 

<!-- With UikitIconButton -->
<org.uikit.UikitIconButton
    android:layout_width="48dp"
    android:layout_height="48dp"
    android:src="@drawable/ic_uikit_play" />
```

**In Java:**
```java
ImageView iv = new ImageView(this);
iv.setImageResource(R.drawable.ic_uikit_heart);
iv.setColorFilter(UikitColor.getPrimaryColor(this));
```

---

### 🗂️ Icon Dictionary (36 Icons)

#### 📁 Files & System
| Resource Name | Meaning | Use Case |
|---|---|---|
| `ic_uikit_home` | Home | Navigation, Home button |
| `ic_uikit_settings` | Gear | Settings menu |
| `ic_uikit_search` | Magnifier | Search bar, search action |
| `ic_uikit_folder` | Folder | File management |
| `ic_uikit_image` | Image | Gallery, photo placeholder |
| `ic_uikit_time` | Clock | History, timeline |
| `ic_uikit_menu` | Hamburger Menu | Drawer menu |
| `ic_uikit_more_vert` | 3 Dots | More options, overflow |

#### 🎵 Media & Playback
| Resource Name | Meaning | Use Case |
|---|---|---|
| `ic_uikit_play` | Play | Media controls, continue |
| `ic_uikit_pause` | Pause | Media controls |
| `ic_uikit_stop` | Stop | Media controls, cancel |
| `ic_uikit_next` | Next Track | Media controls |
| `ic_uikit_prev` | Previous Track | Media controls |
| `ic_uikit_music` | Music Note | Music list, audio placeholder |
| `ic_uikit_volume_up` | Volume Up | Increase volume |
| `ic_uikit_volume_down` | Volume Down | Decrease volume |

#### 🛠️ Actions & Editing
| Resource Name | Meaning | Use Case |
|---|---|---|
| `ic_uikit_add` | Plus | Create, add new |
| `ic_uikit_edit` | Pencil | Edit profile, modify |
| `ic_uikit_delete` | Trash Can | Delete, clear |
| `ic_uikit_check` | Checkmark | Confirm, done |
| `ic_uikit_close` | Cross | Cancel, close dialog |
| `ic_uikit_arrow_back` | Left Arrow | Go back |
| `ic_uikit_arrow_forward` | Right Arrow | Go forward |

#### 📶 Network & Status
| Resource Name | Meaning | Use Case |
|---|---|---|
| `ic_uikit_wifi` | Wi-Fi | Network status |
| `ic_uikit_bluetooth` | Bluetooth | Bluetooth connection |
| `ic_uikit_battery` | Battery | Battery level |
| `ic_uikit_lock` | Lock | Security, locked |
| `ic_uikit_unlock` | Unlock | Unlocked, public |
| `ic_uikit_alarm` | Alarm | Reminders, alarms |

#### 💬 Communication & Social
| Resource Name | Meaning | Use Case |
|---|---|---|
| `ic_uikit_message` | Message Bubble | Chat, notifications |
| `ic_uikit_email` | Envelope | Email, feedback |
| `ic_uikit_notifications` | Bell | Push notifications |
| `ic_uikit_heart` | Heart | Like, favorite |
| `ic_uikit_star` | Star | Rating, bookmark |
| `ic_uikit_cloud_download` | Cloud Download | Download from cloud |
| `ic_uikit_cloud_upload` | Cloud Upload | Upload to cloud |

---

### 🎨 Why Built-in?
1. **Legacy Compatible**: `VectorDrawable` renders perfectly on API 10+.
2. **Dynamic Colors**: Pure black paths, easily tinted via `app:tint` or `setColorFilter`.
3. **Objective Size Increase**: Although the plain text SVG paths are small, after AAPT2 compilation and resource indexing, the 36 icons (with density adaptations) will objectively increase the `.aar` size by a few hundred KB. If your project is extremely sensitive to APK size and doesn't need these icons, you can manually delete the `uikit/src/main/res/drawable/ic_uikit_*` files.
