package com.theuran.mappet.client.ui.panels;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.scripts.logger.Log;
import mchorse.bbs_mod.ui.dashboard.UIDashboard;
import mchorse.bbs_mod.ui.dashboard.panels.UIDashboardPanel;
import mchorse.bbs_mod.ui.framework.elements.UIElement;
import mchorse.bbs_mod.ui.framework.elements.UIScrollView;
import mchorse.bbs_mod.ui.framework.elements.utils.UIText;

public class UILoggerPanel extends UIDashboardPanel {
    public UIElement logsElement;
    public UIScrollView scrollView;

    public UILoggerPanel(UIDashboard dashboard) {
        super(dashboard);

        this.logsElement = new UIElement();
        this.scrollView = new UIScrollView();

        this.scrollView.relative(this.logsElement);
        this.scrollView.wh(1f, 1f);

        this.scrollView.preRender(ctx -> {

        });

        for (Log log : Mappet.getLogger().getLogs()) {
            this.scrollView.add(new UIText(log.getMessage()).w(1f));
        }

        this.logsElement.relative(this).wh(1, 1);

        this.add(this.logsElement);
    }

    @Override
    public void update() {
        for (Log log : Mappet.getLogger().getLogs()) {
            this.scrollView.add(new UIText(log.getMessage()).w(1f).h(12).relative(this.scrollView));
        }
    }
}
