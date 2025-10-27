package com.theuran.mappet.utils;

import mchorse.bbs_mod.data.DataStorageUtils;
import mchorse.bbs_mod.data.DataToString;
import mchorse.bbs_mod.data.IMapSerializable;
import mchorse.bbs_mod.data.types.MapType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;

public abstract class BaseFileManager implements IMapSerializable, INBTSerializable {
    private Supplier<File> file;

    public BaseFileManager(Supplier<File> file) {
        this.file = file;
    }

    public void save() {
        this.save(this.file.get());
    }

    public boolean save(File file) {
        if (file != null) {
            if (!file.getParentFile().isDirectory()) {
                file.getParentFile().mkdirs();
            }

            DataToString.writeSilently(file, this.toData(), true);
            return true;
        }
        return false;
    }

    public void load() {
        this.load(this.file.get());
    }

    public boolean load(File file) {
        try {
            if (file != null && file.exists()) {
                this.fromData((MapType) DataToString.read(file));
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void fromNbt(NbtElement nbt) {
        this.fromData((DataStorageUtils.fromNbt(nbt).asMap()));
    }

    @Override
    public NbtElement toNbt() {
        return DataStorageUtils.toNbt(this.toData());
    }
}
