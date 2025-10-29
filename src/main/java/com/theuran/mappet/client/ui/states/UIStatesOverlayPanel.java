package com.theuran.mappet.client.ui.states;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.client.ui.UIMappetKeys;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIIcon;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;
import mchorse.bbs_mod.ui.utils.context.ContextMenuManager;
import mchorse.bbs_mod.ui.utils.icons.Icons;
import mchorse.bbs_mod.utils.Direction;

public class UIStatesOverlayPanel extends UIOverlayPanel {
    public UIStates states;
    public UIIcon addIcon;
    public UIIcon searchIcon;

    public UIStatesOverlayPanel() {
        super(UIMappetKeys.STATES_TITLE);

        this.states = new UIStates();
        this.states.set(Mappet.getStates().get()).full(this.content);

        this.addIcon = new UIIcon(Icons.ADD, icon -> this.states.addNew());
        this.addIcon.tooltip(UIMappetKeys.STATES_ADD, Direction.LEFT);
        this.addIcon.context(this::addContext);

        this.states.context(this::addContext);

        this.searchIcon = new UIIcon(Icons.SEARCH, icon -> {
        });
        this.searchIcon.tooltip(UIMappetKeys.STATES_SEARCH, Direction.LEFT);

        this.icons.add(this.addIcon, this.searchIcon);

        this.add(this.states);
        this.markContainer();
    }

    private void addContext(ContextMenuManager menu) {
        menu.action(Icons.ADD, UIMappetKeys.STATES_ADD, () -> this.states.addNew());
        menu.action(Icons.FOLDER, UIMappetKeys.STATES_ADD_GROUP, null);
    }
}
