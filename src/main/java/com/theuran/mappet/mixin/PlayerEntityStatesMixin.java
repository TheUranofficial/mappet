package com.theuran.mappet.mixin;

import com.theuran.mappet.api.states.IStatesProvider;
import com.theuran.mappet.api.states.States;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerEntity.class)
public class PlayerEntityStatesMixin implements IStatesProvider {
    private States states = new States();

    @Override
    public States getStates() {
        return this.states;
    }

    @Override
    public void setStates(States states) {
        this.states = states;
    }
}
