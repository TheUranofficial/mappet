package com.theuran.mappet.mixin;

//magma plohoy malcheg
//import com.theuran.mappet.client.api.scripts.code.ClientScriptCamera;
import com.theuran.mappet.client.managers.ClientOptionsManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(GameOptions.class)
public class GameOptionsMixin {
    @Inject(method = "write", at = @At("TAIL"))
    public void write(CallbackInfo callback) {
        ClientOptionsManager.INSTANCE.invokeEvent();
    }

    @Inject(method = "write", at = @At("HEAD"), cancellable = true)
    public void setPerspective(CallbackInfo callback) {
        //if (ClientScriptCamera.IS_LOCKED) callback.cancel();
    }
}