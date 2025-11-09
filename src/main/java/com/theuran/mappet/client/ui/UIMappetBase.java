package com.theuran.mappet.client.ui;

import com.theuran.mappet.client.api.scripts.code.ui.MappetUIBuilder;
import com.theuran.mappet.client.api.scripts.code.ui.components.UIComponent;
import mchorse.bbs_mod.ui.framework.UIBaseMenu;
import mchorse.bbs_mod.ui.framework.UIRenderingContext;

public class UIMappetBase extends UIBaseMenu {
    private final MappetUIBuilder builder;

    public UIMappetBase(MappetUIBuilder uiBuilder) {
        super();

        this.builder = uiBuilder;

        for (UIComponent<?> component : this.builder.components) {
            this.getRoot().add(component.getMappetElement().relative(this.getRoot()));
        }
    }

    @Override
    public void renderMenu(UIRenderingContext context, int mouseX, int mouseY) {
        super.renderMenu(context, mouseX, mouseY);

        this.builder.animationManager.playAnimations();
    }
}