package com.theuran.mappet.client.api.scripts.code.ui.elements;

import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.UIContext;
import mchorse.bbs_mod.ui.framework.elements.utils.FontRenderer;
import mchorse.bbs_mod.ui.framework.elements.utils.UILabel;
import mchorse.bbs_mod.utils.colors.Colors;

public class UIMappetLabelElement extends UILabel {
    public float alpha = 1f;
    public float backgroundAlpha = 0f;

    public UIMappetLabelElement(IKey label) {
        super(label);
    }

    public UIMappetLabelElement(IKey label, int color) {
        super(label, color);
    }

    public UIMappetLabelElement alpha(float alpha) {
        this.alpha = alpha;
        return this;
    }

    public UIMappetLabelElement backgroundAlpha(float alpha) {
        this.backgroundAlpha = alpha;
        return this;
    }

    @Override
    public void render(UIContext context) {
        FontRenderer font = context.batcher.getFont();
        String label = font.limitToWidth(this.label.get(), this.area.w - 4);
        int x = this.area.x(this.anchorX, font.getWidth(label));
        int y = this.area.y(this.anchorY, font.getHeight());
        int background = Colors.setA(this.background, this.backgroundAlpha);
        context.batcher.textCard(label, (float)x, (float)y, Colors.setA(this.color, (float) Math.max(0.1, this.alpha)), background, 3.0F, this.textShadow);
    }
}
