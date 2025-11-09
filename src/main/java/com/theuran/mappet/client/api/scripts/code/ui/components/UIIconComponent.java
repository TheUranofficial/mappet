package com.theuran.mappet.client.api.scripts.code.ui.components;

import com.theuran.mappet.client.api.scripts.code.ui.elements.UIMappetIconElement;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIIcon;
import mchorse.bbs_mod.ui.utils.icons.Icon;
import mchorse.bbs_mod.ui.utils.icons.Icons;

public class UIIconComponent extends UIComponent<UIMappetIconElement>{
    public UIIconComponent(Icon icon, Runnable onClick) {
        super(new UIMappetIconElement(icon, (uiIcon -> {
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

    public UIIconComponent alpha(float alpha) {
        this.element.alpha(alpha);
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

    public float getAlpha() {
        return this.element.alpha;
    }
}
