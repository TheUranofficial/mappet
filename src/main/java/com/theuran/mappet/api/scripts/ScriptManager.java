package com.theuran.mappet.api.scripts;

import com.caoccao.javet.exceptions.JavetException;
import com.caoccao.javet.interop.V8Host;
import com.caoccao.javet.interop.V8Runtime;
import com.caoccao.javet.values.V8Value;
import com.caoccao.javet.values.reference.V8ValueFunction;
import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.api.scripts.code.ScriptFactory;
import com.theuran.mappet.utils.ScriptUtils;
import mchorse.bbs_mod.data.types.MapType;
import mchorse.bbs_mod.utils.manager.BaseManager;
import mchorse.bbs_mod.utils.manager.storage.JSONLikeStorage;

import java.io.File;
import java.util.function.Supplier;

public class ScriptManager extends BaseManager<Script> {
    public ScriptManager(Supplier<File> folder) {
        super(folder);

        this.storage = new JSONLikeStorage().json();
    }

    public String eval(String content, ScriptEvent properties) throws JavetException {
        V8Runtime runtime = ScriptUtils.createRuntime();

        runtime.getGlobalObject().set("mappet", new ScriptFactory());
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
        Script script = this.load(id);

        try {
            script.initialize();
        } catch (JavetException e) {
            throw new RuntimeException(e);
        }

        return script;
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
