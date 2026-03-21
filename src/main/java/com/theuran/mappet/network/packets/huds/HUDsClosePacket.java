package com.theuran.mappet.network.packets.huds;

import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.network.core.ClientPacket;
import mchorse.bbs_mod.settings.values.core.ValueString;
import mchorse.bbs_mod.settings.values.numeric.ValueBoolean;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class HUDsClosePacket extends ClientPacket {
    private final ValueString id = new ValueString("id", "");
    private final ValueBoolean removeAll = new ValueBoolean("removeAll", false);

    public HUDsClosePacket() {
        this.add(this.id);
        this.add(this.removeAll);
    }

    public HUDsClosePacket(String id, boolean removeAll) {
        this();
        this.id.set(id);
        this.removeAll.set(removeAll);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void handleClient() {
        if (this.removeAll.get()) {
            MappetClient.getHandler().stage.scenes.clear();
        } else {
            MappetClient.getHandler().stage.scenes.remove(this.id.get());
        }
    }
}
