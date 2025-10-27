package com.theuran.mappet.client.api.scripts.code.entity;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

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
