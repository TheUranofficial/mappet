package com.theuran.mappet.client.ui.panels;

import com.theuran.mappet.api.scripts.Script;
import com.theuran.mappet.client.ui.MappetContentType;
import com.theuran.mappet.client.ui.UIMappetKeys;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.ContentType;
import mchorse.bbs_mod.ui.dashboard.UIDashboard;
import mchorse.bbs_mod.ui.dashboard.panels.UIDataDashboardPanel;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIIcon;
import mchorse.bbs_mod.ui.framework.elements.input.text.UITextEditor;
import mchorse.bbs_mod.ui.utils.icons.Icons;
import mchorse.bbs_mod.utils.Direction;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.CommandManager;

public class UIScriptPanel extends UIDataDashboardPanel<Script> {
    private UIIcon run;
    private UITextEditor content;

    public UIScriptPanel(UIDashboard dashboard) {
        super(dashboard);

        this.overlay.namesList.setFileIcon(Icons.PROPERTIES);

        this.content = new UITextEditor(null);
        this.content.background().relative(this.editor).wh(1f, 1f);

        this.run = new UIIcon(Icons.PLAY, this::runScript);
        this.run.tooltip(UIMappetKeys.SCRIPTS_RUN, Direction.LEFT);

        this.editor.add(this.content);

        this.iconBar.add(this.run);

        this.fill(null);
    }

    private void runScript(UIIcon icon) {
        CommandManager commandManager = MinecraftClient.getInstance().getServer().getCommandManager();

        this.save();

        commandManager.executeWithPrefix(MinecraftClient.getInstance().getServer().getCommandSource(), "mappet script " + this.data.getId());
    }

    @Override
    public ContentType getType() {
        return MappetContentType.SCRIPTS;
    }

    @Override
    protected void fillData(Script data) {
        if (data != null) {
            if (!this.content.getText().equals(data.getContent())) {
                this.content.setText(data.getContent());
            }
        }
    }

    @Override
    protected IKey getTitle() {
        return UIMappetKeys.SCRIPTS_TITLE;
    }
}
