package com.theuran.mappet.client.ui.triggers.panels;

import com.theuran.mappet.api.triggers.ScriptTrigger;
import com.theuran.mappet.client.ui.MappetContentType;
import com.theuran.mappet.client.ui.UIMappetKeys;
import com.theuran.mappet.client.ui.triggers.UIEditorTriggersOverlayPanel;
import com.theuran.mappet.client.ui.utils.UIMappetUtils;
import mchorse.bbs_mod.l10n.L10n;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.ContentType;
import mchorse.bbs_mod.ui.framework.elements.input.text.UITextbox;
import mchorse.bbs_mod.ui.utils.UI;

public class UIScriptTriggerPanel extends UIStringTriggerPanel<ScriptTrigger> {
    public UITextbox function;

    public UIScriptTriggerPanel(UIEditorTriggersOverlayPanel overlay, ScriptTrigger trigger) {
        super(overlay, trigger);

        this.function = UIMappetUtils.fullWindowContext(
                new UITextbox(10000, text -> this.trigger.function.set(text)),
                UIMappetKeys.GENERAL_FUNCTION
        );
        this.function.setText(trigger.function.get());

        this.addPicker();
        this.add(UI.label(UIMappetKeys.GENERAL_FUNCTION));
        this.add(this.function);

        this.addDelay();
    }

    @Override
    public IKey getLabel() {
        return UIMappetKeys.SCRIPTS_TITLE;
    }

    @Override
    public ContentType getType() {
        return MappetContentType.SCRIPTS;
    }
}
