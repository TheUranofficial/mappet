package com.theuran.mappet.client.ui.panels;

import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.UIBaseMenu;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIButton;

public class UITriggerBlock extends UIBaseMenu {
    public UIButton leftCLick;
    public UIButton rightClick;

    public UITriggerBlock() {
        this.leftCLick = new UIButton(IKey.raw("Редактировать ЛКМ"), (button) -> {

        });

        this.rightClick = new UIButton(IKey.raw("Редактировать ПКМ"), (button) -> {

        });

        this.leftCLick.x(0.5f);
        this.leftCLick.y(0.45f);
        this.leftCLick.wh(170, 20);

        this.rightClick.x(0.5f);
        this.rightClick.y(0.55f);
        this.rightClick.wh(170, 20);

        this.leftCLick.anchor(0.5f, 0.5f);
        this.rightClick.anchor(0.5f, 0.5f);

        this.leftCLick.relative(this.getRoot());
        this.rightClick.relative(this.getRoot());

        this.getRoot().add(this.leftCLick);
        this.getRoot().add(this.rightClick);
    }
}
