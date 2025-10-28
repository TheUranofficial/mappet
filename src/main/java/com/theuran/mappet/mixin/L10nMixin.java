package com.theuran.mappet.mixin;

import com.theuran.mappet.client.MappetClient;
import mchorse.bbs_mod.l10n.L10n;
import mchorse.bbs_mod.l10n.keys.LangKey;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(L10n.class)
public class L10nMixin {
    @Inject(method = "lang", at = @At("HEAD"), cancellable = true, remap = false)
    private static void lang(String key, CallbackInfoReturnable<LangKey> callbackInfoReturnable) {
        if (key.startsWith("mappet")) {
            callbackInfoReturnable.setReturnValue(MappetClient.getL10n().getKey(key));
        }
    }
}
