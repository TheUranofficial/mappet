package com.theuran.mappet.api.triggers;

import com.caoccao.javet.exceptions.JavetException;
import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.scripts.code.ScriptEvent;
import mchorse.bbs_mod.settings.values.core.ValueString;

public class ScriptTrigger extends Trigger {
    public ValueString script = new ValueString("script", "");
    public ValueString function = new ValueString("function", "");

    public ScriptTrigger() {
        this.add(this.script);
        this.add(this.function);
    }

    public ScriptTrigger(String script, String function) {
        this();
        this.script.set(script);
        this.function.set(function);
    }

    @Override
    public void execute(ScriptEvent scriptEvent) {
        try {
            scriptEvent.setScript(this.script.get());
            scriptEvent.setFunction(this.function.get());
            Mappet.getScripts().getScript(this.script.get()).execute(scriptEvent);
        } catch (JavetException ignored) {
        }
    }

    @Override
    public String getTriggerId() {
        return "script";
    }
}
