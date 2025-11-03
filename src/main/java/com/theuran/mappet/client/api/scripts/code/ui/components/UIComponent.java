package com.theuran.mappet.client.api.scripts.code.ui.components;

import mchorse.bbs_mod.ui.framework.elements.UIElement;

public class UIComponent<T extends UIElement> {
    protected T element;

    public UIComponent(T element) {
        this.element = element;
    }

    public T getMappetElement() {
        return this.element;
    }

    public UIComponent<T> id(String id) {
        this.element.setUndoId(id);
        return this;
    }

    public UIComponent<T> x(int offset) {
        this.element.x(0f, offset);
        return this;
    }

    public UIComponent<T> rx(float value) {
        this.element.x(value, 0);
        return this;
    }

    public UIComponent<T> rx(float value, int offset) {
        this.element.x(value, offset);
        return this;
    }

    public UIComponent<T> y(int offset) {
        this.element.y(0.0F, offset);
        return this;
    }

    public UIComponent<T> ry(float value) {
        this.element.y(value, 0);
        return this;
    }

    public UIComponent<T> ry(float value, int offset) {
        this.element.y(value, offset);
        return this;
    }

    public UIComponent<T> w(int offset) {
        this.element.w(0.0F, offset);
        return this;
    }

    public UIComponent<T> rw(float value) {
        this.element.w(value, 0);
        return this;
    }

    public UIComponent<T> rw(float value, int offset) {
        this.element.w(value, offset);
        return this;
    }

    public UIComponent<T> h(int offset) {
        this.element.h(0.0F, offset);
        return this;
    }

    public UIComponent<T> rh(float value) {
        this.element.h(value, 0);
        return this;
    }

    public UIComponent<T> rh(float value, int offset) {
        this.element.h(value, offset);
        return this;
    }
}
