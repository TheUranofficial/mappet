package com.theuran.mappet.api.scripts.user;

import net.minecraft.server.MinecraftServer;

/**
* Server interface
*/

public interface IScriptServer {
    MinecraftServer getMinecraftServer();

    void send(String message);
}
