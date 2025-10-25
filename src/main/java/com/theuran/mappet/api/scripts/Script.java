package com.theuran.mappet.api.scripts;

import com.caoccao.javet.exceptions.JavetException;
import com.caoccao.javet.interop.IV8Executable;
import com.caoccao.javet.interop.V8Runtime;
import com.caoccao.javet.interop.executors.IV8Executor;
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

        this.initialize();
    }

    private void initialize() {
        this.runtime = ScriptUtils.createRuntime();
    }

    public String execute(ScriptEvent properties) throws JavetException {
        this.runtime.getGlobalObject().set("event", properties);

        IV8Executor executable = this.runtime.getExecutor(this.content.toString()).setResourceName(this.getId());

        executable.compileV8Module();
        executable.compileV8Script();

        executable.executeVoid();

        V8Value result = null;

        if (!properties.getFunction().isEmpty()) {
            V8ValueFunction function = this.runtime.getGlobalObject().get(properties.getFunction());
            result = function.callObject(null, properties);
        }

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