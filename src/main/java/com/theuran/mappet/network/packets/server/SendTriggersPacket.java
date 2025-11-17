package com.theuran.mappet.network.packets.server;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.events.EventType;
import com.theuran.mappet.api.triggers.Trigger;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.basic.AbstractPacket;
import com.theuran.mappet.network.basic.ClientPacketHandler;
import com.theuran.mappet.network.basic.MappetByteBuffer;
import com.theuran.mappet.network.basic.ServerPacketHandler;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class SendTriggersPacket extends AbstractPacket {
    public EventType type;
    public List<Trigger> triggers;

    public SendTriggersPacket() {}

    public SendTriggersPacket(EventType type, List<Trigger> triggers) {
        this.type = type;
        this.triggers = triggers;
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        MappetByteBuffer.writeTriggerList(buf, this.triggers);
        buf.writeEnumConstant(this.type);
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        this.triggers = MappetByteBuffer.readTriggerList(buf);
        this.type = buf.readEnumConstant(EventType.class);
    }

    public static class ServerHandler implements ServerPacketHandler<SendTriggersPacket> {
        @Override
        public void run(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketSender responseSender, SendTriggersPacket packet) {
            Mappet.getEvents().events.put(packet.type, packet.triggers);

            Dispatcher.sendToAll(new SendTriggersPacket(packet.type, packet.triggers), server);
        }
    }

    public static class ClientHandler implements ClientPacketHandler<SendTriggersPacket> {
        @Override
        public void run(MinecraftClient client, ClientPlayNetworkHandler handler, PacketSender responseSender, SendTriggersPacket packet) {
            Mappet.getEvents().events.put(packet.type, packet.triggers);
        }
    }
}
