package com.theuran.mappet.api.scripts.code;

import com.theuran.mappet.api.scripts.user.IScriptServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;

public class ScriptServer implements IScriptServer {
    private MinecraftServer server;

    public ScriptServer(MinecraftServer server) {
        this.server = server;
    }

    public MinecraftServer getMinecraftServer() {
        return this.server;
    }

    public void send(String message) {
        this.server.getPlayerManager().broadcast(Text.of(message), false);
    }
}