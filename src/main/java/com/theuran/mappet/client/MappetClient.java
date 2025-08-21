package com.theuran.mappet.client;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.client.ui.UIMappetDashboard;
import mchorse.bbs_mod.BBSSettings;
import mchorse.bbs_mod.l10n.L10n;
import mchorse.bbs_mod.ui.framework.UIScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class MappetClient implements ClientModInitializer {
    private static UIMappetDashboard dashboard;

    private static KeyBinding keyDashboard;

    private static L10n l10n;

    public static UIMappetDashboard getDashboard() {
        if (dashboard == null)
            dashboard = new UIMappetDashboard();

        return dashboard;
    }

    public static L10n getL10n() {
        return l10n;
    }

    @Override
    public void onInitializeClient() {
        l10n = new L10n();
        l10n.registerOne((lang) -> Mappet.link("lang/" + lang + ".json"));
        l10n.reload(BBSSettings.language.get(), Mappet.getProvider());

        keyDashboard = this.createKey("dashboard", GLFW.GLFW_KEY_EQUAL);

        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            while (keyDashboard.wasPressed()) {
                UIScreen.open(getDashboard());
            }
        });
    }

    public KeyBinding createKey(String id, int key) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding("key.mappet." + id, InputUtil.Type.KEYSYM, key, "category.mappet.main"));
    }
}
