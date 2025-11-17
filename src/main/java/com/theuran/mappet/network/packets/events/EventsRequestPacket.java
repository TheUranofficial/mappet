package com.theuran.mappet.network.packets.events;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.events.EventType;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.core.AbstractPacket;
import com.theuran.mappet.network.core.ServerPacketHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;

public class EventsRequestPacket extends AbstractPacket implements ServerPacketHandler {
    @Override
    public void handle(MinecraftServer server, ServerPlayerEntity player) {
        Map<EventType, Integer> events = new HashMap<>();

        Mappet.getEvents().events.forEach((eventType, triggers) -> events.put(eventType, triggers.size()));

        Dispatcher.sendTo(new EventsUpdatePacket(events), player);
    }
}
