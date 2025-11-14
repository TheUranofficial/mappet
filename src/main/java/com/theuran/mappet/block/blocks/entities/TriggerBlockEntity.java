package com.theuran.mappet.block.blocks.entities;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.scripts.code.ScriptVector;
import com.theuran.mappet.api.triggers.Trigger;
import com.theuran.mappet.block.MappetBlockEntities;
import com.theuran.mappet.block.MappetBlocks;
import mchorse.bbs_mod.data.DataStorageUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TriggerBlockEntity extends BlockEntity {
    private ScriptVector pos1 = new ScriptVector(0, 0, 0);
    private ScriptVector pos2 = new ScriptVector(16, 16, 16);

    private final List<Trigger> triggers = new ArrayList<>();

    public TriggerBlockEntity(BlockPos pos, BlockState state) {
        super(MappetBlockEntities.TRIGGER_BLOCK, pos, state);
    }

    public void setHitbox(ScriptVector pos1, ScriptVector pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        markDirty();
    }

    public void addTrigger(Trigger trigger) {
        this.triggers.add(trigger);
    }

    public void removeTrigger(Trigger trigger) {
        this.triggers.remove(trigger);
    }

    public List<Trigger> getTriggers() {
        return this.triggers;
    }

    public ScriptVector getPos1() {
        return this.pos1;
    }

    public ScriptVector getPos2() {
        return this.pos2;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("x1", (int) this.pos1.x);
        nbt.putInt("y1", (int) this.pos1.y);
        nbt.putInt("z1", (int) this.pos1.z);
        nbt.putInt("x2", (int) this.pos2.x);
        nbt.putInt("y2", (int) this.pos2.y);
        nbt.putInt("z2", (int) this.pos2.z);
        
        for (Trigger trigger : this.triggers) {
            NbtList triggersTag = nbt.getList("triggers", NbtList.COMPOUND_TYPE);

            triggersTag.add(DataStorageUtils.toNbt(trigger.toData()));
        }

        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        this.pos1 = new ScriptVector(nbt.getInt("x1"), nbt.getInt("y1"), nbt.getInt("z1"));
        this.pos2 = new ScriptVector(nbt.getInt("x2"), nbt.getInt("y2"), nbt.getInt("z2"));

        NbtList triggers = nbt.getList("triggers", NbtList.COMPOUND_TYPE);

        for (NbtElement trigger : triggers) {
            this.triggers.add(Mappet.getTriggers().fromData(DataStorageUtils.fromNbt(trigger).asMap()));
        }

        super.readNbt(nbt);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }
}
