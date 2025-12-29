package com.theuran.mappet.utils;

import com.theuran.mappet.api.events.EventType;
import mchorse.bbs_mod.data.types.BaseType;
import mchorse.bbs_mod.data.types.StringType;

public class ValueEventType extends ValueEnum<EventType> {
    public ValueEventType(String id, EventType value) {
        super(id, value);
    }

    @Override
    public BaseType toData() {
        return new StringType(this.value.name());
    }

    @Override
    public void fromData(BaseType type) {
        this.set(EventType.valueOf(type.asString()));
    }
}
