package com.theuran.mappet.client.ui.blocks.trigger;

import com.theuran.mappet.api.scripts.code.ScriptVector;
import com.theuran.mappet.block.MappetBlockEntities;
import com.theuran.mappet.block.blocks.entities.TriggerBlockEntity;
import com.theuran.mappet.client.ui.utils.UIMappetTransform;
import com.theuran.mappet.network.Dispatcher;
import com.theuran.mappet.network.packets.server.UpdateTriggerBlockC2SPacket;
import mchorse.bbs_mod.l10n.keys.IKey;
import mchorse.bbs_mod.ui.framework.UIBaseMenu;
import mchorse.bbs_mod.ui.framework.elements.UIElement;
import mchorse.bbs_mod.ui.framework.elements.buttons.UIButton;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class UITriggerBlock extends UIBaseMenu {
    public final BlockPos blockPos;
    public final TriggerBlockEntity triggerBlockEntity;

    public UIElement layout;

    public UIButton leftCLick;
    public UIButton rightClick;

    public UIMappetTransform transform;

    public UITriggerBlock(BlockPos blockPos) {
        this.blockPos = new BlockPos(blockPos);
        this.triggerBlockEntity = MinecraftClient.getInstance().player.getWorld().getBlockEntity(this.blockPos, MappetBlockEntities.TRIGGER_BLOCK).get();

        this.layout = new UIElement();

        this.layout.xy(0.5f, 0.5f);
        this.layout.wh(0.25f, 0.25f);
        this.layout.anchor(0.5f, 0.5f);

        this.leftCLick = new UIButton(IKey.raw("Редактировать ЛКМ"), (button) -> {

        });

        this.leftCLick.xy(0.5f, 0f);
        this.leftCLick.wh(170, 20);
        this.leftCLick.anchor(0.5f, 0.5f);

        this.rightClick = new UIButton(IKey.raw("Редактировать ПКМ"), (button) -> {

        });

        this.rightClick.xy(0.5f, 0.5f);
        this.rightClick.wh(170, 20);
        this.rightClick.anchor(0.5f, 0.5f);

        this.transform = new UIMappetTransform();

        this.transform.fillS(
            this.triggerBlockEntity.getPos1().x,
            this.triggerBlockEntity.getPos1().y,
            this.triggerBlockEntity.getPos1().z
        );

        this.transform.fillS2(
            this.triggerBlockEntity.getPos2().x,
            this.triggerBlockEntity.getPos2().y,
            this.triggerBlockEntity.getPos2().z
        );

        this.transform.wh(1f, 0.4f);
        this.transform.xy(0.5f, 1f);
        this.transform.anchor(0.5f, 0.5f);

        this.leftCLick.relative(this.layout);
        this.rightClick.relative(this.layout);
        this.transform.relative(this.layout);

        this.layout.add(this.leftCLick);
        this.layout.add(this.rightClick);
        this.layout.add(this.transform);

        this.layout.relative(this.getRoot());

        this.getRoot().add(this.layout);
    }

    @Override
    public void onClose(UIBaseMenu nextMenu) {
        Dispatcher.sendToServer(new UpdateTriggerBlockC2SPacket(this.blockPos,
                new ScriptVector((int) this.transform.sx.getValue(), (int) this.transform.sy.getValue(), (int) this.transform.sz.getValue()),
                new ScriptVector((int) this.transform.s2x.getValue(), (int) this.transform.s2y.getValue(), (int) this.transform.s2z.getValue()))
        );
    }
}
