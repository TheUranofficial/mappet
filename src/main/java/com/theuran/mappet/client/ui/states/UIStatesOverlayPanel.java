package com.theuran.mappet.client.ui.states;

import com.theuran.mappet.client.ui.UIMappetKeys;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIIcon;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;
import mchorse.bbs_mod.ui.utils.icons.Icons;
import mchorse.bbs_mod.utils.Direction;

public class UIStatesOverlayPanel extends UIOverlayPanel {
    private UIStates states;

    public UIIcon addIcon;
    public UIIcon searchIcon;

    public UIStatesOverlayPanel() {
        super(UIMappetKeys.STATES_TITLE);

        this.states = new UIStates();
        this.states.full(this.content);

        this.addIcon = new UIIcon(Icons.ADD, icon -> {
            this.states.add(new UIState(""));
            this.resize();
        });
        this.addIcon.tooltip(UIMappetKeys.STATES_ADD, Direction.LEFT);
        this.addIcon.context(menu -> {
           menu.action(Icons.FOLDER, UIMappetKeys.STATES_ADD_GROUP, null);
        });

        this.searchIcon = new UIIcon(Icons.SEARCH, icon -> {});
        this.searchIcon.tooltip(UIMappetKeys.STATES_SEARCH, Direction.LEFT);

        this.icons.add(this.addIcon, this.searchIcon);

        this.add(this.states);
        this.markContainer();
    }
}
