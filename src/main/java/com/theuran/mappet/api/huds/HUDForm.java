package com.theuran.mappet.api.huds;

import mchorse.bbs_mod.forms.FormUtilsClient;
import mchorse.bbs_mod.forms.entities.IEntity;
import mchorse.bbs_mod.forms.entities.StubEntity;
import mchorse.bbs_mod.forms.renderers.FormRenderingContext;
import mchorse.bbs_mod.settings.values.core.ValueForm;
import mchorse.bbs_mod.settings.values.core.ValueGroup;
import mchorse.bbs_mod.settings.values.core.ValueTransform;
import mchorse.bbs_mod.settings.values.numeric.ValueInt;
import mchorse.bbs_mod.utils.pose.Transform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.RotationAxis;
import org.joml.Vector3f;

public class HUDForm extends ValueGroup {
    public ValueForm form = new ValueForm("form");
    public ValueInt expire = new ValueInt("expire", 0);
    public ValueTransform transform = new ValueTransform("transform", new Transform());

    @Environment(EnvType.CLIENT)
    private IEntity entity;

    private int tick;

    public HUDForm() {
        super("");

        this.add(this.form);
        this.add(this.expire);
        this.add(this.transform);
    }

    @Environment(EnvType.CLIENT)
    public boolean update(boolean allowExpiring) {
        IEntity entity = this.getEntity();

        if (this.form.get() != null) {
            this.form.get().update(entity);
        }

        entity.update();
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
        }

        return this.entity;
    }

    @Environment(EnvType.CLIENT)
    public void render(FormRenderingContext context) {
        if (this.form.get() == null) {
            return;
        }

        Vector3f translate = this.transform.get().translate;
        Vector3f rotate = this.transform.get().rotate;
        Vector3f rotate2 = this.transform.get().rotate2;
        Vector3f scale = this.transform.get().scale;

        context.stack.push();
        context.stack.translate(translate.x, translate.y, translate.z);
        context.stack.multiply(RotationAxis.POSITIVE_Z.rotation(rotate.z));
        context.stack.multiply(RotationAxis.POSITIVE_Y.rotation(rotate.y));
        context.stack.multiply(RotationAxis.POSITIVE_X.rotation(rotate.x));
        context.stack.multiply(RotationAxis.POSITIVE_Z.rotation(rotate2.z));
        context.stack.multiply(RotationAxis.POSITIVE_Y.rotation(rotate2.y));
        context.stack.multiply(RotationAxis.POSITIVE_X.rotation(rotate2.x));
        context.stack.scale(scale.x, scale.y, scale.z);

        FormUtilsClient.render(this.form.get(), context);

        context.stack.pop();
    }
}
