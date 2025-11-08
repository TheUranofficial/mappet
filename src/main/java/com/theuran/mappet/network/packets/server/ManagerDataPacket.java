package com.theuran.mappet.network.packets.server;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.basic.AbstractPacket;
import com.theuran.mappet.network.basic.ClientPacketHandler;
import com.theuran.mappet.network.basic.ServerPacketHandler;
import mchorse.bbs_mod.data.DataStorageUtils;
import mchorse.bbs_mod.data.types.BaseType;
import mchorse.bbs_mod.data.types.ByteType;
import mchorse.bbs_mod.data.types.ListType;
import mchorse.bbs_mod.data.types.MapType;
import mchorse.bbs_mod.settings.values.core.ValueGroup;
import mchorse.bbs_mod.utils.manager.BaseManager;
import mchorse.bbs_mod.utils.repos.RepositoryOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.function.Consumer;

public class ManagerDataPacket extends AbstractPacket {
    public String manager;
    public RepositoryOperation operation;
    public BaseType data;
    public int callbackId;

    public ManagerDataPacket() {
    }

    public ManagerDataPacket(String manager, RepositoryOperation operation, BaseType data, int callbackId) {
        this.manager = manager;
        this.operation = operation;
        this.data = data;
        this.callbackId = callbackId;
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        DataStorageUtils.writeToPacket(buf, this.data);
        buf.writeString(this.manager);
        buf.writeInt(this.operation.ordinal());
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        this.data = DataStorageUtils.readFromPacket(buf);
        this.manager = buf.readString();
        this.operation = RepositoryOperation.values()[buf.readInt()];
    }

    public static class ServerHandler implements ServerPacketHandler<ManagerDataPacket> {
        @Override
        public void run(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketSender responseSender, ManagerDataPacket packet) {
            BaseManager<?> manager;

            if (packet.manager.equals("script"))
                manager = Mappet.getScripts();
            else if (packet.manager.equals("hud"))
                manager = Mappet.getHuds();
            else if (packet.manager.equals("ui"))
                manager = Mappet.getUIs();
            else
                return;

            MapType data = (MapType) packet.data;

            if (packet.operation == RepositoryOperation.LOAD) {
                String id = data.getString("id");
                ValueGroup group = manager.load(id);

                Dispatcher.sendTo(new ManagerDataPacket(packet.manager, packet.operation, group.toData(), packet.callbackId), player);
            } else if (packet.operation == RepositoryOperation.SAVE) {
                manager.save(data.getString("id"), data.getMap("data"));
            } else if (packet.operation == RepositoryOperation.RENAME) {
                manager.rename(data.getString("from"), data.getString("to"));
            } else if (packet.operation == RepositoryOperation.DELETE) {
                manager.delete(data.getString("id"));
            } else if (packet.operation == RepositoryOperation.KEYS) {
                ListType list = DataStorageUtils.stringListToData(manager.getKeys());

                Dispatcher.sendTo(new ManagerDataPacket(packet.manager, packet.operation, list, packet.callbackId), player);
            } else if (packet.operation == RepositoryOperation.ADD_FOLDER) {
                Dispatcher.sendTo(new ManagerDataPacket(packet.manager, packet.operation, new ByteType(manager.addFolder(data.getString("folder"))), packet.callbackId), player);
            } else if (packet.operation == RepositoryOperation.RENAME_FOLDER) {
                Dispatcher.sendTo(new ManagerDataPacket(packet.manager, packet.operation, new ByteType(manager.renameFolder(data.getString("from"), data.getString("to"))), packet.callbackId), player);
            } else if (packet.operation == RepositoryOperation.DELETE_FOLDER) {
                Dispatcher.sendTo(new ManagerDataPacket(packet.manager, packet.operation, new ByteType(manager.deleteFolder(data.getString("folder"))), packet.callbackId), player);
            }
        }
    }

    public static class ClientHandler implements ClientPacketHandler<ManagerDataPacket> {
        @Environment(EnvType.CLIENT)
        @Override
        public void run(MinecraftClient client, ClientPlayNetworkHandler handler, PacketSender responseSender, ManagerDataPacket packet) {
            Consumer<BaseType> callback = Dispatcher.callbacks.remove(packet.callbackId);

            if (callback != null) {
                callback.accept(packet.data);
            }
        }
    }
}
