package com.theuran.mappet.client.ui.scripts;

import mchorse.bbs_mod.ui.framework.elements.input.text.highlighting.BaseSyntaxHighlighter;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.*;

@Environment(EnvType.CLIENT)
public class Highlighter extends BaseSyntaxHighlighter {
    public Highlighter() {
        this.primaryKeywords = new HashSet<>(Arrays.asList("break", "continue", "switch", "case", "default", "try", "catch", "delete", "do", "while", "else", "finally", "if", "else", "for", "each", "in", "instanceof", "new", "throw", "typeof", "with", "yield", "return"));
        this.secondaryKeywords = new HashSet<>(Arrays.asList("const", "function", "class", "var", "let", "prototype", "Math", "JSON", "bbs", "mappet", "event"));
        this.identifierKeywords = new HashSet<>(Arrays.asList("uran", "llama"));
        this.special = new HashSet<>(Arrays.asList("this", "arguments"));
        this.typeKeyswords = new HashSet<>(Arrays.asList("true", "false", "null", "undefined"));
    }

    public Set<String> getPrimaryKeywords() {
        return this.primaryKeywords;
    }

    public Set<String> getSecondaryKeywords() {
        return this.secondaryKeywords;
    }

    public Set<String> getIdentifierKeywords() {
        return this.identifierKeywords;
    }

    public Set<String> getSpecialKeywords() {
        return this.special;
    }

    public Set<String> getTypeKeywords() {
        return this.typeKeyswords;
    }

    public List<String> getAllKeywords() {
        Set<String> result = new HashSet<>();

        result.addAll(this.getPrimaryKeywords());
        result.addAll(this.getSecondaryKeywords());
        result.addAll(this.getIdentifierKeywords());
        result.addAll(this.getSpecialKeywords());
        result.addAll(this.getTypeKeywords());

        return new ArrayList<>(result);
    }
}
