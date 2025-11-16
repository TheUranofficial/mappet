package com.theuran.mappet.network.packets.server;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.events.EventType;
import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.api.triggers.Trigger;
import com.theuran.mappet.client.api.scripts.code.ClientScriptEvent;
import com.theuran.mappet.network.basic.AbstractPacket;
import com.theuran.mappet.network.basic.ClientPacketHandler;
import com.theuran.mappet.network.basic.MappetByteBuffer;
import com.theuran.mappet.network.basic.ServerPacketHandler;
import mchorse.bbs_mod.data.DataStorageUtils;
import mchorse.bbs_mod.data.types.MapType;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class TriggerEventPacket extends AbstractPacket {
    public EventType eventType;
    public Trigger trigger;

    public ScriptEvent scriptEvent;
    public ClientScriptEvent clientScriptEvent;

    public TriggerEventPacket() {}

    public TriggerEventPacket(EventType eventType, Trigger trigger, ScriptEvent scriptEvent) {
        this.eventType = eventType;
        this.trigger = trigger;
        this.scriptEvent = scriptEvent;
    }

    public TriggerEventPacket(EventType eventType, Trigger trigger, ClientScriptEvent clientScriptEvent) {
        this.eventType = eventType;
        this.trigger = trigger;
        this.clientScriptEvent = clientScriptEvent;
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        buf.writeEnumConstant(this.eventType);
        DataStorageUtils.writeToPacket(buf, this.trigger.toData());

        if (this.scriptEvent != null) {
            buf.writeBoolean(true);
            MappetByteBuffer.writeScriptEvent(buf, this.scriptEvent);
        } else {
            buf.writeBoolean(false);
            MappetByteBuffer.writeClientScriptEvent(buf, this.clientScriptEvent);
        }
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        this.eventType = buf.readEnumConstant(EventType.class);

        MapType data = DataStorageUtils.readFromPacket(buf).asMap();
        this.trigger = Mappet.getTriggers().create(Mappet.link(data.getString("type")));

        this.trigger.fromData(data);

        if (buf.readBoolean()) {
            this.scriptEvent = MappetByteBuffer.readScriptEvent(buf);
        } else {
            this.clientScriptEvent = MappetByteBuffer.readClientScriptEvent(buf);
        }
    }

    public static class ClientHandler implements ClientPacketHandler<TriggerEventPacket> {
        @Override
        public void run(MinecraftClient client, ClientPlayNetworkHandler handler, PacketSender responseSender, TriggerEventPacket packet) {
            Mappet.getEvents().eventClient(packet.eventType, packet.clientScriptEvent);
        }
    }

    public static class ServerHandler implements ServerPacketHandler<TriggerEventPacket> {
        @Override
        public void run(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketSender responseSender, TriggerEventPacket packet) {
            ScriptEvent scriptEvent = ScriptEvent.create(
                    packet.scriptEvent.getScript(),
                    packet.scriptEvent.getFunction(),
                    player.getWorld().getEntityById((Integer) packet.scriptEvent.getValue("__subjectId")),
                    player.getWorld().getEntityById((Integer) packet.scriptEvent.getValue("__objectId")),
                    (ServerWorld) player.getWorld(),
                    server
            );

            scriptEvent.setValues(packet.scriptEvent.getValues());

            Mappet.getEvents().eventServer(packet.eventType, scriptEvent);
        }
    }
}
