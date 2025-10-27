package com.theuran.mappet.api.scripts.code.entity;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.scripts.code.bbs.ScriptForm;
import com.theuran.mappet.api.scripts.user.entity.IScriptPlayer;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.packets.server.RunScriptPacket;
import com.theuran.mappet.network.packets.server.SendScriptsS2CPacket;
import mchorse.bbs_mod.forms.FormUtils;
import mchorse.bbs_mod.forms.forms.Form;
import mchorse.bbs_mod.morphing.Morph;
import mchorse.bbs_mod.network.ServerNetwork;
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

    public void sendTo(String scriptId, String function) {
        Dispatcher.sendTo(new SendScriptsS2CPacket(Mappet.getScripts().getScript(scriptId)), this.entity);
        Dispatcher.sendTo(new RunScriptPacket(scriptId, function), this.entity);
    }
}