package com.theuran.mappet.network.packets.utils;

import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.network.core.ClientPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class HandshakeS2CPacket extends ClientPacket {
    @Environment(EnvType.CLIENT)
    @Override
    public void handleClient() {
        MappetClient.isMappetModOnServer = true;
    }
}