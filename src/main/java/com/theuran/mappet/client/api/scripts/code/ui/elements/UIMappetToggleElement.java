package com.theuran.mappet.client.api.scripts.code.ui.elements;

import mchorse.bbs_mod.BBSSettings;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.UIContext;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIToggle;
import mchorse.bbs_mod.ui.framework.elements.utils.FontRenderer;
import mchorse.bbs_mod.ui.utils.icons.Icons;
import mchorse.bbs_mod.utils.colors.Colors;

import java.util.function.Consumer;

public class UIMappetToggleElement extends UIToggle {
    public float alpha;
    public float textAlpha;

    public UIMappetToggleElement(IKey label, Consumer<UIToggle> callback) {
        super(label, callback);
    }

    public UIMappetToggleElement(IKey label, boolean value, Consumer<UIToggle> callback) {
        super(label, value, callback);
    }

    public UIMappetToggleElement alpha(float alpha) {
        this.alpha = alpha;
        return this;
    }

    public UIMappetToggleElement textAlpha(float alpha) {
        this.textAlpha = alpha;
        return this;
    }

    @Override
    protected void renderSkin(UIContext context) {
        FontRenderer font = context.batcher.getFont();
        String label = font.limitToWidth(this.label.get(), this.area.w - 18);
        context.batcher.text(label, (float)this.area.x, (float)this.area.my(font.getHeight()), Colors.setA(this.color, this.textAlpha), this.textShadow);
        int w = 16;
        int h = 10;
        int x = this.area.ex() - w - 2;
        int y = this.area.my();
        int color = Colors.setA(BBSSettings.primaryColor.get(), this.alpha);
        if (this.hover) {
            color = Colors.mulRGB(color, 0.85F);
        }

        context.batcher.box((float)x, (float)(y - h / 2), (float)(x + w), (float)(y - h / 2 + h), -16777216);
        context.batcher.box((float)(x + 1), (float)(y - h / 2 + 1), (float)(x + w - 1), (float)(y - h / 2 + h - 1), -16777216 | (this.getValue() ? color : (this.hover ? 3815994 : 4473924)));
        if (this.getValue()) {
            context.batcher.gradientHBox((float)(x + 1), (float)(y - h / 2 + 1), (float)(x + w / 2), (float)(y - h / 2 + h - 1), Colors.setA(-1, 0.33F), Colors.setA(-1, 0.0F));
        } else {
            context.batcher.gradientHBox((float)(x + w / 2), (float)(y - h / 2 + 1), (float)(x + w - 1), (float)(y - h / 2 + h - 1), 0, -2013265920);
        }

        if (!this.isEnabled()) {
            context.batcher.box((float)x, (float)(y - h / 2), (float)(x + w), (float)(y - h / 2 + h), -2013265920);
        }

        x += this.getValue() ? w - 2 : 2;
        context.batcher.box((float)(x - 4), (float)(y - 8), (float)(x + 4), (float)(y + 8), -16777216);
        context.batcher.box((float)(x - 3), (float)(y - 7), (float)(x + 3), (float)(y + 7), -1);
        context.batcher.box((float)(x - 2), (float)(y - 6), (float)(x + 3), (float)(y + 7), -7829368);
        context.batcher.box((float)(x - 2), (float)(y - 6), (float)(x + 2), (float)(y + 6), -5592406);
        if (!this.isEnabled()) {
            context.batcher.box((float)(x - 4), (float)(y - 8), (float)(x + 4), (float)(y + 8), -2013265920);
            context.batcher.outlinedIcon(Icons.LOCKED, (float)(this.area.ex() - w / 2 - 2), (float)y, 0.5F, 0.5F);
        }
    }
}
