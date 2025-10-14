package com.theuran.mappet.network;

import com.theuran.mappet.api.states.States;
import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.client.ui.panels.UIServerSettings;
import mchorse.bbs_mod.data.DataStorageUtils;
import mchorse.bbs_mod.data.types.MapType;
import mchorse.bbs_mod.network.ClientNetwork;
import mchorse.bbs_mod.network.ClientPacketCrusher;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;

@Environment(EnvType.CLIENT)
public class MappetClientNetwork extends ClientNetwork {
    private static ClientPacketCrusher crusher = new ClientPacketCrusher();

    public static void resetHandshake() {
        crusher.reset();
    }

    public static void setup() {
        ClientPlayNetworking.registerGlobalReceiver(MappetServerNetwork.CLIENT_STATES, (client, handler, buf, responseSender) -> handleStates(client, buf));
    }

    private static void handleStates(MinecraftClient client, PacketByteBuf buf) {
        States states = new States();
        String target = buf.readString();
        MapType data = (MapType) DataStorageUtils.readFromPacket(buf);

        states.fromData(data);

        client.execute(() -> {
            MappetClient.getDashboard().getPanel(UIServerSettings.class).fillStates(target, data);
        });
    }

    public static void sendStatesRequest(String target) {
        PacketByteBuf byteBuf = PacketByteBufs.create();

        byteBuf.writeString(target);

        ClientPlayNetworking.send(MappetServerNetwork.SERVER_REQUEST_STATES, byteBuf);
    }

    public static void sendStates(String target, States states) {
        PacketByteBuf byteBuf = PacketByteBufs.create();

        byteBuf.writeString(target);
        DataStorageUtils.writeToPacket(byteBuf, states.getValues());

        ClientPlayNetworking.send(MappetServerNetwork.SERVER_STATES, byteBuf);
    }
}
