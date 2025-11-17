package com.theuran.mappet.client.ui.ai;

import com.theuran.mappet.client.ui.elements.UIModelElement;
import mchorse.bbs_mod.BBSModClient;
import mchorse.bbs_mod.forms.forms.Form;
import mchorse.bbs_mod.l10n.L10n;
import mchorse.bbs_mod.morphing.IMorphProvider;
import mchorse.bbs_mod.morphing.Morph;
import mchorse.bbs_mod.ui.forms.UIFormPalette;
import mchorse.bbs_mod.ui.forms.editors.forms.UIForm;
import mchorse.bbs_mod.ui.forms.editors.forms.UIMobForm;
import mchorse.bbs_mod.ui.forms.editors.utils.UIFormRenderer;
import mchorse.bbs_mod.ui.framework.UIContext;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;
import mchorse.bbs_mod.ui.framework.elements.utils.UIModelRenderer;
import mchorse.bbs_mod.ui.framework.elements.utils.UIText;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;

public class UIMascotPanel extends UIOverlayPanel {
    public UIMascotPanel() {
        super(L10n.lang(""));

        UIFormPalette morph = new UIFormPalette(this::setForm);


        morph.full(this);

        morph.noBackground();
        morph.canModify();

        morph.list.setupForms(BBSModClient.getFormCategories());
//        this.palette.list.setupForms();

        this.add(morph);
    }

    private void setForm(Form form){

    }

    @Override
    public void render(UIContext context) {
        super.render(context);

    }
}
