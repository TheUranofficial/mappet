package com.theuran.mappet.mixin;

import com.theuran.mappet.Mappet;
import mchorse.bbs_mod.graphics.texture.TextureManager;
import mchorse.bbs_mod.resources.Link;
import mchorse.bbs_mod.utils.resources.MultiLink;
import mchorse.bbs_mod.utils.resources.Pixels;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.io.InputStream;

@Environment(EnvType.CLIENT)
@Mixin(TextureManager.class)
public class TextureManagerMixin {
    @Inject(method = "getPixels", at = @At("HEAD"), cancellable = true, remap = false)
    public void getPixels(Link link, CallbackInfoReturnable<Pixels> callbackInfoReturnable) throws IOException {
        if (!(link instanceof MultiLink) && link.source.equals("mappet")) {
            try (InputStream asset = Mappet.getProvider().getAsset(link)) {
                callbackInfoReturnable.setReturnValue(Pixels.fromPNGStream(asset));
            }
        }
    }
}
