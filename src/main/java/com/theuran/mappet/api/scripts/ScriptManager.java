package com.theuran.mappet.api.scripts;


import mchorse.bbs_mod.data.types.MapType;
import mchorse.bbs_mod.utils.manager.BaseManager;
import mchorse.bbs_mod.utils.manager.storage.JSONLikeStorage;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;

import java.io.File;
import java.util.function.Supplier;

public class ScriptManager extends BaseManager<Script> {
    public static final Context globalContext = Context.newBuilder("js")
            .option("js.ecmascript-version", "2023")
            .allowHostAccess(HostAccess.ALL)
            .build();

    public ScriptManager(Supplier<File> folder) {
        super(folder);

        this.storage = new JSONLikeStorage().json();
    }

    public String evalScript(String id) {
        return evalCode(this.load(id).getContent());
    }

    public String evalCode(String content) {
        return globalContext.eval("js", content).toString();
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
