package com.theuran.mappet.api.scripts;

import com.theuran.mappet.utils.BaseDataRepository;
import mchorse.bbs_mod.data.types.MapType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ScriptRepository extends BaseDataRepository<Script> {
    @Override
    public Script create(String id, MapType mapType) {
        Script script = new Script();

        script.setId(id);

        if (mapType != null)
            script.fromData(mapType);

        return script;
    }

    @Override
    public String getId() {
        return "script";
    }
}
