package com.theuran.mappet.client.api.scripts.code.ui.elements;

import mchorse.bbs_mod.ui.framework.UIContext;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIIcon;
import mchorse.bbs_mod.ui.utils.icons.Icon;
import mchorse.bbs_mod.utils.colors.Colors;

import java.util.function.Consumer;

public class UIMappetIconElement extends UIIcon {
    public float alpha = 1;

    public UIMappetIconElement(Icon icon, Consumer<UIIcon> callback) {
        super(icon, callback);
    }

    public UIMappetIconElement alpha(float alpha) {
        this.alpha = alpha;
        return this;
    }

    @Override
    public void renderSkin(UIContext context) {
        Icon icon = this.getIcon();
        int color = Colors.setA(this.isEnabled() ? (this.hover ? this.hoverColor : this.iconColor) : this.disabledColor, this.alpha);
        context.batcher.icon(icon, color, (float)this.area.mx(), (float)this.area.my(), 0.5F, 0.5F);
    }
}
