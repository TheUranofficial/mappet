package com.theuran.mappet.utils;

import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.packets.ManagerDataPacket;
import mchorse.bbs_mod.data.types.BaseType;
import mchorse.bbs_mod.data.types.MapType;
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
        this.sendPacketLoad(id, data -> {
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

        this.sendPacket(RepositoryOperation.SAVE, map, -1);
    }

    @Override
    public void rename(String from, String to) {
        MapType map = new MapType();

        map.putString("from", from);
        map.putString("to", to);

        this.sendPacket(RepositoryOperation.RENAME, map, -1);
    }

    @Override
    public void delete(String id) {
        MapType map = new MapType();

        map.putString("id", id);

        this.sendPacket(RepositoryOperation.DELETE, map, -1);
    }

    @Override
    public void requestKeys(Consumer<Collection<String>> consumer) {
        MapType map = new MapType();

        this.sendPacket(RepositoryOperation.KEYS, map, data -> {
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

        this.sendPacket(RepositoryOperation.ADD_FOLDER, map, data -> {
            if (data.isNumeric()) {
                consumer.accept(data.asNumeric().boolValue());
            }
        });
    }

    @Override
    public void deleteFolder(String path, Consumer<Boolean> consumer) {
        MapType mapType = new MapType();

        mapType.putString("folder", path);

        this.sendPacket(RepositoryOperation.DELETE_FOLDER, mapType, data -> {
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

        this.sendPacket(RepositoryOperation.RENAME_FOLDER, mapType, data -> {
            if (data.isNumeric()) {
                consumer.accept(data.asNumeric().boolValue());
            }
        });
    }

    private void sendPacket(RepositoryOperation operation, MapType data, int callbackId) {
        Dispatcher.sendToServer(new ManagerDataPacket(this.getId(), operation, data, callbackId));
    }

    private void sendPacket(RepositoryOperation operation, MapType data, Consumer<BaseType> callback) {
        Dispatcher.callbacks.put(Dispatcher.ids, callback);
        this.sendPacket(operation, data, Dispatcher.ids);
        Dispatcher.ids++;
    }

    private void sendPacketLoad(String id, Consumer<BaseType> callback) {
        MapType data = new MapType();

        data.putString("id", id);

        this.sendPacket(RepositoryOperation.LOAD, data, callback);
    }
}
