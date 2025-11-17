package com.theuran.mappet.client.api.scripts.code.ui.components;

import mchorse.bbs_mod.ui.framework.elements.utils.UIText;

public class UITextComponent extends UIComponent<UIText> {
    public UITextComponent(String text) {
        super(new UIText(text));
    }

    public UITextComponent text(String text) {
        this.element.text(text);
        return this;
    }

    public UITextComponent lineHeight(int lineHeight) {
        this.element.lineHeight(lineHeight);
        return this;
    }

    public UITextComponent color(int color, boolean shadow) {
        this.element.color(color, shadow);
        return this;
    }

    public UITextComponent hoverColor(int color) {
        this.element.hoverColor(color);
        return this;
    }

    public UITextComponent padding(int padding) {
        this.element.padding(padding);
        return this;
    }

    public UITextComponent padding(int horizontal, int vertical) {
        this.element.padding(horizontal, vertical);
        return this;
    }

    public UITextComponent textAnchorX(float anchor) {
        this.element.textAnchorX(anchor);
        return this;
    }

    public String getText() {
        return this.element.getText().get();
    }
}
