package com.theuran.mappet.client.api.scripts.code;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.world.ClientWorld;

@Environment(EnvType.CLIENT)
public class ClientScriptWorld {
    public ClientWorld world;

    public ClientScriptWorld(ClientWorld world) {
        this.world = world;
    }
}
