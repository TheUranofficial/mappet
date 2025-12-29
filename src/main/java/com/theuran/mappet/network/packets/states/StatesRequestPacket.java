package com.theuran.mappet.network.packets.states;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.states.IStatesProvider;
import com.theuran.mappet.api.states.States;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.core.ServerPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;

public class StatesRequestPacket extends ServerPacket {
    @Override
    public void handle(MinecraftServer server, ServerPlayerEntity player) {
        Map<String, States> map = new HashMap<>();

        map.put("Server", Mappet.getStates().get());

        for (ServerPlayerEntity serverPlayer : server.getPlayerManager().getPlayerList()) {
            map.put(serverPlayer.getGameProfile().getName(), ((IStatesProvider) serverPlayer).getStates());
        }

        Dispatcher.sendTo(new StatesUpdatePacket(map), player);
    }
}
