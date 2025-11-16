package com.theuran.mappet.client.ui.triggers.panels;

import com.theuran.mappet.api.triggers.ItemTrigger;
import com.theuran.mappet.client.ui.triggers.UIEditorTriggersOverlayPanel;
import mchorse.bbs_mod.ui.forms.editors.panels.widgets.UIItemStack;

public class UIItemTriggerPanel extends UITriggerPanel<ItemTrigger> {
    public UIItemStack stack;

    public UIItemTriggerPanel(UIEditorTriggersOverlayPanel overlay, ItemTrigger trigger) {
        super(overlay, trigger);

        this.stack = new UIItemStack(item -> trigger.stack.set(item));

        this.add(this.stack);
        this.addDelay();
    }
}
