package com.theuran.mappet.client.ui.events;

import com.theuran.mappet.api.events.EventType;
import com.theuran.mappet.client.ui.UIMappetKeys;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.elements.input.list.UILabelList;
import mchorse.bbs_mod.ui.framework.elements.input.list.UIList;
import mchorse.bbs_mod.ui.framework.elements.input.list.UIStringList;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlay;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIStringOverlayPanel;
import mchorse.bbs_mod.ui.utils.Label;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UIEventsOverlayPanel extends UIOverlayPanel {
    public UILabelList<String> list;

    private String latest = "";

    public UIEventsOverlayPanel() {
        super(UIMappetKeys.EVENTS_TITLE);

        this.list = new UILabelList<>(strings -> {
            for (Label<String> string : strings) {
                if (this.latest.equals(string.value)) {
                    UIEventOverlayPanel panel = new UIEventOverlayPanel(EventType.valueOf(string.value.toUpperCase()));

                    UIOverlay.addOverlay(this.getContext(), panel, 0.55f, 0.75f);
                }
                this.latest = string.value;
            }
        });
        this.list.full(this.content);
        this.content.add(this.list);

        for (EventType value : EventType.values()) {
            this.list.add(value.getName(), value.name().toLowerCase());
        }
    }
}
