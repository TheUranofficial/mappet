package com.theuran.mappet.network.packets.server;

import com.theuran.mappet.block.blocks.TriggerBlock;
import com.theuran.mappet.network.basic.AbstractPacket;
import com.theuran.mappet.network.basic.ServerPacketHandler;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.BlockState;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class UpdateTriggerBlockPacket extends AbstractPacket {
    TriggerBlock.Hitbox hitbox;
    BlockPos blockPos;

    public UpdateTriggerBlockPacket(TriggerBlock.Hitbox hitbox, BlockPos pos) {
        this.hitbox = hitbox;
        this.blockPos = pos;
    }

    public UpdateTriggerBlockPacket() {}

    @Override
    public void toBytes(PacketByteBuf buf) {
        buf.writeInt((int)this.hitbox.getPos1().x);
        buf.writeInt((int)this.hitbox.getPos1().y);
        buf.writeInt((int)this.hitbox.getPos1().z);

        buf.writeInt((int)this.hitbox.getPos2().x);
        buf.writeInt((int)this.hitbox.getPos2().y);
        buf.writeInt((int)this.hitbox.getPos2().z);

        buf.writeBlockPos(this.blockPos);
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        this.hitbox = TriggerBlock.Hitbox.SLAB.pos(
                buf.readInt(),
                buf.readInt(),
                buf.readInt(),
                buf.readInt(),
                buf.readInt(),
                buf.readInt()
        );

        this.blockPos = buf.readBlockPos();
    }

    public static class ServerHandler implements ServerPacketHandler<UpdateTriggerBlockPacket> {
        @Override
        public void run(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketSender responseSender, UpdateTriggerBlockPacket packet) {
            BlockState state = player.getWorld().getBlockState(packet.blockPos).with(TriggerBlock.HITBOX, packet.hitbox);

            player.getWorld().setBlockState(packet.blockPos, state);
        }
    }
}