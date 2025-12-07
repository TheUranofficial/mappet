package com.theuran.mappet.client.ui.triggers;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.triggers.Trigger;
import mchorse.bbs_mod.ui.framework.UIContext;
import mchorse.bbs_mod.ui.framework.elements.input.list.UIList;
import mchorse.bbs_mod.ui.utils.UIDataUtils;

import java.util.List;
import java.util.function.Consumer;

public class UITriggerList extends UIList<Trigger> {
    public UITriggerList(Consumer<List<Trigger>> callback) {
        super(callback);
    }

    @Override
    protected void renderElementPart(UIContext context, Trigger element, int i, int x, int y, boolean hover, boolean selected) {
        int color = Mappet.getTriggers().getData(element);

        context.batcher.box(x, y, x + 4, y + this.scroll.scrollItemSize, 0xff000000 + color);

        super.renderElementPart(context, element, i, x + 4, y, hover, selected);
    }

    @Override
    protected String elementToString(UIContext context, int i, Trigger element) {
        return element.asString();
    }

    @Override
    public void render(UIContext context) {
        super.render(context);

        if (this.list.isEmpty()) {
            UIDataUtils.renderRightClickHere(context, this.area);
        }
    }
}
