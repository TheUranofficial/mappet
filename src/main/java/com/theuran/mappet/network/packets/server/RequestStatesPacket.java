package com.theuran.mappet.network.packets.server;

import com.theuran.mappet.Mappet;
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

public class RequestStatesPacket extends AbstractPacket {
    @Override
    public void toBytes(PacketByteBuf buf) {
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
    }

    public static class ServerHandler implements ServerPacketHandler<RequestStatesPacket> {
        @Override
        public void run(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketSender responseSender, RequestStatesPacket packet) {
            Map<String, States> map = new HashMap<>();

            map.put("Server", Mappet.getStates().get());

            for (ServerPlayerEntity serverPlayer : server.getPlayerManager().getPlayerList()) {
                map.put(serverPlayer.getGameProfile().getName(), ((IStatesProvider) serverPlayer).getStates());
            }

            Dispatcher.sendTo(new StatesPacket(map), player);
        }
    }
}
