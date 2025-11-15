package com.theuran.mappet.api.triggers;

import mchorse.bbs_mod.settings.values.core.ValueString;

public abstract class StringTrigger extends Trigger {
    public ValueString key = new ValueString("key", "");

    public StringTrigger() {
        this.add(this.key);
    }

    public StringTrigger(String string) {
        this();
        this.key.set(string);
    }

    @Override
    public String asString() {
        if (this.key.get().isEmpty()) {
            return super.asString();
        }

        return this.key.get();
    }
}
