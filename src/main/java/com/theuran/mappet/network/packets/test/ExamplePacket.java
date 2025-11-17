package com.theuran.mappet.network.packets.test;

import com.theuran.mappet.network.core.AbstractPacket;
import com.theuran.mappet.network.core.ClientPacketHandler;
import mchorse.bbs_mod.settings.values.core.ValueString;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

//Example packet for my networking system
public class ExamplePacket extends AbstractPacket implements ClientPacketHandler {
    public ValueString message = new ValueString("string", "");

    public ExamplePacket() {
        super();
        this.add(this.message);
    }

    public ExamplePacket(String message) {
        this();
        this.message.set(message);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void handleClient() {
        MinecraftClient.getInstance().player.sendMessage(Text.of(this.message.get()));
    }
}
