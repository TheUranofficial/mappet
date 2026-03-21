package com.theuran.mappet.api.huds;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.systems.VertexSorter;
import mchorse.bbs_mod.camera.Camera;
import mchorse.bbs_mod.forms.entities.StubEntity;
import mchorse.bbs_mod.forms.renderers.FormRenderType;
import mchorse.bbs_mod.forms.renderers.FormRenderingContext;
import mchorse.bbs_mod.utils.Factor;
import mchorse.bbs_mod.utils.MathUtils;
import mchorse.bbs_mod.utils.MatrixStackUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix3d;
import org.joml.Matrix3f;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import java.util.LinkedHashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class HUDStage {
    public Map<String, HUDScene> scenes = new LinkedHashMap<>();

    private final Camera camera = new Camera();
    private final FormRenderingContext formContext = new FormRenderingContext();

    private final Vector3f pos = new Vector3f();
    private final static Vector3d tempVec = new Vector3d();
    private final static Matrix3d tempMatrix = new Matrix3d();

    public Factor distance = new Factor(0, 0, 100, (x) -> Math.pow(x, 2) / 100D);

    public void render(DrawContext context, float tickDelta) {
        if (!this.scenes.isEmpty()) {
            Window window = MinecraftClient.getInstance().getWindow();

            RenderSystem.depthFunc(GL11.GL_LEQUAL);

            this.setDistance(15);
            this.pos.set(0, 1, 0);

            this.setupPosition();
            this.setupViewport(0, 0, window.getWidth(), window.getHeight());

            /* Cache the global stuff */
            MatrixStackUtils.cacheMatrices();

            RenderSystem.setProjectionMatrix(this.camera.projection, VertexSorter.BY_Z);
            RenderSystem.setInverseViewRotationMatrix(new Matrix3f(this.camera.view).invert());

            MatrixStack stack = context.getMatrices();

            this.scenes.forEach((id, hudScene) -> {
                /* Rendering begins... */
                stack.push();
                MatrixStackUtils.multiply(stack, this.camera.view);
                stack.translate(-this.camera.position.x, -this.camera.position.y, -this.camera.position.z);

                RenderSystem.setupLevelDiffuseLighting(
                    new Vector3f(0, 0.85F, -1).normalize(),
                    new Vector3f(0, 0.85F, 1).normalize(),
                    this.camera.view
                );

                this.formContext.set(FormRenderType.PREVIEW,
                        new StubEntity(MinecraftClient.getInstance().world),
                        stack,
                        LightmapTextureManager.pack(15, 15),
                        OverlayTexture.DEFAULT_UV,
                        tickDelta)
                    .camera(this.camera).modelRenderer();

                for (HUDForm form : hudScene.forms.getList()) {
                    form.render(this.formContext);
                }

                DiffuseLighting.disableGuiDepthLighting();

                stack.pop();
            });

            /* Return back to orthographic projection */
            MinecraftClient mc = MinecraftClient.getInstance();

            RenderSystem.viewport(0, 0, mc.getWindow().getFramebufferWidth(), mc.getWindow().getFramebufferHeight());
            MatrixStackUtils.restoreMatrices();

            RenderSystem.depthFunc(GL11.GL_ALWAYS);
        }
    }

    public void setDistance(int distanceX) {
        this.distance.setX(distanceX);
    }

    private void setupPosition() {
        this.camera.position.set(this.pos);

        tempVec.set(0, 0, -this.distance.getValue());
        this.rotateVector(tempVec);

        this.camera.position.x += tempVec.x;
        this.camera.position.y += tempVec.y;
        this.camera.position.z += tempVec.z;
    }

    private void rotateVector(Vector3d vec) {
        tempMatrix.identity().rotateX(this.camera.rotation.x);
        tempMatrix.transform(vec);
        tempMatrix.identity().rotateY(MathUtils.PI - this.camera.rotation.y);
        tempMatrix.transform(vec);
    }

    private void setupViewport(float x, float y, float width, float height) {
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);

        int vx = (int) x;
        int vy = (int) y;
        int vw = (int) width;
        int vh = (int) height;

        RenderSystem.viewport(vx, vy, vw, vh);
        this.camera.updatePerspectiveProjection(vw, vh);
        this.camera.updateView();
    }

    public void reset() {
        this.scenes.clear();
    }

    public void update(boolean allowExpiring) {
        this.scenes.values().removeIf((scene) -> scene.update(allowExpiring));
    }
}