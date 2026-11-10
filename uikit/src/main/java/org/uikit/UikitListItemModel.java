package org.uikit;

import java.io.Serializable;

public class UikitListItemModel implements Serializable {
    public String title;
    public String subtitle;
    public int accentColor; // 传 0 表示使用默认主题色

    public UikitListItemModel(String title) {
        this(title, null, 0);
    }

    public UikitListItemModel(String title, String subtitle) {
        this(title, subtitle, 0);
    }

    public UikitListItemModel(String title, String subtitle, int accentColor) {
        this.title = title;
        this.subtitle = subtitle;
        this.accentColor = accentColor;
    }
}
