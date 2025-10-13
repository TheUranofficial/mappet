package com.theuran.mappet.client.ui;

import com.theuran.mappet.client.ui.panels.UIServerSettings;
import mchorse.bbs_mod.ui.dashboard.UIDashboard;
import mchorse.bbs_mod.ui.utils.icons.Icons;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class UIMappetDashboard extends UIDashboard {
    @Override
    protected void registerPanels() {
        this.getPanels().registerPanel(new UIServerSettings(this), UIMappetKeys.SERVER_SETTINGS_TITLE, Icons.GEAR);

        this.setPanel(this.getPanel(UIServerSettings.class));
    }
}
