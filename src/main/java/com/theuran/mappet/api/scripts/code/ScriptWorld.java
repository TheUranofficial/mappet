package com.theuran.mappet.api.scripts.code;

import com.theuran.mappet.api.scripts.code.block.ScriptBlockEntity;
import com.theuran.mappet.api.scripts.code.block.ScriptBlockState;
import com.theuran.mappet.api.scripts.code.entity.ScriptEntity;
import com.theuran.mappet.api.scripts.user.IScriptWorld;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public class ScriptWorld implements IScriptWorld {
    private ServerWorld world;

    public ScriptWorld(ServerWorld world) {
        this.world = world;
    }

    public World getMinecraftWorld() {
        return this.world;
    }

    public ScriptEntity<?> getEntity(String uuid) {
        return ScriptEntity.create(this.world.getEntity(UUID.fromString(uuid)));
    }

    /**
     * Returns the block state at the specified coordinates
     */
    public ScriptBlockState getBlock(int x, int y, int z) {
        return ScriptBlockState.create(this.world.getBlockState(new BlockPos(x, y, z)));
    }

    /**
     * Returns the block state at the specified vector position
     */
    public ScriptBlockState getBlock(ScriptVector vector) {
        return ScriptBlockState.create(this.world.getBlockState(vector.toBlockPos()));
    }

    /**
     * getBlockEntity
     */
    public ScriptBlockEntity getBlockEntity(int x, int y, int z) {
        return new ScriptBlockEntity(this.world.getBlockEntity(new BlockPos(x, y, z)));
    }

    /**
     * getBlockEntity
     */
    public ScriptBlockEntity getBlockEntity(ScriptVector vector) {
        return new ScriptBlockEntity(this.world.getBlockEntity(vector.toBlockPos()));
    }
}
