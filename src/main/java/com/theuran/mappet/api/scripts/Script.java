package com.theuran.mappet.api.scripts;

import com.caoccao.javet.exceptions.JavetException;
import com.caoccao.javet.interop.IV8Executable;
import com.caoccao.javet.interop.V8Runtime;
import com.caoccao.javet.values.V8Value;
import com.caoccao.javet.values.reference.V8ValueFunction;
import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.api.scripts.code.ScriptFactory;
import com.theuran.mappet.utils.ScriptUtils;
import mchorse.bbs_mod.settings.values.core.ValueGroup;
import mchorse.bbs_mod.settings.values.core.ValueString;
import mchorse.bbs_mod.settings.values.numeric.ValueBoolean;

public class Script extends ValueGroup {
    private final ValueString content = new ValueString("content", "");
    private final ValueBoolean isServer = new ValueBoolean("isServer", true);

    private V8Runtime runtime;

    public Script() {
        super("");
        this.add(this.content);
        this.add(this.isServer);
    }

    public void initialize() throws JavetException {
        if (this.runtime == null) {
            this.runtime = ScriptUtils.createRuntime();

            this.runtime.getGlobalObject().set("mappet", new ScriptFactory());

            IV8Executable executable = this.runtime.getExecutor(this.content.toString()).compileV8Script();

            executable.executeVoid();
        }
    }

    public String execute(ScriptEvent properties) throws JavetException {
        String functionName = properties.getFunction();

        if (functionName.isEmpty()) {
            functionName = "main";
        }

        V8ValueFunction function = this.runtime.getGlobalObject().get(functionName);
        V8Value result = function.callObject(null, properties);

        return result == null ? "null" : result.toString();
    }

    public String getContent() {
        return this.content.get();
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public boolean isServer() {
        return this.isServer.get();
    }

    public void setServer(boolean isServer) {
        this.isServer.set(isServer);
    }
}