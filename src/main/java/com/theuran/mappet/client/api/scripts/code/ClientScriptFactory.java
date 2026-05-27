package com.theuran.mappet.client.api.scripts.code;

import com.theuran.mappet.client.api.scripts.code.ui.MappetUIBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ClientScriptFactory {
    public MappetUIBuilder createUI() {
        return new MappetUIBuilder();
    }
}
