package com.theuran.mappet.api.huds;

import mchorse.bbs_mod.settings.values.core.ValueList;

public class HUDMorphList extends ValueList<HUDMorph> {
    public HUDMorphList(String id) {
        super(id);
    }

    @Override
    protected HUDMorph create(String id) {
        return new HUDMorph(id);
    }
}
