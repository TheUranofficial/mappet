package com.theuran.mappet.client.ui.scripts.utils.documentation;

import mchorse.bbs_mod.data.types.BaseType;
import mchorse.bbs_mod.data.types.MapType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class DocClass extends DocEntry {
    public List<DocMethod> methods = new ArrayList<>();

    @Override
    public List<DocEntry> getEntries() {
        return new ArrayList<>(this.methods);
    }

    public DocMethod getMethod(String name) {
        for (DocMethod method : this.methods) {
            if (method.name.equals(name)) {
                return method;
            }
        }

        return null;
    }

    public void setup() {
        this.methods.removeIf(method -> method.annotations.contains("com.theuran.mappet.utils.DiscardMethod"));

        for (DocMethod method : this.methods) {
            method.parent = this;
        }
    }

    @Override
    public void fromData(MapType data) {
        super.fromData(data);

        if (data.has("methods", BaseType.TYPE_LIST)) {
            for (BaseType type : data.getList("methods")) {
                DocMethod method = new DocMethod();

                method.fromData(type.asMap());
                this.methods.add(method);
            }
        }
    }
}