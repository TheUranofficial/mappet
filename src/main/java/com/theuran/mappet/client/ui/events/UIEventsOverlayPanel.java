package com.theuran.mappet.client.ui.events;

import com.theuran.mappet.api.events.EventType;
import com.theuran.mappet.client.ui.UIMappetKeys;
import com.theuran.mappet.client.ui.triggers.UIEditorTriggersOverlayPanel;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.packets.triggers.TriggersRequestPacket;
import mchorse.bbs_mod.ui.framework.elements.input.list.UILabelList;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlay;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;
import mchorse.bbs_mod.ui.utils.Label;

public class UIEventsOverlayPanel extends UIOverlayPanel {
    public UILabelList<String> list;
    public UIEditorTriggersOverlayPanel panel;

    public String latest = "";

    public UIEventsOverlayPanel() {
        super(UIMappetKeys.EVENTS_TITLE);

        this.list = new UILabelList<>(strings -> {
            for (Label<String> string : strings) {
                if (this.latest.equals(string.value)) {
                    this.panel = new UIEditorTriggersOverlayPanel();

                    Dispatcher.sendToServer(new TriggersRequestPacket(EventType.valueOf(string.value.toUpperCase())));
                    UIOverlay.addOverlay(this.getContext(), this.panel, 0.55f, 0.75f).noBackground();
                }
                this.latest = string.value;
            }
        });

        this.list.full(this.content);
        this.content.add(this.list);
    }
}
