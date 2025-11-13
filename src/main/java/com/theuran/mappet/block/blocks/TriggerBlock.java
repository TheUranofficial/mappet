package com.theuran.mappet.block.blocks;

import com.theuran.mappet.api.scripts.code.ScriptVector;
import com.theuran.mappet.client.ui.blocks.trigger.UITriggerBlock;
import mchorse.bbs_mod.ui.framework.UIScreen;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.*;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TriggerBlock extends Block implements Waterloggable {
    public static final BooleanProperty COLLISION = BooleanProperty.of("collision");
    public static final EnumProperty<Hitbox> HITBOX = EnumProperty.of("hitbox", Hitbox.class);

    public TriggerBlock() {
        super(Settings.create().nonOpaque().noCollision());
        this.setDefaultState(this.getStateManager().getDefaultState()
                .with(COLLISION, true)
                .with(HITBOX, Hitbox.SMALL)
                .with(Properties.WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(COLLISION, HITBOX, Properties.WATERLOGGED);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (!state.get(COLLISION)) {
            return VoxelShapes.empty();
        }

        Hitbox hitbox = state.get(HITBOX);

        return VoxelShapes.cuboid(
                hitbox.x1 / 16.0, hitbox.y1 / 16.0, hitbox.z1 / 16.0,
                hitbox.x2 / 16.0, hitbox.y2 / 16.0, hitbox.z2 / 16.0
        );
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Hitbox hitbox = state.get(HITBOX);

        return VoxelShapes.cuboid(
                hitbox.x1 / 16.0, hitbox.y1 / 16.0, hitbox.z1 / 16.0,
                hitbox.x2 / 16.0, hitbox.y2 / 16.0, hitbox.z2 / 16.0
        );
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
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

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient()) {
            UIScreen.open(new UITriggerBlock(pos, state.get(HITBOX)));
            return ActionResult.SUCCESS;
        }
        return ActionResult.CONSUME;
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
                .with(COLLISION, true)
                .with(HITBOX, Hitbox.SMALL)
                .with(Properties.WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).isOf(Fluids.WATER));
    }

    public enum Hitbox implements StringIdentifiable {
        FULL(0, 0, 0, 16, 16, 16),      // Полный блок
        SMALL(4, 4, 4, 12, 12, 12),     // Маленький куб в центре
        SLAB(0, 0, 0, 16, 8, 16),       // Нижняя половина
        STAIR(0, 0, 0, 16, 8, 8);       // Угловой хитбокс

        int x1;
        int y1;
        int z1;
        int x2;
        int y2;
        int z2;

        Hitbox(int x1, int y1, int z1, int x2, int y2, int z2) {
            this.x1 = x1;
            this.y1 = y1;
            this.z1 = z1;
            this.x2 = x2;
            this.y2 = y2;
            this.z2 = z2;
        }

        public ScriptVector getPos1() {
            return new ScriptVector(this.x1, this.y1, this.z1);
        }

        public ScriptVector getPos2() {
            return new ScriptVector(this.x2, this.y2, this.z2);
        }

        public Hitbox pos(int x1, int y1, int z1, int x2, int y2, int z2) {
            this.x1 = x1;
            this.y1 = y1;
            this.z1 = z1;
            this.x2 = x2;
            this.y2 = y2;
            this.z2 = z2;
            return this;
        }

        @Override
        public String asString() {
            return this.name().toLowerCase();
        }
    }
}