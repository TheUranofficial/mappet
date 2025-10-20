package com.theuran.mappet.client.ui;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.huds.HUDScene;
import com.theuran.mappet.client.ui.panels.UIHUDScenePanel;
import mchorse.bbs_mod.ui.ContentType;
import mchorse.bbs_mod.utils.repos.FolderManagerRepository;
import mchorse.bbs_mod.utils.repos.IRepository;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

//TODO: Make a enough repositories for huds
@Environment(EnvType.CLIENT)
public class MappetContentType {
    private static final IRepository<HUDScene> HUD_REPOSITORY = new FolderManagerRepository<>(Mappet.getHuds());

    public static final ContentType HUDS = new ContentType("huds", () -> HUD_REPOSITORY, dashboard -> dashboard.getPanel(UIHUDScenePanel.class));
}
