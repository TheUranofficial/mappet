package com.theuran.mappet.api.scripts;

import mchorse.bbs_mod.data.types.MapType;
import mchorse.bbs_mod.utils.manager.BaseManager;

import java.io.File;
import java.util.function.Supplier;

public class ScriptManager extends BaseManager<Script> {
    public ScriptManager(Supplier<File> folder) {
        super(folder);
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
