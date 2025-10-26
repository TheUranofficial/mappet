package com.theuran.mappet.api.executables;

import com.theuran.mappet.api.scripts.code.ScriptEvent;

public class Executable {
    private int ticks;
    private final ScriptEvent scriptEvent;
    private String script = "";
    private String function = "";
    private String code = "";

    public Executable(int ticks, String script, String function, ScriptEvent scriptEvent) {
        this.ticks = ticks;
        this.script = script;
        this.function = function;
        this.scriptEvent = scriptEvent;
        this.scriptEvent.setScript(this.script);
        this.scriptEvent.setFunction(this.function);
    }

    public Executable(int ticks, String code, ScriptEvent scriptEvent) {
        this.ticks = ticks;
        this.code = code;
        this.scriptEvent = scriptEvent;
    }

    public int getTicks() {
        return this.ticks;
    }

    public void removeTick() {
        this.ticks -= 1;
    }

    public ScriptEvent getScriptEvent() {
        return this.scriptEvent;
    }

    public String getScript() {
        return this.script;
    }

    public String getFunction() {
        return this.function;
    }

    public String getCode() {
        return this.code;
    }
}
