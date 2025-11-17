package com.theuran.mappet.network.packets.blocks.trigger;

import com.theuran.mappet.api.scripts.code.ScriptVector;
import com.theuran.mappet.block.blocks.entities.TriggerBlockEntity;
import com.theuran.mappet.utils.MappetByteBuffer;
import com.theuran.mappet.network.core.AbstractPacket;
import com.theuran.mappet.network.core.ServerPacketHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class TriggerBlockUpdatePacket extends AbstractPacket implements ServerPacketHandler {
    private BlockPos blockPos;
    private ScriptVector pos1;
    private ScriptVector pos2;

    public TriggerBlockUpdatePacket() {
        super();
    }

    public TriggerBlockUpdatePacket(BlockPos blockPos, ScriptVector pos1, ScriptVector pos2) {
        this();
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

    @Override
    public void handle(MinecraftServer server, ServerPlayerEntity player) {
        BlockEntity be = player.getWorld().getBlockEntity(this.blockPos);

        if (be instanceof TriggerBlockEntity trigger) {
            BlockState oldState = player.getWorld().getBlockState(this.blockPos);

            trigger.setHitbox(
                    this.pos1,
                    this.pos2
            );

            BlockState newState = player.getWorld().getBlockState(this.blockPos);

            player.getWorld().updateListeners(this.blockPos, oldState, newState, 0);
        }
    }
}
