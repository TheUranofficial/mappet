package com.theuran.mappet.client.ui.panels;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.client.ui.states.UIStatesEditor;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.dashboard.UIDashboard;
import mchorse.bbs_mod.ui.dashboard.panels.UIDashboardPanel;
import mchorse.bbs_mod.ui.framework.elements.UIElement;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIIcon;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlay;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIStringOverlayPanel;
import mchorse.bbs_mod.ui.framework.elements.utils.UILabel;
import mchorse.bbs_mod.ui.utils.icons.Icons;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class UIServerSettings extends UIDashboardPanel {
    public UIElement states;

    public UIStatesEditor statesEditor;

    public UILabel statesTitle;

    public UIIcon statesSwitch;

    public UIIcon statesAdd;

    private String lastTarget = "~";

    public UIServerSettings(UIDashboard dashboard) {
        super(dashboard);

        this.states = new UIElement();
        this.states.relative(this).wh(0.5f, 1f);

        this.statesEditor = new UIStatesEditor();
        this.statesEditor.relative(this.states).y(25).w(1f).h(1f, -25);
        this.statesTitle = new UILabel(IKey.constant("")).labelAnchor(0, 0.5f);
        this.statesTitle.relative(this.states).xy(10, 10).wh(120, 20);
        this.statesSwitch = new UIIcon(Icons.SEARCH, this::openSearch);
        this.statesSwitch.relative(this.states).x(1f, -50).y(10);
        this.statesAdd = new UIIcon(Icons.ADD, this::addState);
        this.statesAdd.relative(this.states).x(1f, -30).y(10);

        this.states.add(this.statesTitle, this.statesSwitch, this.statesAdd, this.statesEditor);
        this.add(this.states);
    }

    private void addState(UIIcon icon) {
        this.statesEditor.addNew();
    }

    private void openSearch(UIIcon icon) {
        List<String> targets = new ArrayList<>();

        targets.add("~");

        for (PlayerEntity player : MinecraftClient.getInstance().world.getPlayers()) {
            targets.add(player.getGameProfile().getName());
        }

        UIStringOverlayPanel overlayPanel = new UIStringOverlayPanel(IKey.constant("mappet.server.settings.states.pick"), false, targets, (target) -> {
            if (target.isEmpty())
                return;

            System.out.println(Mappet.getStates().getValues());
        });

        UIOverlay.addOverlay(this.getContext(), overlayPanel.set(this.lastTarget), 0.4f, 0.6f);
    }
}
