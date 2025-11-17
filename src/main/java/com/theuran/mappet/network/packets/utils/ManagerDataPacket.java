package com.theuran.mappet.network.packets.utils;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.core.AbstractPacket;
import com.theuran.mappet.network.core.ClientPacketHandler;
import com.theuran.mappet.network.core.ServerPacketHandler;
import com.theuran.mappet.utils.ValueEnum;
import com.theuran.mappet.utils.ValueType;
import mchorse.bbs_mod.data.DataStorageUtils;
import mchorse.bbs_mod.data.types.BaseType;
import mchorse.bbs_mod.data.types.ByteType;
import mchorse.bbs_mod.data.types.ListType;
import mchorse.bbs_mod.data.types.MapType;
import mchorse.bbs_mod.settings.values.core.ValueGroup;
import mchorse.bbs_mod.settings.values.core.ValueString;
import mchorse.bbs_mod.settings.values.numeric.ValueInt;
import mchorse.bbs_mod.utils.manager.BaseManager;
import mchorse.bbs_mod.utils.repos.RepositoryOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.function.Consumer;

public class ManagerDataPacket extends AbstractPacket implements ClientPacketHandler, ServerPacketHandler {
    private final ValueInt callbackId = new ValueInt("callbackId", 0);
    private final ValueString manager = new ValueString("manager", "");
    private final ValueEnum<RepositoryOperation> operation = new ValueEnum<>("operation", null);
    private final ValueType data = new ValueType("data", null);

    public ManagerDataPacket() {
        super();
        this.add(this.callbackId);
        this.add(this.manager);
        this.add(this.operation);
        this.add(this.data);
    }

    public ManagerDataPacket(String manager, RepositoryOperation operation, BaseType data, int callbackId) {
        this();
        this.manager.set(manager);
        this.operation.set(operation);
        this.data.set(data);
        this.callbackId.set(callbackId);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void handleClient() {
        Consumer<BaseType> callback = Dispatcher.callbacks.remove(this.callbackId.get());

        if (callback != null) {
            callback.accept(this.data.get());
        }
    }

    @Override
    public void handle(MinecraftServer server, ServerPlayerEntity player) {
        BaseManager<?> manager;

        manager = switch (this.manager.get()) {
            case "script" -> Mappet.getScripts();
            case "hud" -> Mappet.getHuds();
            case "ui" -> Mappet.getUIs();
            default -> throw new IllegalStateException("Unexpected value: " + this.manager.get());
        };

        MapType data = this.data.get().asMap();

        switch (this.operation.get()) {
            case RepositoryOperation.LOAD -> {
                String id = data.getString("id");
                ValueGroup group = manager.load(id);

                Dispatcher.sendTo(new ManagerDataPacket(this.manager.get(), this.operation.get(), group.toData(), this.callbackId.get()), player);
            }
            case RepositoryOperation.SAVE -> manager.save(data.getString("id"), data.getMap("data"));
            case RepositoryOperation.RENAME -> manager.rename(data.getString("from"), data.getString("to"));
            case RepositoryOperation.DELETE -> manager.delete(data.getString("id"));
            case RepositoryOperation.KEYS -> {
                ListType list = DataStorageUtils.stringListToData(manager.getKeys());

                Dispatcher.sendTo(new ManagerDataPacket(this.manager.get(), this.operation.get(), list, this.callbackId.get()), player);
            }
            case RepositoryOperation.ADD_FOLDER -> Dispatcher.sendTo(new ManagerDataPacket(this.manager.get(), this.operation.get(), new ByteType(manager.addFolder(data.getString("folder"))), this.callbackId.get()), player);
            case RepositoryOperation.RENAME_FOLDER -> Dispatcher.sendTo(new ManagerDataPacket(this.manager.get(), this.operation.get(), new ByteType(manager.renameFolder(data.getString("from"), data.getString("to"))), this.callbackId.get()), player);
            case RepositoryOperation.DELETE_FOLDER -> Dispatcher.sendTo(new ManagerDataPacket(this.manager.get(), this.operation.get(), new ByteType(manager.deleteFolder(data.getString("folder"))), this.callbackId.get()), player);
        }
    }
}
