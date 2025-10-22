package com.theuran.mappet.api.scripts;


import com.theuran.mappet.api.scripts.code.ScriptEvent;
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

    public String runScript(String scriptName, Properties properties) {
        return evalCode(this.load(scriptName).getContent(), properties);
    }

    public String evalCode(String content, Properties properties) {
        try (Context context = Context.newBuilder("js")
                .option("js.ecmascript-version", "2023")
                .allowHostAccess(HostAccess.ALL)
                .build()) {

            String functionName = ((ScriptEvent) properties.get("Context")).getFunction();

            if (functionName.isEmpty()) {
                functionName = "main";
            }

            Value bindings = context.getBindings("js");

            bindings.putMember("Context", properties.get("Context"));

            String out = context.eval("js", content).toString();

            Value function = bindings.getMember(functionName);

            if (function != null && function.canExecute()) {
                return function.execute(properties.get("Context")).toString();
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
