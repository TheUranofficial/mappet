package com.theuran.mappet.network.packets.huds;

import com.theuran.mappet.api.huds.HUDScene;
import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.network.core.ClientPacket;
import mchorse.bbs_mod.settings.values.core.ValueString;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class HUDsSetupPacket extends ClientPacket {
    private final ValueString id = new ValueString("id", "");

    public HUDsSetupPacket() {
        this.add(id);
    }

    public HUDsSetupPacket(String id) {
        this();
        this.id.set(id);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void handleClient() {
        HUDScene scene = MappetClient.getHuds().huds.get(this.id.get());

        MappetClient.getHandler().stage.scenes.put(this.id.get(), scene);
    }
}
