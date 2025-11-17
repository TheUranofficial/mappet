package com.theuran.mappet.network.packets.states;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.states.IStatesProvider;
import com.theuran.mappet.api.states.States;
import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.network.core.AbstractPacket;
import com.theuran.mappet.network.core.ClientPacketHandler;
import com.theuran.mappet.network.core.ServerPacketHandler;
import mchorse.bbs_mod.data.DataStorageUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Map;

public class StatesUpdatePacket extends AbstractPacket implements ClientPacketHandler, ServerPacketHandler {
    private Map<String, States> states;

    public StatesUpdatePacket() {
        super();
    }

    public StatesUpdatePacket(Map<String, States> states) {
        this();
        this.states = states;
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        buf.writeMap(this.states, PacketByteBuf::writeString, (packet, value) -> DataStorageUtils.writeToPacket(packet, value.values));
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        this.states = buf.readMap(PacketByteBuf::readString, (packet) -> new States(DataStorageUtils.readFromPacket(packet).asMap()));
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void handleClient() {
        MappetClient.getDashboard().statesPanel.states.set(this.states);
    }

    @Override
    public void handle(MinecraftServer server, ServerPlayerEntity player) {
        Mappet.getStates().set(this.states.get("Server"));

        for (ServerPlayerEntity serverPlayer : server.getPlayerManager().getPlayerList()) {
            if (this.states.containsKey(serverPlayer.getGameProfile().getName())) {
                ((IStatesProvider) serverPlayer).setStates(this.states.get(serverPlayer.getGameProfile().getName()));
            }
        }
    }
}
