package com.theuran.mappet.client.api.scripts.code.ui.elements;

import mchorse.bbs_mod.BBSSettings;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.UIContext;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIButton;
import mchorse.bbs_mod.ui.framework.elements.utils.FontRenderer;
import mchorse.bbs_mod.utils.colors.Colors;

import java.util.function.Consumer;

public class UIMappetButtonElement extends UIButton {
    public float alpha = 1f;
    public float textAlpha = 1f;

    public UIMappetButtonElement(IKey label, Consumer<UIButton> callback) {
        super(label, callback);
    }

    public UIMappetButtonElement alpha(float alpha) {
        this.alpha = alpha;
        return this;
    }

    public UIMappetButtonElement textAlpha(float textAlpha) {
        this.textAlpha = textAlpha;
        return this;
    }

    @Override
    public void renderSkin(UIContext context) {
        int color = Colors.setA(this.custom ? this.customColor : BBSSettings.primaryColor.get(), this.alpha);
        if (this.hover) {
            color = Colors.mulRGB(color, 0.85F);
        }

        if (this.background) {
            this.area.render(context.batcher, color);
        }

        FontRenderer font = context.batcher.getFont();
        String label = font.limitToWidth(this.label.get(), this.area.w - 4);
        int x = this.area.mx(font.getWidth(label));
        int y = this.area.my(font.getHeight());
        context.batcher.text(label, (float)x, (float)y, Colors.setA(Colors.mulRGB(this.textColor, this.hover ? 0.9F : 1.0F), (float) Math.max(0.1, this.textAlpha)), this.textShadow);
        this.renderLockedArea(context);
    }
}
