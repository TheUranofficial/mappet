package com.theuran.mappet.client;

import com.theuran.mappet.block.MappetBlocks;
import com.theuran.mappet.client.ui.blocks.trigger.UITriggerBlock;
import mchorse.bbs_mod.graphics.Draw;
import mchorse.bbs_mod.ui.framework.UIBaseMenu;
import mchorse.bbs_mod.ui.framework.UIScreen;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

public class TriggerRenderer {
    public static void init() {
        WorldRenderEvents.END.register((context) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            UIBaseMenu currentMenu = UIScreen.getCurrentMenu();

            if (client.getDebugHud().shouldShowDebugHud() && client.interactionManager.getCurrentGameMode().isCreative()) {
                renderDebugBoxes(context);
            } else if (currentMenu instanceof UITriggerBlock uiTriggerBlock) {
                Camera camera = context.camera();

                renderHitboxAtPos(context.matrixStack(), uiTriggerBlock.blockPos, camera, client.world);
            }
        });
    }

    private static void renderDebugBoxes(WorldRenderContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        Camera camera = context.camera();
        BlockPos playerPos = camera.getBlockPos();

        int radius = 10;
        BlockPos.stream(playerPos.add(-radius, -radius, -radius),
                        playerPos.add(radius, radius, radius))
                .forEach(pos -> {
                    if (shouldRenderDebugBox(client.world, pos)) {
                        renderBoxAtPos(context.matrixStack(), pos, camera);
                    }
                });
    }

    private static boolean shouldRenderDebugBox(World world, BlockPos pos) {
        return world.getBlockState(pos).isOf(MappetBlocks.TRIGGER_BLOCK);
    }

    private static void renderBoxAtPos(MatrixStack matrices, BlockPos pos, Camera camera) {
        matrices.push();

        matrices.translate(pos.getX() - camera.getPos().x,
                pos.getY() - camera.getPos().y,
                pos.getZ() - camera.getPos().z);

        Draw.renderBox(matrices, 0, 0, 0, 1, 1, 1, 0.8F, 0.8F, 0F, 0.3F);

        matrices.pop();
    }

    private static void renderHitboxAtPos(MatrixStack matrices, BlockPos pos, Camera camera, World world) {
        BlockState state = world.getBlockState(pos);

        VoxelShape hitbox = state.getOutlineShape(world, pos);

        matrices.push();

        for (Box box : hitbox.getBoundingBoxes()) {
            matrices.push();

            matrices.translate(pos.getX() - camera.getPos().x,
                    pos.getY() - camera.getPos().y,
                    pos.getZ() - camera.getPos().z);

            Draw.renderBox(matrices,
                    (float) box.minX, (float) box.minY, (float) box.minZ,
                    (float) box.maxX*0.7, (float) box.maxY*0.7, (float) box.maxZ*0.7,
                    1.0F, 0.0F, 0.0F, 0.3F);

            matrices.pop();
        }

        matrices.pop();
    }
}
