package com.theuran.mappet.network.packets.huds;

import com.theuran.mappet.api.huds.HUDScene;
import com.theuran.mappet.network.core.CommonPacket;
import mchorse.bbs_mod.data.DataStorageUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class HUDsSyncPacket extends CommonPacket {
    public List<HUDScene> hudScenes;

    public HUDsSyncPacket() {

    }

    public HUDsSyncPacket(List<HUDScene> hudScenes) {
        this.hudScenes = hudScenes;
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        buf.writeCollection(this.hudScenes, (buffer, hudScene) -> {
            DataStorageUtils.writeToPacket(buffer, hudScene.toData());
        });
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        buf.readList(DataStorageUtils::readFromPacket);
    }

    @Override
    public void handle(MinecraftServer server, ServerPlayerEntity player) {

    }

    @Override
    @Environment(EnvType.CLIENT)
    public void handleClient() {

    }
}
