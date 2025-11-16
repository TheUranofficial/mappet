package com.theuran.mappet.network.packets.server;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.api.triggers.Trigger;
import com.theuran.mappet.network.basic.AbstractPacket;
import com.theuran.mappet.network.basic.ServerPacketHandler;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class TriggerKeybindC2SPacket extends AbstractPacket {
    public String keybindId;

    public TriggerKeybindC2SPacket() {}

    public TriggerKeybindC2SPacket(String keybindId) {
        this.keybindId = keybindId;
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        buf.writeString(this.keybindId);
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        this.keybindId = buf.readString();
    }

    public static class ServerHandler implements ServerPacketHandler<TriggerKeybindC2SPacket> {
        @Override
        public void run(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketSender responseSender, TriggerKeybindC2SPacket packet) {
            List<Trigger> triggers = Mappet.getKeybinds().getTriggers(packet.keybindId);

            for (Trigger trigger : triggers) {
                if (trigger.isServer()) {
                    trigger.execute(ScriptEvent.create(player, null, player.getServerWorld(), server));
                }
            }
        }
    }
}
