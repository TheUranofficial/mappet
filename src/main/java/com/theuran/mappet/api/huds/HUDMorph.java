package com.theuran.mappet.api.huds;

import mchorse.bbs_mod.data.DataStorageUtils;
import mchorse.bbs_mod.data.types.BaseType;
import mchorse.bbs_mod.data.types.MapType;
import mchorse.bbs_mod.forms.entities.IEntity;
import mchorse.bbs_mod.forms.entities.StubEntity;
import mchorse.bbs_mod.forms.forms.Form;
import mchorse.bbs_mod.settings.values.ValueBoolean;
import mchorse.bbs_mod.settings.values.ValueFloat;
import mchorse.bbs_mod.settings.values.ValueGroup;
import mchorse.bbs_mod.settings.values.ValueInt;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import org.joml.Vector3f;

public class HUDMorph extends ValueGroup {
    @Environment(EnvType.CLIENT)
    private IEntity entity;
    private int tick = 0;
    private ValueBoolean ortho = new ValueBoolean("ortho", false);
    private ValueFloat orthoX = new ValueFloat("orthoX", 0.0f);
    private ValueFloat orthoY = new ValueFloat("orthoY", 0.0f);
    private ValueInt expire = new ValueInt("expire", 0);
    private Vector3f translate = new Vector3f();
    private Vector3f scale = new Vector3f(1, 1, 1);
    private Vector3f rotate = new Vector3f();
    private Form form;

    public HUDMorph(String id) {
        super(id);
        this.add(this.ortho);
        this.add(this.orthoX);
        this.add(this.orthoY);
        this.add(this.expire);
    }

    @Environment(EnvType.CLIENT)
    public boolean update(boolean allowExpiring) {
        IEntity stubEntity = this.getEntity();

        if (!this.form.getProperties().isEmpty()) {
            this.form.update(stubEntity);
        }

        this.tick++;

        if (!allowExpiring) {
            return false;
        }

        return this.expire.get() > 0 && this.tick >= this.expire.get();
    }

    @Environment(EnvType.CLIENT)
    public IEntity getEntity() {
        if (this.entity == null) {
            this.entity = new StubEntity(MinecraftClient.getInstance().world);
            this.entity.setYaw(0.0f);
            this.entity.setPrevYaw(0.0f);
            this.entity.setPitch(0.0f);
            this.entity.setPrevPitch(0.0f);
            this.entity.setHeadYaw(0.0f);
            this.entity.setPrevHeadYaw(0.0f);
            this.entity.setBodyYaw(0.0f);
            this.entity.setPrevBodyYaw(0.0f);
            this.entity.setPrevPrevBodyYaw(0.0f);
            this.entity.setOnGround(true);
        }

        return this.entity;
    }

    @Override
    public void fromData(BaseType data) {
        super.fromData(data);

        if (data.isMap()) {
            if (data.asMap().has("Form")) {
                this.form.fromData(data.asMap().get("Form").asMap());
            }

            this.translate = DataStorageUtils.vector3fFromData(data.asMap().getList("Translate"));
            this.scale = DataStorageUtils.vector3fFromData(data.asMap().getList("Scale"));
            this.rotate = DataStorageUtils.vector3fFromData(data.asMap().getList("Rotate"));
        }
    }

    @Override
    public BaseType toData() {
        MapType data = super.toData().asMap();

        data.put("Form", this.form.toData());

        if (this.translate.x != 0 || this.translate.y != 0 || this.translate.z != 0) {
            data.put("Translate", DataStorageUtils.vector3fToData(this.translate));
        }

        if (this.scale.x != 1 || this.scale.y != 1 || this.scale.z != 1) {
            data.put("Scale", DataStorageUtils.vector3fToData(this.scale));
        }

        if (this.rotate.x != 0 || this.rotate.y != 0 || this.rotate.z != 0) {
            data.put("Rotate", DataStorageUtils.vector3fToData(this.rotate));
        }

        return data;
    }
}
