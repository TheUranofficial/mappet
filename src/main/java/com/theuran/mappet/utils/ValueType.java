package com.theuran.mappet.utils;

import mchorse.bbs_mod.data.types.BaseType;
import mchorse.bbs_mod.settings.values.base.BaseValueBasic;

public class ValueType extends BaseValueBasic<BaseType> {
    public ValueType(String id, BaseType value) {
        super(id, value);
    }

    @Override
    public BaseType toData() {
        return this.value;
    }

    @Override
    public void fromData(BaseType type) {
        this.set(type);
    }
}
