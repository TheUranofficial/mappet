package com.theuran.mappet.network.packets.server;

import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.basic.AbstractPacket;
import com.theuran.mappet.network.basic.ClientPacketHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class HandshakeS2CPacket extends AbstractPacket {
    @Override
    public void toBytes(PacketByteBuf buf) {}

    @Override
    public void fromBytes(PacketByteBuf buf) {}

    public static class ClientHandler implements ClientPacketHandler<HandshakeS2CPacket> {
        @Environment(EnvType.CLIENT)
        @Override
        public void run(MinecraftClient client, ClientPlayNetworkHandler handler, PacketSender responseSender, HandshakeS2CPacket packet) {
            Dispatcher.isMappetModOnServer = true;
        }
    }
}
