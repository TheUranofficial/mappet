package com.theuran.mappet.client.api.scripts.code.ui.components;

import mchorse.bbs_mod.ui.framework.elements.input.text.UITextbox;

import java.util.function.Consumer;

public class UITextboxComponent extends UIComponent<UITextbox>{
    public UITextboxComponent(int maxLength, Consumer<String> consumer) {
        super(new UITextbox(maxLength, consumer));
    }


}
