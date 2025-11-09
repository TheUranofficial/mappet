package com.theuran.mappet.client.api.scripts.code.ui.components;

import com.theuran.mappet.client.api.scripts.code.ui.elements.UIGraphics;
import com.theuran.mappet.client.api.scripts.code.ui.elements.graphic.Graphic;
import mchorse.bbs_mod.utils.colors.Colors;

public class UIGraphicsComponent extends UIComponent<UIGraphics> {
    public UIGraphicsComponent() {
        super(new UIGraphics());
    }

    public UIGraphicsComponent box(int color, float alpha) {
        this.element.graphic = new Graphic.Box(Colors.setA(color, alpha));
        return this;
    }

    public UIGraphicsComponent box(int color) {
        return this.box(color, 1f);
    }

    public UIGraphicsComponent gradientH(int color1, int color2) {
        return this.gradientH(color1, color2, 1f, 1f);
    }

    public UIGraphicsComponent gradientH(int color1, int color2, float alpha) {
        return this.gradientH(color1, color2, alpha, alpha);
    }

    public UIGraphicsComponent gradientH(int color1, int color2, float alpha1, float alpha2) {
        this.element.graphic = new Graphic.GradientH(Colors.setA(color1, alpha1), Colors.setA(color2, alpha2));
        return this;
    }

    public UIGraphicsComponent gradientV(int color1, int color2) {
        return this.gradientV(color1, color2, 1f, 1f);
    }

    public UIGraphicsComponent gradientV(int color1, int color2, float alpha) {
        return this.gradientV(color1, color2, alpha, alpha);
    }

    public UIGraphicsComponent gradientV(int color1, int color2, float alpha1, float alpha2) {
        this.element.graphic = new Graphic.GradientV(Colors.setA(color1, alpha1), Colors.setA(color2, alpha2));
        return this;
    }

    public UIGraphicsComponent texture(String path, int color) {
        return this.texture(path, color, 1f);
    }

    public UIGraphicsComponent texture(String path) {
        return this.texture(path, -1);
    }

    public UIGraphicsComponent texture(String path, int color, float alpha) {
        this.element.graphic = new Graphic.FullTexturedBox(path, Colors.setA(color, alpha));
        return this;
    }

    public UIGraphicsComponent outline(int color, int border) {
        return this.outline(color, border, 1f);
    }

    public UIGraphicsComponent outline(int color) {
        return this.outline(color, 1);
    }

    public UIGraphicsComponent outline(int color, int border, float alpha) {
        this.element.graphic = new Graphic.Outline(Colors.setA(color, alpha), border);
        return this;
    }
}
