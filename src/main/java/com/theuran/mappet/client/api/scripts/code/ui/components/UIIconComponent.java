package com.theuran.mappet.client.api.scripts.code.ui.components;

import mchorse.bbs_mod.ui.framework.elements.buttons.UIIcon;
import mchorse.bbs_mod.ui.utils.icons.Icon;

public class UIIconComponent extends UIComponent<UIIcon>{
    public UIIconComponent(Icon icon, Runnable onClick) {
        super(new UIIcon(icon, (uiIcon -> {
            if (onClick != null) {
                onClick.run();
            }
        })));
    }

    public UIIconComponent color(int color) {
        this.element.iconColor(color);
        return this;
    }

    public UIIconComponent hoverColor(int color) {
        this.element.hoverColor(color);
        return this;
    }

    public UIIconComponent disabledColor(int color) {
        this.element.disabledColor(color);
        return this;
    }

    public int getColor() {
        return this.element.iconColor;
    }

    public int getHoverColor() {
        return this.element.hoverColor;
    }

    public int getDisabledColor() {
        return this.element.disabledColor;
    }
}
