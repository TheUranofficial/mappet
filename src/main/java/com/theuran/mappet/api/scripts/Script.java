package com.theuran.mappet.api.scripts;

import com.theuran.mappet.utils.ScriptUtils;
import mchorse.bbs_mod.settings.values.ValueBoolean;
import mchorse.bbs_mod.settings.values.ValueGroup;
import mchorse.bbs_mod.settings.values.ValueString;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class Script extends ValueGroup {
    public ValueString code = new ValueString("code", "");
    public ValueBoolean unique = new ValueBoolean("unique", true);

    private ScriptEngine engine;

    public Script() {
        super("");
        this.add(this.code);
        this.add(this.unique);
    }

    public void start(ScriptManager manager) throws ScriptException {
        if (this.engine == null) {
            this.initializeEngine();
            this.configureEngineContext();
        }
    }

    private void initializeEngine() throws ScriptException {
        String extension = this.getScriptExtension();

        this.engine = ScriptUtils.getEngineByExtension(extension);

        if (this.engine == null) {
            String message = "Looks like Mappet can't find script engine for a \"" + this.getScriptExtension() + "\" file extension.";

            throw new ScriptException(message, this.getId(), -1);
        }

        ScriptUtils.sanitize(this.engine);
    }

    private void configureEngineContext() {
        this.engine.getContext().setAttribute("javax.script.filename", this.getId(), ScriptContext.ENGINE_SCOPE);
        this.engine.getContext().setAttribute("polyglot.js.allowHostAccess", true, ScriptContext.ENGINE_SCOPE);
    }

    public String getScriptExtension() {
        String id = this.getId();
        int index = id.lastIndexOf('.');

        return index >= 0 ? id.substring(index + 1) : "js";
    }
}
