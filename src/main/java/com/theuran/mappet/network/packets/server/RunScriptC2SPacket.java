package com.theuran.mappet.network.packets.server;

import com.caoccao.javet.exceptions.JavetException;
import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.api.scripts.logger.LogType;
import com.theuran.mappet.network.basic.AbstractPacket;
import com.theuran.mappet.network.basic.ServerPacketHandler;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Date;

public class RunScriptC2SPacket extends AbstractPacket {
    private String script;
    private String function;
    private String content;

    public RunScriptC2SPacket() {}

    public RunScriptC2SPacket(String script, String function, String content) {
        this.script = script;
        this.function = function;
        this.content = content;
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        buf.writeString(script);
        buf.writeString(function);
        buf.writeString(content);
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        this.script = buf.readString();
        this.function = buf.readString();
        this.content = buf.readString();
    }

    public static class ServerHandler implements ServerPacketHandler<RunScriptC2SPacket> {
        @Override
        public void run(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketSender responseSender, RunScriptC2SPacket packet) {
            try {
                Mappet.getScripts().updateLoadedScript(packet.script, packet.content);

                Mappet.getScripts().execute(ScriptEvent.create(packet.script, packet.function, player, null, player.getServerWorld(), server));
            } catch (JavetException e) {
                String message = e.getLocalizedMessage();

                player.sendMessage(Text.of(message), false);
            }
        }
    }
}
