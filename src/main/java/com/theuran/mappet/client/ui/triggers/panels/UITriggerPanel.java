package com.theuran.mappet.client.ui.triggers.panels;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.triggers.*;
import com.theuran.mappet.client.ui.UIMappetKeys;
import com.theuran.mappet.client.ui.triggers.UIEditorTriggersOverlayPanel;
import mchorse.bbs_mod.l10n.L10n;
import mchorse.bbs_mod.ui.framework.elements.UIElement;
import mchorse.bbs_mod.ui.framework.elements.input.UITrackpad;
import mchorse.bbs_mod.ui.framework.elements.utils.UILabel;
import mchorse.bbs_mod.ui.utils.UI;

public class UITriggerPanel <T extends Trigger> extends UIElement {
    public UITrackpad frequency;

    protected UIEditorTriggersOverlayPanel overlay;
    protected T trigger;

    public UITriggerPanel(UIEditorTriggersOverlayPanel overlay, T trigger) {
        this.overlay = overlay;
        this.trigger = trigger;

        this.frequency = new UITrackpad(value -> this.trigger.maxDelay(value.intValue()));
        this.frequency.limit(1).integer().setValue(trigger.getMaxDelay());

        UILabel label = UI.label(L10n.lang("mappet.triggers.types." + Mappet.getTriggers().getType(trigger).path));

        this.column().vertical().stretch();
        this.add(label);
    }

    public void addDelay() {
        this.add(UI.label(UIMappetKeys.TRIGGERS_FREQUENCY).marginTop(12), this.frequency);
    }
}
