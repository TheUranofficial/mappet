package com.theuran.mappet.api.scripts;

import mchorse.bbs_mod.settings.values.ValueBoolean;
import mchorse.bbs_mod.settings.values.ValueGroup;
import mchorse.bbs_mod.settings.values.ValueString;

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