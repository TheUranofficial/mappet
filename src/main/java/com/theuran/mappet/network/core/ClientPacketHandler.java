package com.theuran.mappet.network.core;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public interface ClientPacketHandler {
    @Environment(EnvType.CLIENT)
    void handleClient();
}
