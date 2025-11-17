package com.theuran.mappet.network.packets;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.events.EventType;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.basic.AbstractPacket;
import com.theuran.mappet.network.basic.ServerPacketHandler;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class RequestTriggersPacket extends AbstractPacket {
    public EventType type;

    public RequestTriggersPacket() {}

    public RequestTriggersPacket(EventType type) {
        this.type = type;
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        buf.writeEnumConstant(this.type);
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        this.type = buf.readEnumConstant(EventType.class);
    }

    public static class ServerHandler implements ServerPacketHandler<RequestTriggersPacket> {
        @Override
        public void run(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketSender responseSender, RequestTriggersPacket packet) {
            Dispatcher.sendTo(new SendTriggersPacket(packet.type, Mappet.getEvents().getTriggers(packet.type)), player);
        }
    }
}
