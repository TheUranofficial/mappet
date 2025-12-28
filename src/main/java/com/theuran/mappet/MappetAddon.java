package com.theuran.mappet;

import com.theuran.mappet.resources.packs.MappetInternalAssetsPack;
import mchorse.bbs_mod.events.BBSAddonMod;
import mchorse.bbs_mod.events.Subscribe;
import mchorse.bbs_mod.events.register.RegisterL10nEvent;
import mchorse.bbs_mod.events.register.RegisterSourcePacksEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.Collections;

public class MappetAddon implements BBSAddonMod {
    @Subscribe
    public void registerSourcePacks(RegisterSourcePacksEvent event) {
        event.provider.register(new MappetInternalAssetsPack());
    }

    @Environment(EnvType.CLIENT)
    @Subscribe
    public void registerL10n(RegisterL10nEvent event) {
        event.l10n.register(lang -> Collections.singletonList(Mappet.link("lang/" + lang + ".json")));
        event.l10n.reload();
    }
}
