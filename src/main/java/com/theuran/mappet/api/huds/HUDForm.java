package com.theuran.mappet.api.huds;

import com.mojang.blaze3d.systems.RenderSystem;
import mchorse.bbs_mod.forms.FormUtilsClient;
import mchorse.bbs_mod.forms.entities.IEntity;
import mchorse.bbs_mod.forms.entities.StubEntity;
import mchorse.bbs_mod.forms.renderers.FormRenderingContext;
import mchorse.bbs_mod.settings.values.core.ValueForm;
import mchorse.bbs_mod.settings.values.core.ValueGroup;
import mchorse.bbs_mod.settings.values.core.ValueTransform;
import mchorse.bbs_mod.settings.values.numeric.ValueBoolean;
import mchorse.bbs_mod.settings.values.numeric.ValueFloat;
import mchorse.bbs_mod.settings.values.numeric.ValueInt;
import mchorse.bbs_mod.utils.MatrixStackUtils;
import mchorse.bbs_mod.utils.pose.Transform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

public class HUDForm extends ValueGroup {
    public ValueForm form = new ValueForm("form");
    public ValueBoolean ortho = new ValueBoolean("ortho");
    public ValueFloat orthoX = new ValueFloat("orthoX", 0f);
    public ValueFloat orthoY = new ValueFloat("orthoY", 0f);
    public ValueInt expire = new ValueInt("expire", 0);
    public ValueTransform transform = new ValueTransform("transform", new Transform());

    @Environment(EnvType.CLIENT)
    private IEntity entity;

    private int tick;

    public HUDForm() {
        super("");

        this.add(this.form);
        this.add(this.ortho);
        this.add(this.orthoX);
        this.add(this.orthoY);
        this.add(this.expire);
        this.add(this.transform);
    }

    @Environment(EnvType.CLIENT)
    public boolean update(boolean allowExpiring) {
        IEntity entity = this.getEntity();

        if (this.form.get() != null)
            this.form.get().update(entity);

        entity.setAge(entity.getAge() + 1);
        this.tick++;

        if (!allowExpiring)
            return false;

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
    public void render(FormRenderingContext context, int w, int h) {
        if (this.form.get() == null)
            return;

        if (this.ortho.get()) {
            this.transform.get().translate.x = w * this.orthoX.get() + this.transform.get().translate.x;
            this.transform.get().translate.y = h * this.orthoY.get() + this.transform.get().translate.y;
        }

        context.stack.push();
        MatrixStackUtils.applyTransform(context.stack, this.transform.get());

        FormUtilsClient.render(this.form.get(), context);

        context.stack.pop();
    }
}
