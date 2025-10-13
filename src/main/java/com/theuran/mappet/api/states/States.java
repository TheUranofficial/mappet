package com.theuran.mappet.api.states;

import mchorse.bbs_mod.data.DataStorageUtils;
import mchorse.bbs_mod.data.DataToString;
import mchorse.bbs_mod.data.IMapSerializable;
import mchorse.bbs_mod.data.types.BaseType;
import mchorse.bbs_mod.data.types.MapType;
import mchorse.bbs_mod.utils.IOUtils;
import net.minecraft.nbt.NbtElement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

//Sorry mchorse but ima lazy for understanding basemanager
//TODO: Rewrite this into basemanager extender
public class States implements IMapSerializable {
    private MapType values = new MapType();

    private final File file;

    public States(File file) {
        this.file = file;
    }

    public States() {
        this.file = null;
    }

    @Override
    public void toData(MapType map) {
        map.combine(this.values);
    }

    @Override
    public void fromData(MapType entries) {
        this.values = entries;
    }

    //Server states

    public void setNumber(String id, double value) {
        if (Double.isNaN(value)) return;

        this.values.putDouble(id, value);
    }

    public double getNumber(String id) {
        return this.values.getDouble(id);
    }

    public void setString(String id, String value) {
        this.values.putString(id, value);
    }

    public String getString(String id) {
        return this.values.getString(id);
    }

    public void setBoolean(String id, boolean value) {
        this.values.putBool(id, value);
    }

    public boolean getBoolean(String id) {
        return this.values.getBool(id);
    }

    public MapType getValues() {
        return this.values;
    }

    public BaseType get(String key) {
        return this.values.get(key);
    }

    public Set<String> keys() {
        return this.values.keys();
    }

    public void save() {
        try {
            if (this.file != null)
                IOUtils.writeText(this.file, DataToString.toString(this.toData(), true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            if (this.file != null && this.file.exists()) {
                this.fromData(DataToString.mapFromString(IOUtils.readText(this.file)));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void fromNbt(NbtElement element) {
        MapType map = (MapType) DataStorageUtils.fromNbt(element);

        this.fromData(map);
    }

    public NbtElement toNbt() {
        return DataStorageUtils.toNbt(this.toData());
    }
}