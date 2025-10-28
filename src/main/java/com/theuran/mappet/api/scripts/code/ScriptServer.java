package com.theuran.mappet.api.scripts.code;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.scripts.code.mappet.MappetStates;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;

public class ScriptServer {
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

    public MappetStates getStates() {
        return new MappetStates(Mappet.getStates().get());
    }
}