package com.theuran.mappet.client.ui.states;

import mchorse.bbs_mod.ui.framework.UIContext;
import mchorse.bbs_mod.ui.framework.elements.context.UIContextMenu;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class UIAddStateContextMenu extends UIContextMenu {
    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void setMouse(UIContext context) {

    }
}
