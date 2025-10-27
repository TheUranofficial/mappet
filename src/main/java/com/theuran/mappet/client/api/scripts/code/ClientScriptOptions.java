package com.theuran.mappet.client.api.scripts.code;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.util.Arm;

public class ClientScriptOptions {
    GameOptions options;

    public ClientScriptOptions() {
        options = MinecraftClient.getInstance().options;
    }
    public String getArm() {
        return options.getMainArm().getValue().name();
    }

    public void setArm(String arm) {
        options.getMainArm().setValue(Arm.valueOf(arm));
    }
}
