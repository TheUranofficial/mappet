package com.theuran.mappet.client.ui;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.huds.HUDScene;
import com.theuran.mappet.api.scripts.Script;
import com.theuran.mappet.client.ui.panels.UIHUDScenePanel;
import com.theuran.mappet.client.ui.panels.UIScriptPanel;
import mchorse.bbs_mod.ui.ContentType;
import mchorse.bbs_mod.utils.repos.FolderManagerRepository;
import mchorse.bbs_mod.utils.repos.IRepository;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

//TODO: Make a enough repositories
@Environment(EnvType.CLIENT)
public class MappetContentType {
    private static final IRepository<HUDScene> HUD_REPOSITORY = new FolderManagerRepository<>(Mappet.getHuds());
    private static final IRepository<Script> SCRIPTS_REPOSITORY = new FolderManagerRepository<>(Mappet.getScripts());

    public static final ContentType HUDS = new ContentType("huds", () -> HUD_REPOSITORY, dashboard -> dashboard.getPanel(UIHUDScenePanel.class));

    public static final ContentType SCRIPTS = new ContentType("scripts", () -> SCRIPTS_REPOSITORY, dashboard -> dashboard.getPanel(UIScriptPanel.class));
}
