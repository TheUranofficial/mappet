package com.theuran.mappet.network.packets.scripts;

import com.caoccao.javet.exceptions.JavetException;
import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.client.api.scripts.code.ClientScriptEvent;
import com.theuran.mappet.network.basic.AbstractPacket;
import com.theuran.mappet.network.basic.ClientPacketHandler;
import com.theuran.mappet.network.basic.ServerPacketHandler;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class RunScriptPacket extends AbstractPacket {
    private String script;
    private String function;
    private String content;
    private boolean update;

    public RunScriptPacket() {}

    public RunScriptPacket(String script, String function, String content) {
        this.script = script;
        this.function = function;
        this.content = content;
        this.update = true;
    }

    public RunScriptPacket(String script, String function) {
        this.script = script;
        this.function = function;
        this.content = "";
        this.update = false;
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        buf.writeString(script);
        buf.writeString(function);
        buf.writeString(content);
        buf.writeBoolean(update);
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        this.script = buf.readString();
        this.function = buf.readString();
        this.content = buf.readString();
        this.update = buf.readBoolean();
    }

    public static class ServerHandler implements ServerPacketHandler<RunScriptPacket> {
        @Override
        public void run(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketSender responseSender, RunScriptPacket packet) {
            try {
                if (packet.update)
                    Mappet.getScripts().updateLoadedScript(packet.script, packet.content);

                Mappet.getScripts().execute(ScriptEvent.create(packet.script, packet.function, player, null, player.getServerWorld(), server));
            } catch (JavetException e) {
                String message = e.getLocalizedMessage();

                player.sendMessage(Text.of(message), false);
            }
        }
    }

    public static class ClientHandler implements ClientPacketHandler<RunScriptPacket> {
        @Override
        public void run(MinecraftClient client, ClientPlayNetworkHandler handler, PacketSender responseSender, RunScriptPacket packet) {
            try {
                MappetClient.getScripts().execute(ClientScriptEvent.create(packet.script, packet.function, client.player, null, client.player.clientWorld));
            } catch (JavetException e) {
                String message = e.getLocalizedMessage();

                client.player.sendMessage(Text.of(message), false);
            }
        }
    }
}
