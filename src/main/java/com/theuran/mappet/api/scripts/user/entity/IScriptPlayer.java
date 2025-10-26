package com.theuran.mappet.api.scripts.user.entity;

import net.minecraft.entity.player.PlayerEntity;

/**
 * Player interface
 */

public interface IScriptPlayer extends IScriptEntity {
    PlayerEntity getMinecraftPlayer();
}
