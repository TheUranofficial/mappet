package com.theuran.mappet.api.scripts;

import mchorse.bbs_mod.settings.values.core.ValueGroup;
import mchorse.bbs_mod.settings.values.core.ValueString;
import mchorse.bbs_mod.settings.values.numeric.ValueBoolean;

public class Script extends ValueGroup {
    private final ValueString content = new ValueString("content", "");
    private final ValueBoolean isServer = new ValueBoolean("isServer", true);

    public Script() {
        super("");
        this.add(this.content);
        this.add(this.isServer);
    }

    public String getContent() {
        return this.content.get();
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public boolean isServer() {
        return this.isServer.get();
    }

    public void setServer(boolean isServer) {
        this.isServer.set(isServer);
    }
}