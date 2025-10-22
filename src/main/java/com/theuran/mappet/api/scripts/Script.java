package com.theuran.mappet.api.scripts;

import mchorse.bbs_mod.settings.values.ValueGroup;
import mchorse.bbs_mod.settings.values.ValueString;

public class Script extends ValueGroup {
    private final ValueString content = new ValueString("content", "");

    public Script() {
        super("");
        this.add(content);
    }

    public String getContent() {
        return this.content.get();
    }

    public void setContent(String content) {
        this.content.set(content);
    }
}