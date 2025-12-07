package com.theuran.mappet.api.huds;

import mchorse.bbs_mod.settings.values.core.ValueList;

public class HUDForms extends ValueList<HUDForm> {
    public HUDForms(String id) {
        super(id);
    }

    @Override
    protected HUDForm create(String id) {
        HUDForm form = new HUDForm();

        form.setId(id);

        return form;
    }
}
