package com.theuran.mappet.client.ui.panels;

import com.mojang.blaze3d.systems.RenderSystem;
import com.theuran.mappet.api.scripts.code.ScriptVector;
import com.theuran.mappet.api.triggers.RequestTrigger;
import com.theuran.mappet.block.MappetBlocks;
import com.theuran.mappet.block.blocks.entities.TriggerBlockEntity;
import com.theuran.mappet.client.MappetClient;
import com.theuran.mappet.client.api.triggerBlocks.ClientTriggerBlocksManager;
import com.theuran.mappet.client.ui.UIMappetDashboard;
import com.theuran.mappet.client.ui.triggers.UIEditorTriggersOverlayPanel;
import com.theuran.mappet.client.ui.utils.UIMappetTransform;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.packets.blocks.trigger.TriggerBlockUpdatePacket;
import com.theuran.mappet.network.packets.triggers.TriggersRequestC2SPacket;
import mchorse.bbs_mod.BBSSettings;
import mchorse.bbs_mod.blocks.entities.ModelBlockEntity;
import mchorse.bbs_mod.camera.CameraUtils;
import mchorse.bbs_mod.client.BBSRendering;
import mchorse.bbs_mod.graphics.Draw;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.Keys;
import mchorse.bbs_mod.ui.UIKeys;
import mchorse.bbs_mod.ui.dashboard.UIDashboard;
import mchorse.bbs_mod.ui.dashboard.panels.IFlightSupported;
import mchorse.bbs_mod.ui.dashboard.panels.UIDashboardPanel;
import mchorse.bbs_mod.ui.forms.UINestedEdit;
import mchorse.bbs_mod.ui.framework.UIContext;
import mchorse.bbs_mod.ui.framework.elements.UIElement;
import mchorse.bbs_mod.ui.framework.elements.UIScrollView;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIButton;
import mchorse.bbs_mod.ui.framework.elements.overlay.UIOverlay;
import mchorse.bbs_mod.ui.framework.elements.utils.FontRenderer;
import mchorse.bbs_mod.ui.utils.UI;
import mchorse.bbs_mod.ui.utils.UIUtils;
import mchorse.bbs_mod.utils.AABB;
import mchorse.bbs_mod.utils.PlayerUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3d;
import org.joml.Vector3f;

import java.util.HashSet;
import java.util.Set;

@Environment(EnvType.CLIENT)
public class UITriggerBlockPanel extends UIDashboardPanel implements IFlightSupported
{
    public UIScrollView scrollView;
    public UIElement editor;
    public UITriggerBlockEntityList triggerBlocks;

    public UIButton leftClick;
    public UIButton rightClick;
    public UIMappetTransform transform;

    private TriggerBlockEntity triggerBlock;
    private TriggerBlockEntity hovered;
    private Vector3f mouseDirection = new Vector3f();

    private Set<TriggerBlockEntity> toSave = new HashSet<>();

    public UITriggerBlockPanel(UIMappetDashboard dashboard)
    {
        super(dashboard);

        this.triggerBlocks = new UITriggerBlockEntityList((l) -> this.fill(l.get(0), false));
        this.triggerBlocks.context((menu) ->
        {
            if (this.triggerBlock != null) menu.action(UIKeys.MODEL_BLOCKS_KEYS_TELEPORT, this::teleport);
        });
        this.triggerBlocks.background();
        this.triggerBlocks.h(144);

        this.leftClick = new UIButton(IKey.raw("Редактировать ЛКМ"), (button) ->
        {
            Dispatcher.sendToServer(new TriggersRequestC2SPacket(RequestTrigger.TRIGGER_BLOCK_LMB, this.triggerBlock.getPos().toShortString()));
            UIOverlay.addOverlay(this.getContext(), new UIEditorTriggersOverlayPanel(RequestTrigger.TRIGGER_BLOCK_LMB), 0.55f, 0.75f).noBackground();
        });

        this.rightClick = new UIButton(IKey.raw("Редактировать ПКМ"), (button) ->
        {
            Dispatcher.sendToServer(new TriggersRequestC2SPacket(RequestTrigger.TRIGGER_BLOCK_RMB, this.triggerBlock.getPos().toShortString()));
            UIOverlay.addOverlay(this.getContext(), new UIEditorTriggersOverlayPanel(RequestTrigger.TRIGGER_BLOCK_RMB), 0.55f, 0.75f).noBackground();
        });

        this.transform = new UIMappetTransform();

        this.transform.onScale(() -> save(triggerBlock));

        this.transform.onScale2(() -> save(triggerBlock));

        this.editor = UI.column(this.leftClick, this.rightClick, this.transform);

        this.scrollView = UI.scrollView(5, 10, this.triggerBlocks, this.editor);
        this.scrollView.scroll.opposite().cancelScrolling();
        this.scrollView.relative(this).w(200).h(1.0F);

        this.fill(null, false);
        this.keys().register(Keys.MODEL_BLOCKS_TELEPORT, this::teleport);
        this.add(this.scrollView);
    }

    private void teleport()
    {
        if (this.triggerBlock != null)
        {
            BlockPos pos = this.triggerBlock.getPos();
            PlayerUtils.teleport((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D);
            UIUtils.playClick();
        }
    }

    @Override
    public boolean supportsRollFOVControl() { return false; }

    @Override
    public void appear()
    {
        super.appear();
        this.dashboard.orbitKeysUI.setEnabled(() -> true);
    }

    @Override
    public void disappear()
    {
        super.disappear();
        this.dashboard.orbitKeysUI.setEnabled(null);
    }

    public TriggerBlockEntity getTriggerBlock() { return this.triggerBlock; }

    @Override
    public boolean needsBackground() { return false; }

    @Override
    public boolean canPause() { return false; }

    @Override
    public void open()
    {
        super.open();
        this.updateList();

        if (this.triggerBlock != null && this.triggerBlock.isRemoved())
        {
            this.fill(null, true);
        }
    }

    @Override
    public void close()
    {
        super.close();

        for (TriggerBlockEntity entity : this.toSave)
        {
            this.save(entity);
        }

        this.toSave.clear();
    }

    private void updateList()
    {
        this.triggerBlocks.clear();

        for(TriggerBlockEntity triggerBlock : ClientTriggerBlocksManager.capturedTriggerBlocks) {
            this.triggerBlocks.add(triggerBlock);
        }

        this.fill(this.triggerBlock, true);
    }

    public void fill(TriggerBlockEntity triggerBlock, boolean select)
    {
        if (triggerBlock != null) this.toSave.add(triggerBlock);

        this.triggerBlock = triggerBlock;

        if (triggerBlock != null) this.fillData();

        this.editor.setVisible(triggerBlock != null);

        if (select) this.triggerBlocks.setCurrentScroll(triggerBlock);
    }

    private void fillData()
    {
        this.transform.fillS(
                this.triggerBlock.getPos1().x,
                this.triggerBlock.getPos1().y,
                this.triggerBlock.getPos1().z
        );
        this.transform.fillS2(
                this.triggerBlock.getPos2().x,
                this.triggerBlock.getPos2().y,
                this.triggerBlock.getPos2().z
        );
    }

    private void save(TriggerBlockEntity triggerBlock)
    {
        ScriptVector pos1 = new ScriptVector((int) this.transform.sx.getValue(), (int) this.transform.sy.getValue(), (int) this.transform.sz.getValue());
        ScriptVector pos2 = new ScriptVector((int) this.transform.s2x.getValue(), (int) this.transform.s2y.getValue(), (int) this.transform.s2z.getValue());

        if (triggerBlock != null && (triggerBlock.getPos1().equals(pos1) || triggerBlock.getPos2().equals(pos2)))
        {
            Dispatcher.sendToServer(new TriggerBlockUpdatePacket(
                    triggerBlock.getPos(),
                    pos1,
                    pos2
            ));
        }
    }

    @Override
    protected boolean subMouseClicked(UIContext context)
    {
        if (super.subMouseClicked(context)) return true;

        if (this.hovered != null && context.mouseButton == 0)
        {
            this.fill(this.hovered, true);
        }

        return false;
    }

    @Override
    public void render(UIContext context)
    {
        String label = UIKeys.FILM_CONTROLLER_SPEED.format(new Object[]{ this.dashboard.orbit.speed.getValue() }).get();
        FontRenderer font = context.batcher.getFont();
        int w = font.getWidth(label);
        int x = this.area.w - w - 5;
        int y = this.area.ey() - font.getHeight() - 5;

        context.batcher.textCard(label, (float) x, (float) y, -1, -2013265920);
        super.render(context);
    }

    @Override
    public void renderInWorld(WorldRenderContext context)
    {
        super.renderInWorld(context);

        Camera camera = context.camera();
        Vec3d pos = camera.getPos();

        MinecraftClient mc = MinecraftClient.getInstance();
        double mx = mc.mouse.getX();
        double my = mc.mouse.getY();

        this.mouseDirection.set(CameraUtils.getMouseDirection(
                RenderSystem.getProjectionMatrix(),
                context.matrixStack().peek().getPositionMatrix(),
                (int) mx, (int) my, 0, 0, mc.getWindow().getWidth(), mc.getWindow().getHeight()
        ));
        this.hovered = this.getClosestObject(new Vector3d(pos.x, pos.y, pos.z), this.mouseDirection);

        RenderSystem.enableDepthTest();

        for (TriggerBlockEntity entity : this.triggerBlocks.getList())
        {
            BlockPos blockPos = entity.getPos();

            context.matrixStack().push();
            context.matrixStack().translate(
                    (double) blockPos.getX() - pos.x,
                    (double) blockPos.getY() - pos.y,
                    (double) blockPos.getZ() - pos.z
            );

            if (this.hovered != entity && entity != this.triggerBlock)
            {
                Draw.renderBox(context.matrixStack(),
                        entity.getPos1().x, entity.getPos1().y, entity.getPos1().z,
                        entity.getPos2().x / 16, entity.getPos2().y / 16, entity.getPos2().z / 16);
            }
            else
            {
                Draw.renderBox(context.matrixStack(),
                        entity.getPos1().x, entity.getPos1().y, entity.getPos1().z,
                        entity.getPos2().x / 16, entity.getPos2().y / 16, entity.getPos2().z / 16,
                        0.0F, 0.5F, 1.0F);
            }

            context.matrixStack().pop();
        }

        RenderSystem.disableDepthTest();
    }

    private TriggerBlockEntity getClosestObject(Vector3d finalPosition, Vector3f mouseDirection)
    {
        TriggerBlockEntity closest = null;

        for (TriggerBlockEntity object : this.triggerBlocks.getList())
        {
            AABB aabb = this.getHitbox(object);

            if (aabb.intersectsRay(finalPosition, mouseDirection))
            {
                if (closest == null)
                {
                    closest = object;
                }
                else
                {
                    AABB aabb2 = this.getHitbox(closest);

                    if (finalPosition.distanceSquared(aabb.x, aabb.y, aabb.z) < finalPosition.distanceSquared(aabb2.x, aabb2.y, aabb2.z))
                    {
                        closest = object;
                    }
                }
            }
        }

        return closest;
    }

    private AABB getHitbox(TriggerBlockEntity entity)
    {
        BlockPos pos = entity.getPos();

        return new AABB(
                (double) pos.getX() + entity.getPos1().x - 10,
                (double) pos.getY() + entity.getPos1().y - 10,
                (double) pos.getZ() + entity.getPos1().z - 10,
                (double) pos.getX() + entity.getPos2().x - 10,
                (double) pos.getY() + entity.getPos2().y - 10,
                (double) pos.getZ() + entity.getPos2().z - 10
        );
    }
}