package com.theuran.mappet.client.ui.panels;

import com.theuran.mappet.api.ui.UI;
import com.theuran.mappet.client.ui.MappetContentType;
import com.theuran.mappet.client.ui.UIMappetKeys;
import com.theuran.mappet.client.ui.uibilder.ElementsOverlayPanel;
import mchorse.bbs_mod.l10n.L10n;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.ContentType;
import mchorse.bbs_mod.ui.UIKeys;
import mchorse.bbs_mod.ui.dashboard.UIDashboard;
import mchorse.bbs_mod.ui.dashboard.panels.UIDataDashboardPanel;
import mchorse.bbs_mod.ui.film.utils.undo.UIUndoHistoryOverlay;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIIcon;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlay;
import mchorse.bbs_mod.ui.utils.icons.Icons;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static com.theuran.mappet.client.ui.UIMappetKeys.*;

@Environment(EnvType.CLIENT)
public class UIBuilderPanel extends UIDataDashboardPanel<UI> {
    private final UIIcon addIcon;
    private final UIIcon scriptIcon;
    private UIIcon modulesPicker;

    public static List<String> UInames = Arrays.asList(
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

        //Сюда регистер всяких ui элементов

        this.modulesPicker = new UIIcon(Icons.SERVER, this :: openElements);
        this.modulesPicker.tooltip(ElementPanel);
        this.iconBar.add(this.modulesPicker);

        this.addIcon = new UIIcon(Icons.ADD, this :: addElement);
        this.addIcon.tooltip(ElementAdd);
        this.iconBar.add(this.addIcon);

        this.scriptIcon = new UIIcon(Icons.PROPERTIES, this :: openUIScript);
        this.iconBar.add(this.scriptIcon);
        this.scriptIcon.tooltip(UIScript);

        this.addIcon.context((cont) -> {
            for (Object name : UInames) {
                cont.action(Icons.BLOCK, IKey.raw(String.valueOf(name)), () -> {
                    //тут будут взаимодействия (добавлению UI элементов)
                });
            }
        });

        this.fill(null);
    }

    private void openUIScript(UIIcon uiIcon) {
    }

    private void addElement(UIIcon uiIcon) {
    }

    private void openElements(UIIcon uiIcon) {
        UIOverlay.addOverlayRight(this.getContext(), new ElementsOverlayPanel(), 200, 1);
    }

    @Override
    public ContentType getType() {
        return MappetContentType.UIS;
    }

    @Override
    protected void fillData(UI ui) {
        //Обновление даты
    }

    @Override
    protected IKey getTitle() {
        return UIMappetKeys.UI_BUILDER_TITLE;
    }
}
