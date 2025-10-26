package com.theuran.mappet.api.scripts.code.entity;

import com.theuran.mappet.api.scripts.user.entity.IScriptPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class ScriptPlayer extends ScriptEntity<PlayerEntity> implements IScriptPlayer {
    public ScriptPlayer(ServerPlayerEntity entity) {
        super(entity);
    }

    public PlayerEntity getMinecraftPlayer() {
        return this.entity;
    }
}