package com.theuran.mappet.client.api.scripts.code.ui.components;

import com.theuran.mappet.client.api.scripts.code.ui.MappetUIBuilder;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlay;

import java.util.function.Consumer;

public class UIOverlayComponent extends UIComponent<UIOverlay> {
    public UIOverlayComponent(Consumer<MappetUIBuilder> consumer) {
        super(new UIOverlay());

        MappetUIBuilder uiBuilder = new MappetUIBuilder();

        consumer.accept(uiBuilder);

        for (UIComponent<?> component : uiBuilder.components) {
            this.element.add(component.getMappetElement().relative(this.element));
        }
    }

    public UIOverlayComponent background(int color) {
        this.element.background(color);
        return this;
    }

    public UIOverlayComponent noBackground() {
        this.element.noBackground();
        return this;
    }
}
