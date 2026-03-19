package com.theuran.mappet.client.ui.panels;

import com.theuran.mappet.block.blocks.entities.TriggerBlockEntity;
import mchorse.bbs_mod.ui.framework.UIContext;
import mchorse.bbs_mod.ui.framework.elements.input.list.UIList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.List;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class UITriggerBlockEntityList extends UIList<TriggerBlockEntity>
{
    public UITriggerBlockEntityList(Consumer<List<TriggerBlockEntity>> callback)
    {
        super(callback);
        this.scroll.scrollItemSize = 16;
    }

    @Override
    protected String elementToString(UIContext context, int i, TriggerBlockEntity element)
    {
        return element.getPos().toString();
    }
}
