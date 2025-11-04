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

    public UIComponent<T> rxy(float valueX, float valueY) {
        this.element.x(valueX, 0);
        this.element.y(valueY, 0);
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

    public UIComponent<T> rwh(float valueW, float valueH) {
        this.element.w(valueW, 0);
        this.element.h(valueH, 0);
        return this;
    }

    public UIComponent<T> wh(int width, int height) {
        this.element.w(width);
        this.element.h(height);
        return this;
    }

    public UIComponent<T> xy(int x, int y) {
        this.element.x(x);
        this.element.y(y);
        return this;
    }

    public int getX() {
        return this.element.getFlex().x.offset;
    }

    public int getY() {
        return this.element.getFlex().y.offset;
    }

    public float getRx() {
        return this.element.getFlex().x.value;
    }

    public float getRy() {
        return this.element.getFlex().y.value;
    }

    public int getWidth() {
        return this.element.getFlex().w.offset;
    }

    public int getHeight() {
        return this.element.getFlex().h.offset;
    }

    public float getRw() {
        return this.element.getFlex().w.value;
    }

    public float getRh() {
        return this.element.getFlex().h.value;
    }

    public UIComponent<T> enabled(boolean enabled) {
        this.element.setEnabled(enabled);
        return this;
    }

    public UIComponent<T> visible(boolean visible) {
        this.element.setVisible(visible);
        return this;
    }
}
