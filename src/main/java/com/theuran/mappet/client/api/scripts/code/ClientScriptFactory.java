package com.theuran.mappet.client.api.scripts.code;

import com.theuran.mappet.client.api.scripts.code.ui.MappetUIBuilder;

public class ClientScriptFactory {
    public MappetUIBuilder createUI() {
        return new MappetUIBuilder();
    }
}
