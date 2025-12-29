package com.theuran.mappet.network.packets.triggers;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.events.EventType;
import com.theuran.mappet.api.triggers.Trigger;
import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.network.core.CommonPacket;
import com.theuran.mappet.utils.MappetByteBuffer;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.utils.ValueEventType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class TriggersSendPacket extends CommonPacket {
    private final ValueEventType type = new ValueEventType("type", null);
    private List<Trigger> triggers;

    public TriggersSendPacket() {
        super();
        this.add(this.type);
    }

    public TriggersSendPacket(EventType type, List<Trigger> triggers) {
        this();
        this.type.set(type);
        this.triggers = triggers;
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        super.fromBytes(buf);

        this.triggers = MappetByteBuffer.readTriggerList(buf);
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        super.toBytes(buf);

        MappetByteBuffer.writeTriggerList(buf, this.triggers);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void handleClient() {
        Mappet.getEvents().events.put(this.type.get(), this.triggers);

        if (MappetClient.getDashboard().eventsPanel.panel != null) {
            MappetClient.getDashboard().eventsPanel.panel.set(this.type.get(), this.triggers);
        }
    }

    @Override
    public void handle(MinecraftServer server, ServerPlayerEntity player) {
        Mappet.getEvents().events.put(this.type.get(), this.triggers);

        Dispatcher.sendToAll(new TriggersSendPacket(this.type.get(), this.triggers), server);
    }
}
