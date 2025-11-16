package com.theuran.mappet.utils;

import mchorse.bbs_mod.data.DataToString;
import mchorse.bbs_mod.data.IMapSerializable;
import mchorse.bbs_mod.data.types.MapType;
import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;

public abstract class BaseFileManager implements IMapSerializable {
    private final Supplier<File> file;

    public BaseFileManager(Supplier<File> file) {
        this.file = file;
    }

    public void save() {
        this.save(this.file.get());
    }

    public void save(File file) {
        if (file != null) {
            if (!file.getParentFile().isDirectory()) {
                file.getParentFile().mkdirs();
            }

            DataToString.writeSilently(file, this.toData(), true);
        }
    }

    public void load() {
        this.load(this.file.get());
    }

    public void load(File file) {
        try {
            if (file != null && file.exists()) {
                this.fromData((MapType) DataToString.read(file));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
