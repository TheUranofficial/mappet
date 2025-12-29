package com.theuran.mappet.network.packets.scripts;

import com.caoccao.javet.exceptions.JavetException;
import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.client.api.scripts.code.ClientScriptEvent;
import com.theuran.mappet.network.core.CommonPacket;
import mchorse.bbs_mod.settings.values.core.ValueString;
import mchorse.bbs_mod.settings.values.numeric.ValueBoolean;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ScriptsRunPacket extends CommonPacket {
    private final ValueString script = new ValueString("script", "");
    private final ValueString function = new ValueString("function", "");
    private final ValueString content = new ValueString("content", "");
    private final ValueBoolean update = new ValueBoolean("update", true);

    public ScriptsRunPacket() {
        super();
        this.add(this.script, this.function, this.content, this.update);
    }

    public ScriptsRunPacket(String script, String function, String content) {
        this();
        this.script.set(script);
        this.function.set(function);
        this.content.set(content);
    }

    public ScriptsRunPacket(String script, String function) {
        this();
        this.script.set(script);
        this.function.set(function);
        this.update.set(false);
    }

    @Override
    public void handle(MinecraftServer server, ServerPlayerEntity player) {
        try {
            if (this.update.get())
                Mappet.getScripts().updateLoadedScript(this.script.get(), this.content.get());

            Mappet.getScripts().execute(ScriptEvent.create(this.script.get(), this.function.get(), player, null, player.getServerWorld(), server));
        } catch (JavetException e) {
            String message = e.getLocalizedMessage();

            player.sendMessage(Text.of(message), false);
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void handleClient() {
        try {
            MappetClient.getScripts().execute(ClientScriptEvent.create(this.script.get(), this.function.get(), MinecraftClient.getInstance().player, null, MinecraftClient.getInstance().player.clientWorld));
        } catch (JavetException e) {
            String message = e.getLocalizedMessage();

            MinecraftClient.getInstance().player.sendMessage(Text.of(message), false);
        }
    }
}
