package com.theuran.mappet.api.triggers;

import com.caoccao.javet.exceptions.JavetException;
import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.client.api.scripts.code.ClientScriptEvent;
import com.theuran.mappet.client.ui.triggers.UIEditorTriggersOverlayPanel;
import com.theuran.mappet.client.ui.triggers.panels.UITriggerPanel;
import com.theuran.mappet.client.ui.triggers.panels.UIScriptTriggerPanel;
import mchorse.bbs_mod.settings.values.core.ValueString;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class ScriptTrigger extends StringTrigger {
    public ValueString function = new ValueString("function", "");

    public ScriptTrigger() {
        super();
        this.add(this.function);
    }

    public ScriptTrigger(String script, String function) {
        this();
        this.key.set(script);
        this.function.set(function);
    }

    @Override
    public void execute(ScriptEvent scriptEvent) {
        try {
            scriptEvent.setScript(this.key.get());
            scriptEvent.setFunction(this.function.get());
            Mappet.getScripts().execute(scriptEvent);
        } catch (JavetException ignored) {
        }
    }

    @Override
    public void execute(ClientScriptEvent scriptEvent) {
        try {
            scriptEvent.setScript(this.key.get());
            scriptEvent.setFunction(this.function.get());
            MappetClient.getScripts().execute(scriptEvent);
        } catch (JavetException ignored) {
        }
    }

    @Override
    public String getTriggerId() {
        return "script";
    }

    @Environment(EnvType.CLIENT)
    public UITriggerPanel<?> getPanel(UIEditorTriggersOverlayPanel overlayPanel) {
        return new UIScriptTriggerPanel(overlayPanel, this);
    }
}
