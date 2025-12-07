package com.theuran.mappet.api.huds;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.systems.VertexSorter;
import mchorse.bbs_mod.forms.renderers.FormRenderingContext;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class HUDStage {
    public Map<String, HUDScene> scenes = new LinkedHashMap<>();

    private List<HUDForm> renderOrtho = new ArrayList<>();
    private List<HUDForm> renderPerspective = new ArrayList<>();

    public void reset() {
        this.scenes.clear();
    }

    public void update(boolean allowExpiring) {
        this.scenes.values().removeIf(scene -> scene.update(allowExpiring));
    }

    public void render(FormRenderingContext context, int w, int h) {
//        MinecraftClient mc = MinecraftClient.getInstance();
//
//        int width = mc.getWindow().getWidth();
//        int height = mc.getWindow().getHeight();
//
//        this.renderOrtho.clear();
//        this.renderPerspective.clear();
//
//        RenderSystem.backupProjectionMatrix();
//
//        DiffuseLighting.enableGuiDepthLighting();
//        this.enableGLStates();
//
//        RenderSystem.clear(GL11.GL_DEPTH_BUFFER_BIT, MinecraftClient.IS_SYSTEM_MAC);
//
//        float rx = (float) Math.ceil((double) width / w);
//        float ry = (float) Math.ceil((double) height / h);
//
//        int vx = 0;
//        int vy = (int) (height - h * ry);
//        int vw = (int) (w * rx);
//        int vh = (int) (h * ry);
//
//        float aspect = (float) vw / (float) vh;
//        float lastFov = Float.MIN_VALUE;
//
//        RenderSystem.viewport(vx, vy, vw, vh);
//
//        context.stack.push();
//        context.stack.translate(0, -1, -2);
//
//        for (HUDScene scene : this.scenes.values()) {
//            if (lastFov != scene.fov.get()) {
//                Matrix4f perspectiveMatrix = new Matrix4f();
//
//                perspectiveMatrix.setPerspective(MathUtils.toRad(scene.fov.get()), aspect, 0.05f, 1000.0f);
//
//                RenderSystem.setProjectionMatrix(perspectiveMatrix, VertexSorter.BY_DISTANCE);
//
//                lastFov = scene.fov.get();
//            }
//
//            this.renderPerspective.clear();
//
//            for (HUDForm form : scene.forms.getList()) {
//                if (form.ortho.get()) {
//                    this.renderOrtho.add(form);
//                } else {
//                    this.renderPerspective.add(form);
//                }
//            }
//
//            this.renderPerspective.sort(this::depthSort);
//
//            for (HUDForm form : this.renderPerspective) {
//                form.render(context, w, h);
//                System.out.println("sdadas");
//            }
//        }
//
//        context.stack.pop();
//
//        RenderSystem.restoreProjectionMatrix();
//
//        this.setupOrtho(w, h, true);
//
//        RenderSystem.clear(GL11.GL_DEPTH_BUFFER_BIT, MinecraftClient.IS_SYSTEM_MAC);
//
//        this.renderOrtho.sort(this::depthSort);
//
//        for (HUDForm form : this.renderOrtho) {
//            form.render(context, w, h);
//            System.out.println("sosal");
//        }
//
//        this.disableGLStates();
//        this.setupOrtho(w, h, false);
    }

    private void setupOrtho(int w, int h, boolean flip) {
        MinecraftClient mc = MinecraftClient.getInstance();

        RenderSystem.viewport(0, 0, mc.getWindow().getFramebufferWidth(), mc.getWindow().getFramebufferHeight());

        Matrix4f orthoMatrix = new Matrix4f();

        if (flip) {
            orthoMatrix.setOrtho(0.0f, (float) w, 0.0f, (float) h, 1000.f, 3000000.0f);
        } else {
            orthoMatrix.setOrtho(0.0f, (float) w, (float) h, 0.0f, 1000.0f, 3000000.0f);
        }

        RenderSystem.setProjectionMatrix(orthoMatrix, VertexSorter.BY_Z);
    }

    private int depthSort(HUDForm a, HUDForm b) {
        float diff = a.transform.get().translate.z - b.transform.get().translate.z;

        if (diff == 0) {
            return 0;
        }

        return diff < 0 ? -1 : 1;
    }

    private void enableGLStates() {
        DiffuseLighting.enableGuiDepthLighting();
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.disableCull();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void disableGLStates() {
        RenderSystem.enableCull();
        RenderSystem.disableDepthTest();
        DiffuseLighting.disableGuiDepthLighting();
    }
}