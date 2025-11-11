package com.theuran.mappet.client.ui.events.panels;

import com.theuran.mappet.api.triggers.CommandTrigger;
import com.theuran.mappet.client.ui.events.UIEventOverlayPanel;
import com.theuran.mappet.client.ui.utils.UIMappetUtils;
import mchorse.bbs_mod.l10n.L10n;
import mchorse.bbs_mod.ui.framework.elements.input.text.UITextbox;

public class UICommandTriggerPanel extends UITriggerPanel<CommandTrigger> {
    public UITextbox command;

    public UICommandTriggerPanel(UIEventOverlayPanel overlay, CommandTrigger trigger) {
        super(overlay, trigger);

        this.command = UIMappetUtils.fullWindowContext(
                new UITextbox(10000, text -> this.trigger.command.set(text)),
                L10n.lang("mappet.triggers.types.command")
        );
        this.command.setText(trigger.command.get());

        this.add(this.command);
        this.addDelay();
    }
}
