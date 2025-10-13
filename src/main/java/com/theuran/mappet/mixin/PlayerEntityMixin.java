package com.theuran.mappet.mixin;

import com.theuran.mappet.api.states.IStatesProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void onWriteCustomDataToNbt(NbtCompound nbt, CallbackInfo info) {
        if (this instanceof IStatesProvider provider) {
            nbt.put("MappetStates", provider.getStates().toNbt());
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void onReadCustomDataFromNbt(NbtCompound nbt, CallbackInfo info) {
        if (this instanceof IStatesProvider provider) {
            if (nbt.contains("MappetStates")) {
                provider.getStates().fromNbt(nbt.get("MappetStates"));
            }
        }
    }
}
