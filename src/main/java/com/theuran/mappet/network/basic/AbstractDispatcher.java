package com.theuran.mappet.network.basic;

import com.theuran.mappet.Mappet;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractDispatcher {
    public abstract void register();

    @SuppressWarnings("unchecked")
    protected <T extends AbstractPacket, A> void registerPacket(Class<T> packetClass, Class<A> packetHandler, EnvType envType) {
        try {
            T packet = packetClass.getConstructor().newInstance();

            // Только регистрируем если среда выполнения совпадает с типом пакета
            if (FabricLoader.getInstance().getEnvironmentType() != envType) {
                return;
            }

            if (envType == EnvType.CLIENT) {
                // Этот код будет выполняться только на клиенте
                ClientPacketHandler<T> clientHandler = (ClientPacketHandler<T>) packetHandler.getConstructor().newInstance();
                ClientPlayNetworking.registerGlobalReceiver(packet.getId(), ((client, handler, buf, responseSender) -> {
                    packet.fromBytes(buf);
                    client.execute(() -> clientHandler.run(client, handler, responseSender, packet));
                }));
            } else {
                // Этот код будет выполняться только на сервере
                ServerPacketHandler<T> serverHandler = (ServerPacketHandler<T>) packetHandler.getConstructor().newInstance();
                ServerPlayNetworking.registerGlobalReceiver(packet.getId(), ((server, player, handler, buf, responseSender) -> {
                    packet.fromBytes(buf);
                    server.execute(() -> serverHandler.run(server, player, handler, responseSender, packet));
                }));
            }
        } catch (Exception e) {
            Mappet.LOGGER.error("Can't register packet", e);
            throw new RuntimeException(e);
        }
    }
}