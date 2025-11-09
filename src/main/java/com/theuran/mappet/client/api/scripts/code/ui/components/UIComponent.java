package com.theuran.mappet.client.api.scripts.code.ui.components;

import com.theuran.mappet.api.scripts.code.ScriptVector;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.elements.UIElement;
import mchorse.bbs_mod.utils.Direction;

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
        this.element.resize();
        return this;
    }

    public UIComponent<T> rx(float value) {
        this.element.x(value, 0);
        this.element.resize();
        return this;
    }

    public UIComponent<T> rx(float value, int offset) {
        this.element.x(value, offset);
        this.element.resize();
        return this;
    }

    public UIComponent<T> y(int offset) {
        this.element.y(0.0F, offset);
        this.element.resize();
        return this;
    }

    public UIComponent<T> ry(float value) {
        this.element.y(value, 0);
        this.element.resize();
        return this;
    }

    public UIComponent<T> ry(float value, int offset) {
        this.element.y(value, offset);
        this.element.resize();
        return this;
    }

    public UIComponent<T> rxy(float valueX, float valueY) {
        this.element.x(valueX, 0);
        this.element.y(valueY, 0);
        this.element.resize();
        return this;
    }

    public UIComponent<T> w(int offset) {
        this.element.w(0.0F, offset);
        this.element.resize();
        return this;
    }

    public UIComponent<T> rw(float value) {
        this.element.w(value, 0);
        this.element.resize();
        return this;
    }

    public UIComponent<T> rw(float value, int offset) {
        this.element.w(value, offset);
        this.element.resize();
        return this;
    }

    public UIComponent<T> h(int offset) {
        this.element.h(0.0F, offset);
        this.element.resize();
        return this;
    }

    public UIComponent<T> rh(float value) {
        this.element.h(value, 0);
        this.element.resize();
        return this;
    }

    public UIComponent<T> rh(float value, int offset) {
        this.element.h(value, offset);
        this.element.resize();
        return this;
    }

    public UIComponent<T> rwh(float valueW, float valueH) {
        this.element.w(valueW, 0);
        this.element.h(valueH, 0);
        this.element.resize();
        return this;
    }

    public UIComponent<T> wh(int width, int height) {
        this.element.w(width);
        this.element.h(height);
        this.element.resize();
        return this;
    }

    public UIComponent<T> xy(int x, int y) {
        this.element.x(x);
        this.element.y(y);
        this.element.resize();
        return this;
    }

    public UIComponent<T> anchor(float x) {
        return this.anchor(x, x);
    }

    public UIComponent<T> anchor(float x, float y) {
        this.element.anchor(x, y);
        return this;
    }

    public UIComponent<T> anchorX(float x) {
        this.element.anchorX(x);
        return this;
    }

    public UIComponent<T> anchorY(float y) {
        this.element.anchorY(y);
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

    public ScriptVector getAnchor() {
        return new ScriptVector(this.element.getFlex().x.anchor, this.element.getFlex().y.anchor, 0);
    }

    public float getAnchorX() {
        return this.element.getFlex().x.anchor;
    }

    public float getAnchorY() {
        return this.element.getFlex().y.anchor;
    }

    public UIComponent<T> enabled(boolean enabled) {
        this.element.setEnabled(enabled);
        return this;
    }

    public boolean isEnabled() {
        return this.element.isEnabled();
    }

    public UIComponent<T> visible(boolean visible) {
        this.element.setVisible(visible);
        return this;
    }

    public boolean isVisible() {
        return this.element.isVisible();
    }

    public UIComponent<T> culling(boolean culling) {
        this.element.culled = culling;
        return this;
    }

    public UIComponent<T> tooltip(String label, String direction) {
        this.element.tooltip(IKey.raw(label), Direction.valueOf(direction));
        return this;
    }

    public UIComponent<T> tooltip(String label) {
        this.element.tooltip(IKey.raw(label));
        return this;
    }

    public UIComponent<T> tooltip(String label, int width, String direction) {
        this.element.tooltip(IKey.raw(label), width, Direction.valueOf(direction));
        return this;
    }

    public UIComponent<T> noTooltip() {
        this.element.removeTooltip();
        return this;
    }

    public UIComponent<T> margin(int all) {
        this.element.margin(all);
        return this;
    }

    public UIComponent<T> margin(int horizontal, int vertical) {
        this.element.margin(horizontal, vertical);
        return this;
    }

    public UIComponent<T> margin(int left, int top, int right, int botton) {
        this.element.margin(left, top, right, botton);
        return this;
    }

    public UIComponent<T> marginBottom(int botton) {
        this.element.marginBottom(botton);
        return this;
    }

    public UIComponent<T> marginLeft(int left) {
        this.element.marginLeft(left);
        return this;
    }

    public UIComponent<T> marginRight(int right) {
        this.element.marginRight(right);
        return this;
    }

    public UIComponent<T> marginTop(int top) {
        this.element.marginTop(top);
        return this;
    }

    public Object getValue(String key) {
        return this.element.getCustomValue(key);
    }

    public void setValue(String key, Object value) {
        this.element.setCustomValue(key, value);
    }
}
