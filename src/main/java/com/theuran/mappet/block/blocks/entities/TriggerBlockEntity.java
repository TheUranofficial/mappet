package com.theuran.mappet.block.blocks.entities;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.scripts.code.ScriptVector;
import com.theuran.mappet.api.triggers.Trigger;
import com.theuran.mappet.block.MappetBlockEntities;
import com.theuran.mappet.event.TriggerBlockEntityUpdateCallback;
import mchorse.bbs_mod.data.DataStorageUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TriggerBlockEntity extends BlockEntity {
    private ScriptVector pos1 = new ScriptVector(0, 0, 0);
    private ScriptVector pos2 = new ScriptVector(16, 16, 16);

    private final List<Trigger> triggersRMB = new ArrayList<>();
    private final List<Trigger> triggersLMB = new ArrayList<>();

    public TriggerBlockEntity(BlockPos pos, BlockState state) {
        super(MappetBlockEntities.TRIGGER_BLOCK, pos, state);
    }

    public void setHitbox(ScriptVector pos1, ScriptVector pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        markDirty();
    }

    public List<Trigger> getTriggersRMB() {
        return this.triggersRMB;
    }

    public List<Trigger> getTriggersLMB() {
        return this.triggersLMB;
    }

    public ScriptVector getPos1() {
        return this.pos1;
    }

    public ScriptVector getPos2() {
        return this.pos2;
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        TriggerBlockEntityUpdateCallback.EVENT.invoker().update(this);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("x1", (int) this.pos1.x);
        nbt.putInt("y1", (int) this.pos1.y);
        nbt.putInt("z1", (int) this.pos1.z);
        nbt.putInt("x2", (int) this.pos2.x);
        nbt.putInt("y2", (int) this.pos2.y);
        nbt.putInt("z2", (int) this.pos2.z);
        
        for (Trigger trigger : this.triggersRMB) {
            NbtList triggersTag = nbt.getList("triggersRMB", NbtList.COMPOUND_TYPE);

            triggersTag.add(DataStorageUtils.toNbt(trigger.toData()));
        }

        for (Trigger trigger : this.triggersLMB) {
            NbtList triggersTag = nbt.getList("triggersLMB", NbtList.COMPOUND_TYPE);

            triggersTag.add(DataStorageUtils.toNbt(trigger.toData()));
        }

        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        this.pos1 = new ScriptVector(nbt.getInt("x1"), nbt.getInt("y1"), nbt.getInt("z1"));
        this.pos2 = new ScriptVector(nbt.getInt("x2"), nbt.getInt("y2"), nbt.getInt("z2"));

        NbtList triggersRMB = nbt.getList("triggersRMB", NbtList.COMPOUND_TYPE);
        NbtList triggersLMB = nbt.getList("triggersLMB", NbtList.COMPOUND_TYPE);

        for (NbtElement trigger : triggersRMB) {
            this.triggersRMB.add(Mappet.getTriggers().fromData(DataStorageUtils.fromNbt(trigger).asMap()));
        }

        for (NbtElement trigger : triggersLMB) {
            this.triggersLMB.add(Mappet.getTriggers().fromData(DataStorageUtils.fromNbt(trigger).asMap()));
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
