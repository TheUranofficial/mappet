package com.theuran.mappet.client.ui.states;

import com.theuran.mappet.api.states.States;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.elements.UIScrollView;

import java.util.Map;

public class UIStates extends UIScrollView {
    private Map<String, States> states;

    public UIStates() {
        super();

        this.column().scroll().vertical().stretch().padding(5);
        this.markContainer();
    }

    public void set(Map<String, States> states) {
        this.states = states;

        this.removeAll();

        for (String key : states.keySet()) {
            if (states.get(key) != null) {
                this.add(new UIStateGroup(IKey.constant(key), states.get(key)));
            }
        }

        this.resize();
    }

    public Map<String, States> get() {
        return this.states;
    }
}
