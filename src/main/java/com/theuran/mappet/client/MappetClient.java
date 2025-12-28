package com.theuran.mappet.client;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.client.api.keybinds.ClientKeybindManager;
import com.theuran.mappet.client.api.scripts.ClientScriptManager;
import com.theuran.mappet.client.keybinds.MappetKeybinds;
import com.theuran.mappet.client.ui.UIMappetDashboard;
import com.theuran.mappet.utils.InputUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

@Environment(EnvType.CLIENT)
public class MappetClient implements ClientModInitializer {
    public static boolean isMappetModOnServer;

    private static UIMappetDashboard dashboard;

    private static ClientScriptManager scripts;
    private static ClientKeybindManager keybinds;
    private static RenderingHandler handler;

    @Override
    public void onInitializeClient() {
        Mappet.getDispatcher().registerClient();

        ClientEventHandler.init();

        MappetKeybinds.init();

        scripts = new ClientScriptManager();
        keybinds = new ClientKeybindManager();
        handler = new RenderingHandler();

        InputUtils.init();

        HammerRenderer.init();
        TriggerRenderer.init();

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            dashboard = null;

            isMappetModOnServer = false;
            MappetClient.handler.reset();
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!client.isPaused()) {
                handler.update();
            }
        });

        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            handler.render(drawContext, tickDelta);
        });
    }

    public static ClientScriptManager getScripts() {
        return scripts;
    }

    public static ClientKeybindManager getKeybinds() {
        return keybinds;
    }

    public static RenderingHandler getHandler() {
        return handler;
    }

    public static UIMappetDashboard getDashboard() {
        if (dashboard == null)
            dashboard = new UIMappetDashboard();

        return dashboard;
    }
}
