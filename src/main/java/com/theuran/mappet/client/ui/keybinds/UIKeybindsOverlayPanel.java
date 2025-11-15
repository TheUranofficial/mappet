package com.theuran.mappet.client.ui.keybinds;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.keybinds.Keybind;
import com.theuran.mappet.client.ui.UIMappetKeys;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.elements.input.list.UILabelList;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlay;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIPromptOverlayPanel;
import mchorse.bbs_mod.ui.utils.Label;

public class UIKeybindsOverlayPanel extends UIOverlayPanel {
    public UILabelList<String> keybinds;

    private String latest = "";

    public UIKeybindsOverlayPanel() {
        super(UIMappetKeys.KEYBINDS_TITLE);

        this.keybinds = new UILabelList<>(strings -> {
            for (Label<String> string : strings) {
                if (this.latest.equals(string.value)) {
                    UIOverlay.addOverlay(this.getContext(), new UIKeybindOverlayPanel(Mappet.getKeybinds().getKeybind(this.latest)), 0.55f, 0.75f);
                }
                this.latest = string.value;
            }
        });

        this.keybinds.context(context -> {
            context.action(UIMappetKeys.KEYBINDS_ADD, () -> {
                UIPromptOverlayPanel panel = new UIPromptOverlayPanel(IKey.raw("Keybind"), IKey.EMPTY, value -> {
                    Keybind keybind = new Keybind(value, "", -1, Keybind.Type.PRESSED, Keybind.Modificator.NONE);

                    Mappet.getKeybinds().addKeybind(keybind);

                    this.keybinds.add(IKey.raw(keybind.getId()), keybind.getId());
                });

                UIOverlay.addOverlay(this.getContext(), panel);
            });
        });

        this.keybinds.full(this.content);
        this.content.add(this.keybinds);

        for (Keybind keybind : Mappet.getKeybinds().getKeybinds()) {
            this.keybinds.add(IKey.raw(keybind.getId()), keybind.getId());
        }
    }
}