package com.theuran.mappet.api.scripts;

import com.caoccao.javet.exceptions.JavetException;
import com.caoccao.javet.interop.V8Runtime;
import com.caoccao.javet.values.V8Value;
import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.utils.ScriptUtils;
import mchorse.bbs_mod.data.types.MapType;
import mchorse.bbs_mod.utils.manager.BaseManager;
import mchorse.bbs_mod.utils.manager.storage.JSONLikeStorage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ScriptManager extends BaseManager<Script> {
    private final Map<String, Script> scripts = new HashMap<>();

    public ScriptManager(Supplier<File> folder) {
        super(folder);

        this.storage = new JSONLikeStorage().json();
    }

    public String eval(String content, ScriptEvent properties) throws JavetException {
        V8Runtime runtime = ScriptUtils.createRuntime();

        runtime.getGlobalObject().set("event", properties);

        V8Value value = runtime.getExecutor(content).execute();

        if (value != null)
            value.close();

        return value == null ? "null" : value.toString();
    }

    public String execute(ScriptEvent properties) throws JavetException {
        Script script = this.getScript(properties.getScript());

        return script == null ? "null" : script.execute(properties);
    }

    public Script getScript(String id) {
        if (this.isLoadScript(id)) {
            return this.getLoadedScript(id);
        }
        return this.loadScript(id);
    }

    public void setLoadScript(String id) {
        this.scripts.put(id, this.load(id));
    }

    public Script loadScript(String id) {
        Script script = this.load(id);

        this.scripts.put(id, script);

        return script;
    }

    public Script getLoadedScript(String id) {
        return this.scripts.get(id);
    }

    public boolean isLoadScript(String id) {
        return this.scripts.containsKey(id);
    }

    @Override
    protected Script createData(String s, MapType mapType) {
        Script script = new Script();

        if (mapType != null) {
            script.fromData(mapType);
        }

        return script;
    }
}
