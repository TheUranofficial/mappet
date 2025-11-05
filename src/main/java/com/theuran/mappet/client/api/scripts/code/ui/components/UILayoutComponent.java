package com.theuran.mappet.client.api.scripts.code.ui.components;

import com.theuran.mappet.client.api.scripts.code.ui.MappetUIBuilder;
import mchorse.bbs_mod.ui.framework.elements.UIElement;

import java.util.function.BiConsumer;

public class UILayoutComponent extends UIComponent<UIElement> {
    public UILayoutComponent(BiConsumer<UILayoutComponent, MappetUIBuilder> consumer) {
        super(new UIElement());

        MappetUIBuilder uiBuilder = new MappetUIBuilder();

        consumer.accept(this, uiBuilder);

        for (UIComponent<?> component : uiBuilder.components) {
            this.element.add(component.getMappetElement().relative(this.element));
        }
    }
}
