package com.theuran.mappet.network.packets.scripts;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.core.ServerPacket;
import mchorse.bbs_mod.settings.values.core.ValueString;
import mchorse.bbs_mod.settings.values.numeric.ValueBoolean;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class ScriptsSavePacket extends ServerPacket {
    private final ValueString script = new ValueString("script", "");
    private final ValueString content = new ValueString("content", "");
    private final ValueBoolean isServer = new ValueBoolean("isServer", true);

    public ScriptsSavePacket() {
        super();
        this.add(this.script, this.content, this.isServer);
    }

    public ScriptsSavePacket(String script, String content, boolean isServer) {
        this();
        this.script.set(script);
        this.content.set(content);
        this.isServer.set(isServer);
    }

    @Override
    public void handle(MinecraftServer server, ServerPlayerEntity player) {
        Mappet.getScripts().updateLoadedScript(this.script.get(), this.content.get(), this.isServer.get());

        if (!this.isServer.get()) {
            Dispatcher.sendTo(new ScriptsSendPacket(Mappet.getScripts().getClientScripts()), player);
        }
    }
}
