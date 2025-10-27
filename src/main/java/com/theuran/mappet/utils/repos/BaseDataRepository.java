package com.theuran.mappet.utils.repos;

import mchorse.bbs_mod.data.types.BaseType;
import mchorse.bbs_mod.data.types.MapType;
import mchorse.bbs_mod.network.ClientNetwork;
import mchorse.bbs_mod.settings.values.core.ValueGroup;
import mchorse.bbs_mod.utils.repos.IRepository;
import mchorse.bbs_mod.utils.repos.RepositoryOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public abstract class BaseDataRepository <T extends ValueGroup> implements IRepository<T> {
    @Override
    public void load(String id, Consumer<T> consumer) {
        ClientNetwork.sendManagerDataLoad(id, data -> {
            if (data.isMap()) {
                consumer.accept(this.create(id, data.asMap()));
            }
        });
    }

    public abstract String getId();

    @Override
    public void save(String id, MapType data) {
        MapType map = new MapType();

        map.putString("id", id);
        map.put("data", data);

        ClientNetwork.sendManagerData(-1, RepositoryOperation.SAVE, map);
    }

    @Override
    public void rename(String from, String to) {
        MapType map = new MapType();

        map.putString("from", from);
        map.putString("to", to);

        ClientNetwork.sendManagerData(-1, RepositoryOperation.RENAME, map);
    }

    @Override
    public void delete(String id) {
        MapType map = new MapType();

        map.putString("id", id);

        ClientNetwork.sendManagerData(-1, RepositoryOperation.DELETE, map);
    }

    @Override
    public void requestKeys(Consumer<Collection<String>> consumer) {
        MapType map = new MapType();

        ClientNetwork.sendManagerData(RepositoryOperation.KEYS, map, data -> {
            if (data.isList()) {
                List<String> list = new ArrayList<>();

                for (BaseType element : data.asList()) {
                    list.add(element.asString());
                }

                consumer.accept(list);
            }
        });
    }

    @Override
    public File getFolder() {
        return null;
    }

    @Override
    public void addFolder(String path, Consumer<Boolean> consumer) {
        MapType map = new MapType();

        map.putString("folder", path);

        ClientNetwork.sendManagerData(RepositoryOperation.ADD_FOLDER, map, data -> {
            if (data.isNumeric()) {
                consumer.accept(data.asNumeric().boolValue());
            }
        });
    }

    @Override
    public void deleteFolder(String path, Consumer<Boolean> consumer) {
        MapType mapType = new MapType();

        mapType.putString("folder", path);

        ClientNetwork.sendManagerData(RepositoryOperation.DELETE_FOLDER, mapType, (data) -> {
            if (data.isNumeric()) {
                consumer.accept(data.asNumeric().boolValue());
            }
        });
    }

    @Override
    public void renameFolder(String from, String to, Consumer<Boolean> consumer) {
        MapType mapType = new MapType();

        mapType.putString("from", from);
        mapType.putString("to", to);

        ClientNetwork.sendManagerData(RepositoryOperation.RENAME_FOLDER, mapType, (data) -> {
            if (data.isNumeric()) {
                consumer.accept(data.asNumeric().boolValue());
            }
        });
    }
}
