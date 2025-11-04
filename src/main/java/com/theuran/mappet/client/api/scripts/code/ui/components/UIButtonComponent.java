package com.theuran.mappet.client.api.scripts.code.ui.components;

import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIButton;

public class UIButtonComponent extends UIComponent<UIButton> {
    public UIButtonComponent(String label, Runnable onClick) {
        super(new UIButton(IKey.raw(label), (b) -> {
            if (onClick != null) {
                onClick.run();
            }
        }));
    }

    public String getLabel() {
        return this.element.label.get();
    }

    public int getColor() {
        return this.element.customColor;
    }

    public int getTextColor() {
        return this.element.textColor;
    }

    public boolean isTextShadow() {
        return this.element.textShadow;
    }

    public boolean isBackground() {
        return this.element.background;
    }

    public UIButtonComponent label(String text) {
        this.element.label = IKey.raw(text);
        return this;
    }

    public UIButtonComponent color(int color) {
        this.element.color(color);
        return this;
    }

    public UIButtonComponent textColor(int color) {
        this.element.textColor(color, this.element.textShadow);
        return this;
    }

    public UIButtonComponent textShadow(boolean shadow) {
        this.element.textShadow = shadow;
        return this;
    }

    public UIButtonComponent background(boolean background) {
        this.element.background(background);
        return this;
    }
}
