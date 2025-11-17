package com.theuran.mappet.client.api.keybinds;

import com.theuran.mappet.api.triggers.Trigger;
import com.theuran.mappet.client.api.scripts.code.ClientScriptEvent;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.packets.TriggerKeybindC2SPacket;
import com.theuran.mappet.api.keybinds.Keybind;
import com.theuran.mappet.utils.InputUtils;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.util.InputUtil;

import java.util.*;

public class ClientKeybindManager {
    Map<Keybind, List<Trigger>> keybinds = new HashMap<>();

    public ClientKeybindManager() {
        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            String keyId = "";

            for (Keybind keybinding : this.getKeybindings()) {
                boolean keyPressed = false;

                boolean isPressedModificator = keybinding.getMod().getKeycode() == -1;

                if (!isPressedModificator) {
                    isPressedModificator = InputUtil.isKeyPressed(client.getWindow().getHandle(), keybinding.getMod().getKeycode());
                }

                if (isPressedModificator) {
                    if (keybinding.getType() == Keybind.Type.PRESSED) {
                        if (InputUtils.wasKeyJustPressed(keybinding.getKeycode())) {
                            keyPressed = true;
                        }
                    } else if (keybinding.getType() == Keybind.Type.RELEASED) {
                        if (InputUtils.isKeyReleased(keybinding.getKeycode())) {
                            keyPressed = true;
                        }
                    }

                }

                if (keyPressed) {
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
