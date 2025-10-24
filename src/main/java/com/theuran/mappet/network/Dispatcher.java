package com.theuran.mappet.network;

import com.theuran.mappet.network.basic.AbstractDispatcher;
import com.theuran.mappet.network.basic.AbstractPacket;
import com.theuran.mappet.network.packets.server.RunScriptC2SPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class Dispatcher {
    private static final AbstractDispatcher DISPATCHER = new AbstractDispatcher() {
        @Override
        public void register() {
            this.registerPacket(RunScriptC2SPacket.class, RunScriptC2SPacket.ServerHandler.class, EnvType.SERVER);
        }
    };

    public static void sendTo(AbstractPacket packet, ServerPlayerEntity player) {
        PacketByteBuf buf = packet.buf;

        packet.toBytes(buf);
        ServerPlayNetworking.send(player, packet.getId(), buf);
    }

    public static void sendToAll(AbstractPacket packet, MinecraftServer server) {
        PacketByteBuf buf = packet.buf;

        packet.toBytes(buf);

        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            ServerPlayNetworking.send(player, packet.getId(), buf);
        }
    }

    public static void sendToServer(AbstractPacket packet) {
        PacketByteBuf buf = packet.buf;

        packet.toBytes(buf);
        ClientPlayNetworking.send(packet.getId(), buf);
    }

    public static void register() {
        DISPATCHER.register();
    }
}
