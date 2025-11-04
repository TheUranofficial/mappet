package com.theuran.mappet.client.api.scripts.code.ui.components;

import mchorse.bbs_mod.ui.framework.elements.input.text.UITextbox;

import java.util.function.Consumer;

public class UITextboxComponent extends UIComponent<UITextbox>{
    public UITextboxComponent(int maxLength, Consumer<String> consumer) {
        super(new UITextbox(maxLength, consumer));
    }

    public UITextboxComponent text(String text) {
        this.element.setText(text);
        return this;
    }

    public UITextboxComponent color(int color) {
        this.element.setColor(color);
        return this;
    }

    public UITextboxComponent border(boolean border) {
        this.element.textbox.setBorder(border);
        return this;
    }
}
