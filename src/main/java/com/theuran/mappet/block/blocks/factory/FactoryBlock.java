package com.theuran.mappet.block.blocks.factory;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;

public class FactoryBlock extends Block {
    public static final EnumProperty<Settings> SETTINGS = EnumProperty.of("settings", Settings.class);

    public FactoryBlock() {
        super(FabricBlockSettings.create());
        this.setDefaultState(this.getStateManager().getDefaultState()
                .with(SETTINGS, Settings.COBBLESTONE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SETTINGS);
    }

    public enum Settings implements StringIdentifiable {
        COBBLESTONE,
        SLIME,
        ICE;

        @Override
        public String asString() {
            return this.name().toLowerCase();
        }
    }
}
