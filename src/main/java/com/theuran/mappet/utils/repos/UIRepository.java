package com.theuran.mappet.utils.repos;

import com.theuran.mappet.api.ui.UI;
import mchorse.bbs_mod.data.types.MapType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class UIRepository extends BaseDataRepository<UI> {
    @Override
    public UI create(String id, MapType data) {
        UI ui = new UI();

        ui.setId(id);

        if (data != null)
            ui.fromData(data);

        return ui;
    }
}
