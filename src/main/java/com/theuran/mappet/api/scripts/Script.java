package com.theuran.mappet.api.scripts;

import com.caoccao.javet.exceptions.JavetException;
import com.caoccao.javet.interop.V8Runtime;
import com.caoccao.javet.interop.executors.IV8Executor;
import com.caoccao.javet.values.V8Value;
import com.caoccao.javet.values.reference.V8Script;
import com.caoccao.javet.values.reference.V8ValueFunction;
import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.api.scripts.logger.LogType;
import com.theuran.mappet.client.api.scripts.code.ClientScriptEvent;
import com.theuran.mappet.utils.ScriptUtils;
import mchorse.bbs_mod.settings.values.core.ValueGroup;
import mchorse.bbs_mod.settings.values.core.ValueString;
import mchorse.bbs_mod.settings.values.numeric.ValueBoolean;

import java.util.Date;

public class Script extends ValueGroup {
    private final ValueString content = new ValueString("content", "");
    private final ValueBoolean isServer = new ValueBoolean("isServer", true);

    private V8Runtime runtime;
    private V8Script script;

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
        try {
            this.runtime.getGlobalObject().set("event", properties);
            if (script == null) {
                IV8Executor executable = this.runtime.getExecutor(this.content.toString()).setResourceName(this.getId());

                executable.compileV8Module();
                script = executable.compileV8Script();

                executable.executeVoid();
            }

            script.executeVoid();

            V8Value result = null;

            if (this.runtime.getGlobalObject().has("main")) {
                V8ValueFunction function = this.runtime.getGlobalObject().get(properties.getFunction());
                result = function.call(null, properties);
                function.close();
            }

            return result == null ? "null" : result.toString();
        } catch (JavetException e) {
            Mappet.getLogger().addLog(LogType.ERROR, properties.getScript(), e);
            e.printStackTrace();
            throw e;
        }
    }

    public String execute(ClientScriptEvent properties) throws JavetException {
        try {
            this.runtime.getGlobalObject().set("event", properties);
            if (script == null) {
                IV8Executor executable = this.runtime.getExecutor(this.content.toString()).setResourceName(this.getId());

                executable.compileV8Module();
                script = executable.compileV8Script();

                executable.executeVoid();
            }

            script.executeVoid();

            V8Value result = null;

            if (this.runtime.getGlobalObject().has("main")) {
                V8ValueFunction function = this.runtime.getGlobalObject().get(properties.getFunction());
                result = function.call(null, properties);
                function.close();
            }

            return result == null ? "null" : result.toString();
        } catch (JavetException e) {
            //Mappet.getLogger().addLog(LogType.ERROR, properties.getScript(), e);
            e.printStackTrace();
            throw e;
        }
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