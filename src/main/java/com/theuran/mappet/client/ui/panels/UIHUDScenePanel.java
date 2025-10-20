package com.theuran.mappet.client.ui.panels;

import com.theuran.mappet.api.huds.HUDScene;
import com.theuran.mappet.client.ui.MappetContentType;
import com.theuran.mappet.client.ui.UIMappetKeys;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.ContentType;
import mchorse.bbs_mod.ui.dashboard.UIDashboard;
import mchorse.bbs_mod.ui.dashboard.panels.UIDataDashboardPanel;
import mchorse.bbs_mod.ui.forms.UINestedEdit;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIIcon;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIToggle;
import mchorse.bbs_mod.ui.framework.elements.input.UITrackpad;
import mchorse.bbs_mod.ui.utils.icons.Icons;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class UIHUDScenePanel extends UIDataDashboardPanel<HUDScene> {
    public UIIcon morphs;
    public UINestedEdit morph;
    public UIToggle ortho;
    public UITrackpad orthoX;
    public UITrackpad orthoY;
    public UITrackpad expire;

    public UIHUDScenePanel(UIDashboard dashboard) {
        super(dashboard);

        this.overlay.namesList.setFileIcon(Icons.POSE);

        this.morphs = new UIIcon(Icons.MORE, icon -> this.openMorphs());
        this.morph = new UINestedEdit(this::openMorphMenu);
        this.ortho = new UIToggle(UIMappetKeys.HUD_SCENE_ORTHO, toggle -> {});

        this.editor.add(this.morphs);

        this.fill(null);
    }

    private void openMorphMenu(Boolean bool) {
    }

    private void openMorphs() {

    }

    @Override
    public ContentType getType() {
        return MappetContentType.HUDS;
    }

    @Override
    protected void fillData(HUDScene hudScene) {

    }

    @Override
    protected IKey getTitle() {
        return UIMappetKeys.HUD_SCENE_TITLE;
    }
}
