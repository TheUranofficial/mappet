package com.theuran.mappet.block.blocks.entities;

import com.theuran.mappet.api.scripts.code.ScriptVector;
import com.theuran.mappet.block.MappetBlockEntities;
import com.theuran.mappet.block.MappetBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class TriggerBlockEntity extends BlockEntity {
    private ScriptVector pos1 = new ScriptVector(0, 0, 0);
    private ScriptVector pos2 = new ScriptVector(16, 16, 16);

    public TriggerBlockEntity(BlockPos pos, BlockState state) {
        super(MappetBlockEntities.TRIGGER_BLOCK, pos, state);
    }

    public void setHitbox(ScriptVector pos1, ScriptVector pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        markDirty();
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
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        this.pos1 = new ScriptVector(nbt.getInt("x1"), nbt.getInt("y1"), nbt.getInt("z1"));
        this.pos2 = new ScriptVector(nbt.getInt("x2"), nbt.getInt("y2"), nbt.getInt("z2"));

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
