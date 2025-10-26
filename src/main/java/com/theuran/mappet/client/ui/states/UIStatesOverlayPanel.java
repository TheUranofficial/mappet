package com.theuran.mappet.client.ui.states;

import com.theuran.mappet.client.ui.UIMappetKeys;
import mchorse.bbs_mod.ui.framework.elements.UIScrollView;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;
import mchorse.bbs_mod.ui.utils.ScrollDirection;

public class UIStatesOverlayPanel extends UIOverlayPanel {
    public UIScrollView options;

    public UIStatesOverlayPanel() {
        super(UIMappetKeys.STATES_TITLE);
        this.options = new UIScrollView(ScrollDirection.VERTICAL);
        this.options.scroll.scrollSpeed = 51;
        this.options.full(this.content);
        this.options.column().scroll().vertical().stretch().padding(10).height(20);

        this.add(this.options);
        this.markContainer();
    }
}
