package com.theuran.mappet.client;

import com.theuran.mappet.api.huds.HUDStage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;

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

        stage.render(context, tickDelta);
    }
}
