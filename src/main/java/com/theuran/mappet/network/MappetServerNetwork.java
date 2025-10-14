package com.theuran.mappet.network;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.states.States;
import mchorse.bbs_mod.data.DataStorageUtils;
import mchorse.bbs_mod.data.types.MapType;
import mchorse.bbs_mod.network.ServerNetwork;
import mchorse.bbs_mod.network.ServerPacketCrusher;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.core.jmx.Server;

public class MappetServerNetwork extends ServerNetwork {
    public static final Identifier CLIENT_STATES = new Identifier(Mappet.MOD_ID, "c1");

    public static final Identifier SERVER_REQUEST_STATES = new Identifier(Mappet.MOD_ID, "s1");
    public static final Identifier SERVER_STATES = new Identifier(Mappet.MOD_ID, "s2");

    private static ServerPacketCrusher crusher = new ServerPacketCrusher();

    public static void reset() {
        crusher.reset();
    }

    public static void setup() {
        ServerPlayNetworking.registerGlobalReceiver(SERVER_REQUEST_STATES, ((server, player, handler, buf, responseSender) -> handleRequestStates(server, player, buf)));
        ServerPlayNetworking.registerGlobalReceiver(SERVER_STATES, (server, player, handler, buf, responseSender) -> handleStates(server, player, buf));
    }

    private static void handleRequestStates(MinecraftServer server, ServerPlayerEntity player, PacketByteBuf buf) {
        String target = buf.readString();

        server.execute(() -> {
            States states = States.getStates(server, target);

            if (states != null) {
                PacketByteBuf byteBuf = PacketByteBufs.create();

                byteBuf.writeString(target);
                DataStorageUtils.writeToPacket(byteBuf, states.toData());

                ServerPlayNetworking.send(player, CLIENT_STATES, byteBuf);
            }
        });
    }

    private static void handleStates(MinecraftServer server, ServerPlayerEntity player, PacketByteBuf buf) {
        String target = buf.readString();

        server.execute(() -> {
            if (!player.hasPermissionLevel(2))
                return;

            States states = States.getStates(server, target);

            if (states != null) {
                MapType mapType = (MapType) DataStorageUtils.readFromPacket(buf);

                states.fromData(mapType);
            }
        });
    }
}
