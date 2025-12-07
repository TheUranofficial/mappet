package com.theuran.mappet.client.ui.utils;

import com.theuran.mappet.client.ui.UIMappetKeys;
import mchorse.bbs_mod.ui.framework.elements.UIScrollView;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;

public class UIOptionsOverlayPanel extends UIOverlayPanel {
    public UIScrollView fields;

    public UIOptionsOverlayPanel() {
        super(UIMappetKeys.GENERAL_OPTIONS);

        this.fields = new UIScrollView();
        this.fields.column().vertical().stretch().scroll().width(0).padding(6);
        this.fields.full(this.content);

        this.content.add(this.fields);
    }
}
