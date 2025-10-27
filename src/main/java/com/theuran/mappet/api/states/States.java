package com.theuran.mappet.api.states;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.utils.BaseFileManager;
import mchorse.bbs_mod.data.types.BaseType;
import mchorse.bbs_mod.data.types.MapType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;

import java.io.File;
import java.util.Set;
import java.util.function.Supplier;

public class States extends BaseFileManager {
    private MapType values = new MapType();

    public States(Supplier<File> file) {
        super(file);
    }

    public States() {
        super(() -> null);
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
        return this.values.elements.containsKey(key);
    }

    public void remove(String key) {
        this.values.remove(key);
    }

    public static States getStates(MinecraftServer server, String target) {
        if (target.equals("~"))
            return Mappet.getStates();

        PlayerEntity player = server.getPlayerManager().getPlayer(target);

        if (player instanceof IStatesProvider provider)
            return provider.getStates();

        return null;
    }
}