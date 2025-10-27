package com.theuran.mappet.client.ui.uibilder;

import com.theuran.mappet.client.ui.UIMappetKeys;
import mchorse.bbs_mod.ui.framework.elements.input.list.UIStringList;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;

import static com.theuran.mappet.client.ui.panels.UIBuilderPanel.UInames;

public class ElementsOverlayPanel extends UIOverlayPanel {
    private UIStringList list;

    public ElementsOverlayPanel() {
        super(UIMappetKeys.ElementPanel);
        this.list = new UIStringList((l) -> {});

        this.list.setList(UInames);

        this.list.context((ElementContext) -> {

        });

        this.list.full(this.content);
        this.list.setIndex(0);
        this.content.add(this.list);
    }
}
