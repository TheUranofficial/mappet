package com.theuran.mappet.client.ui.scripts.documentation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class DocPackage extends DocEntry {
    @Override
    public String getName() {
        return "../";
    }
}