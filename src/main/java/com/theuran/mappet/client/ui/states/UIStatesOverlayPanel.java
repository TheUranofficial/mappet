package com.theuran.mappet.client.ui.states;

import com.theuran.mappet.client.ui.UIMappetKeys;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIIcon;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;
import mchorse.bbs_mod.ui.utils.icons.Icons;
import mchorse.bbs_mod.utils.Direction;

public class UIStatesOverlayPanel extends UIOverlayPanel {
    public UIStates states;
    public UIIcon searchIcon;

    public UIStatesOverlayPanel() {
        super(UIMappetKeys.STATES_TITLE);

        this.states = new UIStates();
        this.states.full(this.content);

        this.searchIcon = new UIIcon(Icons.SEARCH, icon -> {
        });
        this.searchIcon.tooltip(UIMappetKeys.STATES_SEARCH, Direction.LEFT);

        this.icons.add(this.searchIcon);

        this.add(this.states);
        this.states.markContainer();
    }
}
