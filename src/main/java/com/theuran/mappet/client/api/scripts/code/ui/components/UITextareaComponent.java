package com.theuran.mappet.client.api.scripts.code.ui.components;

import mchorse.bbs_mod.ui.framework.elements.input.text.UITextarea;
import mchorse.bbs_mod.ui.framework.elements.input.text.highlighting.HighlightedTextLine;

import java.util.function.Consumer;

public class UITextareaComponent extends UIComponent<UITextarea<HighlightedTextLine>>{
    public UITextareaComponent(Consumer<String> consumer) {
        super(new UITextarea<>(consumer));
    }

    public UITextareaComponent background(boolean background) {
        this.element.background(background);
        return this;
    }

    public UITextareaComponent padding(int padding) {
        this.element.padding(padding);
        return this;
    }

    public UITextareaComponent lineHeight(int lineHeight) {
        this.element.lineHeight(lineHeight);
        return this;
    }

    public UITextareaComponent textColor(int textColor, boolean textShadow) {
        this.element.setColor(textColor, textShadow);
        return this;
    }

    public UITextareaComponent textColor(int textColor) {
        this.element.setColor(textColor, false);
        return this;
    }

    public UITextareaComponent text(String text) {
        this.element.setText(text);
        return this;
    }

    public String getText() {
        return this.element.getText();
    }

    public boolean isFocused() {
        return this.element.isFocused();
    }

    public UITextareaComponent wrap(boolean wrapping) {
        this.element.wrap(wrapping);
        return this;
    }

    public UITextareaComponent clear() {
        this.element.clear();
        return this;
    }
}
