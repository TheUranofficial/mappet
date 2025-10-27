package com.theuran.mappet.client.ui.builder;

import com.theuran.mappet.client.ui.UIMappetKeys;
import com.theuran.mappet.client.ui.panels.UIBuilderPanel;
import mchorse.bbs_mod.ui.framework.elements.input.list.UIStringList;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;

public class UIElementsOverlayPanel extends UIOverlayPanel {
    private UIStringList list;

    public UIElementsOverlayPanel() {
        super(UIMappetKeys.UI_BUILDER_ELEMENT_PANEL);
        this.list = new UIStringList((l) -> {});

        this.list.setList(UIBuilderPanel.names);

        this.list.context((menu) -> {

        });

        this.list.full(this.content);
        this.list.setIndex(0);
        this.content.add(this.list);
    }
}
