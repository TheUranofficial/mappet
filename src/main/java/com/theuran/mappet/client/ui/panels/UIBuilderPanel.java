package com.theuran.mappet.client.ui.panels;

import com.theuran.mappet.api.ui.UI;
import com.theuran.mappet.client.ui.MappetContentType;
import com.theuran.mappet.client.ui.UIMappetKeys;
import com.theuran.mappet.client.ui.builder.UIElementsOverlayPanel;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.ContentType;
import mchorse.bbs_mod.ui.dashboard.UIDashboard;
import mchorse.bbs_mod.ui.dashboard.panels.UIDataDashboardPanel;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIIcon;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlay;
import mchorse.bbs_mod.ui.utils.icons.Icons;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.Arrays;
import java.util.List;

@Environment(EnvType.CLIENT)
public class UIBuilderPanel extends UIDataDashboardPanel<UI> {
    public UIIcon addIcon;
    public UIIcon scriptIcon;
    public UIIcon modulesPicker;

    public static List<String> names = Arrays.asList(
            "Button",
            "Icon",
            "Label",
            "TextBox",
            "Toggle",
            "TrackPad",
            "Morph"
    );

    public UIBuilderPanel(UIDashboard dashboard) {
        super(dashboard);

        this.overlay.namesList.setFileIcon(Icons.MAZE);

        this.modulesPicker = new UIIcon(Icons.SERVER, this::openElements);
        this.modulesPicker.tooltip(UIMappetKeys.UI_BUILDER_ELEMENT_PANEL);

        this.addIcon = new UIIcon(Icons.ADD, this::addElement);
        this.addIcon.tooltip(UIMappetKeys.UI_BUILDER_ELEMENT_ADD);

        this.scriptIcon = new UIIcon(Icons.PROPERTIES, this :: openUIScript);
        this.scriptIcon.tooltip(UIMappetKeys.UI_BUILDER_SCRIPT);

        this.addIcon.context((menu) -> {
            for (String name : names) {
                menu.action(Icons.BLOCK, IKey.constant(name), () -> {
                    //тут будут взаимодействия (добавлению UI элементов)
                });
            }
        });

        this.iconBar.add(this.modulesPicker, this.addIcon, this.scriptIcon);

        this.fill(null);
    }

    private void openUIScript(UIIcon uiIcon) {
    }

    private void addElement(UIIcon uiIcon) {
    }

    private void openElements(UIIcon uiIcon) {
        UIOverlay.addOverlayRight(this.getContext(), new UIElementsOverlayPanel(), 200, 1);
    }

    @Override
    public ContentType getType() {
        return MappetContentType.UIS;
    }

    @Override
    protected void fillData(UI data) {
        //Бро тебе стоит познакомиться получше со всей этой темой
        this.modulesPicker.setEnabled(data != null);
        this.addIcon.setEnabled(data != null);
        this.scriptIcon.setEnabled(data != null);
    }

    @Override
    protected IKey getTitle() {
        return UIMappetKeys.UI_BUILDER_TITLE;
    }
}
