package com.theuran.mappet.api.scripts.user;

import com.theuran.mappet.api.scripts.code.entity.ScriptEntity;
import net.minecraft.world.World;

/**
* World interface
*/

public interface IScriptWorld {
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
    World getMinecraftWorld();

    ScriptEntity<?> getEntity(String uuid);
}
