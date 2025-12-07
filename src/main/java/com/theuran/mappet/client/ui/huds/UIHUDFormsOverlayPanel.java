package com.theuran.mappet.client.ui.huds;

import com.theuran.mappet.api.huds.HUDForm;
import com.theuran.mappet.api.huds.HUDScene;
import com.theuran.mappet.client.ui.UIMappetKeys;
import mchorse.bbs_mod.data.types.MapType;
import mchorse.bbs_mod.graphics.window.Window;
import mchorse.bbs_mod.ui.UIKeys;
import mchorse.bbs_mod.ui.framework.UIContext;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlayPanel;
import mchorse.bbs_mod.ui.utils.UIDataUtils;
import mchorse.bbs_mod.ui.utils.icons.Icons;
import mchorse.bbs_mod.utils.colors.Colors;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class UIHUDFormsOverlayPanel extends UIOverlayPanel {
    public UIHUDFormsListElement forms;

    private HUDScene scene;
    private Consumer<HUDForm> callback;

    public UIHUDFormsOverlayPanel(HUDScene scene, Consumer<HUDForm> callback) {
        super(UIMappetKeys.HUD_SCENE_OVERLAY_TITLE);

        this.scene = scene;
        this.callback = callback;

        this.forms = new UIHUDFormsListElement(list -> this.accept(list.getFirst()));
        this.forms.sorting().context(menu -> {
            menu.action(Icons.ADD, UIKeys.GENERAL_ADD, this::addForm);

            if (this.forms.isSelected()) {
                menu.action(Icons.COPY, UIKeys.GENERAL_COPY, this::copyForm);

                MapType data = Window.getClipboardMap("_CopyHUDForm");

                if (data != null) {
                    HUDForm form = new HUDForm();

                    form.fromData(data);

                    menu.action(Icons.PASTE, UIKeys.GENERAL_PASTE, () -> this.addForm(form));
                }

                menu.action(Icons.REMOVE, UIKeys.GENERAL_REMOVE, Colors.NEGATIVE, this::removeForm);
            }
        });

        this.forms.full(this.content).x(6).w(1F, -12);

        this.forms.setList(this.scene.forms.getList());
        this.forms.scroll.scrollSpeed *= 2;

        this.content.add(this.forms);
    }

    private void addForm() {
        this.addForm(new HUDForm());
    }

    private void addForm(HUDForm form) {
        this.scene.forms.add(form);
        this.forms.update();

        this.forms.setCurrentScroll(form);
        this.accept(form);
    }

    private void copyForm() {
        Window.setClipboard(this.forms.getCurrentFirst().toData().asMap(), "_CopyHUDForm");
    }
    
    private void removeForm() {
        int index = this.forms.getIndex();

        this.scene.forms.getAll().remove(index);
        this.forms.update();
        this.forms.setIndex(index < 1 ? 0 : index - 1);

        this.accept(this.forms.getCurrentFirst());
    }

    public UIHUDFormsOverlayPanel set(HUDForm form) {
        this.forms.setCurrentScroll(form);

        return this;
    }

    protected void accept(HUDForm form) {
        if (this.callback != null)
            this.callback.accept(form);
    }

    @Override
    public void render(UIContext context) {
        super.render(context);

        if (this.scene.forms.getAll().size() <= 1) {
            UIDataUtils.renderRightClickHere(context, this.content.area);
        }
    }

    @Override
    protected void renderBackground(UIContext context) {
        super.renderBackground(context);

        this.content.area.render(context.batcher, Colors.A100);
    }
}
