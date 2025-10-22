package com.theuran.mappet.client.ui.elements;

import mchorse.bbs_mod.ui.framework.UIContext;
import mchorse.bbs_mod.ui.framework.elements.input.text.UITextEditor;
import mchorse.bbs_mod.utils.colors.Colors;

import java.util.function.Consumer;

public class UIScriptEditor extends UITextEditor {
    public UIScriptEditor(Consumer<String> callback) {
        super(callback);
    }

    @Override
    protected void renderBackground(UIContext context) {
        this.area.render(context.batcher, Colors.A50 | Colors.mulRGB(this.getHighlighter().getStyle().background, 0.8F));
    }
}
