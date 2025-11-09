package com.theuran.mappet.client.api.scripts.code.ui.components;

import com.theuran.mappet.client.api.scripts.code.ui.elements.UIMappetToggleElement;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIToggle;

public class UIToggleComponent extends UIComponent<UIMappetToggleElement> {
    public UIToggleComponent(String label, boolean value, Runnable runnable) {
        super(new UIMappetToggleElement(IKey.raw(label), value, (toggle) -> {
            if (runnable != null) {
                runnable.run();
            }
        }));
    }

    public String getLabel() {
        return this.element.label.get();
    }

    public int getColor() {
        return this.element.color;
    }

    public float getAlpha() {
        return this.element.alpha;
    }

    public float getTextAlpha() {
        return this.element.textAlpha;
    }

    public boolean isTextShadow() {
        return this.element.textShadow;
    }

    public boolean getValue() {
        return this.element.getValue();
    }

    public UIToggleComponent label(String label) {
        this.element.label(IKey.raw(label));
        return this;
    }

    public UIToggleComponent color(int color) {
        this.element.color(color);
        return this;
    }

    public UIToggleComponent alpha(float alpha) {
        this.element.alpha = alpha;
        return this;
    }

    public UIToggleComponent textAlpha(int alpha) {
        this.element.textAlpha = alpha;
        return this;
    }

    public UIToggleComponent textShadow(boolean shadow) {
        this.element.textShadow = shadow;
        return this;
    }

    public UIToggleComponent value(boolean value) {
        this.element.setValue(value);
        return this;
    }
}
