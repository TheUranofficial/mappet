package com.theuran.mappet.client.ui.keybinds;

import com.theuran.mappet.api.keybinds.Keybind;
import com.theuran.mappet.utils.InputUtils;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.elements.input.UITrackpad;
import mchorse.bbs_mod.ui.framework.elements.input.list.UIList;
import mchorse.bbs_mod.ui.framework.elements.input.text.UITextbox;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIEditorOverlayPanel;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlay;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;
import mchorse.bbs_mod.ui.framework.elements.utils.UILabel;
import net.minecraft.client.util.InputUtil;

public class UIKeybindOverlayPanel extends UIOverlayPanel {
    Keybind keybind;

    UITextbox id;
    UITextbox categoryId;
    UILabel keycodeLabel;
    UITrackpad keycode;

    public UIKeybindOverlayPanel(Keybind keybind) {
        super(IKey.raw(keybind.getId()));

        this.keybind = keybind;

        this.id = new UITextbox();
        this.categoryId = new UITextbox();
        this.keycodeLabel = new UILabel(IKey.raw("NONE"));
        this.keycode = new UITrackpad();

        this.id.x(15).h(20).w(0.5f, -20);
        this.categoryId.x(0.5f, 20).h(20).w(0.5f, -15);

        this.keycodeLabel.x(0.5f, -25).y(35).wh(25, 20);
        this.keycodeLabel.labelAnchor(0.5f, 0.5f);

        this.keycode.xy(15, 35).w(0.5f, -50).h(20);
        this.keycode.integer().limit(-1, 1000);
        this.keycode.callback = (number) -> {
            this.keycodeLabel.label = IKey.raw(InputUtil.fromKeyCode((int) Math.round(number), 0).getLocalizedText().getString());
        };

        this.id.relative(this.content);
        this.categoryId.relative(this.content);
        this.keycodeLabel.relative(this.content);
        this.keycode.relative(this.content);

        this.add(this.id);
        this.add(this.categoryId);
        this.add(this.keycodeLabel);
        this.add(this.keycode);
    }
}
