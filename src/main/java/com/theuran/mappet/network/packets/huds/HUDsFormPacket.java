package com.theuran.mappet.network.packets.huds;

import com.theuran.mappet.api.huds.HUDForm;
import com.theuran.mappet.api.huds.HUDScene;
import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.network.core.ClientPacket;
import mchorse.bbs_mod.forms.forms.Form;
import mchorse.bbs_mod.settings.values.core.ValueForm;
import mchorse.bbs_mod.settings.values.core.ValueString;
import mchorse.bbs_mod.settings.values.numeric.ValueInt;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class HUDsFormPacket extends ClientPacket {
    private final ValueString id = new ValueString("id", "");
    private final ValueInt index = new ValueInt("index", 0);
    private final ValueForm form = new ValueForm("form");

    public HUDsFormPacket() {
        this.add(this.id);
        this.add(this.index);
        this.add(this.form);
    }

    public HUDsFormPacket(String id, int index, Form form) {
        this();
        this.id.set(id);
        this.index.set(index);
        this.form.set(form);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void handleClient() {
        HUDScene scene = MappetClient.getHandler().stage.scenes.get(this.id.get());

        if (scene != null) {
            ((HUDForm) scene.forms.get(String.valueOf(this.index.get()))).form.set(this.form.get());
        }
    }
}