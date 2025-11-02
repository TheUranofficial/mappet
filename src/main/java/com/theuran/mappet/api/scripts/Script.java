package com.theuran.mappet.api.scripts;

import com.caoccao.javet.exceptions.JavetException;
import com.caoccao.javet.interop.V8Runtime;
import com.caoccao.javet.interop.executors.IV8Executor;
import com.caoccao.javet.values.V8Value;
import com.caoccao.javet.values.reference.V8Script;
import com.caoccao.javet.values.reference.V8ValueFunction;
import com.caoccao.javet.values.reference.V8ValueGlobalObject;
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

    public Script() {
        super("");
        this.add(this.content);
        this.add(this.isServer);
        this.initialize();
    }

    public Script(String id, String content, boolean isServer) {
        this();
        this.setId(id);
        this.setContent(content);
        this.setServer(isServer);
    }

    private void initialize() {
        this.runtime = ScriptUtils.createRuntime();
    }

    public String execute(ScriptEvent properties) throws JavetException {
        if (!properties.getScript().equals(this.getId())) {
            properties.setScript(this.getId());
        }

        V8ValueGlobalObject globalObject = null;
        V8ValueFunction function = null;

        try {
            globalObject = this.runtime.getGlobalObject();
            globalObject.set("event", properties);

            compileAndExecuteScript();

            V8Value result = null;
            String functionName = properties.getFunction();

            if (!functionName.isEmpty() && globalObject.has(functionName)) {
                function = globalObject.get(functionName);
                result = function.call(null, properties);
            }

            return result == null ? "null" : result.toString();

        } catch (JavetException e) {
            Mappet.getLogger().addLog(LogType.ERROR, this.getId(), e);
            throw e;
        } finally {
            closeQuietly(function);
            closeQuietly(globalObject);
        }
    }

    public String execute(ClientScriptEvent properties) throws JavetException {
        if (!properties.getScript().equals(this.getId())) {
            properties.setScript(this.getId());
        }

        V8ValueGlobalObject globalObject = null;
        V8ValueFunction function = null;

        try {
            globalObject = this.runtime.getGlobalObject();
            globalObject.set("event", properties);

            compileAndExecuteScript();

            V8Value result = null;
            String functionName = properties.getFunction();

            if (!functionName.isEmpty() && globalObject.has(functionName)) {
                function = globalObject.get(functionName);
                result = function.call(null, properties);
            }

            return result == null ? "null" : result.toString();

        } catch (JavetException e) {
            Mappet.getLogger().addLog(LogType.ERROR, this.getId(), e);
            throw e;
        } finally {
            closeQuietly(function);
            closeQuietly(globalObject);
        }
    }

    private void compileAndExecuteScript() throws JavetException {
        IV8Executor executor = this.runtime.getExecutor(this.content.toString())
                .setResourceName(this.getId());
        executor.compileV8Module();
        executor.compileV8Script().executeVoid();
    }

    private void closeQuietly(AutoCloseable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (Exception e) {
            }
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