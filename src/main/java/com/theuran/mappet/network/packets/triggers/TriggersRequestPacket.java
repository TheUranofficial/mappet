package com.theuran.mappet.network.packets.triggers;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.events.EventType;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.core.ServerPacket;
import com.theuran.mappet.utils.ValueEventType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class TriggersRequestPacket extends ServerPacket {
    private final ValueEventType type = new ValueEventType("type", null);

    public TriggersRequestPacket() {
        super();
        this.add(this.type);
    }

    public TriggersRequestPacket(EventType type) {
        this();
        this.type.set(type);
    }

    @Override
    public void handle(MinecraftServer server, ServerPlayerEntity player) {
        Dispatcher.sendTo(new TriggersSendPacket(this.type.get(), Mappet.getEvents().getTriggers(this.type.get())), player);
    }
}
