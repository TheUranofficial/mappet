package com.theuran.mappet.api.states;

import mchorse.bbs_mod.data.DataToString;
import mchorse.bbs_mod.data.IMapSerializable;
import mchorse.bbs_mod.data.types.MapType;
import mchorse.bbs_mod.utils.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class States implements IMapSerializable {
    private final File file;

    public States(File file) {
        this.file = file;
    }

    @Override
    public void toData(MapType mapType) {

    }

    @Override
    public void fromData(MapType entries) {

    }

    public void save() {
        try {
            IOUtils.writeText(this.file, DataToString.toString(this.toData(), true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            if (this.file.exists()) {
                this.fromData(DataToString.mapFromString(IOUtils.readText(this.file)));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}