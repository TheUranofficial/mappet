package com.theuran.mappet.api.huds;

import mchorse.bbs_mod.settings.values.core.ValueGroup;
import mchorse.bbs_mod.settings.values.numeric.ValueFloat;

public class HUDScene extends ValueGroup {
    public HUDForms forms = new HUDForms("forms");
    public ValueFloat fov = new ValueFloat("fov", 70f);

    public HUDScene() {
        super("");

        this.add(this.forms);
        this.add(this.fov);
    }

    public boolean update(boolean allowExpiring) {
        this.forms.getAll().removeIf(form -> ((HUDForm) form).update(allowExpiring));

        return allowExpiring && this.forms.getList().isEmpty();
    }
}
