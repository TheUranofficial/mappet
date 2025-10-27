package com.theuran.mappet.network.packets.server;

import com.theuran.mappet.api.scripts.Script;
import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.network.basic.AbstractPacket;
import com.theuran.mappet.network.basic.ClientPacketHandler;
import com.theuran.mappet.utils.MappetByteBuffer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

import java.util.ArrayList;
import java.util.List;

public class SendScriptsS2CPacket extends AbstractPacket {
    List<Script> scripts = new ArrayList<>();

    public SendScriptsS2CPacket() {

    }

    public SendScriptsS2CPacket(Script... scripts) {
        this.scripts.addAll(List.of(scripts));
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        buf.writeCollection(this.scripts, MappetByteBuffer::writeScript);
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        this.scripts = buf.readList((buffer) -> MappetByteBuffer.readScript(buffer));
    }

    public static class ClientHandler implements ClientPacketHandler<SendScriptsS2CPacket> {
        @Override
        public void run(MinecraftClient client, ClientPlayNetworkHandler handler, PacketSender responseSender, SendScriptsS2CPacket packet) {
            MappetClient.getScripts().addScripts(packet.scripts);
        }
    }
}
