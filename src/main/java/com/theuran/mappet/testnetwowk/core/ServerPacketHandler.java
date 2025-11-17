package com.theuran.mappet.testnetwowk.core;

import net.minecraft.server.network.ServerPlayerEntity;

public interface ServerPacketHandler {
    void handle(ServerPlayerEntity player);
}
