package com.theuran.mappet.client.ui;

import com.theuran.mappet.client.ui.panels.UIHUDScenePanel;
import com.theuran.mappet.client.ui.panels.UIServerSettingsPanel;
import mchorse.bbs_mod.ui.dashboard.UIDashboard;
import mchorse.bbs_mod.ui.utils.icons.Icons;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class UIMappetDashboard extends UIDashboard {
    @Override
    protected void registerPanels() {
        this.getPanels().registerPanel(new UIServerSettingsPanel(this), UIMappetKeys.SERVER_SETTINGS_TITLE, Icons.GEAR);
        this.getPanels().registerPanel(new UIHUDScenePanel(this), UIMappetKeys.HUD_SCENE_TITLE, Icons.POSE);

        this.setPanel(this.getPanel(UIServerSettingsPanel.class));
    }
}
