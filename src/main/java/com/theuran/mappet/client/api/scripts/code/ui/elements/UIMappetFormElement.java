package com.theuran.mappet.client.api.scripts.code.ui.elements;

import mchorse.bbs_mod.forms.FormUtilsClient;
import mchorse.bbs_mod.forms.forms.Form;
import mchorse.bbs_mod.ui.framework.UIContext;
import mchorse.bbs_mod.ui.framework.elements.UIElement;

public class UIMappetFormElement extends UIElement {
    public Form form;

    public UIMappetFormElement(Form form) {
        this.form = form;
    }

    public UIMappetFormElement form(Form form) {
        this.form = form;
        return this;
    }

    @Override
    public void render(UIContext context) {
        super.render(context);

        if (this.form != null) {
            FormUtilsClient.renderUI(this.form, context,
                    this.getFlex().getX(),
                    this.getFlex().getY(),
                    this.getFlex().getX() + this.getFlex().getW(),
                    this.getFlex().getY() + this.getFlex().getH()
            );
        }
    }
}
