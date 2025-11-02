package com.theuran.mappet.network.packets.server;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.scripts.Script;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.basic.AbstractPacket;
import com.theuran.mappet.network.basic.ServerPacketHandler;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class SaveScriptC2SPacket extends AbstractPacket {
    private String script;
    private String content;
    private boolean isServer;

    public SaveScriptC2SPacket() {}

    public SaveScriptC2SPacket(String script, String content, boolean isServer) {
        this.script = script;
        this.content = content;
        this.isServer = isServer;
    }

    public SaveScriptC2SPacket(Script script) {
        this.script = script.getId();
        this.content = script.getContent();
        this.isServer = script.isServer();
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        buf.writeString(script);
        buf.writeString(content);
        buf.writeBoolean(isServer);
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        this.script = buf.readString();
        this.content = buf.readString();
        this.isServer = buf.readBoolean();
    }

    public static class ServerHandler implements ServerPacketHandler<SaveScriptC2SPacket> {
        @Override
        public void run(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketSender responseSender, SaveScriptC2SPacket packet) {
            Mappet.getScripts().updateLoadedScript(packet.script, packet.content, packet.isServer);

            if (!packet.isServer) {
                Dispatcher.sendTo(new SendScriptsS2CPacket(Mappet.getScripts().getClientScripts()), player);
            }
        }
    }
}