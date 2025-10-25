package com.theuran.mappet.client.ui.panels;

import com.theuran.mappet.Mappet;
import mchorse.bbs_mod.ui.dashboard.UIDashboard;
import mchorse.bbs_mod.ui.dashboard.panels.UIDashboardPanel;
import mchorse.bbs_mod.ui.framework.elements.UIElement;
import mchorse.bbs_mod.ui.framework.elements.input.text.UITextEditor;

public class UILoggerPanel extends UIDashboardPanel {
    public UIElement logsElement;

    public UITextEditor logs;
    public UILoggerPanel(UIDashboard dashboard) {
        super(dashboard);

        this.logsElement = new UIElement();
        this.logs = new UITextEditor((t) -> {
            this.logs.setText(Mappet.getLogger().getLogLabels());
        });

        this.logs.setText(Mappet.getLogger().getLogLabels());

        this.logs.relative(this);
        this.logs.background();
        this.logs.wh(1f, 1f);
        this.logs.noLineNumbers();

        this.logsElement.relative(this).wh(1, 1);

        this.logsElement.add(this.logs);

        this.add(this.logsElement);
    }

    @Override
    public void update() {
        this.logs.unfocus(null);
    }
}
