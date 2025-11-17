package com.theuran.mappet.client.keybinds;

import com.caoccao.javet.exceptions.JavetException;
import com.theuran.mappet.api.scripts.Script;
import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.client.api.scripts.code.ClientScriptEvent;
import com.theuran.mappet.client.ui.panels.UIScriptPanel;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.packets.scripts.RunScriptPacket;
import mchorse.bbs_mod.ui.framework.UIScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class MappetKeybinds {
    private static final KeyBinding KEY_DASHBOARD = createKey("dashboard", GLFW.GLFW_KEY_EQUAL);
    private static final KeyBinding KEY_RUN_SCRIPT = createKey("runScript", GLFW.GLFW_KEY_F6);

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            while (KEY_DASHBOARD.wasPressed()) {
                UIScreen.open(MappetClient.getDashboard());
            }

            while (KEY_RUN_SCRIPT.wasPressed()) {
                ClientPlayerEntity player = MinecraftClient.getInstance().player;
                Script data = MappetClient.getDashboard().getPanel(UIScriptPanel.class).getData();

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
    }

    private static KeyBinding createKey(String id, int key) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding("mappet.key." + id, InputUtil.Type.KEYSYM, key, "mappet.config.title"));
    }
}
