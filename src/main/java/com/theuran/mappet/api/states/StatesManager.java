package com.theuran.mappet.api.states;

import com.theuran.mappet.utils.BaseFileManager;
import mchorse.bbs_mod.data.types.MapType;

import java.io.File;
import java.util.function.Supplier;

public class StatesManager extends BaseFileManager {
    private final States states = new States();

    public StatesManager(Supplier<File> file) {
        super(file);
    }

    @Override
    public void toData(MapType mapType) {
        mapType.combine(states.values);
    }

    @Override
    public void fromData(MapType mapType) {
        states.values = mapType;
    }

    public States get() {
        return this.states;
    }
}
