package com.theuran.mappet.client.ui.states;

import com.theuran.mappet.client.ui.UIMappetKeys;
import mchorse.bbs_mod.ui.film.clips.UIIdleClip;
import mchorse.bbs_mod.ui.framework.elements.UIScrollView;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIIcon;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;
import mchorse.bbs_mod.ui.utils.ScrollDirection;
import mchorse.bbs_mod.ui.utils.icons.Icons;
import mchorse.bbs_mod.utils.Direction;

public class UIStatesOverlayPanel extends UIOverlayPanel {
    public UIScrollView states;

    public UIIcon addIcon;
    public UIIcon searchIcon;

    public UIStatesOverlayPanel() {
        super(UIMappetKeys.STATES_TITLE);
        this.states = new UIScrollView(ScrollDirection.VERTICAL);
        this.states.scroll.scrollSpeed = 51;
        this.states.full(this.content);
        this.states.column().scroll().vertical().stretch().padding(10).height(20);

        this.addIcon = new UIIcon(Icons.ADD, icon -> {});
        this.addIcon.tooltip(UIMappetKeys.STATES_ADD, Direction.RIGHT);
        this.searchIcon = new UIIcon(Icons.SEARCH, icon -> {});
        this.searchIcon.tooltip(UIMappetKeys.STATES_SEARCH, Direction.RIGHT);

        this.icons.add(this.addIcon, this.searchIcon);

        this.add(this.states);
        this.markContainer();
    }
}
