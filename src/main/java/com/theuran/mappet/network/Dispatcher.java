package com.theuran.mappet.network;

import com.theuran.mappet.network.core.AbstractDispatcher;
import com.theuran.mappet.network.packets.blocks.trigger.TriggerBlockUpdatePacket;
import com.theuran.mappet.network.packets.events.EventsExecuteTriggersPacket;
import com.theuran.mappet.network.packets.events.EventsRequestPacket;
import com.theuran.mappet.network.packets.events.EventsUpdatePacket;
import com.theuran.mappet.network.packets.huds.HUDsSetupPacket;
import com.theuran.mappet.network.packets.keybinds.*;
import com.theuran.mappet.network.packets.scripts.ScriptsRunPacket;
import com.theuran.mappet.network.packets.scripts.ScriptsSavePacket;
import com.theuran.mappet.network.packets.scripts.ScriptsSendPacket;
import com.theuran.mappet.network.packets.states.StatesRequestPacket;
import com.theuran.mappet.network.packets.states.StatesUpdatePacket;
import com.theuran.mappet.network.packets.triggers.TriggersRequestC2SPacket;
import com.theuran.mappet.network.packets.triggers.TriggersSendPacket;
import com.theuran.mappet.network.packets.utils.HandshakeS2CPacket;
import com.theuran.mappet.network.packets.utils.ManagerDataPacket;
import mchorse.bbs_mod.data.types.BaseType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Dispatcher extends AbstractDispatcher {
    @Environment(EnvType.CLIENT)
    public static Map<Integer, Consumer<BaseType>> callbacks = new HashMap<>();
    @Environment(EnvType.CLIENT)
    public static int ids = 0;

    @Override
    public void setup() {
        //Utils
        this.register(HandshakeS2CPacket.class);
        this.register(ManagerDataPacket.class);
        this.register(TriggerBlockUpdatePacket.class);

        //States
        this.register(StatesUpdatePacket.class);
        this.register(StatesRequestPacket.class);

        //Events
        this.register(EventsExecuteTriggersPacket.class);
        this.register(EventsRequestPacket.class);
        this.register(EventsUpdatePacket.class);

        //Triggers
        this.register(TriggersRequestC2SPacket.class);
        this.register(TriggersSendPacket.class);

        //Scripts
        this.register(ScriptsSendPacket.class);
        this.register(ScriptsSavePacket.class);
        this.register(ScriptsRunPacket.class);
        
        //Keybinds
        this.register(KeybindsExecuteTriggersPacket.class);
        this.register(KeybindsRequestPacket.class);
        this.register(KeybindsUpdatePacket.class);
        this.register(KeybindsSetPacket.class);
        this.register(KeybindsSyncPacket.class);

        //Huds
        this.register(HUDsSetupPacket.class);
    }
}