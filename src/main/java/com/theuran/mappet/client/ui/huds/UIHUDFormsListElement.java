package com.theuran.mappet.client.ui.huds;

import com.theuran.mappet.api.huds.HUDForm;
import mchorse.bbs_mod.ui.framework.UIContext;
import mchorse.bbs_mod.ui.framework.elements.input.list.UIList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.List;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class UIHUDFormsListElement extends UIList<HUDForm> {
    public UIHUDFormsListElement(Consumer<List<HUDForm>> callback) {
        super(callback);
    }

    @Override
    protected String elementToString(UIContext context, int i, HUDForm element) {
        return element.form.get() == null ? "-" : element.form.get().getDisplayName();
    }
}
