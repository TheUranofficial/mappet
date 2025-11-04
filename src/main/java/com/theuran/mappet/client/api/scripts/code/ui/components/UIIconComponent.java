package com.theuran.mappet.client.api.scripts.code.ui.components;

import mchorse.bbs_mod.ui.framework.elements.buttons.UIIcon;
import mchorse.bbs_mod.ui.utils.icons.Icon;
import mchorse.bbs_mod.ui.utils.icons.Icons;

public class UIIconComponent extends UIComponent<UIIcon>{
    public UIIconComponent(String id, Runnable onClick) {
        super(new UIIcon(Icons.ICONS.get(id), (uiIcon -> {
            if (onClick != null) {
                onClick.run();
            }
        })));
    }
}
