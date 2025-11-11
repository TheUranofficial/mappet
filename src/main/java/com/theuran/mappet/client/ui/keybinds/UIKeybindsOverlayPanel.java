package com.theuran.mappet.client.ui.keybinds;

import com.theuran.mappet.api.events.EventType;
import com.theuran.mappet.client.ui.UIMappetKeys;
import com.theuran.mappet.client.ui.events.UIEventOverlayPanel;
import mchorse.bbs_mod.ui.framework.elements.input.list.UILabelList;
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
                    UIEventOverlayPanel panel = new UIEventOverlayPanel(EventType.valueOf(string.value.toUpperCase()));

                    UIOverlay.addOverlay(this.getContext(), panel, 0.55f, 0.75f);
                }
                this.latest = string.value;
            }
        });
        this.keybinds.full(this.content);
        this.content.add(this.keybinds);

        for (EventType value : EventType.values()) {
            this.keybinds.add(value.getName(), value.name().toLowerCase());
        }
    }
}
