package com.theuran.mappet.client.ui.states;

import com.theuran.mappet.api.states.States;
import mchorse.bbs_mod.ui.framework.elements.UIScrollView;

import java.util.Comparator;

public class UIStatesEditor extends UIScrollView {
    private States states;

    public UIStatesEditor() {
        super();

        this.column(5).vertical().stretch().scroll().padding(10);
    }

    public States getStates() {
        return this.states;
    }

    public UIStatesEditor setStates(States states) {
        this.states = states;

        this.removeAll();

        if (states != null) {
            for (String key : states.keys()) {
                this.add(new UIState(key, states));
            }
        }
        
        this.sortElements();
        this.resize();

        return this;
    }

    private void sortElements() {
        this.getChildren().sort(Comparator.comparing(a -> ((UIState) a).getKey()));
    }

    public void addNew() {
        if (this.states == null)
            return;

        int index = this.states.size() + 1;
        String key = "state_" + index;

        while (this.states.has(key)) {
            index += 1;
            key = "state_" + index;
        }

        this.states.setNumber(key, 0);
        this.add(new UIState(key, this.states));

        this.sortElements();

        this.getParentContainer().resize();
    }
}
