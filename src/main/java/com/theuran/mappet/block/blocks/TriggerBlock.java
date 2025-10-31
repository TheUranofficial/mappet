package com.theuran.mappet.block.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TriggerBlock extends Block {
    public static final BooleanProperty COLLISION = BooleanProperty.of("collision");
    public static final EnumProperty<Hitbox> HITBOX = EnumProperty.of("foo", Hitbox.class);

    public TriggerBlock() {
        super(Settings.create().nonOpaque().noCollision());
        this.setDefaultState(this.getStateManager().getDefaultState().with(COLLISION, true));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HITBOX);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(COLLISION) ? VoxelShapes.empty() : VoxelShapes.fullCube();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.fullCube();
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1f;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {
            boolean current = state.get(COLLISION);
            world.setBlockState(pos, state.with(COLLISION, !current));
            return ActionResult.SUCCESS;
        }
        return ActionResult.CONSUME;
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(COLLISION, true);
    }

    public enum Hitbox implements StringIdentifiable {
        X1(1),
        Y1(1),
        Z1(1),
        X2(1),
        Y2(1),
        Z2(1);

        final int value;

        Hitbox(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        @Override
        public String asString() {
            return this.name().toLowerCase();
        }
    }
}