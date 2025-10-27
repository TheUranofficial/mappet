package com.theuran.mappet.network;

import com.theuran.mappet.network.basic.AbstractDispatcher;
import com.theuran.mappet.network.basic.AbstractPacket;
import com.theuran.mappet.network.packets.server.HandshakeS2CPacket;
import com.theuran.mappet.network.packets.server.RunScriptPacket;
import com.theuran.mappet.network.packets.server.SaveScriptC2SPacket;
import com.theuran.mappet.network.packets.server.SendScriptsS2CPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class Dispatcher {
    @Environment(EnvType.CLIENT)
    public static boolean isMappetModOnServer;

    private static final AbstractDispatcher DISPATCHER = new AbstractDispatcher() {
        @Override
        public void register() {
            //SERVER
            this.registerPacket(SaveScriptC2SPacket.class, SaveScriptC2SPacket.ServerHandler.class, EnvType.SERVER);

            //CLIENT
            this.registerPacket(HandshakeS2CPacket.class, HandshakeS2CPacket.ClientHandler.class, EnvType.CLIENT);
            this.registerPacket(SendScriptsS2CPacket.class, SendScriptsS2CPacket.ClientHandler.class, EnvType.CLIENT);

            //COMMON
            this.registerPacket(RunScriptPacket.class, RunScriptPacket.ServerHandler.class, EnvType.SERVER);
            this.registerPacket(RunScriptPacket.class, RunScriptPacket.ClientHandler.class, EnvType.CLIENT);

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
