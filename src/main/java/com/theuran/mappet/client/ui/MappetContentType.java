package com.theuran.mappet.client.ui;

import com.theuran.mappet.Mappet;
import com.theuran.mappet.api.huds.HUDManager;
import com.theuran.mappet.api.huds.HUDRepository;
import com.theuran.mappet.api.huds.HUDScene;
import com.theuran.mappet.api.scripts.Script;
import com.theuran.mappet.api.scripts.ScriptManager;
import com.theuran.mappet.api.scripts.ScriptRepository;
import com.theuran.mappet.api.ui.UI;
import com.theuran.mappet.api.ui.UIManager;
import com.theuran.mappet.api.ui.UIRepository;
import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.client.ui.panels.UIBuilderPanel;
import com.theuran.mappet.client.ui.panels.UIHUDScenePanel;
import com.theuran.mappet.client.ui.panels.UIScriptPanel;
import mchorse.bbs_mod.ui.ContentType;
import mchorse.bbs_mod.utils.repos.FolderManagerRepository;
import mchorse.bbs_mod.utils.repos.IRepository;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

import java.io.File;

@Environment(EnvType.CLIENT)
public class MappetContentType {
    public static final ContentType HUDS = new ContentType("huds", MappetContentType::getHudRepository, dashboard -> dashboard.getPanel(UIHUDScenePanel.class));
    public static final ContentType SCRIPTS = new ContentType("scripts", MappetContentType::getScriptRepository, dashboard -> dashboard.getPanel(UIScriptPanel.class));
    public static final ContentType UIS = new ContentType("uis", MappetContentType::getUIRepository, dashboard -> dashboard.getPanel(UIBuilderPanel.class));

    private static IRepository<Script> getScriptRepository() {
        if (MinecraftClient.getInstance().isIntegratedServerRunning()) {
            return new FolderManagerRepository<>(Mappet.getScripts());
        } else {
            return MappetClient.isMappetModOnServer ? new ScriptRepository() : new FolderManagerRepository<>(new ScriptManager(() -> new File(Mappet.getAssetsFolder().getParentFile(), "data/scripts")));
        }
    }

    private static IRepository<HUDScene> getHudRepository() {
        if (MinecraftClient.getInstance().isIntegratedServerRunning()) {
            return new FolderManagerRepository<>(Mappet.getHuds());
        } else {
            return MappetClient.isMappetModOnServer ? new HUDRepository() : new FolderManagerRepository<>(new HUDManager(() -> new File(Mappet.getAssetsFolder().getParentFile(), "data/huds")));
        }
    }

    private static IRepository<UI> getUIRepository() {
        if (MinecraftClient.getInstance().isIntegratedServerRunning()) {
            return new FolderManagerRepository<>(Mappet.getUIs());
        } else {
            return MappetClient.isMappetModOnServer ? new UIRepository() : new FolderManagerRepository<>(new UIManager(() -> new File(Mappet.getAssetsFolder().getParentFile(), "data/uis")));
        }
    }
}
