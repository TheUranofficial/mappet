package com.theuran.mappet.api.huds;

import com.mojang.blaze3d.systems.RenderSystem;
import mchorse.bbs_mod.forms.renderers.FormRenderingContext;
import mchorse.bbs_mod.utils.MathUtils;
import net.minecraft.client.MinecraftClient;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HUDStage {
    public Map<String, HUDScene> scenes = new LinkedHashMap<>();

    private List<HUDForm> renderOrtho = new ArrayList<>();
    private List<HUDForm> renderPerspective = new ArrayList<>();
    private Matrix4f projection = new Matrix4f();

    public void reset() {
        this.scenes.clear();
    }

    public void update(boolean allowExpiring) {
        this.scenes.values().removeIf(scene -> scene.update(allowExpiring));
    }

    public void render(FormRenderingContext context, int w, int h) {
        RenderSystem.clear(GL11.GL_DEPTH_BUFFER_BIT, false);

        this.renderOrtho.clear();

        float aspect = (float) MinecraftClient.getInstance().getWindow().getWidth() / MinecraftClient.getInstance().getWindow().getWidth();
        float lastFov = Float.MIN_VALUE;

        context.stack.push();
        context.stack.loadIdentity();
        context.stack.translate(0, -1, -2);

        for (HUDScene scene : this.scenes.values()) {
            if (lastFov != scene.fov.get()) {
                this.projection.identity().perspective(MathUtils.toRad(scene.fov.get()), aspect, 0.05f, 1000);

                lastFov = scene.fov.get();
            }

            this.renderPerspective.clear();

            for (HUDForm form : scene.forms.getList()) {
                if (form.ortho.get()) {
                    this.renderOrtho.add(form);
                } else {
                    this.renderPerspective.add(form);
                }
            }

            this.renderPerspective.sort(this::depthSort);

            for (HUDForm form : this.renderPerspective) {
                form.render(context, w, h);
            }
        }

        context.stack.pop();

        this.projection.identity().ortho(0, w, 0, h, -1000, 1000);

        RenderSystem.clear(GL11.GL_DEPTH_BUFFER_BIT, false);

        this.renderOrtho.sort(this::depthSort);

        for (HUDForm form : this.renderOrtho) {
            form.render(context, w, h);
        }
    }

    private int depthSort(HUDForm a, HUDForm b) {
        float diff = a.transform.get().translate.z - b.transform.get().translate.z;

        if (diff == 0) {
            return 0;
        }

        return diff < 0 ? -1 : 1;
    }
}
