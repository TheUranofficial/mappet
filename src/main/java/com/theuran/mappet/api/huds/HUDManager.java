package com.theuran.mappet.api.huds;

import mchorse.bbs_mod.data.types.MapType;
import mchorse.bbs_mod.utils.manager.BaseManager;
import mchorse.bbs_mod.utils.manager.storage.JSONLikeStorage;

import java.io.File;
import java.util.function.Supplier;

public class HUDManager extends BaseManager<HUDScene> {
    public HUDManager(Supplier<File> folder) {
        super(folder);

        this.storage = new JSONLikeStorage().json();
    }

    @Override
    public HUDScene createData(String id, MapType mapType) {
        HUDScene scene = new HUDScene();

        if (mapType != null) {
            scene.fromData(mapType);
        }

        return scene;
    }
}
