package com.theuran.mappet.client.ui.states;

import com.theuran.mappet.api.states.States;
import mchorse.bbs_mod.ui.framework.elements.UIScrollView;

import java.util.Comparator;

public class UIStates extends UIScrollView {
    private States states;

    public UIStates() {
        super();

        this.column().scroll().vertical().stretch().padding(5);
    }

    public UIStates set(States states) {
        this.states = states;

        this.removeAll();

        //йоу че каво а я так умею питухон стайл рулит
        if (states != null)
            for (String key : states.keys())
                this.add(new UIState(key, states));

        this.sortElements();
        this.resize();

        return this;
    }

    public void addNew() {
        if (this.states == null)
            return;

        int index = this.states.size();
        String key;

        do {
            index += 1;
            key = "state_" + index;
        } while (this.states.has(key));

        this.states.setString(key, "");
        this.add(new UIState(key, this.states));

        this.sortElements();
        this.getParentContainer().resize();
    }

    private void sortElements() {
        this.getChildren().sort(Comparator.comparing(state -> ((UIState) state).getKey()));
    }
}
