package com.theuran.mappet.testnetwowk.core;

import com.theuran.mappet.Mappet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDispatcher {
    protected List<Class<? extends AbstractPacket>> clientPackets = new ArrayList<>();
    protected List<Class<? extends AbstractPacket>> serverPackets = new ArrayList<>();

    public AbstractDispatcher() {
        this.setup();
    }

    protected abstract void setup();

    protected void register(Class<? extends AbstractPacket> packetClass) {
        if (ServerPacketHandler.class.isAssignableFrom(packetClass)) {
            this.serverPackets.add(packetClass);
        }

        if (ClientPacketHandler.class.isAssignableFrom(packetClass)) {
            this.clientPackets.add(packetClass);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractPacket> void register() {
        for (Class<? extends AbstractPacket> serverPacket : this.serverPackets) {
            try {
                T packet = (T) serverPacket.getConstructor().newInstance();

                ServerPlayNetworking.registerGlobalReceiver(packet.createId(), (server, player, handler, buf, responseSender) -> {
                    packet.fromBytes(buf);

                    server.execute(() -> ((ServerPacketHandler) packet).handle(player));
                });
            } catch (Exception e) {
                Mappet.LOGGER.error("Can't register packet", e);
                throw new RuntimeException(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Environment(EnvType.CLIENT)
    public <T extends AbstractPacket> void registerClient() {
        for (Class<? extends AbstractPacket> serverPacket : this.clientPackets) {
            try {
                T packet = (T) serverPacket.getConstructor().newInstance();

                ClientPlayNetworking.registerGlobalReceiver(packet.createId(), (client, handler, buf, responseSender) -> {
                    packet.fromBytes(buf);

                    client.execute(() -> ((ClientPacketHandler) packet).handleClient());
                });
            } catch (Exception e) {
                Mappet.LOGGER.error("Can't register packet", e);
                throw new RuntimeException(e);
            }
        }
    }

    public static void sendTo(AbstractPacket packet, ServerPlayerEntity player) {
        PacketByteBuf buf = PacketByteBufs.create();

        packet.toBytes(buf);

        ServerPlayNetworking.send(player, packet.createId(), buf);
    }

    public static void sendToAll(AbstractPacket packet, MinecraftServer server) {
        PacketByteBuf buf = PacketByteBufs.create();

        packet.toBytes(buf);

        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            ServerPlayNetworking.send(player, packet.createId(), buf);
        }
    }

    public static void sendToServer(AbstractPacket packet) {
        PacketByteBuf buf = PacketByteBufs.create();

        packet.toBytes(buf);

        ClientPlayNetworking.send(packet.createId(), buf);
    }
}
