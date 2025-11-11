package com.theuran.mappet.client.ui;

import com.theuran.mappet.client.ui.events.UIEventsOverlayPanel;
import com.theuran.mappet.client.ui.events.UIKeybindsOverlayPanel;
import com.theuran.mappet.client.ui.panels.*;
import com.theuran.mappet.client.ui.states.UIStatesOverlayPanel;
import com.theuran.mappet.client.ui.utils.MappetIcons;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.packets.server.RequestStatesPacket;
import mchorse.bbs_mod.ui.dashboard.UIDashboard;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIIcon;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlay;
import mchorse.bbs_mod.ui.utils.UI;
import mchorse.bbs_mod.ui.utils.icons.Icons;
import mchorse.bbs_mod.utils.Direction;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.system.linux.UIO;

@Environment(EnvType.CLIENT)
public class UIMappetDashboard extends UIDashboard {
    public UIIcon states;
    public UIIcon events;
    public UIIcon keybinds;
    public UIStatesOverlayPanel statesPanel;
    public UIEventsOverlayPanel eventsPanel;
    public UIKeybindsOverlayPanel keybindsPanel;

    public UIMappetDashboard() {
        super();

        this.statesPanel = new UIStatesOverlayPanel();
        this.states = new UIIcon(MappetIcons.STATES, icon -> {
            UIOverlay.addOverlayLeft(this.context, this.statesPanel, 240);
            Dispatcher.sendToServer(new RequestStatesPacket());
        });
        this.states.tooltip(UIMappetKeys.STATES_TITLE, Direction.TOP);

        this.eventsPanel = new UIEventsOverlayPanel();
        this.events = new UIIcon(MappetIcons.EVENTS, icon -> {
            UIOverlay.addOverlayRight(this.context, this.eventsPanel, 240);
        });
        this.events.tooltip(UIMappetKeys.EVENTS_TITLE, Direction.TOP);

        this.keybindsPanel = new UIKeybindsOverlayPanel();
        this.keybinds = new UIIcon(MappetIcons.KEYBINDS, icon -> {
            UIOverlay.addOverlayRight(this.context, this.keybindsPanel, 240);
        });
        this.keybinds.tooltip(UIMappetKeys.KEYBINDS_TITLE, Direction.TOP);

        this.getPanels().pinned.getChildren().clear();
        this.getPanels().pinned.add(this.states, this.events, this.keybinds);
    }

    @Override
    protected void registerPanels() {
        this.getPanels().registerPanel(new UIScriptPanel(this), UIMappetKeys.SCRIPTS_TITLE, Icons.PROPERTIES);

        this.setPanel(this.getPanel(UILoggerPanel.class));
    }
}
