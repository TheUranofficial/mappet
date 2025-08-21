package com.theuran.mappet.mixin;

import mchorse.bbs_mod.resources.Link;
import mchorse.bbs_mod.resources.packs.InternalAssetsSourcePack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.io.File;
import java.util.Collection;

@Mixin(InternalAssetsSourcePack.class)
public interface InternalDuctTapeMixin {
    @Invoker(value = "getLinksFromZipFile", remap = false)
    void getLinksFromZipFile(File file, Link link, Collection<Link> links, boolean recursive);
}
