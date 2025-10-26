package com.theuran.mappet.api.triggers;

import com.theuran.mappet.api.scripts.code.ScriptEvent;
import mchorse.bbs_mod.data.types.MapType;
import net.minecraft.server.MinecraftServer;

public class CommandTrigger extends Trigger {
    private String command;

    @Override
    public void execute(ScriptEvent scriptEvent) {
        MinecraftServer server = scriptEvent.getServer().getMinecraftServer();
        server.getCommandManager().executeWithPrefix(server.getCommandSource(), this.command);
    }

    @Override
    public String getId() {
        return "Command";
    }

    @Override
    public void toData(MapType mapType) {
        mapType.putString("command", this.command);
    }

    @Override
    public void fromData(MapType entries) {
        this.command = entries.getString("command");
    }
}
