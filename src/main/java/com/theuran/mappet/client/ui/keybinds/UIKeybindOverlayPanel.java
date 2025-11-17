package com.theuran.mappet.client.ui.keybinds;

import com.theuran.mappet.api.keybinds.Keybind;
import com.theuran.mappet.utils.InputUtils;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.elements.input.UIKeybind;
import mchorse.bbs_mod.ui.framework.elements.input.UITrackpad;
import mchorse.bbs_mod.ui.framework.elements.input.list.UIList;
import mchorse.bbs_mod.ui.framework.elements.input.text.UITextbox;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIEditorOverlayPanel;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlay;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;
import mchorse.bbs_mod.ui.framework.elements.utils.UILabel;
import mchorse.bbs_mod.ui.utils.UI;
import net.minecraft.client.util.InputUtil;

public class UIKeybindOverlayPanel extends UIOverlayPanel {
    Keybind keybind;

    UILabel idLabel;
    UITextbox id;
    UILabel categoryIdLabel;
    UITextbox categoryId;

    UIKeybind keybindElement;

    public UIKeybindOverlayPanel(Keybind keybind) {
        super(IKey.raw(keybind.getId()));

        this.keybind = keybind;

        this.idLabel = new UILabel(IKey.raw("ID"));
        this.id = new UITextbox();


        this.categoryIdLabel = new UILabel(IKey.raw("CATEGORY"));
        this.categoryId = new UITextbox();
        this.keybindElement = new UIKeybind(keyCombo -> {
        });

        this.idLabel.x(15).h(20).w(0.5f, -20);
        this.categoryIdLabel.x(0.5f, 20).h(20).w(0.5f, -35);

        this.id.xy(15, 15).h(20).w(0.5f, -20);
        this.categoryId.x(0.5f, 20).y(15).h(20).w(0.5f, -35);

        this.keybindElement.xy(15, 50).w(1f, -30).h(20);
        this.keybindElement.single();

        this.id.relative(this.content);
        this.idLabel.relative(this.content);
        this.categoryId.relative(this.content);
        this.categoryIdLabel.relative(this.content);
        this.keybindElement.relative(this.content);

        this.add(this.id);
        this.add(this.idLabel);
        this.add(this.categoryId);
        this.add(this.categoryIdLabel);
        this.add(this.keybindElement);
    }
}
