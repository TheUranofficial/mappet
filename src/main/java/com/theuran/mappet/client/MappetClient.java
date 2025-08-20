package com.theuran.mappet.client;

import com.theuran.mappet.client.ui.UIMappetDashboard;
import mchorse.bbs_mod.BBSMod;
import mchorse.bbs_mod.BBSModClient;
import mchorse.bbs_mod.l10n.L10n;
import mchorse.bbs_mod.resources.Link;
import mchorse.bbs_mod.ui.framework.UIScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.Collections;

public class MappetClient implements ClientModInitializer {
    private static UIMappetDashboard dashboard;

    private static KeyBinding keyDashboard;

    private static L10n l10n;

    public static UIMappetDashboard getDashboard() {
        if (dashboard == null)
            dashboard = new UIMappetDashboard();

        return dashboard;
    }

    @Override
    public void onInitializeClient() {
        l10n = new L10n();
        l10n.register((lang) -> Collections.singletonList(Link.assets("strings/" + lang + ".json")));
        l10n.reload();

        keyDashboard = this.createKey("dashboard", GLFW.GLFW_KEY_EQUAL);

        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            while (keyDashboard.wasPressed()) {
                UIScreen.open(getDashboard());
                client.player.sendMessage(Text.of(l10n.getStrings().toString()));
            }
        });
    }

    public KeyBinding createKey(String id, int key) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding("key.mappet." + id, InputUtil.Type.KEYSYM, key, "category.mappet.main"));
    }
}
