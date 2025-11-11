package com.theuran.mappet.client.ui.events.panels;

import com.theuran.mappet.api.triggers.StateTrigger;
import com.theuran.mappet.client.ui.events.UIEventOverlayPanel;

public class UIStateTriggerPanel extends UITriggerPanel<StateTrigger> {
    public UIStateTriggerPanel(UIEventOverlayPanel overlay, StateTrigger trigger) {
        super(overlay, trigger);
    }
}
