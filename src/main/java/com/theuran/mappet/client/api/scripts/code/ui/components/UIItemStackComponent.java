package com.theuran.mappet.client.api.scripts.code.ui.components;

import com.theuran.mappet.api.scripts.code.item.ScriptItemStack;
import mchorse.bbs_mod.ui.forms.editors.panels.widgets.UIItemStack;
import net.minecraft.item.ItemStack;

import java.util.function.Consumer;

public class UIItemStackComponent extends UIComponent<UIItemStack> {
    public UIItemStackComponent(Consumer<ScriptItemStack> consumer) {
        super(new UIItemStack(stack -> {
            consumer.accept(ScriptItemStack.create(stack));
        }));
    }

    public UIItemStackComponent stack(ItemStack stack) {
        this.element.setStack(stack);
        return this;
    }
}
