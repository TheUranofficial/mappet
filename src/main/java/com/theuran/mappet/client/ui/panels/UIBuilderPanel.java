package com.theuran.mappet.client.ui.panels;

import com.theuran.mappet.api.ui.UI;
import com.theuran.mappet.client.ui.MappetContentType;
import com.theuran.mappet.client.ui.UIMappetKeys;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.ContentType;
import mchorse.bbs_mod.ui.dashboard.UIDashboard;
import mchorse.bbs_mod.ui.dashboard.panels.UIDataDashboardPanel;
import mchorse.bbs_mod.ui.utils.icons.Icons;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class UIBuilderPanel extends UIDataDashboardPanel<UI> {
    public UIBuilderPanel(UIDashboard dashboard) {
        super(dashboard);

        this.overlay.namesList.setFileIcon(Icons.MAZE);

        //Сюда регистер всяких ui элементов

        this.fill(null);
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
