package com.theuran.mappet.api.ui;

import mchorse.bbs_mod.data.types.MapType;
import mchorse.bbs_mod.utils.manager.BaseManager;

import java.io.File;
import java.util.function.Supplier;

public class UIManager extends BaseManager<UI> {
    public UIManager(Supplier<File> folder) {
        super(folder);
    }

    @Override
    protected UI createData(String id, MapType data) {
        UI ui = new UI();

        if (data != null)
            ui.fromData(data);

        return ui;
    }
}
