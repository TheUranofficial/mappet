package com.theuran.mappet.client.ui.scripts;

import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.UIContext;
import mchorse.bbs_mod.ui.framework.elements.context.UISimpleContextMenu;
import mchorse.bbs_mod.ui.framework.elements.input.list.UILabelList;
import mchorse.bbs_mod.ui.framework.elements.input.text.UITextEditor;
import mchorse.bbs_mod.ui.framework.elements.input.text.highlighting.HighlightedTextLine;
import mchorse.bbs_mod.ui.framework.elements.input.text.undo.TextEditUndo;
import mchorse.bbs_mod.ui.framework.elements.input.text.utils.Cursor;
import mchorse.bbs_mod.ui.framework.elements.utils.UILabel;
import mchorse.bbs_mod.ui.utils.context.ContextMenuManager;
import mchorse.bbs_mod.utils.colors.Colors;
import org.joml.Vector2d;
import org.lwjgl.glfw.GLFW;

import java.util.function.Consumer;

public class UIScriptEditor extends UITextEditor {

    public UIScriptEditor(Consumer<String> callback) {
        super(callback);

        this.callback = this::handleLogic;

        this.highlighter(new Highlighter());
    }

    private void handleLogic(String text) {
        Vector2d cursorPosition = this.getCursorPosition(this.getFont(), this.cursor);

        String c = this.getCurrentLine().text.substring(this.cursor.offset-1, this.cursor.offset);


        if (c.equals(".")) {
            ContextMenuManager cmm = new ContextMenuManager();

            cmm.action(IKey.raw("lox"), () -> {});

            UISimpleContextMenu contextMenu = cmm.create();

            this.getContext().replaceContextMenu(contextMenu);

            contextMenu.xy((int) cursorPosition.x+10-50, (int) cursorPosition.y-12);
            contextMenu.resize();
        } else {
            this.getContext().closeContextMenu();
        }
    }

    @Override
    protected boolean handleKeys(UIContext context, TextEditUndo undo, boolean ctrl, boolean shift) {
        if (ctrl && context.isReleased(GLFW.GLFW_KEY_D)) {
            String currentLineText = this.getCurrentLine().text;

            undo.text = "";
            undo.cursor.copy(new Cursor(this.cursor.line, currentLineText.length()));
            undo.selection.copy(new Cursor(-1, 0));

            int newLineIndex = this.cursor.line + 1;
            this.addLine(newLineIndex, currentLineText);

            this.cursor.set(newLineIndex, Math.min(this.cursor.offset, currentLineText.length()));

            undo.post("\n" + currentLineText, this.cursor, new Cursor(-1, 0));

            undo.ready();
            return true;
        }
        return super.handleKeys(context, undo, ctrl, shift);
    }

    private HighlightedTextLine getLine(int line) {
        return this.getLines().get(line);
    }

    private void addLine(HighlightedTextLine textLine) {
        this.getLines().add(textLine);
    }

    private void addLine(int line, String text) {
        this.getLines().add(line, new HighlightedTextLine(text));
    }

    private HighlightedTextLine getCurrentLine() {
        return this.getLines().get(this.cursor.line);
    }

    @Override
    public void renderBackground(UIContext context) {
        this.area.render(context.batcher, Colors.A50 | Colors.mulRGB(this.getHighlighter().getStyle().background, 0.8F));
    }
}
