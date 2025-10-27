package com.theuran.mappet.api.triggers;

import com.theuran.mappet.api.scripts.code.ScriptEvent;
import mchorse.bbs_mod.settings.values.core.ValueString;
import net.minecraft.server.MinecraftServer;

public class CommandTrigger extends Trigger {
    public ValueString command = new ValueString("command", "");

    public CommandTrigger() {
        this.add(this.command);
    }

    public CommandTrigger(String command) {
        this();
        this.command.set(command);
    }

    @Override
    public void execute(ScriptEvent scriptEvent) {
        MinecraftServer server = scriptEvent.getServer().getMinecraftServer();

        server.getCommandManager().executeWithPrefix(server.getCommandSource(), this.command.get());
    }

    @Override
    public String getTriggerId() {
        return "command";
    }
}
