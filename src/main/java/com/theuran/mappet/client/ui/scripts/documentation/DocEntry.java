package com.theuran.mappet.client.ui.scripts.documentation;

import com.theuran.mappet.client.ui.scripts.UIScriptEditor;
import mchorse.bbs_mod.data.types.MapType;
import mchorse.bbs_mod.ui.framework.elements.UIScrollView;
import mchorse.bbs_mod.ui.framework.elements.utils.UIText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class DocEntry {
    public DocEntry parent;

    public String name = "";
    public String doc = "";

    public static String processCode(String code) {
        List<String> strings = new ArrayList<>(Arrays.asList(code.split("\n")));
        int first = 0;

        /* Find first non-empty string */
        for (String string : strings) {
            if (string.trim().isEmpty()) {
                first += 1;
            } else {
                break;
            }
        }

        /* Once first string is found, find the first string's indentation*/
        String firstLine = strings.get(first);
        int indent = 0;

        for (int i = 0; i < firstLine.length(); i++) {
            if (firstLine.charAt(i) == ' ') {
                indent += 1;
            } else {
                break;
            }
        }

        /* Remove last string which should contain "}</pre>" */
        strings.removeLast();

        /* Remove the first line's indentation from the rest of the code */
        if (indent > 0) {
            for (int i = 0; i < strings.size(); i++) {
                String string = strings.get(i);

                if (string.length() > indent) {
                    strings.set(i, string.substring(indent));
                }
            }
        }

        return String.join("\n", strings).trim();
    }

    public static void process(String doc, UIScrollView target) {
        String[] splits = doc.split("\n{2,}");
        boolean parsing = false;
        StringBuilder code = new StringBuilder();

        for (String line : splits) {
            if (line.trim().startsWith("<pre>{@code")) {
                parsing = true;
                line = line.trim().substring("<pre>{@code".length() + 1);
            }

            if (parsing) {
                code.append("\n\n").append(line);
            } else {
                boolean p = line.trim().startsWith("<p>");

                line = line.replaceAll("\n", "").trim();
                line = line.replaceAll("<b>", "");
                line = line.replaceAll("<i>", "§i");
                line = line.replaceAll("<s>", "");
                line = line.replaceAll("<code>", "§2");
                line = line.replaceAll("<li> *", "\n- ");
                line = line.replaceAll("</(b|i|s|code|ul|li)>", "§r");
                line = line.replaceAll("</?(p|ul|li)>", "");
                line = line.replaceAll("\\{@link +[^}]+\\.([^}]+)}", "§7$1§r");
                line = line.replaceAll("\\{@link +([^}]*)#([^}]+)}", "§7$1§r.§7$2§r");
                line = line.replaceAll("\\{@link ([^}]+)}", "§7$1§r");
                line = line.replaceAll("&lt;", "<");
                line = line.replaceAll("&gt;", ">");
                line = line.replaceAll("&amp;", "&");

                UIText text = new UIText(line.trim().replaceAll(" {2,}", " "));

                if (p) {
                    text.marginTop(12);
                }

                target.add(text);
            }

            if (line.trim().endsWith("}</pre>")) {
                UIScriptEditor editor = new UIScriptEditor(null);
                String text = processCode(code.toString()).replaceAll("§", "\\\\u00A7");

                editor.setText(text);
                editor.background().h(editor.getLines().size() * 12 + 20);

                if (!target.getChildren().isEmpty()) {
                    editor.marginTop(6);
                }

                target.add(editor);

                parsing = false;
                code = new StringBuilder();
            }
        }
    }

    public void fillIn(UIScrollView target) {
        process(this.doc, target);
    }

    public List<DocEntry> getEntries() {
        return Collections.emptyList();
    }

    public String getName() {
        int index = this.name.lastIndexOf(".");

        if (index < 0) {
            return this.name;
        }

        return this.name.substring(index + 1);
    }

    public void fromData(MapType data) {
        this.name = data.getString("name");
        this.doc = data.getString("doc");
    }
}