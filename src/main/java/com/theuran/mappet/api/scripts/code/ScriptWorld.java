package com.theuran.mappet.api.scripts.code;

import com.theuran.mappet.api.scripts.code.entity.ScriptEntity;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.UUID;

public class ScriptWorld {
    private World world;

    public ScriptWorld(World world) {
        this.world = world;
    }

    /**
     * Returns the Minecraft World object associated with this script world
     *
     * <pre>{@code
     * //You can use yarn mappings
     * //All mappings search site https://linkie.shedaniel.dev/
     *
     * c.getWorld().getMinecraftWorld().getWorldChunk(CubeCode.vector(232, 232, 223).toBlockPos());
     * }</pre>
     */
    public World getMinecraftWorld() {
        return this.world;
    }

    public ScriptEntity<?> getEntity(String uuid) {
        return ScriptEntity.create(((ServerWorld) this.world).getEntity(UUID.fromString(uuid)));
    }
}
