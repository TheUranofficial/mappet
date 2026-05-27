package com.theuran.mappet.api.localization;

import mchorse.bbs_mod.data.IMapSerializable;
import mchorse.bbs_mod.data.types.MapType;

public class LocalizationEntry implements IMapSerializable {
    private String id;
    private String text;

    public LocalizationEntry(String key, String value) {
        this.id = key;
        this.text = value;
    }

    public String getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public void toData(MapType data) {
        data.putString("id", this.id);
        data.putString("text", this.text);
    }

    @Override
    public void fromData(MapType data) {
        this.id = data.getString("id");
        this.text = data.getString("text");
    }
}