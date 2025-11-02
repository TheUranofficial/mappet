package com.theuran.mappet.client.ui.panels;

import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.dashboard.UIDashboard;
import mchorse.bbs_mod.ui.dashboard.panels.UISidebarDashboardPanel;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIButton;

public class UITriggerBlockPanel extends UISidebarDashboardPanel {
    public UIButton editTriggers;

    public UITriggerBlockPanel(UIDashboard dashboard) {
        super(dashboard);

        this.editTriggers = new UIButton(IKey.raw("lox"), (button) -> {

        });

        this.editTriggers.relative(this.editor);

        this.editor.add(this.editTriggers);
    }

    @Override
    public void requestNames() {

    }
}
