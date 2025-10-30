package com.theuran.mappet.client.api.keybinds;

import com.theuran.mappet.api.triggers.Trigger;
import com.theuran.mappet.utils.keys.Key;
import com.theuran.mappet.utils.keys.Keybind;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.util.InputUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClientKeybindManager {
    Map<Keybind, List<Trigger>> keybinds = new HashMap<>();

    public ClientKeybindManager() {
        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            for (Keybind keybinding : this.getKeybindings()) {
                boolean isKey = false;
                for (Key key : keybinding.keys) {
                    Key.TEST_KEY.wasPressed()
                }
            }
        });
    }

    public Set<Keybind> getKeybindings() {
        return this.keybinds.keySet();
    }
}
