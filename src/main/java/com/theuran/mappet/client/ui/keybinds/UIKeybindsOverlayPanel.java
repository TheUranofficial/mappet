package com.theuran.mappet.client.ui.keybinds;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.events.EventType;
import com.theuran.mappet.api.keybinds.Keybind;
import com.theuran.mappet.api.keybinds.KeybindManager;
import com.theuran.mappet.api.triggers.Trigger;
import com.theuran.mappet.client.ui.UIMappetKeys;
import com.theuran.mappet.client.ui.triggers.UIEditorTriggersOverlayPanel;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.elements.input.list.UILabelList;
import mchorse.bbs_mod.ui.framework.elements.input.list.UIList;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIEditorOverlayPanel;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlay;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;
import mchorse.bbs_mod.ui.utils.Label;

public class UIKeybindsOverlayPanel extends UIOverlayPanel {
    public UILabelList<String> keybinds;

    private String latest = "";

    public UIKeybindsOverlayPanel() {
        super(UIMappetKeys.KEYBINDS_TITLE);

        this.keybinds = new UILabelList<>(strings -> {
            for (Label<String> string : strings) {
                if (this.latest.equals(string.value)) {
                    UIEditorTriggersOverlayPanel panel = new UIEditorTriggersOverlayPanel(Mappet.getKeybinds().getTriggers(string.value));

                    UIOverlay.addOverlay(this.getContext(), panel, 0.55f, 0.75f);
                }
                this.latest = string.value;
            }
        });
        this.keybinds.full(this.content);
        this.content.add(this.keybinds);

        Mappet.getKeybinds().keybinds.forEach((keybind, triggers) -> {
            this.keybinds.add(IKey.raw(keybind.getId()), keybind.getId());
        });
    }
}