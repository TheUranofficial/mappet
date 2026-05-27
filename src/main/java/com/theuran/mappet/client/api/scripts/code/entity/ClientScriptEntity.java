package com.theuran.mappet.client.api.scripts.code.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

@Environment(EnvType.CLIENT)
public class ClientScriptEntity <T extends Entity> {
    protected T entity;

    public static ClientScriptEntity<?> create(Entity entity) {
        if (entity instanceof PlayerEntity) {
            return new ClientScriptPlayer((ClientPlayerEntity) entity);
        } else if (entity != null) {
            return new ClientScriptEntity<>(entity);
        }

        return null;
    }

    protected ClientScriptEntity(T entity) {
        this.entity = entity;
    }

    public T getMinecraftEntity() {
        return entity;
    }
}
