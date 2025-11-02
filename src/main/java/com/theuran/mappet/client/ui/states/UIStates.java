package com.theuran.mappet.client.ui.states;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.client.ui.UIMappetKeys;
import mchorse.bbs_mod.ui.framework.elements.UIScrollView;

public class UIStates extends UIScrollView {
    public UIStates() {
        super();

        this.add(new UIStateGroup(UIMappetKeys.SERVER_TITLE, Mappet.getStates().get()));

        this.column().scroll().vertical().stretch().padding(5);
    }
}
