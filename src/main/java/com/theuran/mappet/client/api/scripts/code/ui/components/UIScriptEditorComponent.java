package com.theuran.mappet.client.api.scripts.code.ui.components;

import com.theuran.mappet.client.api.scripts.code.ui.elements.UIMappetScriptEditorElement;

import java.util.function.Consumer;

public class UIScriptEditorComponent extends UIComponent<UIMappetScriptEditorElement>{
    public UIScriptEditorComponent(Consumer<String> consumer) {
        super(new UIMappetScriptEditorElement(consumer));
    }

    public UIScriptEditorComponent background(boolean background) {
        this.element.background(background);
        return this;
    }

    public UIScriptEditorComponent padding(int padding) {
        this.element.padding(padding);
        return this;
    }

    public UIScriptEditorComponent lingHeight(int lineHeight) {
        this.element.lineHeight(lineHeight);
        return this;
    }

    public UIScriptEditorComponent textColor(int textColor, boolean textShadow) {
        this.element.setColor(textColor, textShadow);
        return this;
    }

    public UIScriptEditorComponent textColor(int textColor) {
        this.element.setColor(textColor, false);
        return this;
    }

    public float getTextAlpha() {
        return this.element.textAlpha;
    }

    public UIScriptEditorComponent textAlpha(float alpha) {
        this.element.textAlpha = alpha;
        return this;
    }

    public float getBackgroundAlpha() {
        return this.element.backgroundAlpha;
    }

    public UIScriptEditorComponent backgroundAlpha(float alpha) {
        this.element.backgroundAlpha = alpha;
        return this;
    }

    public UIScriptEditorComponent text(String text) {
        this.element.setText(text);
        return this;
    }

    public String getText() {
        return this.element.getText();
    }

    public boolean isFocused() {
        return this.element.isFocused();
    }

    public UIScriptEditorComponent wrap(boolean wrapping) {
        this.element.wrap(wrapping);
        return this;
    }

    public UIScriptEditorComponent clear() {
        this.element.clear();
        return this;
    }
}
