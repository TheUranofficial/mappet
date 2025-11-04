package com.theuran.mappet.client.ui;

import com.theuran.mappet.client.ui.panels.*;
import com.theuran.mappet.client.ui.states.UIStatesOverlayPanel;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.packets.server.RequestStatesPacket;
import com.theuran.mappet.network.packets.server.StatesPacket;
import mchorse.bbs_mod.ui.dashboard.UIDashboard;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIIcon;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlay;
import mchorse.bbs_mod.ui.utils.icons.Icons;
import mchorse.bbs_mod.utils.Direction;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class UIMappetDashboard extends UIDashboard {
    public UIIcon states;
    public UIStatesOverlayPanel statesPanel;

    public UIMappetDashboard() {
        super();

        this.statesPanel = new UIStatesOverlayPanel();
        this.states = new UIIcon(Icons.SETTINGS, icon -> {
            UIOverlay.addOverlayLeft(this.context, this.statesPanel, 240);
            Dispatcher.sendToServer(new RequestStatesPacket());
        });
        this.states.tooltip(UIMappetKeys.STATES_TITLE, Direction.TOP);

        this.getPanels().pinned.getChildren().clear();
        this.getPanels().pinned.add(this.states);
    }

    @Override
    protected void registerPanels() {
        this.getPanels().registerPanel(new UIHUDScenePanel(this), UIMappetKeys.HUD_SCENE_TITLE, Icons.POSE);
        this.getPanels().registerPanel(new UIScriptPanel(this), UIMappetKeys.SCRIPTS_TITLE, Icons.PROPERTIES);
        this.getPanels().registerPanel(new UILoggerPanel(this), UIMappetKeys.LOGGER_TITLE, Icons.CROPS);
        this.getPanels().registerPanel(new UIBuilderPanel(this), UIMappetKeys.UI_BUILDER_TITLE, Icons.MAZE);

        this.setPanel(this.getPanel(UILoggerPanel.class));
    }
}
