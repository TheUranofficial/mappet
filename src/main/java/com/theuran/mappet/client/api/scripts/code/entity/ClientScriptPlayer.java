package com.theuran.mappet.client.api.scripts.code.entity;

import com.theuran.mappet.client.api.scripts.code.ui.MappetUIBuilder;
import com.theuran.mappet.client.ui.UIMappetBase;
import com.theuran.mappet.client.api.scripts.code.ClientScriptCamera;
import com.theuran.mappet.client.managers.ClientOptionsManager;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.packets.server.RunScriptPacket;
import mchorse.bbs_mod.ui.framework.UIScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.Perspective;
import net.minecraft.text.Text;

import java.util.Map;

public class ClientScriptPlayer extends ClientScriptEntity<ClientPlayerEntity> {
    public ClientScriptPlayer(ClientPlayerEntity entity) {
        super(entity);
    }

    public ClientPlayerEntity getMinecraftPlayer() {
        return this.entity;
    }

    public void send(String message) {
        this.entity.sendMessage(Text.of(message));
    }

    public ClientScriptCamera getCamera() {
        return new ClientScriptCamera();
    }

    public void setOption(String key, Object value) {
        ClientOptionsManager.INSTANCE.set(key, value);
        ClientOptionsManager.INSTANCE.invokeEvent();
    }

    public Object getOption(String key) {
        return ClientOptionsManager.INSTANCE.get(key);
    }

    public Map<String, Object> getAllOptions() {
        return ClientOptionsManager.INSTANCE.map();
    }

    public void setPerspective(int view) {
        MinecraftClient.getInstance().options.setPerspective(Perspective.values()[view % 3]);
    }

    public int getPerspective() {
        return MinecraftClient.getInstance().options.getPerspective().ordinal();
    }

    public boolean hasLicense() {
        return MinecraftClient.getInstance().getSession().getUuidOrNull() != null;
    }

    public void executeServerScript(String name, String function) {
        Dispatcher.sendToServer(new RunScriptPacket(name, function));
    }

    public void openUI(MappetUIBuilder builder) {
        UIScreen.open(new UIMappetBase(builder));
    }
}
