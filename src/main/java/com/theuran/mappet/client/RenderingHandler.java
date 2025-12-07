package com.theuran.mappet.client;

import com.theuran.mappet.api.huds.HUDStage;
import mchorse.bbs_mod.forms.entities.StubEntity;
import mchorse.bbs_mod.forms.renderers.FormRenderType;
import mchorse.bbs_mod.forms.renderers.FormRenderingContext;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.LightmapTextureManager;

@Environment(EnvType.CLIENT)
public class RenderingHandler {
    public HUDStage stage = new HUDStage();
    public HUDStage currentStage;

    public void update() {
        HUDStage stage = this.currentStage == null ? this.stage : this.currentStage;

        stage.update(stage == this.stage);
    }

    public void reset() {
        this.stage.reset();
        this.currentStage = null;
    }

    public void render(DrawContext context, float tickDelta) {
        HUDStage stage = this.currentStage == null ? this.stage : this.currentStage;

        stage.render(new FormRenderingContext()
                        .set(FormRenderType.ITEM_FP, new StubEntity(MinecraftClient.getInstance().world), context.getMatrices(), LightmapTextureManager.MAX_LIGHT_COORDINATE, 0, tickDelta)
                        .camera(MinecraftClient.getInstance().gameRenderer.getCamera()),
                context.getScaledWindowWidth(), context.getScaledWindowHeight());
    }
}
