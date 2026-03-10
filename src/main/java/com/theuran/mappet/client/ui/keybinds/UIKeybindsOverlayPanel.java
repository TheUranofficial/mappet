package com.theuran.mappet.client.ui.keybinds;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.keybinds.Keybind;
import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.client.ui.UIMappetKeys;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.packets.keybinds.KeybindsSetPacket;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.elements.input.list.UILabelList;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlay;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIPromptOverlayPanel;
import mchorse.bbs_mod.ui.utils.Label;

public class UIKeybindsOverlayPanel extends UIOverlayPanel {
    public UILabelList<String> keybinds;

    public String latest = "";

    public UIKeybindOverlayPanel panel;

    public UIKeybindsOverlayPanel() {
        super(UIMappetKeys.KEYBINDS_TITLE);

        this.keybinds = new UILabelList<>(strings -> {
            for (Label<String> string : strings) {
                if (!this.latest.isEmpty() && this.latest.equals(string.value)) {
                    UIKeybindOverlayPanel panel = new UIKeybindOverlayPanel(MappetClient.getKeybinds().getKeybind(this.latest));
                    UIOverlay.addOverlay(this.getContext(), panel, 0.55f, 0.75f);
                    this.panel = panel;
                }
                this.latest = string.value;
            }
        });

        this.keybinds.context(context -> {
            context.action(UIMappetKeys.KEYBINDS_ADD, () -> {
                UIPromptOverlayPanel panel = new UIPromptOverlayPanel(IKey.raw("Keybind"), IKey.EMPTY, value -> {
                    Keybind keybind = new Keybind(value, "", -1, Keybind.Type.PRESSED, Keybind.Modificator.NONE);

                    Dispatcher.sendToServer(new KeybindsSetPacket(keybind.id(), keybind));

                    MappetClient.getKeybinds().addKeybind(keybind);

                    this.keybinds.add(IKey.raw(keybind.id()), keybind.id());
                });

                UIOverlay.addOverlay(this.getContext(), panel);
            });
        });

        this.keybinds.full(this.content);
        this.content.add(this.keybinds);

        for (Keybind keybind : MappetClient.getKeybinds().getKeybindings()) {
            this.keybinds.add(IKey.raw(keybind.id()), keybind.id());
        }
    }
}