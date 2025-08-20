package com.theuran.mappet.client.ui;

import mchorse.bbs_mod.forms.forms.Form;
import mchorse.bbs_mod.network.ClientNetwork;
import mchorse.bbs_mod.ui.dashboard.UIDashboard;
import mchorse.bbs_mod.ui.dashboard.panels.UIDashboardPanel;
import mchorse.bbs_mod.ui.forms.UIFormPalette;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class TestPanel extends UIDashboardPanel {
    public UIFormPalette palette = new UIFormPalette(this::setForm);

    private void setForm(Form form) {
        ClientNetwork.sendPlayerForm(form);
    }

    public TestPanel(UIDashboard dashboard) {
        super(dashboard);
        this.palette.updatable();
        this.palette.immersive();
        this.palette.full(this);
        this.palette.editor.renderer.full(dashboard.getRoot());
        this.palette.noBackground();
        this.palette.canModify();
    }
}
