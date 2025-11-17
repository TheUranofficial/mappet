package com.theuran.mappet.network.core;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public interface ServerPacketHandler {
    void handle(MinecraftServer server, ServerPlayerEntity player);
}
