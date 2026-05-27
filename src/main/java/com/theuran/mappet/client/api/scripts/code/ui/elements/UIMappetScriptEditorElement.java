package com.theuran.mappet.client.api.scripts.code.ui.elements;

import com.theuran.mappet.client.ui.scripts.UIScriptEditor;
import mchorse.bbs_mod.ui.framework.UIContext;
import mchorse.bbs_mod.utils.colors.Colors;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class UIMappetScriptEditorElement extends UIScriptEditor {
    public float textAlpha = 1f;
    public float backgroundAlpha = 1f;

    public UIMappetScriptEditorElement(Consumer<String> callback) {
        super(callback);
    }

    public UIMappetScriptEditorElement textAlpha(float alpha) {
        this.textAlpha = alpha;

        return this;
    }

    public UIMappetScriptEditorElement backgroundAlpha(float alpha) {
        this.backgroundAlpha = alpha;

        return this;
    }

    @Override
    public void renderBackground(UIContext context) {
        this.area.render(context.batcher, Colors.setA(Colors.mulRGB(this.getHighlighter().getStyle().background, 0.8F), this.backgroundAlpha));
    }
}
