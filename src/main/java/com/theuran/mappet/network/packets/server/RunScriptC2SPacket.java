package com.theuran.mappet.network.packets.server;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.network.basic.AbstractPacket;
import com.theuran.mappet.network.basic.ServerPacketHandler;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class RunScriptC2SPacket extends AbstractPacket {
    private String script;
    private String function;

    public RunScriptC2SPacket() {}

    public RunScriptC2SPacket(String script, String function) {
        this.script = script;
        this.function = function;
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        buf.writeString(script);
        buf.writeString(function);
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        this.script = buf.readString();
        this.function = buf.readString();
    }

    public static class ServerHandler implements ServerPacketHandler<RunScriptC2SPacket> {
        @Override
        public void run(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketSender responseSender, RunScriptC2SPacket packet) {
            Mappet.getScripts().runScript(ScriptEvent.create(packet.script, packet.function, player, null, player.getServerWorld(), server));
        }
    }
}
