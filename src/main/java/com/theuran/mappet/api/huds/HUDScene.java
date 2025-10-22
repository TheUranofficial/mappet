package com.theuran.mappet.api.huds;

import mchorse.bbs_mod.settings.values.ValueGroup;

public class HUDScene extends ValueGroup {
    private HUDMorphList morphs = new HUDMorphList("morphs");

    public HUDScene() {
        super("");
        this.add(this.morphs);
    }
}
