package com.theuran.mappet.client.api.scripts.code.ui;

import com.theuran.mappet.client.api.scripts.code.ui.components.UIButtonComponent;
import com.theuran.mappet.client.api.scripts.code.ui.components.UIComponent;
import com.theuran.mappet.client.api.scripts.code.ui.components.UILabelComponent;
import com.theuran.mappet.client.api.scripts.code.ui.components.UIToggleComponent;
import mchorse.bbs_mod.ui.framework.elements.UIElement;

import java.util.ArrayList;
import java.util.List;

public class MappetUIBuilder {
    public List<UIComponent> elements = new ArrayList<>();

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
}