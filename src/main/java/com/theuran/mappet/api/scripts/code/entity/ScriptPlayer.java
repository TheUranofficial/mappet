package com.theuran.mappet.api.scripts.code.entity;

import com.theuran.mappet.api.scripts.code.ScriptFactory;
import com.theuran.mappet.api.scripts.code.bbs.ScriptForm;
import com.theuran.mappet.api.scripts.user.entity.IScriptPlayer;
import mchorse.bbs_mod.data.DataToString;
import mchorse.bbs_mod.entity.IEntityFormProvider;
import mchorse.bbs_mod.forms.FormUtils;
import mchorse.bbs_mod.forms.forms.Form;
import mchorse.bbs_mod.morphing.IMorphProvider;
import mchorse.bbs_mod.morphing.Morph;
import mchorse.bbs_mod.network.ServerNetwork;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class ScriptPlayer extends ScriptEntity<ServerPlayerEntity> implements IScriptPlayer {
    public ScriptPlayer(ServerPlayerEntity entity) {
        super(entity);
    }

    public ServerPlayerEntity getMinecraftPlayer() {
        return this.entity;
    }

    public void setForm(ScriptForm scriptForm) {
        Form form = scriptForm.getForm();
        ServerNetwork.sendMorphToTracked(this.getMinecraftPlayer(), form);
        Morph.getMorph(this.getMinecraftPlayer()).setForm(FormUtils.copy(form));
    }

    public ScriptForm getForm() {
        return new ScriptForm(Morph.getMorph(this.getMinecraftPlayer()).getForm());
    }
}