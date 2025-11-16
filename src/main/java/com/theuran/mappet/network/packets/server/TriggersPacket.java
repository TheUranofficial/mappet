package com.theuran.mappet.network.packets.server;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.events.EventType;
import com.theuran.mappet.api.triggers.Trigger;
import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.client.ui.triggers.UIEditorTriggersOverlayPanel;
import com.theuran.mappet.network.basic.AbstractPacket;
import com.theuran.mappet.network.basic.ClientPacketHandler;
import com.theuran.mappet.network.basic.MappetByteBuffer;
import com.theuran.mappet.network.basic.ServerPacketHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class TriggersPacket extends AbstractPacket {
    public List<Trigger> triggers;
    public EventType type;

    public TriggersPacket() {}

    public TriggersPacket(List<Trigger> triggers, EventType type) {
        this.triggers = triggers;
        this.type = type;
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

    public static class ClientHandler implements ClientPacketHandler<TriggersPacket> {
        @Environment(EnvType.CLIENT)
        @Override
        public void run(MinecraftClient client, ClientPlayNetworkHandler handler, PacketSender responseSender, TriggersPacket packet) {
            UIEditorTriggersOverlayPanel panel = MappetClient.getDashboard().eventsPanel.panel;

            panel.set(packet.triggers, packet.type);
        }
    }

    public static class ServerHandler implements ServerPacketHandler<TriggersPacket> {
        @Override
        public void run(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketSender responseSender, TriggersPacket packet) {
            Mappet.getEvents().events.put(packet.type, packet.triggers);
        }
    }
}