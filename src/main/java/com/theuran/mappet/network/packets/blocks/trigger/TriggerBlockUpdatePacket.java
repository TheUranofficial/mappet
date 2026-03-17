package com.theuran.mappet.network.packets.blocks.trigger;

import com.theuran.mappet.api.scripts.code.ScriptVector;
import com.theuran.mappet.api.triggers.Trigger;
import com.theuran.mappet.block.blocks.entities.TriggerBlockEntity;
import com.theuran.mappet.network.core.ServerPacket;
import com.theuran.mappet.utils.MappetByteBuffer;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class TriggerBlockUpdatePacket extends ServerPacket {
    private BlockPos blockPos;
    private ScriptVector pos1;
    private ScriptVector pos2;
    private boolean isUpdateTriggers = false;
    private List<Trigger> triggersRMB;
    private List<Trigger> triggersLMB;

    public TriggerBlockUpdatePacket() {
        super();
    }

    public TriggerBlockUpdatePacket(BlockPos blockPos, ScriptVector pos1, ScriptVector pos2) {
        this();
        this.blockPos = blockPos;
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    public TriggerBlockUpdatePacket(BlockPos blockPos, ScriptVector pos1, ScriptVector pos2, List<Trigger> triggersRMB, List<Trigger> triggersLMB) {
        this();
        this.blockPos = blockPos;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.isUpdateTriggers = true;
        this.triggersRMB = triggersRMB;
        this.triggersLMB = triggersLMB;
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        buf.writeBlockPos(this.blockPos);
        MappetByteBuffer.writeScriptVector(buf, this.pos1);
        MappetByteBuffer.writeScriptVector(buf, this.pos2);

        buf.writeBoolean(this.isUpdateTriggers);

        if (this.isUpdateTriggers) {
            MappetByteBuffer.writeTriggerList(buf, this.triggersRMB);

            MappetByteBuffer.writeTriggerList(buf, this.triggersLMB);
        }
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        this.blockPos = buf.readBlockPos();
        this.pos1 = MappetByteBuffer.readScriptVector(buf);
        this.pos2 = MappetByteBuffer.readScriptVector(buf);

        this.isUpdateTriggers = buf.readBoolean();

        if (this.isUpdateTriggers) {
            this.triggersRMB = MappetByteBuffer.readTriggerList(buf);
            this.triggersLMB = MappetByteBuffer.readTriggerList(buf);
        }
    }

    @Override
    public void handle(MinecraftServer server, ServerPlayerEntity player) {
        BlockEntity be = player.getWorld().getBlockEntity(this.blockPos);

        if (be instanceof TriggerBlockEntity triggerBlockEntity) {
            BlockState oldState = player.getWorld().getBlockState(this.blockPos);

            triggerBlockEntity.setHitbox(
                    this.pos1,
                    this.pos2
            );

            if (this.triggersRMB != null && this.triggersLMB != null) {
                triggerBlockEntity.getTriggersRMB().clear();
                triggerBlockEntity.getTriggersLMB().clear();

                for (Trigger trigger : this.triggersRMB) {
                    triggerBlockEntity.getTriggersRMB().add(trigger);
                }

                for (Trigger trigger : this.triggersLMB) {
                    triggerBlockEntity.getTriggersLMB().add(trigger);
                }
            }

            BlockState newState = player.getWorld().getBlockState(this.blockPos);

            player.getWorld().updateListeners(this.blockPos, oldState, newState, 0);
        }
    }
}
