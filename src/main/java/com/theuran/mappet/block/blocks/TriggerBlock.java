package com.theuran.mappet.block.blocks;

import com.mojang.serialization.MapCodec;
import com.theuran.mappet.api.scripts.code.ScriptVector;
import com.theuran.mappet.block.blocks.entities.TriggerBlockEntity;
import com.theuran.mappet.client.ui.blocks.trigger.UITriggerBlock;
import mchorse.bbs_mod.ui.framework.UIScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.*;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TriggerBlock extends BlockWithEntity implements Waterloggable {
    public static final BooleanProperty COLLISION = BooleanProperty.of("collision");

    public TriggerBlock() {
        super(Settings.create().nonOpaque().noCollision());
        this.setDefaultState(this.getStateManager().getDefaultState()
                .with(COLLISION, true)
                .with(Properties.WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(COLLISION, Properties.WATERLOGGED);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (!state.get(COLLISION)) {
            return VoxelShapes.empty();
        }

        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof TriggerBlockEntity trigger) {
            ScriptVector p1 = trigger.getPos1();
            ScriptVector p2 = trigger.getPos2();
            return VoxelShapes.cuboid(p1.x / 16.0, p1.y / 16.0, p1.z / 16.0, p2.x / 16.0, p2.y / 16.0, p2.z / 16.0);
        }
        return VoxelShapes.fullCube();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof TriggerBlockEntity trigger) {
            ScriptVector p1 = trigger.getPos1();
            ScriptVector p2 = trigger.getPos2();
            return VoxelShapes.cuboid(p1.x / 16.0, p1.y / 16.0, p1.z / 16.0, p2.x / 16.0, p2.y / 16.0, p2.z / 16.0);
        }
        return VoxelShapes.fullCube();
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    protected MapCodec<TriggerBlock> getCodec() {
        return createCodec((settings) -> new TriggerBlock());
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient()) {
            UIScreen.open(new UITriggerBlock(pos));
            return ActionResult.SUCCESS;
        }
        return ActionResult.CONSUME;
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
                .with(COLLISION, true)
                .with(Properties.WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).isOf(Fluids.WATER));
    }

    @Override
    public @Nullable TriggerBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TriggerBlockEntity(pos, state);
    }
}