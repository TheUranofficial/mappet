package com.theuran.mappet.api.states;

import mchorse.bbs_mod.data.DataToString;
import mchorse.bbs_mod.data.IMapSerializable;
import mchorse.bbs_mod.data.types.MapType;
import mchorse.bbs_mod.utils.IOUtils;
import net.minecraft.entity.Entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

//Sorry mchorse but ima lazy for understanding basemanager
//TODO: Rewrite this into basemanager extender
public class States implements IMapSerializable {
    private HashMap<UUID, MapType> entities = new HashMap<>();
    private MapType values = new MapType();

    private final File file;

    public States(File file) {
        this.file = file;
    }

    @Override
    public void toData(MapType map) {
        MapType entities = new MapType();

        this.entities.forEach((uuid, entries) -> {
            entities.put(String.valueOf(uuid), entries);
        });

        map.put("values", this.values);
        map.put("entities", entities);
    }

    @Override
    public void fromData(MapType entries) {
        HashMap<UUID, MapType> entities = new HashMap<>();

        entries.getMap("entities").keys().forEach(key ->
                entities.put(UUID.fromString(key), entries.getMap("entities").getMap(key))
        );

        this.values = entries.getMap("values");
        this.entities = entities;
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

    //Entities states

    public void setNumber(MapType mapType, String id, double value) {
        if (Double.isNaN(value)) return;

        mapType.putDouble(id, value);
    }

    public double getNumber(MapType mapType, String id) {
        return mapType.getDouble(id);
    }

    public void setString(MapType mapType, String id, String value) {
        mapType.putString(id, value);
    }

    public String getString(MapType mapType, String id) {
        return mapType.getString(id);
    }

    public void setBoolean(MapType mapType, String id, boolean value) {
        mapType.putBool(id, value);
    }

    public boolean getBoolean(MapType mapType, String id) {
        return mapType.getBool(id);
    }

    public MapType getValues() {
        return this.values;
    }

    public HashMap<UUID, MapType> getEntities() {
        return this.entities;
    }

    public MapType getEntityStates(Entity entity) {
        return this.entities.computeIfAbsent(entity.getUuid(), uuid -> new MapType());
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