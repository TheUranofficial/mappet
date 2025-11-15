package com.theuran.mappet.client.ui.triggers.panels;

import com.theuran.mappet.api.triggers.ScriptTrigger;
import com.theuran.mappet.client.ui.UIMappetKeys;
import com.theuran.mappet.client.ui.triggers.UIEditorTriggersOverlayPanel;
import com.theuran.mappet.client.ui.triggers.UITriggerPanel;
import com.theuran.mappet.client.ui.utils.UIMappetUtils;
import mchorse.bbs_mod.l10n.L10n;
import mchorse.bbs_mod.ui.framework.elements.input.text.UITextbox;
import mchorse.bbs_mod.ui.utils.UI;

public class UIScriptTriggerPanel extends UITriggerPanel<ScriptTrigger> {
    public UITextbox script;
    public UITextbox function;

    public UIScriptTriggerPanel(UIEditorTriggersOverlayPanel overlay, ScriptTrigger trigger) {
        super(overlay, trigger);

        this.script = UIMappetUtils.fullWindowContext(
                new UITextbox(10000, text -> this.trigger.script.set(text)),
                L10n.lang("mappet.triggers.types.script")
        );
        this.script.setText(trigger.script.get());

        this.function = UIMappetUtils.fullWindowContext(
                new UITextbox(10000, text -> this.trigger.function.set(text)),
                UIMappetKeys.GENERAL_FUNCTION
        );
        this.function.setText(trigger.function.get());

        this.add(this.script);

        this.add(UI.label(UIMappetKeys.GENERAL_FUNCTION));

        this.add(this.function);

        this.addDelay();
    }
}
