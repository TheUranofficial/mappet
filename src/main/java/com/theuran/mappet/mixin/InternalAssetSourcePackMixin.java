package com.theuran.mappet.mixin;

import com.theuran.mappet.Mappet;
import mchorse.bbs_mod.BBSMod;
import mchorse.bbs_mod.resources.Link;
import mchorse.bbs_mod.resources.packs.InternalAssetsSourcePack;
import net.fabricmc.loader.api.FabricLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;

@Mixin(InternalAssetsSourcePack.class)
public abstract class InternalAssetSourcePackMixin {
    /**
     * Теперь будут маппет ресы урааа
     *
     * @author Femozman
     * @reason Это всё потому что дебт хант
     **/
    @Overwrite(remap = false)
    private void stupidWorkaround(Collection<Link> links, Link link, boolean recursive) {
        loadLinksFromMod(BBSMod.MOD_ID, links, link, recursive);
        loadLinksFromMod(Mappet.MOD_ID, links, link, recursive);
    }

    private void loadLinksFromMod(String modId, Collection<Link> links, Link link, boolean recursive) {
        FabricLoader.getInstance().getModContainer(modId).ifPresent(mod -> {
            Path jarPath = mod.getOrigin().getPaths().stream().findFirst().orElse(null);
            if (jarPath != null) {
                File jarFile = jarPath.toFile();
                if (jarFile.isFile() && jarFile.getName().endsWith(".jar")) {
                    ((InternalDuctTapeMixin) this).getLinksFromZipFile(jarFile, link, links, recursive);
                }
            }
        });
    }
}
