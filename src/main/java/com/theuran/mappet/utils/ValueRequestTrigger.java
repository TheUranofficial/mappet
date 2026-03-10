package com.theuran.mappet.utils;

import com.theuran.mappet.api.triggers.RequestTrigger;
import mchorse.bbs_mod.data.types.BaseType;
import mchorse.bbs_mod.data.types.StringType;

public class ValueRequestTrigger extends ValueEnum<RequestTrigger> {
    public ValueRequestTrigger(String id, RequestTrigger value) {
        super(id, value);
    }

    @Override
    public BaseType toData() {
        return new StringType(this.value.name());
    }

    @Override
    public void fromData(BaseType type) {
        this.set(RequestTrigger.valueOf(type.asString()));
    }
}
