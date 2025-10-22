package com.theuran.mappet.network.basic;

import com.theuran.mappet.Mappet;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractDispatcher {
    public abstract void register();

    protected <T extends AbstractPacket, A extends PacketHandler> void registerPacket(Class<T> packetClass, Class<A> packetHandler, EnvType envType) {
        try {
            T packet = packetClass.getConstructor().newInstance();

            if (envType == EnvType.CLIENT) {
                ClientPacketHandler<T> clientHandler = (ClientPacketHandler<T>)packetHandler.getConstructor().newInstance();
                ClientPlayNetworking.registerGlobalReceiver(packet.getId(), ((client, handler, buf, responseSender) -> {
                    packet.fromBytes(buf);

                    client.execute(() -> clientHandler.run(client, handler, responseSender, packet));
                }));
            } else {
                ServerPacketHandler<T> serverHandler = (ServerPacketHandler<T>)packetHandler.getConstructor().newInstance();
                ServerPlayNetworking.registerGlobalReceiver(packet.getId(), ((server, player, handler, buf, responseSender) -> {
                    packet.fromBytes(buf);

                    serverHandler.run(server, player, handler, responseSender, packet);
                }));
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Mappet.LOGGER.error("Can't create empty constructor packet. ", e);
            throw new RuntimeException(e);
        }
    }
}