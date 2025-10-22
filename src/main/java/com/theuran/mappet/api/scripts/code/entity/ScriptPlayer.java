package com.theuran.mappet.api.scripts.code.entity;

import net.minecraft.server.network.ServerPlayerEntity;

public class ScriptPlayer extends ScriptEntity<ServerPlayerEntity> {
    public ScriptPlayer(ServerPlayerEntity entity) {
        super(entity);
    }

    /**
     * Returns the Minecraft player entity associated with this script player
     */
    public ServerPlayerEntity getMinecraftPlayer() {
        return this.entity;
    }
}