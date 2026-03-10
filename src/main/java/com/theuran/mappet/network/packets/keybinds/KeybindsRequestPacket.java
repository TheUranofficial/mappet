package com.theuran.mappet.network.packets.keybinds;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.keybinds.Keybind;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.core.ServerPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;

public class KeybindsRequestPacket extends ServerPacket {
    @Override
    public void handle(MinecraftServer server, ServerPlayerEntity player) {
        Map<Keybind, Integer> keybinds = new HashMap<>();

        Mappet.getKeybinds().keybinds.forEach((keybind, triggers) -> keybinds.put(keybind, triggers.size()));

        Dispatcher.sendTo(new KeybindsUpdatePacket(keybinds), player);
    }
}
