package com.theuran.mappet.api.triggers;

import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.client.api.scripts.code.ClientScriptEvent;
import com.theuran.mappet.client.ui.triggers.UIEditorTriggersOverlayPanel;
import com.theuran.mappet.client.ui.triggers.panels.UITriggerPanel;
import com.theuran.mappet.client.ui.triggers.panels.UICommandTriggerPanel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;

public class CommandTrigger extends StringTrigger {
    public CommandTrigger() {
        super();
    }

    public CommandTrigger(String command) {
        this();
        this.key.set(command);
    }

    @Override
    public void execute(ScriptEvent scriptEvent) {
        MinecraftServer server = scriptEvent.getServer().getMinecraftServer();

        server.getCommandManager().executeWithPrefix(server.getCommandSource(), this.key.get());
    }

    @Override
    public void execute(ClientScriptEvent scriptEvent) {
        MinecraftClient.getInstance().player.networkHandler.sendChatCommand(this.key.get());
    }

    @Override
    public String getTriggerId() {
        return "command";
    }

    @Environment(EnvType.CLIENT)
    @Override
    public UITriggerPanel<?> getPanel(UIEditorTriggersOverlayPanel overlayPanel) {
        return new UICommandTriggerPanel(overlayPanel, this);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public String asString() {
        if (!this.key.get().startsWith("/")) {
            return "/" + this.key.get();
        }

        return this.key.get();
    }
}
