package com.theuran.mappet.client.api.scripts.code.ui.components;

import com.theuran.mappet.client.api.scripts.code.ui.MappetUIBuilder;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.elements.UIElement;
import mchorse.bbs_mod.ui.framework.elements.UIScrollView;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlay;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class UIOverlayComponent extends UIComponent<UIElement> {
    String title;
    List<UIComponent<?>> components;

    public UIOverlayComponent(String title, Consumer<MappetUIBuilder> consumer) {
        super(new UIElement());

        this.title = title;

        MappetUIBuilder uiBuilder = new MappetUIBuilder();

        consumer.accept(uiBuilder);

        this.components = uiBuilder.components;
    }

    public void open(float w, float h) {
        UIOverlayPanel overlayPanel = new UIOverlayPanel(IKey.raw(title));

        for (UIComponent<?> child : this.components) {
            overlayPanel.add(child.getMappetElement().relative(overlayPanel));
        }

        UIOverlay.addOverlay(this.element.getContext(), overlayPanel, w, h);
    }
}