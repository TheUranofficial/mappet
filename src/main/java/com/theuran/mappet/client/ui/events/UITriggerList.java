package com.theuran.mappet.client.ui.events;

import com.theuran.mappet.api.triggers.Trigger;
import mchorse.bbs_mod.ui.framework.elements.input.list.UIList;

import java.util.List;
import java.util.function.Consumer;

public class UITriggerList extends UIList<Trigger> {
    public UITriggerList(Consumer<List<Trigger>> callback) {
        super(callback);
    }
}
