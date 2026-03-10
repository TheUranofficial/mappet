package com.theuran.mappet.client.api.keybinds;

import com.theuran.mappet.api.triggers.Trigger;
import com.theuran.mappet.client.api.scripts.code.ClientScriptEvent;
import com.theuran.mappet.api.keybinds.Keybind;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.packets.keybinds.KeybindsExecuteTriggersPacket;
import com.theuran.mappet.utils.InputUtils;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.util.InputUtil;

import java.util.*;

public class ClientKeybindManager {
    public Map<Keybind, List<Trigger>> keybinds = new HashMap<>();

    public ClientKeybindManager() {
        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            String keyId = "";

            for (Keybind keybinding : this.getKeybindings()) {
                boolean keyPressed = false;

                boolean isPressedModificator = keybinding.mod().getKeycode() == -1;

                if (!isPressedModificator) {
                    isPressedModificator = InputUtil.isKeyPressed(client.getWindow().getHandle(), keybinding.mod().getKeycode());
                }

                if (isPressedModificator) {
                    if (keybinding.type() == Keybind.Type.PRESSED) {
                        if (InputUtils.wasKeyJustPressed(keybinding.keycode())) {
                            keyPressed = true;
                        }
                    } else if (keybinding.type() == Keybind.Type.RELEASED) {
                        if (InputUtils.isKeyReleased(keybinding.keycode())) {
                            keyPressed = true;
                        }
                    }

                }

                if (keyPressed) {
                    keyId = keybinding.id();
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
                    Dispatcher.sendToServer(new KeybindsExecuteTriggersPacket(keyId));
                }
            }
        });
    }

    public Set<Keybind> getKeybindings() {
        return this.keybinds.keySet();
    }

    public Keybind getKeybind(String keybindId) {
        for (Keybind keybind : this.getKeybindings()) {
            if (keybind.id().equals(keybindId)) {
                return keybind;
            }
        }
        return null;
    }

    public List<Trigger> getTriggers(String id) {
        for (Map.Entry<Keybind, List<Trigger>> entry : this.keybinds.entrySet()) {
            if (entry.getKey().id().equals(id)) {
                return entry.getValue();
            }
        }
        return new ArrayList<>();
    }

    public List<Trigger> getTriggers(Keybind keybind) {
        return this.getTriggers(keybind.id());
    }

    public void addKeybind(Keybind keybind) {
        this.keybinds.put(keybind, new ArrayList<>());
    }
}
