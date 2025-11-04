package com.theuran.mappet.client.api.scripts.code.ui.components;

import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.elements.utils.UILabel;

public class UILabelComponent extends UIComponent<UILabel> {
    public UILabelComponent(String label, int color) {
        super(new UILabel(IKey.raw(label), color));
    }

    public String getLabel() {
        return this.element.label.get();
    }

    public int getColor() {
        return this.element.color;
    }

    public boolean isTextShadow() {
        return this.element.textShadow;
    }

    public float getAnchorX() {
        return this.element.anchorX;
    }

    public float getAnchorY() {
        return this.element.anchorY;
    }

    public int getBackgroundColor() {
        return this.element.background;
    }

    public UILabelComponent label(String label) {
        this.element.label = IKey.raw(label);
        return this;
    }

    public UILabelComponent color(int color) {
        this.element.color(color);
        return this;
    }

    public UILabelComponent textShadow(boolean textShadow) {
        this.element.textShadow = textShadow;
        return this;
    }

    public UILabelComponent anchorX(float value) {
        this.element.anchorX(value);
        return this;
    }

    public UILabelComponent anchorY(float value) {
        this.element.anchorY(value);
        return this;
    }

    public UILabelComponent background(int color) {
        this.element.background(color);
        return this;
    }
}
