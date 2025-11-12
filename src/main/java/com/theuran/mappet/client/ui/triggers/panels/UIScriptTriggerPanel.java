package com.theuran.mappet.client.ui.triggers.panels;

import com.theuran.mappet.api.triggers.ScriptTrigger;
import com.theuran.mappet.client.ui.triggers.UIEditorTriggersOverlayPanel;
import com.theuran.mappet.client.ui.triggers.UITriggerPanel;
import com.theuran.mappet.client.ui.utils.UIMappetUtils;
import mchorse.bbs_mod.l10n.L10n;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.elements.input.text.UITextbox;
import mchorse.bbs_mod.ui.framework.elements.utils.UILabel;

public class UIScriptTriggerPanel extends UITriggerPanel<ScriptTrigger> {
    UITextbox script;
    UITextbox function;

    public UIScriptTriggerPanel(UIEditorTriggersOverlayPanel overlay, ScriptTrigger trigger) {
        super(overlay, trigger);

        this.script = UIMappetUtils.fullWindowContext(
                new UITextbox(10000, text -> this.trigger.script.set(text)),
                L10n.lang("mappet.triggers.types."+trigger.getTriggerId())
        );
        this.script.setText(trigger.script.get());

        this.function = UIMappetUtils.fullWindowContext(
                new UITextbox(10000, text -> this.trigger.function.set(text)),
                IKey.raw("Function")
        );
        this.function.setText(trigger.function.get());

        this.add(this.script);

        this.add(new UILabel(IKey.raw("Function")));

        this.add(this.function);

        this.addDelay();
    }
}
