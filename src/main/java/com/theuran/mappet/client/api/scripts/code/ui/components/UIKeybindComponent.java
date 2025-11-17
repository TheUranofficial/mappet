package com.theuran.mappet.client.api.scripts.code.ui.components;

import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.elements.input.UIKeybind;
import mchorse.bbs_mod.ui.utils.keys.KeyCombo;

import java.util.function.Consumer;

public class UIKeybindComponent extends UIComponent<UIKeybind> {
    public UIKeybindComponent(Consumer<String> consumer) {
        super(new UIKeybind(keyCombo -> {
            consumer.accept(keyCombo.getKeyCombo());
        }));
    }

    public UIKeybindComponent single() {
        this.element.single();
        return this;
    }

    public UIKeybindComponent mouse() {
        this.element.mouse();
        return this;
    }

    public UIKeybindComponent escape() {
        this.element.escape();
        return this;
    }

    public UIKeybindComponent keyCombo(String label, int... keys) {
        this.element.setKeyCombo(new KeyCombo(IKey.raw(label), keys));
        return this;
    }
}
