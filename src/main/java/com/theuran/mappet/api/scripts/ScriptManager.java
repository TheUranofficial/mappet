package com.theuran.mappet.api.scripts;


import com.oracle.truffle.js.scriptengine.GraalJSScriptEngine;
import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.api.scripts.code.ScriptFactory;
import mchorse.bbs_mod.data.types.MapType;
import mchorse.bbs_mod.utils.manager.BaseManager;
import mchorse.bbs_mod.utils.manager.storage.JSONLikeStorage;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;

import java.io.File;
import java.util.function.Supplier;

public class ScriptManager extends BaseManager<Script> {
    public ScriptManager(Supplier<File> folder) {
        super(folder);

        this.storage = new JSONLikeStorage().json();
    }

    public String runScript(ScriptEvent properties) {
        return evalCode(this.load(properties.getScript()).getContent(), properties);
    }

    public String evalCode(String content, ScriptEvent properties) {
        try (Context context = Context.newBuilder("js")
                .option("engine.WarnInterpreterOnly", "false")
                .allowHostAccess(HostAccess.ALL)
                .build()) {

            String functionName = properties.getFunction();

            if (functionName.isEmpty()) {
                functionName = "main";
            }

            Value bindings = context.getBindings("js");

            bindings.putMember("mappet", new ScriptFactory());
            bindings.putMember("event", properties);

            String out = context.eval("js", content).toString();

            Value function = bindings.getMember(functionName);

            if (function != null && function.canExecute()) {
                return function.execute(properties).toString();
            }

            return out;
        }
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
