package com.theuran.mappet.mixin;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.client.MappetClient;
import mchorse.bbs_mod.BBSModClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(BBSModClient.class)
public class BBSModClientMixin {
    @Inject(remap = false, method = "reloadLanguage", at = @At("TAIL"))
    private static void reloadLanguage(String language, CallbackInfo info) {
        MappetClient.getL10n().reload(language, Mappet.getProvider());
    }
}
