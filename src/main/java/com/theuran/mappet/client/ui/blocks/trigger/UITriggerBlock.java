package com.theuran.mappet.client.ui.blocks.trigger;

import com.theuran.mappet.client.ui.utils.UIMappetTrackpad;
import com.theuran.mappet.client.ui.utils.UIMappetTransform;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.UIBaseMenu;
import mchorse.bbs_mod.ui.framework.elements.UIElement;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIButton;
import mchorse.bbs_mod.ui.framework.elements.input.UITrackpad;

public class UITriggerBlock extends UIBaseMenu {
    public UIElement layout;

    public UIButton leftCLick;
    public UIButton rightClick;

    public UIMappetTransform transform;

    public UITriggerBlock() {
        this.layout = new UIElement();

        this.layout.xy(0.5f, 0.5f);
        this.layout.wh(0.25f, 0.25f);
        this.layout.anchor(0.5f, 0.5f);

        this.leftCLick = new UIButton(IKey.raw("Редактировать ЛКМ"), (button) -> {

        });

        this.leftCLick.xy(0.5f, 0f);
        this.leftCLick.wh(170, 20);
        this.leftCLick.anchor(0.5f, 0.5f);

        this.rightClick = new UIButton(IKey.raw("Редактировать ПКМ"), (button) -> {

        });

        this.rightClick.xy(0.5f, 0.5f);
        this.rightClick.wh(170, 20);
        this.rightClick.anchor(0.5f, 0.5f);

        this.transform = new UIMappetTransform();

        this.transform.wh(1f, 0.4f);
        this.transform.xy(0.5f, 1f);
        this.transform.anchor(0.5f, 0.5f);

        this.leftCLick.relative(this.layout);
        this.rightClick.relative(this.layout);
        this.transform.relative(this.layout);

        this.layout.add(this.leftCLick);
        this.layout.add(this.rightClick);
        this.layout.add(this.transform);

        this.layout.relative(this.getRoot());

        this.getRoot().add(this.layout);
    }
}
