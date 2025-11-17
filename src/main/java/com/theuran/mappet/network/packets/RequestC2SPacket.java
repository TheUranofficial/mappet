package com.theuran.mappet.network.packets;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.events.EventType;
import com.theuran.mappet.api.states.IStatesProvider;
import com.theuran.mappet.api.states.States;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.basic.AbstractPacket;
import com.theuran.mappet.network.basic.ServerPacketHandler;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;

public class RequestC2SPacket extends AbstractPacket {
    public Type type;

    public RequestC2SPacket() {}

    public RequestC2SPacket(Type type) {
        this.type = type;
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        buf.writeEnumConstant(this.type);
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        this.type = buf.readEnumConstant(Type.class);
    }

    public static class ServerHandler implements ServerPacketHandler<RequestC2SPacket> {
        @Override
        public void run(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketSender responseSender, RequestC2SPacket packet) {
            switch (packet.type) {
                case STATES -> handleStates(server, player);
                case EVENTS -> handleEvents(player);
            }
        }

        private void handleStates(MinecraftServer server, ServerPlayerEntity player) {
            Map<String, States> map = new HashMap<>();

            map.put("Server", Mappet.getStates().get());

            for (ServerPlayerEntity serverPlayer : server.getPlayerManager().getPlayerList()) {
                map.put(serverPlayer.getGameProfile().getName(), ((IStatesProvider) serverPlayer).getStates());
            }

            Dispatcher.sendTo(new StatesPacket(map), player);
        }

        private void handleEvents(ServerPlayerEntity player) {
            Map<EventType, Integer> events = new HashMap<>();

            Mappet.getEvents().events.forEach((eventType, triggers) -> events.put(eventType, triggers.size()));

            Dispatcher.sendTo(new EventsS2CPacket(events), player);
        }
    }

    public enum Type {
        STATES,
        EVENTS
    }
}
