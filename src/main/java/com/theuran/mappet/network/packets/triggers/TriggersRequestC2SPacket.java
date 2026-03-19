package com.theuran.mappet.network.packets.triggers;

import com.theuran.mappet.api.triggers.RequestTrigger;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.core.ServerPacket;
import com.theuran.mappet.utils.values.ValueRequestTrigger;
import mchorse.bbs_mod.settings.values.core.ValueString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class TriggersRequestC2SPacket extends ServerPacket {
    private final ValueRequestTrigger requestTrigger = new ValueRequestTrigger("requestTrigger", null);
    private final ValueString id = new ValueString("id", "");

    public TriggersRequestC2SPacket() {
        super();
        this.add(this.requestTrigger);
        this.add(this.id);
    }

    public TriggersRequestC2SPacket(RequestTrigger type, String id) {
        this();
        this.requestTrigger.set(type);
        this.id.set(id);
    }

    @Override
    public void handle(MinecraftServer server, ServerPlayerEntity player) {
        Dispatcher.sendTo(new TriggersSendPacket(this.requestTrigger.get(), this.id.get()), player);
    }
}
