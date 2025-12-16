package com.theuran.mappet.client.ui.scripts.documentation;

import mchorse.bbs_mod.data.types.MapType;

public class DocParameter extends DocEntry {
    private String type = "";

    public String getType() {
        int index = this.type.lastIndexOf(".");

        if (index < 0) {
            return this.type;
        }

        return this.type.substring(index + 1);
    }

    @Override
    public void fromData(MapType data) {
        super.fromData(data);

        this.type = data.getString("type");
    }
}
