package com.theuran.mappet.client.api.scripts.code.entity;

import com.theuran.mappet.client.api.scripts.code.ClientScriptOptions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

public class ClientScriptPlayer extends ClientScriptEntity<ClientPlayerEntity> {
    public ClientScriptPlayer(ClientPlayerEntity entity) {
        super(entity);
    }

    public ClientPlayerEntity getMinecraftPlayer() {
        return this.entity;
    }

    public void send(String message) {
        this.entity.sendMessage(Text.of(MinecraftClient.getInstance().options.getMainArm().getValue().asString()));
    }

    public ClientScriptOptions getOptions() {
        return new ClientScriptOptions();
    }
}
