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

public class HandshakePacket extends AbstractPacket {
    public HandshakePacket() {}

    @Override
    public void toBytes(PacketByteBuf buf) {}

    @Override
    public void fromBytes(PacketByteBuf buf) {}

    public static class ClientHandler implements ClientPacketHandler<HandshakePacket> {
        @Environment(EnvType.CLIENT)
        @Override
        public void run(MinecraftClient client, ClientPlayNetworkHandler handler, PacketSender responseSender, HandshakePacket packet) {
            Dispatcher.isMappetModOnServer = true;
        }
    }
}
