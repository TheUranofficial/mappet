package com.theuran.mappet.network.packets.server;

import com.theuran.mappet.network.basic.AbstractPacket;
import com.theuran.mappet.network.basic.ServerPacketHandler;
import mchorse.bbs_mod.data.DataStorageUtils;
import mchorse.bbs_mod.data.types.MapType;
import mchorse.bbs_mod.utils.repos.RepositoryOperation;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class ManagerDataPacket extends AbstractPacket {
    public int callbackId;
    public RepositoryOperation operation;
    public MapType data;

    public ManagerDataPacket() {}

    public ManagerDataPacket(int callbackId, RepositoryOperation operation, MapType data) {
        this.callbackId = callbackId;
        this.operation = operation;
        this.data = data;
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        DataStorageUtils.writeToPacket(buf, this.data);
        buf.writeInt(this.callbackId);
        buf.writeInt(this.operation.ordinal());
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        this.data = (MapType) DataStorageUtils.readFromPacket(buf);
        this.callbackId = buf.readInt();
        this.operation = RepositoryOperation.values()[buf.readInt()];
    }

    //TODO: not should touch this shit
    public static class ServerHandler implements ServerPacketHandler<ManagerDataPacket> {
        @Override
        public void run(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketSender responseSender, ManagerDataPacket packet) {

        }
    }
}
