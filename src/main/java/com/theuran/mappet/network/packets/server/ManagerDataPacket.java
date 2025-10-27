package com.theuran.mappet.network.packets.server;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.basic.AbstractPacket;
import com.theuran.mappet.network.basic.ClientPacketHandler;
import com.theuran.mappet.network.basic.ServerPacketHandler;
import mchorse.bbs_mod.data.DataStorageUtils;
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

//TODO: make this
public class ManagerDataPacket extends AbstractPacket {
    public String manager;
    public RepositoryOperation operation;
    public MapType data;

    public ManagerDataPacket() {
    }

    public ManagerDataPacket(String manager, RepositoryOperation operation, MapType data) {
        this.manager = manager;
        this.operation = operation;
        this.data = data;
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        DataStorageUtils.writeToPacket(buf, this.data);
        buf.writeString(this.manager);
        buf.writeInt(this.operation.ordinal());
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        this.data = (MapType) DataStorageUtils.readFromPacket(buf);
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

            if (packet.operation == RepositoryOperation.LOAD) {
                String id = packet.data.getString("id");
                ValueGroup group = manager.load(id);

                Dispatcher.sendTo(new ManagerDataPacket(packet.manager, packet.operation, group.toData().asMap()), player);
            } else if (packet.operation == RepositoryOperation.SAVE) {
                manager.save(packet.data.getString("id"), packet.data.getMap("data"));
            } else if (packet.operation == RepositoryOperation.RENAME) {
                manager.rename(packet.data.getString("from"), packet.data.getString("to"));
            } else if (packet.operation == RepositoryOperation.DELETE) {
                manager.delete(packet.data.getString("id"));
            }
        }
    }

    public static class ClientHandler implements ClientPacketHandler<ManagerDataPacket> {
        @Environment(EnvType.CLIENT)
        @Override
        public void run(MinecraftClient client, ClientPlayNetworkHandler handler, PacketSender responseSender, ManagerDataPacket packet) {

        }
    }
}
