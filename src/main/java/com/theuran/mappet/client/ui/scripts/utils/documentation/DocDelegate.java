package com.theuran.mappet.client.ui.scripts.utils.documentation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class DocDelegate extends DocEntry {
    public DocEntry delegate;

    public DocDelegate(DocEntry delegate) {
        this.delegate = delegate;
    }

    @Override
    public String getName() {
        return "../";
    }

    @Override
    public DocEntry getEntry() {
        return this.delegate;
    }
}
