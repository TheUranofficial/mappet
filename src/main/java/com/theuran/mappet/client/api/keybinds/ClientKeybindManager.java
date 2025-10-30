package com.theuran.mappet.client.api.keybinds;

import com.theuran.mappet.api.triggers.ScriptTrigger;
import com.theuran.mappet.api.triggers.Trigger;
import com.theuran.mappet.client.api.scripts.code.ClientScriptEvent;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.packets.server.TriggerKeybindC2SPacket;
import com.theuran.mappet.utils.keys.Key;
import com.theuran.mappet.utils.keys.Keybind;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.*;

public class ClientKeybindManager {
    Map<Keybind, List<Trigger>> keybinds = new HashMap<>();

    public ClientKeybindManager() {
        List<Trigger> triggers = new ArrayList<>();

        ScriptTrigger scriptTrigger = new ScriptTrigger("f", "main");

        scriptTrigger.changeSide();

        triggers.add(scriptTrigger);

        keybinds.put(new Keybind("lox").key(new Key(Key.Type.PRESSED, GLFW.GLFW_KEY_G)), triggers);

        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            String keyId = "";

            for (Keybind keybinding : this.getKeybindings()) {
                boolean allKeysPressed = false;

                for (Key key : keybinding.getKeys()) {
                    if (InputUtil.isKeyPressed(client.getWindow().getHandle(), key.keycode)) {
                        allKeysPressed = true;
                        break;
                    }
                }

                if (allKeysPressed) {
                    keyId = keybinding.getId();
                    break;
                }
            }

            if (!keyId.isEmpty()) {
                boolean isServerPacket = false;
                for (Trigger trigger : this.getTriggers(keyId)) {
                    if (trigger.isServer()) {
                        isServerPacket = true;
                    } else {
                        trigger.execute(ClientScriptEvent.create(client.player, null, client.world));
                    }
                }

                if (isServerPacket) {
                    Dispatcher.sendToServer(new TriggerKeybindC2SPacket(keyId));
                }
            }
        });
    }

    public Set<Keybind> getKeybindings() {
        return this.keybinds.keySet();
    }

    public List<Trigger> getTriggers(String id) {
        for (Map.Entry<Keybind, List<Trigger>> entry : this.keybinds.entrySet()) {
            if (entry.getKey().getId().equals(id)) {
                return entry.getValue();
            }
        }
        return new ArrayList<>();
    }
}
