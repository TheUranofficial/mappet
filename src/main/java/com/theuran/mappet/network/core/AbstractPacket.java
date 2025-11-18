package com.theuran.mappet.network.core;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.utils.IByteBufSerializable;
import mchorse.bbs_mod.data.DataStorageUtils;
import mchorse.bbs_mod.settings.values.base.BaseValue;
import mchorse.bbs_mod.settings.values.core.ValueGroup;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public abstract class AbstractPacket extends ValueGroup implements IByteBufSerializable {
    public AbstractPacket() {
        super("");

        this.setId(this.createId().getPath());
    }

    public void add(BaseValue... values) {
        for (BaseValue value : values) {
            this.add(value);
        }
    }

    public Identifier createId() {
        return Identifier.of(Mappet.MOD_ID, this.getClass().getSimpleName().toLowerCase());
    }

    @Override
    public void toBytes(PacketByteBuf buf) {
        for (BaseValue value : this.getAll()) {
            DataStorageUtils.writeToPacket(buf, value.toData());
        }
    }

    @Override
    public void fromBytes(PacketByteBuf buf) {
        for (BaseValue value : this.getAll()) {
            value.fromData(DataStorageUtils.readFromPacket(buf));
        }
    }
}
