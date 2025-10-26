package com.theuran.mappet.api.triggers;

import com.caoccao.javet.exceptions.JavetException;
import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.scripts.code.ScriptEvent;
import mchorse.bbs_mod.data.types.MapType;

public class ScriptTrigger extends Trigger {
    private String script;
    private String function;

    public ScriptTrigger(String script, String function) {
        this.script = script;
        this.function = function;
    }

    @Override
    public void execute(ScriptEvent scriptEvent) {
        try {
            scriptEvent.setScript(this.script);
            scriptEvent.setFunction(this.function);
            Mappet.getScripts().getScript(this.script).execute(scriptEvent);
        } catch (JavetException e) {
        }
    }

    @Override
    public String getId() {
        return "Script";
    }

    @Override
    public void toData(MapType mapType) {
        mapType.putString("script", this.script);
        mapType.putString("function", this.function);
    }

    @Override
    public void fromData(MapType entries) {
        this.script = entries.getString("script");
        this.function = entries.getString("function");
    }
}
