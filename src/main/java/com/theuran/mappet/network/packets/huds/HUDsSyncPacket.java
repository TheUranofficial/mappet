package com.theuran.mappet.network.packets.huds;

import com.theuran.mappet.api.huds.HUDScene;
import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.network.core.ClientPacket;
import mchorse.bbs_mod.data.DataStorageUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.PacketByteBuf;

import java.util.Map;

public class HUDsSyncPacket extends ClientPacket {
    public Map<String, HUDScene> hudScenes;

    public HUDsSyncPacket() {}

    public HUDsSyncPacket(Map<String, HUDScene> hudScenes) {
        this.hudScenes = hudScenes;
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        buf.writeMap(this.hudScenes, PacketByteBuf::writeString, (packet, scene) -> DataStorageUtils.writeToPacket(packet, scene.toData()));
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        buf.readList(DataStorageUtils::readFromPacket);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void handleClient() {
        //MappetClient.getHuds().huds.clear();
        //MappetClient.getHuds().huds.putAll(this.hudScenes);
    }
}
