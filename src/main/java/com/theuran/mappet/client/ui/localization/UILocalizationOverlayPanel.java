package com.theuran.mappet.client.ui.localization;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.events.EventType;
import com.theuran.mappet.api.localization.LocalizationType;
import com.theuran.mappet.client.ui.triggers.UIEditorTriggersOverlayPanel;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.elements.input.list.UILabelList;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlay;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;
import mchorse.bbs_mod.ui.utils.Label;

public class UILocalizationOverlayPanel extends UIOverlayPanel {
    public UILabelList<String> list;

    private String latest = "";

    public UILocalizationOverlayPanel() {
        super(IKey.raw("localization"));

        this.list = new UILabelList<>(strings -> {
            for (Label<String> string : strings) {
                if (this.latest.equals(string.value)) {
                    UIEditorTriggersOverlayPanel panel = new UIEditorTriggersOverlayPanel(Mappet.getEvents().getTriggers(EventType.valueOf(string.value.toUpperCase())));

                    UIOverlay.addOverlay(this.getContext(), panel, 0.55f, 0.75f);
                }
                this.latest = string.value;
            }
        });
        this.list.full(this.content);
        this.content.add(this.list);

        for (LocalizationType value : LocalizationType.values()) {
            this.list.add(IKey.raw(value.name()), value.name().toLowerCase());
        }
    }
}
