package com.theuran.mappet.client.ui.triggers.panels;

import com.theuran.mappet.api.triggers.ItemTrigger;
import com.theuran.mappet.client.ui.triggers.UIEditorTriggersOverlayPanel;

public class UIItemTriggerPanel extends UITriggerPanel<ItemTrigger> {
    public UIItemTriggerPanel(UIEditorTriggersOverlayPanel overlay, ItemTrigger trigger) {
        super(overlay, trigger);

        this.addDelay();
    }
}
