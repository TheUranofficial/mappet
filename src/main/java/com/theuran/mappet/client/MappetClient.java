package com.theuran.mappet.client;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.client.api.keybinds.ClientKeybindManager;
import com.theuran.mappet.client.api.scripts.ClientScriptManager;
import com.theuran.mappet.client.keybinds.MappetKeybinds;
import com.theuran.mappet.client.ui.UIMappetDashboard;
import com.theuran.mappet.utils.InputUtils;
import mchorse.bbs_mod.BBSSettings;
import mchorse.bbs_mod.l10n.L10n;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

@Environment(EnvType.CLIENT)
public class MappetClient implements ClientModInitializer {
    public static boolean isMappetModOnServer;

    private static UIMappetDashboard dashboard;

    private static L10n l10n;

    private static ClientScriptManager scripts;
    private static ClientKeybindManager keybinds;

    @Override
    public void onInitializeClient() {
        Mappet.getDispatcher().registerClient();

        ClientEventHandler.init();

        l10n = new L10n();
        l10n.registerOne((lang) -> Mappet.link("lang/" + lang + ".json"));
        l10n.reload(BBSSettings.language.get(), Mappet.getProvider());

        MappetKeybinds.init();

        scripts = new ClientScriptManager();
        keybinds = new ClientKeybindManager();

        InputUtils.init();

        HammerRenderer.init();
        TriggerRenderer.init();

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            dashboard = null;

            isMappetModOnServer = false;
        });
    }

    public static ClientScriptManager getScripts() {
        return scripts;
    }

    public static ClientKeybindManager getKeybinds() {
        return keybinds;
    }

    public static UIMappetDashboard getDashboard() {
        if (dashboard == null)
            dashboard = new UIMappetDashboard();

        return dashboard;
    }

    public static L10n getL10n() {
        return l10n;
    }
}
