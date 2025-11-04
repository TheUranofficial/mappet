package com.theuran.mappet.client.api.scripts.code.ui;

import com.theuran.mappet.client.api.scripts.code.ui.components.*;
import mchorse.bbs_mod.ui.framework.elements.UIElement;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MappetUIBuilder {
    public List<UIComponent<?>> elements = new ArrayList<>();

    public MappetUIBuilder() {

    }

    public UIButtonComponent button() {
        return this.button("", null);
    }

    public UIButtonComponent button(String label) {
        return this.button(label, null);
    }

    public UIButtonComponent button(String label, Runnable onClick) {
        UIButtonComponent button = new UIButtonComponent(label, onClick);
        this.elements.add(button);
        return button;
    }

    public UILabelComponent label(String text, int color) {
        UILabelComponent label = new UILabelComponent(text, color);
        this.elements.add(label);
        return label;
    }

    public UILabelComponent label(String text) {
        UILabelComponent label = new UILabelComponent(text, -1);
        this.elements.add(label);
        return label;
    }

    public UIToggleComponent toggle(String label, boolean value, Runnable runnable) {
        UIToggleComponent toggle = new UIToggleComponent(label, value, runnable);
        this.elements.add(toggle);
        return toggle;
    }

    public UIToggleComponent toggle(String label, boolean value) {
        UIToggleComponent toggle = new UIToggleComponent(label, value, null);
        this.elements.add(toggle);
        return toggle;
    }

    public UIToggleComponent toggle(String label) {
        UIToggleComponent toggle = new UIToggleComponent(label, false, null);
        this.elements.add(toggle);
        return toggle;
    }

    public UITextboxComponent textbox() {
        return this.textbox(10000, null);
    }

    public UITextboxComponent textbox(int maxLength) {
        return this.textbox(maxLength, null);
    }

    public UITextboxComponent textbox(int maxLength, Consumer<String> consumer) {
        return this.textbox("", maxLength, consumer);
    }

    public UITextboxComponent textbox(String text) {
        return this.textbox(text, 10000, null);
    }

    public UITextboxComponent textbox(String text, int maxLength) {
        return this.textbox(text, maxLength, null);
    }

    public UITextboxComponent textbox(String text, Consumer<String> consumer) {
        return this.textbox(text, 10000, consumer);
    }

    public UITextboxComponent textbox(String text, int maxLength, Consumer<String> consumer) {
        UITextboxComponent textbox = new UITextboxComponent(maxLength, consumer);
        textbox.text(text);
        this.elements.add(textbox);
        return textbox;
    }
}