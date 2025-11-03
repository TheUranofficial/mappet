package com.theuran.mappet.api.states;

import com.theuran.mappet.utils.INBTSerializable;
import mchorse.bbs_mod.data.DataStorageUtils;
import mchorse.bbs_mod.data.types.BaseType;
import mchorse.bbs_mod.data.types.MapType;
import net.minecraft.nbt.NbtElement;

import java.util.Set;

public class States implements INBTSerializable {
    public MapType values = new MapType();

    public void setNumber(String id, double value) {
        if (Double.isNaN(value)) return;

        this.values.putDouble(id, value);
    }

    public States() {}

    public States(MapType values) {
        this.values = values;
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

    public BaseType get(String key) {
        return this.values.get(key);
    }

    public void set(String key, BaseType value) {
        this.values.put(key, value);
    }

    public Set<String> keys() {
        return this.values.keys();
    }

    public int size() {
        return this.values.size();
    }

    public boolean has(String key) {
        return this.values.has(key);
    }

    public void remove(String key) {
        this.values.remove(key);
    }

    @Override
    public void fromNbt(NbtElement nbt) {
        this.values = DataStorageUtils.fromNbt(nbt).asMap();
    }

    @Override
    public NbtElement toNbt() {
        return DataStorageUtils.toNbt(this.values);
    }
}