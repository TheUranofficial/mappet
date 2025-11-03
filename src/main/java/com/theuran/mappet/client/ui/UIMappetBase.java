package com.theuran.mappet.client.ui;

import com.theuran.mappet.client.api.scripts.code.ui.MappetUIBuilder;
import com.theuran.mappet.client.api.scripts.code.ui.components.UIComponent;
import mchorse.bbs_mod.ui.framework.UIBaseMenu;

public class UIMappetBase extends UIBaseMenu {
    public UIMappetBase(MappetUIBuilder uiBuilder) {
        super();

        for (UIComponent component : uiBuilder.elements) {
            this.getRoot().add(component.getMappetElement().relative(this.getRoot()));
        }
    }
}