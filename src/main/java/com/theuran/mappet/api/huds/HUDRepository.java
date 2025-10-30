package com.theuran.mappet.api.huds;

import com.theuran.mappet.utils.BaseDataRepository;
import mchorse.bbs_mod.data.types.MapType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class HUDRepository extends BaseDataRepository<HUDScene> {
    @Override
    public HUDScene create(String id, MapType mapType) {
        HUDScene scene = new HUDScene();

        scene.setId(id);

        if (mapType != null)
            scene.fromData(mapType);

        return scene;
    }

    @Override
    public String getId() {
        return "hud";
    }
}
