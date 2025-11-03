package com.theuran.mappet.client;

import com.caoccao.javet.exceptions.JavetException;
import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.scripts.Script;
import com.theuran.mappet.client.api.keybinds.ClientKeybindManager;
import com.theuran.mappet.client.api.scripts.ClientScriptManager;
import com.theuran.mappet.client.api.scripts.code.ClientScriptEvent;
import com.theuran.mappet.client.api.scripts.code.ui.MappetUIBuilder;
import com.theuran.mappet.client.ui.UIMappetBase;
import com.theuran.mappet.client.ui.UIMappetDashboard;
import com.theuran.mappet.client.ui.panels.UIScriptPanel;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.packets.server.RunScriptPacket;
import com.theuran.mappet.utils.InputUtils;
import mchorse.bbs_mod.BBSSettings;
import mchorse.bbs_mod.l10n.L10n;
import mchorse.bbs_mod.ui.framework.UIScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class MappetClient implements ClientModInitializer {
    private static UIMappetDashboard dashboard;

    private static KeyBinding keyDashboard;
    private static KeyBinding keyRunScript;

    private static L10n l10n;

    private static ClientScriptManager scripts;
    private static ClientKeybindManager keybinds;

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
        Dispatcher.register();

        l10n = new L10n();
        l10n.registerOne((lang) -> Mappet.link("lang/" + lang + ".json"));
        l10n.reload(BBSSettings.language.get(), Mappet.getProvider());

        keyDashboard = this.createKey("dashboard", GLFW.GLFW_KEY_EQUAL);
        keyRunScript = this.createKey("runScript", GLFW.GLFW_KEY_F6);

        scripts = new ClientScriptManager();
        keybinds = new ClientKeybindManager();

        InputUtils.init();

        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            while (keyDashboard.wasPressed()) {
                UIScreen.open(MappetClient.getDashboard());
            }
            while (keyRunScript.wasPressed()) {
                ClientPlayerEntity player = MinecraftClient.getInstance().player;
                Script data = dashboard.getPanel(UIScriptPanel.class).getData();

                if (data != null) {
                    Script script = MappetClient.getScripts().getScript(data.getId());

                    if (script != null) {
                        if (script.isServer()) {
                            Dispatcher.sendToServer(new RunScriptPacket(data.getId(), "main", data.getContent()));
                        } else {
                            try {
                                script.execute(ClientScriptEvent.create(data.getId(), "main", player, null, player.clientWorld));
                            } catch (JavetException ignored) {
                            }
                        }
                    }
                }
            }
        });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            dashboard = null;

            Dispatcher.isMappetModOnServer = false;
        });
    }

    public KeyBinding createKey(String id, int key) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding("mappet.key." + id, InputUtil.Type.KEYSYM, key, "mappet.config.title"));
    }

    public static ClientScriptManager getScripts() {
        return scripts;
    }

    public static ClientKeybindManager getKeybinds() {
        return keybinds;
    }
}
