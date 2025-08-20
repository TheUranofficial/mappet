package com.theuran.mappet.client.ui;

import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.dashboard.UIDashboard;
import mchorse.bbs_mod.ui.utils.icons.Icons;

public class UIMappetDashboard extends UIDashboard {
    @Override
    protected void registerPanels() {
        this.getPanels().registerPanel(new TestPanel(this), IKey.raw("Femoz"), Icons.BILIBILI);

        this.getPanels().setPanel(this.getPanel(TestPanel.class));
    }
}
