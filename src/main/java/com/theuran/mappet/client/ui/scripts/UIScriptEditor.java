package com.theuran.mappet.client.ui.scripts;

import mchorse.bbs_mod.ui.framework.UIContext;
import mchorse.bbs_mod.ui.framework.elements.input.text.UITextEditor;
import mchorse.bbs_mod.ui.framework.elements.input.text.undo.TextEditUndo;
import mchorse.bbs_mod.utils.colors.Colors;
import org.lwjgl.glfw.GLFW;

import java.util.function.Consumer;

public class UIScriptEditor extends UITextEditor {
    public UIScriptEditor(Consumer<String> callback) {
        super(callback);

        this.highlighter(new Highlighter());
    }

    @Override
    protected boolean handleKeys(UIContext context, TextEditUndo undo, boolean ctrl, boolean shift) {
        if (ctrl && context.isReleased(GLFW.GLFW_KEY_D)) {
            //CODE
        }
        return super.handleKeys(context, undo, ctrl, shift);
    }

    @Override
    protected void renderBackground(UIContext context) {
        this.area.render(context.batcher, Colors.A50 | Colors.mulRGB(this.getHighlighter().getStyle().background, 0.8F));
    }
}
