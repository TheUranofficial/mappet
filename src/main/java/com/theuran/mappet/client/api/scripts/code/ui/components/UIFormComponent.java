package com.theuran.mappet.client.api.scripts.code.ui.components;

import com.theuran.mappet.api.scripts.code.bbs.BBSForm;
import com.theuran.mappet.client.api.scripts.code.ui.elements.UIMappetFormElement;

public class UIFormComponent extends UIComponent<UIMappetFormElement> {
    public UIFormComponent() {
        super(new UIMappetFormElement(null));
    }

    public UIFormComponent form(BBSForm form) {
        this.element.form = form.getForm();
        return this;
    }
}
