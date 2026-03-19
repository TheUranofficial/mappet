package com.theuran.mappet.api.huds;

import mchorse.bbs_mod.settings.values.core.ValueGroup;

public class HUDScene extends ValueGroup {
    public HUDForms forms = new HUDForms("forms");

    public HUDScene() {
        super("");

        this.add(this.forms);
    }

    public boolean update(boolean allowExpiring) {
        this.forms.getAll().removeIf(form -> ((HUDForm) form).update(allowExpiring));

        return allowExpiring && this.forms.getList().isEmpty();
    }
}
