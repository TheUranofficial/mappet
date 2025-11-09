package com.theuran.mappet.client.api.scripts.code.ui.components;

import com.theuran.mappet.api.scripts.code.ScriptVector;
import com.theuran.mappet.client.api.scripts.code.ui.elements.UIMappetLabelElement;
import mchorse.bbs_mod.l10n.keys.IKey;

public class UILabelComponent extends UIComponent<UIMappetLabelElement> {
    public UILabelComponent(String label, int color) {
        super(new UIMappetLabelElement(IKey.raw(label), color));
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

    public ScriptVector getLabelAnchor() {
        return new ScriptVector(this.element.anchorX, this.element.anchorY, 0);
    }

    public float getLabelAnchorX() {
        return this.element.anchorX;
    }

    public float getLabelAnchorY() {
        return this.element.anchorY;
    }

    public UILabelComponent labelAnchor(float x, float y) {
        this.element.labelAnchor(x, y);
        return this;
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

    public UILabelComponent alpha(float alpha) {
        this.element.alpha(alpha);
        return this;
    }

    public UILabelComponent backgroundAlpha(float alpha) {
        this.element.backgroundAlpha(alpha);
        return this;
    }

    public float getAlpha() {
        return this.element.alpha;
    }

    public float getBackgroundAlpha() {
        return this.element.backgroundAlpha;
    }

    public UILabelComponent textShadow(boolean textShadow) {
        this.element.textShadow = textShadow;
        return this;
    }

    public UILabelComponent background(int color) {
        this.element.background(color);
        return this;
    }
}
