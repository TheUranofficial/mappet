package com.theuran.mappet.network.packets.utils;

import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.network.core.AbstractPacket;
import com.theuran.mappet.network.core.ClientPacketHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class HandshakeS2CPacket extends AbstractPacket implements ClientPacketHandler {
    @Environment(EnvType.CLIENT)
    @Override
    public void handleClient() {
        MappetClient.isMappetModOnServer = true;
    }
}