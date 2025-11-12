package com.theuran.mappet.client.ui.triggers.panels;

import com.theuran.mappet.api.triggers.SoundTrigger;
import com.theuran.mappet.client.ui.triggers.UIEditorTriggersOverlayPanel;
import com.theuran.mappet.client.ui.triggers.UITriggerPanel;
import com.theuran.mappet.client.ui.utils.UIMappetUtils;
import mchorse.bbs_mod.l10n.L10n;
import mchorse.bbs_mod.ui.framework.elements.input.text.UITextbox;

public class UISoundTriggerPanel extends UITriggerPanel<SoundTrigger> {
    UITextbox sound;

    public UISoundTriggerPanel(UIEditorTriggersOverlayPanel overlay, SoundTrigger trigger) {
        super(overlay, trigger);

        this.sound = UIMappetUtils.fullWindowContext(
                new UITextbox(10000, text -> this.trigger.sound.set(text)),
                L10n.lang("mappet.triggers.types."+trigger.getTriggerId())
        );
        this.sound.setText(trigger.sound.get());

        this.add(this.sound);
        this.addDelay();
    }
}
