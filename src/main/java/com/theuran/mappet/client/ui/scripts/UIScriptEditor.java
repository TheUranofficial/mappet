package com.theuran.mappet.client.ui.scripts;

import com.theuran.mappet.client.ui.utils.MappetIcons;
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

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class UIScriptEditor extends UITextEditor {
    private long lastTypedTime = 0;

    private final long autocompleteDelay = 1000;
    private boolean isTypedText = false;

    private String oldText;

    public UIScriptEditor(Consumer<String> callback) {
        super(callback);

        this.callback = this::handleLogic;

        this.highlighter(new Highlighter());
    }

    @Override
    public void render(UIContext context) {
        super.render(context);

        long now = System.currentTimeMillis();

        if (now - this.lastTypedTime >= autocompleteDelay) {
            if (!this.getContext().hasContextMenu() && this.isTypedText) {
                handleAutocomplete();
                this.isTypedText = false;
            }
        }
    }

    private void handleLogic(String text) {
        if (this.oldText == null){
            this.oldText = text;
        }

        if (!this.oldText.equals(text)) {
            this.lastTypedTime = System.currentTimeMillis();
            this.isTypedText = true;

            this.getContext().closeContextMenu();
        }

        this.oldText = text;
    }

    private void handleAutocomplete() {
        Vector2d cursorPos = this.getCursorPosition(this.getFont(), this.cursor);

        String prefix = getPrefix();
        List<String> suggestions = getSuggestions(prefix);

        if (suggestions.isEmpty()) {
            this.getContext().closeContextMenu();
            return;
        }

        ContextMenuManager cmm = new ContextMenuManager();

        for (String s : suggestions) {
            cmm.action(MappetIcons.API_BBS_FORM, IKey.raw(s), () -> {
                applySuggestion(prefix, s);
            });
        }

        UISimpleContextMenu menu = cmm.create();
        this.getContext().replaceContextMenu(menu);

        menu.resize();

        int menuH = menu.area.h;

        int cursorX = (int) cursorPos.x;
        int cursorY = (int) cursorPos.y;

        int editorTop = this.area.y;
        int editorBottom = this.area.y + this.area.h;

        int lineHeight = this.getFont().getHeight();

        int padding = 15;

        int desiredY = cursorY + lineHeight + padding;

        if (desiredY + menuH > editorBottom) {
            desiredY = cursorY - menuH;

            if (desiredY < editorTop) {
                desiredY = editorTop;
            }
        }

        int desiredX = cursorX - 30;

        menu.xy(desiredX, desiredY);
        menu.resize();
    }



    private void applySuggestion(String prefix, String suggestion) {
        int pos = this.cursor.offset;

        String line = this.getCurrentLine().text;

        String before = line.substring(0, pos - prefix.length());
        String after = line.substring(pos);

        this.getCurrentLine().set(before + after);

        this.cursor.offset = before.length();

        this.pasteText(suggestion);
    }

    private List<String> getSuggestions(String prefix) {
        if (prefix.isEmpty()) {
            return Collections.emptyList();
        }

        return ((Highlighter)this.getHighlighter()).getAllKeywords().stream()
                .filter(s -> s.startsWith(prefix) && !s.equals(prefix))
                .sorted()
                .toList();
    }

    private String getPrefix() {
        String line = this.getCurrentLine().text;
        int pos = this.cursor.offset;

        int start = pos;
        while (start > 0) {
            char ch = line.charAt(start - 1);
            if (!Character.isLetterOrDigit(ch) && ch != '_') {
                break;
            }
            start--;
        }

        String prefix = line.substring(start, pos);

        if (start > 0 && line.charAt(start - 1) == '.') {
            return "";
        }

        return prefix;
    }


    @Override
    protected boolean handleKeys(UIContext context, TextEditUndo undo, boolean ctrl, boolean shift) {
        if (ctrl && (context.isPressed(GLFW.GLFW_KEY_D) || context.isRepeated(GLFW.GLFW_KEY_D))) {
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
