package com.theuran.mappet.network.packets.keybinds;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.scripts.code.ScriptEvent;
import com.theuran.mappet.api.triggers.Trigger;
import com.theuran.mappet.network.core.ServerPacket;
import mchorse.bbs_mod.settings.values.core.ValueString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class KeybindsExecuteTriggersPacket extends ServerPacket {
    private final ValueString keybindId = new ValueString("keybindId", "");

    public KeybindsExecuteTriggersPacket() {
        super();
        this.add(this.keybindId);
    }

    public KeybindsExecuteTriggersPacket(String keybindId) {
        this();
        this.keybindId.set(keybindId);
    }

    @Override
    public void handle(MinecraftServer server, ServerPlayerEntity player) {
        List<Trigger> triggers = Mappet.getKeybinds().getTriggers(this.keybindId.get());

        for (Trigger trigger : triggers) {
            if (trigger.isServer()) {
                trigger.execute(ScriptEvent.create(player, null, player.getServerWorld(), server));
            }
        }
    }
}
