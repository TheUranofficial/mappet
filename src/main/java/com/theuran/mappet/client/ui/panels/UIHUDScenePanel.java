package com.theuran.mappet.client.ui.panels;

import com.theuran.mappet.api.huds.HUDForm;
import com.theuran.mappet.api.huds.HUDScene;
import com.theuran.mappet.api.huds.HUDStage;
import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.client.ui.MappetContentType;
import com.theuran.mappet.client.ui.UIMappetKeys;
import com.theuran.mappet.client.ui.huds.UIHUDFormsOverlayPanel;
import com.theuran.mappet.client.ui.utils.UIOptionsDataDashboardPanel;
import mchorse.bbs_mod.forms.forms.Form;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.ContentType;
import mchorse.bbs_mod.ui.dashboard.UIDashboard;
import mchorse.bbs_mod.ui.forms.UIFormPalette;
import mchorse.bbs_mod.ui.forms.UINestedEdit;
import mchorse.bbs_mod.ui.framework.elements.IUIElement;
import mchorse.bbs_mod.ui.framework.elements.UIElement;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIIcon;
import mchorse.bbs_mod.ui.framework.elements.input.UITrackpad;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlay;
import mchorse.bbs_mod.ui.utils.UI;
import mchorse.bbs_mod.ui.utils.icons.Icons;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class UIHUDScenePanel extends UIOptionsDataDashboardPanel<HUDScene> {
    public UIIcon forms;
    public UINestedEdit form;

    public UITrackpad expire;

    private final HUDStage stage = new HUDStage();
    private HUDForm current;

    public UIHUDScenePanel(UIDashboard dashboard) {
        super(dashboard);

        this.overlay.namesList.setFileIcon(Icons.FULLSCREEN);

        this.forms = new UIIcon(Icons.POSE, icon -> this.openForms());
        this.form = new UINestedEdit(this::openFormMenu);

        this.expire = new UITrackpad(value -> this.current.expire.set(value.intValue()));
        this.expire.limit(0).integer();

        this.addOptions();
        this.options.fields.add(
                this.form,
                UI.label(UIMappetKeys.HUD_SCENE_EXPIRE).marginTop(8), this.expire
        );

        this.iconBar.add(this.forms);

        this.fill(null);
    }

    private void openFormMenu(Boolean editing) {
        UIFormPalette.open(this, editing, this.current.form.get(), this::setForm);
    }

    private void setForm(Form form) {
        this.current.form.set(form);
        this.form.setForm(form);
    }

    private void openForms() {
        UIHUDFormsOverlayPanel overlay = new UIHUDFormsOverlayPanel(this.data, this::pickForm);

        UIOverlay.addOverlay(this.getContext(), overlay.set(this.current), 0.4F, 0.6F);
    }

    @Override
    public ContentType getType() {
        return MappetContentType.HUDS;
    }

    @Override
    protected void fillData(HUDScene data) {
        this.forms.setEnabled(data != null);

        if (data != null) {
            this.stage.reset();
            this.stage.scenes.put(data.getId(), data);

            this.pickForm(this.data.forms.getList().isEmpty() ? null : this.data.forms.getList().getFirst());
        }
    }

    private void pickForm(HUDForm current) {
        this.current = current;

        this.optionsIcon.setEnabled(data != null && this.current != null);

        for (IUIElement element : this.options.getChildren()) {
            if (element instanceof UIElement) {
                ((UIElement) element).setEnabled(current != null);
            }
        }

        if (current != null) {
            this.form.setForm(current.form.get());
            this.expire.setValue(current.expire.get());
        }
    }

    @Override
    protected IKey getTitle() {
        return UIMappetKeys.HUD_SCENE_TITLE;
    }

    @Override
    public boolean needsBackground() {
        return false;
    }

    @Override
    public void appear() {
        super.appear();

        MappetClient.getHandler().currentStage = this.stage;
    }

    @Override
    public void disappear() {
        MappetClient.getHandler().currentStage = null;
    }

    @Override
    public void close() {
        super.close();

        MappetClient.getHandler().currentStage = null;
    }
}
