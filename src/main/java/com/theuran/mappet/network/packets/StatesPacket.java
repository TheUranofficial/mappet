package com.theuran.mappet.network.packets;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.states.IStatesProvider;
import com.theuran.mappet.api.states.States;
import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.network.basic.AbstractPacket;
import com.theuran.mappet.network.basic.ClientPacketHandler;
import com.theuran.mappet.network.basic.ServerPacketHandler;
import mchorse.bbs_mod.data.DataStorageUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Map;

public class StatesPacket extends AbstractPacket {
    public Map<String, States> states;

    public StatesPacket() {
    }

    public StatesPacket(Map<String, States> states) {
        this.states = states;
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        buf.writeMap(this.states, PacketByteBuf::writeString, (packet, value) -> DataStorageUtils.writeToPacket(packet, value.values));
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        this.states = buf.readMap(PacketByteBuf::readString, (packet) -> new States(DataStorageUtils.readFromPacket(packet).asMap()));
    }

    public static class ClientHandler implements ClientPacketHandler<StatesPacket> {
        @Environment(EnvType.CLIENT)
        @Override
        public void run(MinecraftClient client, ClientPlayNetworkHandler handler, PacketSender responseSender, StatesPacket packet) {
            MappetClient.getDashboard().statesPanel.states.set(packet.states);
        }
    }

    public static class ServerHandler implements ServerPacketHandler<StatesPacket> {
        @Override
        public void run(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketSender responseSender, StatesPacket packet) {
            Mappet.getStates().set(packet.states.get("Server"));

            for (ServerPlayerEntity serverPlayer : server.getPlayerManager().getPlayerList()) {
                if (packet.states.containsKey(serverPlayer.getGameProfile().getName())) {
                    ((IStatesProvider) serverPlayer).setStates(packet.states.get(serverPlayer.getGameProfile().getName()));
                }
            }
        }
    }
}
