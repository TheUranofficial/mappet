package com.theuran.mappet.client.ui.utils;

import mchorse.bbs_mod.settings.values.core.ValueGroup;
import mchorse.bbs_mod.ui.dashboard.UIDashboard;
import mchorse.bbs_mod.ui.dashboard.panels.UIDataDashboardPanel;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIIcon;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlay;
import mchorse.bbs_mod.ui.utils.icons.Icons;

public abstract class UIOptionsDataDashboardPanel <T extends ValueGroup> extends UIDataDashboardPanel<T> {
    public UIIcon optionsIcon;
    public UIOptionsOverlayPanel options;

    public UIOptionsDataDashboardPanel(UIDashboard dashboard) {
        super(dashboard);

        this.options = new UIOptionsOverlayPanel();
        this.optionsIcon = new UIIcon(Icons.GEAR, icon -> UIOverlay.addOverlayRight(this.getContext(), this.options, 200, 20).noBackground());
    }

    protected void addOptions() {
        this.iconBar.addAfter(this.openOverlay, this.optionsIcon);
    }

    @Override
    public void fill(T data) {
        super.fill(data);

        this.optionsIcon.setEnabled(data != null);
    }
}
