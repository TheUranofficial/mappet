package com.theuran.mappet.client.ui.scripts.utils;

import com.theuran.mappet.client.ui.UIMappetKeys;
import mchorse.bbs_mod.data.DataToString;
import mchorse.bbs_mod.forms.FormUtils;
import mchorse.bbs_mod.forms.forms.Form;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.forms.UIFormPalette;
import mchorse.bbs_mod.ui.framework.elements.UIElement;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIButton;
import mchorse.bbs_mod.ui.framework.elements.input.text.UITextEditor;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;
import mchorse.bbs_mod.ui.utils.UI;

public class UIFormOverlayPanel extends UIOverlayPanel {
    public UIButton pick;
    public UIButton insert;

    private UITextEditor editor;
    private Form form;

    public UIFormOverlayPanel(IKey title, UITextEditor editor, Form form) {
        super(title);

        this.editor = editor;
        this.form = form;

        this.pick = new UIButton(UIMappetKeys.SCRIPTS_OVERLAY_PICK_FORM, this::pickForm);
        this.insert = new UIButton(UIMappetKeys.SCRIPTS_OVERLAY_INSERT, this::insert);

        UIElement row = UI.row(this.pick, this.insert);

        row.relative(this.content).y(1f, -30).w(1f).h(20);
        this.content.add(row);
    }

    private void insert(UIButton button) {
        this.close();

        if (this.form != null) {
            String data = this.form.toData().toString();

            this.editor.pasteText(DataToString.escapeQuoted(data));
        }
    }

    private void setForm(Form form) {
        this.form = FormUtils.fromData(form.toData());
    }

    private void pickForm(UIButton button) {
        UIFormPalette.open(this.getParent(), false, this.form, this::setForm);
    }
}
