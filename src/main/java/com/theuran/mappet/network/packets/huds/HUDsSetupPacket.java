package com.theuran.mappet.network.packets.huds;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.huds.HUDScene;
import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.network.core.ClientPacket;
import com.theuran.mappet.utils.values.ValueType;
import mchorse.bbs_mod.data.types.BaseType;
import mchorse.bbs_mod.data.types.MapType;
import mchorse.bbs_mod.settings.values.core.ValueString;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class HUDsSetupPacket extends ClientPacket {
    private final ValueString id = new ValueString("id", "");
    private final ValueType data = new ValueType("data", null);

    public HUDsSetupPacket() {
        this.add(this.id);
        this.add(this.data);
    }

    public HUDsSetupPacket(String id, BaseType data) {
        this();
        this.id.set(id);
        this.data.set(data);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void handleClient() {
        HUDScene scene = Mappet.getHuds().create(this.id.get(), ((MapType) this.data.get()));

        MappetClient.getHandler().stage.scenes.put(this.id.get(), scene);
    }
}
