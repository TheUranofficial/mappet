package com.theuran.mappet.api.scripts.code;

import com.theuran.mappet.api.scripts.code.entity.ScriptEntity;
import com.theuran.mappet.api.scripts.user.IScriptWorld;
import net.minecraft.server.world.ServerWorld;
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
}
