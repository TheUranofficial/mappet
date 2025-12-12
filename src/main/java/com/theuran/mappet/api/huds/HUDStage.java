package com.theuran.mappet.api.huds;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import java.util.LinkedHashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class HUDStage {
    public Map<String, HUDScene> scenes = new LinkedHashMap<>();

    public void render(DrawContext context, float tickDelta) {
        scenes.forEach((id, hudScene) -> {
            hudScene.render(context, tickDelta);
        });
    }

    public void reset() {
        this.scenes.clear();
    }

    public void update(boolean allowExpiring) {
        this.scenes.values().removeIf((scene) -> scene.update(allowExpiring));
    }
}