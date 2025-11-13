package com.theuran.mappet.network.packets.server;

import com.theuran.mappet.api.scripts.code.ScriptVector;
import com.theuran.mappet.block.MappetBlockEntities;
import com.theuran.mappet.block.blocks.entities.TriggerBlockEntity;
import com.theuran.mappet.network.basic.AbstractPacket;
import com.theuran.mappet.network.basic.MappetByteBuffer;
import com.theuran.mappet.network.basic.ServerPacketHandler;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class UpdateTriggerBlockC2SPacket extends AbstractPacket {
    BlockPos blockPos;
    ScriptVector pos1;
    ScriptVector pos2;

    public UpdateTriggerBlockC2SPacket() {}

    public UpdateTriggerBlockC2SPacket(BlockPos blockPos, ScriptVector pos1, ScriptVector pos2) {
        this.blockPos = blockPos;
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        buf.writeBlockPos(this.blockPos);
        MappetByteBuffer.writeScriptVector(buf, this.pos1);
        MappetByteBuffer.writeScriptVector(buf, this.pos2);
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        this.blockPos = buf.readBlockPos();
        this.pos1 = MappetByteBuffer.readScriptVector(buf);
        this.pos2 = MappetByteBuffer.readScriptVector(buf);
    }

    public static class ServerHandler implements ServerPacketHandler<UpdateTriggerBlockC2SPacket> {
        @Override
        public void run(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketSender responseSender, UpdateTriggerBlockC2SPacket packet) {
            BlockEntity be = player.getWorld().getBlockEntity(packet.blockPos);
            if (be instanceof TriggerBlockEntity trigger) {
                BlockState oldState = player.getWorld().getBlockState(packet.blockPos);
                trigger.setHitbox(
                        packet.pos1,
                        packet.pos2
                );

                BlockState newState = player.getWorld().getBlockState(packet.blockPos);

                player.getWorld().updateListeners(packet.blockPos, oldState, newState, 0);
            }
        }
    }
}
