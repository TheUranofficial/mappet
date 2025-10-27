package com.theuran.mappet.entity.npc;

import mchorse.bbs_mod.entity.IEntityFormProvider;
import mchorse.bbs_mod.forms.entities.MCEntity;
import mchorse.bbs_mod.forms.forms.Form;
import mchorse.bbs_mod.network.ServerNetwork;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Arm;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NpcEntity extends LivingEntity implements IEntityFormProvider {
    private MCEntity entity = new MCEntity(this);
    private Form form;
    private Map<EquipmentSlot, ItemStack> equipment = new HashMap<>();

    public static DefaultAttributeContainer.Builder createNpcAttributes() {
        return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1).add(EntityAttributes.GENERIC_ATTACK_SPEED).add(EntityAttributes.GENERIC_LUCK);
    }

    public NpcEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    public MCEntity getEntity() {
        return this.entity;
    }

    @Override
    public int getEntityId() {
        return this.getId();
    }

    @Override
    public Form getForm() {
        return this.form;
    }

    @Override
    public void setForm(Form form) {
        Form lastForm = this.form;
        this.form = form;
        if (!this.getWorld().isClient()) {
            if (lastForm != null) {
                lastForm.onDemorph(this);
            }

            if (form != null) {
                form.onMorph(this);
            }
        }
    }

    @Override
    public boolean shouldRender(double distance) {
        double d = this.getBoundingBox().getAverageSideLength();
        if (Double.isNaN(d)) {
            d = 1.0;
        }

        return distance < d * (double)256.0F * d * (double)256.0F;
    }

    public Iterable<ItemStack> getHandItems() {
        return List.of(this.getEquippedStack(EquipmentSlot.MAINHAND), this.getEquippedStack(EquipmentSlot.OFFHAND));
    }

    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return this.equipment.getOrDefault(slot, ItemStack.EMPTY);
    }

    public void equipStack(EquipmentSlot slot, ItemStack stack) {
        this.equipment.put(slot, stack == null ? ItemStack.EMPTY : stack);
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return List.of(this.getEquippedStack(EquipmentSlot.FEET), this.getEquippedStack(EquipmentSlot.LEGS), this.getEquippedStack(EquipmentSlot.CHEST), this.getEquippedStack(EquipmentSlot.HEAD));
    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        ServerNetwork.sendEntityForm(player, this);
    }

    public int getPermissionLevel() {
        return 4;
    }
}
