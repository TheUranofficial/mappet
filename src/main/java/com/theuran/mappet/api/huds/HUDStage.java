package com.theuran.mappet.api.huds;

import com.mojang.blaze3d.systems.RenderSystem;
import mchorse.bbs_mod.forms.renderers.FormRenderingContext;
import mchorse.bbs_mod.utils.MathUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class HUDStage {
    public Map<String, HUDScene> scenes = new LinkedHashMap<>();

    private List<HUDForm> renderOrtho = new ArrayList<>();
    private List<HUDForm> renderPerpsective = new ArrayList<>();
    private Matrix4f projection = new Matrix4f();

    public void reset() {
        this.scenes.clear();
    }

    public void update(boolean allowExpiring) {
        this.scenes.values().removeIf((scene) -> scene.update(allowExpiring));
    }

    public void render(FormRenderingContext context, int w, int h) {
        MinecraftClient mc = MinecraftClient.getInstance();

        RenderSystem.clear(GL11.GL_DEPTH_BUFFER_BIT, MinecraftClient.IS_SYSTEM_MAC);

        this.renderOrtho.clear();

        float aspect = (float) mc.getWindow().getWidth() / (float) mc.getWindow().getHeight();
        float lastFov = Float.MIN_VALUE;

        context.stack.push();
        context.stack.loadIdentity();
        context.stack.translate(0, -1, -2);

        for (HUDScene scene : this.scenes.values()) {
            if (lastFov != scene.fov.get()) {
                this.projection.identity().perspective(MathUtils.toRad(scene.fov.get()), aspect, 0.05F, 1000);
                //context.getUBO().update(this.projection, new Matrix4f());

                lastFov = scene.fov.get();
            }

            this.renderPerpsective.clear();

            for (HUDForm form : scene.forms.getList()) {
                if (form.ortho.get()) {
                    this.renderOrtho.add(form);
                } else {
                    this.renderPerpsective.add(form);
                }
            }

            this.renderPerpsective.sort(this::depthSort);

            for (HUDForm form : this.renderPerpsective) {
                form.render(context, w, h);
            }
        }

        context.stack.pop();

        this.projection.identity().ortho(0, w, 0, h, -1000, 1000);
        //context.getUBO().update(this.projection, Matrices.EMPTY_4F);

        RenderSystem.clear(GL11.GL_DEPTH_BUFFER_BIT, MinecraftClient.IS_SYSTEM_MAC);

        this.renderOrtho.sort(this::depthSort);

        for (HUDForm form : this.renderOrtho) {
            form.render(context, w, h);
        }

        RenderSystem.depthFunc(GL11.GL_ALWAYS);
    }

    private int depthSort(HUDForm a, HUDForm b) {
        float diff = a.transform.get().translate.z - b.transform.get().translate.z;

        if (diff == 0) {
            return 0;
        }

        return diff < 0 ? -1 : 1;
    }
}