package com.theuran.mappet.client.ui.scripts.documentation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class DocList extends DocEntry {
    public List<DocEntry> entries = new ArrayList<>();

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<DocEntry> getEntries() {
        return this.entries;
    }
}
