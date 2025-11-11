package com.theuran.mappet.client.ui.keybinds;

import com.theuran.mappet.api.keybinds.Keybind;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.elements.input.list.UIList;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIEditorOverlayPanel;

public class UIKeybindOverlayPanel extends UIEditorOverlayPanel<Keybind> {
    public UIKeybindOverlayPanel(IKey title) {
        super(title);
    }

    @Override
    protected UIList<Keybind> createList() {
        return null;
    }

    @Override
    protected void fillData(Keybind item) {

    }
}
