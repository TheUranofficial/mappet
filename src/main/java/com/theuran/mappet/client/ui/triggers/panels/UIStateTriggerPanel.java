package com.theuran.mappet.client.ui.triggers.panels;

import com.theuran.mappet.api.triggers.StateTrigger;
import com.theuran.mappet.client.ui.triggers.UIEditorTriggersOverlayPanel;
import com.theuran.mappet.client.ui.triggers.UITriggerPanel;

public class UIStateTriggerPanel extends UITriggerPanel<StateTrigger> {
    public UIStateTriggerPanel(UIEditorTriggersOverlayPanel overlay, StateTrigger trigger) {
        super(overlay, trigger);

        this.addDelay();
    }
}
