package com.theuran.mappet.network;

import com.theuran.mappet.network.basic.AbstractDispatcher;
import com.theuran.mappet.network.basic.AbstractPacket;
import com.theuran.mappet.network.packets.server.*;
import com.theuran.mappet.network.packets.server.scripts.RunScriptPacket;
import com.theuran.mappet.network.packets.server.scripts.SaveScriptC2SPacket;
import com.theuran.mappet.network.packets.server.scripts.SendScriptsS2CPacket;
import mchorse.bbs_mod.data.types.BaseType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Dispatcher {
    @Environment(EnvType.CLIENT)
    public static boolean isMappetModOnServer;
    @Environment(EnvType.CLIENT)
    public static Map<Integer, Consumer<BaseType>> callbacks = new HashMap<>();
    @Environment(EnvType.CLIENT)
    public static int ids = 0;

    private static final AbstractDispatcher DISPATCHER = new AbstractDispatcher() {
        @Override
        public void register() {
            //SERVER
            this.registerPacket(SaveScriptC2SPacket.class, SaveScriptC2SPacket.ServerHandler.class, EnvType.SERVER);
            this.registerPacket(TriggerKeybindC2SPacket.class, TriggerKeybindC2SPacket.ServerHandler.class, EnvType.SERVER);
            this.registerPacket(RequestStatesPacket.class, RequestStatesPacket.ServerHandler.class, EnvType.SERVER);

            //CLIENT
            this.registerPacket(HandshakeS2CPacket.class, HandshakeS2CPacket.ClientHandler.class, EnvType.CLIENT);
            this.registerPacket(SendScriptsS2CPacket.class, SendScriptsS2CPacket.ClientHandler.class, EnvType.CLIENT);

            //COMMON
            this.registerPacket(RunScriptPacket.class, RunScriptPacket.ServerHandler.class, EnvType.SERVER);
            this.registerPacket(RunScriptPacket.class, RunScriptPacket.ClientHandler.class, EnvType.CLIENT);
            this.registerPacket(ManagerDataPacket.class, ManagerDataPacket.ServerHandler.class, EnvType.SERVER);
            this.registerPacket(ManagerDataPacket.class, ManagerDataPacket.ClientHandler.class, EnvType.CLIENT);
            this.registerPacket(TriggerEventPacket.class, TriggerEventPacket.ServerHandler.class, EnvType.SERVER);
            this.registerPacket(TriggerEventPacket.class, TriggerEventPacket.ClientHandler.class, EnvType.CLIENT);
            this.registerPacket(StatesPacket.class, StatesPacket.ClientHandler.class, EnvType.CLIENT);
            this.registerPacket(StatesPacket.class, StatesPacket.ServerHandler.class, EnvType.SERVER);
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
