package com.theuran.mappet.api.scripts.code.entity;

import com.theuran.mappet.api.scripts.code.mappet.MappetStates;
import com.theuran.mappet.api.scripts.code.bbs.BBSForm;
import com.theuran.mappet.api.states.IStatesProvider;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.packets.server.scripts.RunScriptPacket;
import mchorse.bbs_mod.forms.FormUtils;
import mchorse.bbs_mod.forms.forms.Form;
import mchorse.bbs_mod.morphing.Morph;
import mchorse.bbs_mod.network.ServerNetwork;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ScriptPlayer extends ScriptEntity<ServerPlayerEntity> {
    public ScriptPlayer(ServerPlayerEntity entity) {
        super(entity);
    }

    public ServerPlayerEntity getMinecraftPlayer() {
        return this.entity;
    }

    public MappetStates getStates() {
        return new MappetStates(((IStatesProvider) this.entity).getStates());
    }

    public void setForm(BBSForm scriptForm) {
        Form form = scriptForm.getForm();
        ServerNetwork.sendMorphToTracked(this.getMinecraftPlayer(), form);
        Morph.getMorph(this.getMinecraftPlayer()).setForm(FormUtils.copy(form));
    }

    public BBSForm getForm() {
        return new BBSForm(Morph.getMorph(this.getMinecraftPlayer()).getForm());
    }

    public void executeClientScript(String scriptId, String function) {
        Dispatcher.sendTo(new RunScriptPacket(scriptId, function), this.entity);
    }

    public void send(String message) {
        this.entity.sendMessage(Text.of(message));
    }
}