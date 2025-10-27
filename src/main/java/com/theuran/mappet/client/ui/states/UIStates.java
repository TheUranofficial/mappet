package com.theuran.mappet.client.ui.states;

import mchorse.bbs_mod.ui.framework.elements.UIScrollView;

public class UIStates extends UIScrollView {
    public UIStates() {
        super();

        this.column().scroll().vertical().stretch().padding(5);
    }
}
