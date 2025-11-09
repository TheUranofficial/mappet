package com.theuran.mappet.client.api.scripts.code.ui.elements;

import com.theuran.mappet.client.api.scripts.code.ui.elements.graphic.Graphic;
import mchorse.bbs_mod.BBSModClient;
import mchorse.bbs_mod.resources.Link;
import mchorse.bbs_mod.ui.framework.UIContext;
import mchorse.bbs_mod.ui.framework.elements.UIElement;

public class UIGraphics extends UIElement {
    public Graphic graphic;

    @Override
    public void render(UIContext context) {
        switch (graphic) {
            case Graphic.Box(int color) -> context.batcher.box(
                    this.getFlex().getX(),
                    this.getFlex().getY(),
                    this.getFlex().getX() + this.getFlex().getW(),
                    this.getFlex().getY() + this.getFlex().getH(),
                    color
            );
            case Graphic.GradientH(int color1, int color2) -> context.batcher.gradientHBox(
                    this.getFlex().getX(),
                    this.getFlex().getY(),
                    this.getFlex().getX() + this.getFlex().getW(),
                    this.getFlex().getY() + this.getFlex().getH(),
                    color1,
                    color2
            );
            case Graphic.GradientV(int color1, int color2) -> context.batcher.gradientVBox(
                    this.getFlex().getX(),
                    this.getFlex().getY(),
                    this.getFlex().getX() + this.getFlex().getW(),
                    this.getFlex().getY() + this.getFlex().getH(),
                    color1,
                    color2
            );
            case Graphic.FullTexturedBox(String path, int color) -> context.batcher.fullTexturedBox(
                    BBSModClient.getTextures().getTexture(Link.create(path)),
                    color,
                    this.getFlex().getX(),
                    this.getFlex().getY(),
                    this.getFlex().getW(),
                    this.getFlex().getH()
            );
            case Graphic.Outline(int color, int border) -> context.batcher.outline(
                    this.getFlex().getX(),
                    this.getFlex().getY(),
                    this.getFlex().getX() + this.getFlex().getW(),
                    this.getFlex().getY() + this.getFlex().getH(),
                    color, border
            );
        }
    }
}