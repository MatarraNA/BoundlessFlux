package com.Matarra.boundlessflux.item.swords.base;

import com.Matarra.boundlessflux.item.item_energy.CapabilityEnergyProvider;
import com.Matarra.boundlessflux.config.BoundlessCommonConfig;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class BoundlessSwordBase extends SwordItem
{
    public BoundlessSwordBase(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);

        // only show energy if world or player exists
        Minecraft mc = Minecraft.getInstance();
        if (world == null || mc.player == null) {
            return;
        }

        // set energy text
        stack.getCapability(ForgeCapabilities.ENERGY, null)
                .ifPresent(energy -> {
                    MutableComponent energyText = Component.literal("Energy: " + String.format("%,d", energy.getEnergyStored()) + " / " + String.format("%,d", energy.getMaxEnergyStored()));
                    tooltip.add(energyText.withStyle(ChatFormatting.GREEN));
                });
        tooltip.add(Component.literal("Max Energy Cap: " + String.format("%,d", BoundlessCommonConfig.MAX_ENERGY_CAP_SWORD.get()))
                .withStyle(ChatFormatting.RED));
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }

    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack pStack) {
        return super.getName(pStack).copy().withStyle(ChatFormatting.GOLD);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {

        Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
        if (slot == EquipmentSlot.MAINHAND) {
            multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier",getAttackDamage(stack), AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -2f, AttributeModifier.Operation.ADDITION));
        }
        return multimap;
    }

    // Calculate how much damage the sword should use based on its current energy
    protected float getAttackDamage(ItemStack stack) {
        return stack.getCapability(ForgeCapabilities.ENERGY, null)
                .map(e ->(float) (e.getEnergyStored() * BoundlessCommonConfig.PERCENT_ENERGY_CONSUMED_ONHIT_SWORD.get()
                         * BoundlessCommonConfig.PERCENT_ENERGY_CONVERTED_TO_DAMAGE_SWORD.get()) - 1f)
                .orElse(0f);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        // consume a percentage of remaining power
        int energyConsumed = pStack.getCapability(ForgeCapabilities.ENERGY, null)
                .map(e -> e.receiveEnergy((int)(e.getEnergyStored() * BoundlessCommonConfig.PERCENT_ENERGY_CONSUMED_ONHIT_SWORD.get()) * -1, false))
                .orElse(0);
        energyConsumed *= -1; // convert it back to a positive number

        // handle super for enemy
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return true;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapabilityEnergyProvider(stack, BoundlessCommonConfig.DEFAULT_MAX_ENERGY_SWORD.get());
    }

    /*
        CODE REFERENCED FROM MINING GADGETS
        https://github.com/Direwolf20-MC/MiningGadgets/blob/master/src/main/java/com/direwolf20/mininggadgets/common/items/MiningGadget.java
     */
    @Override
    public int getBarWidth(ItemStack stack) {
        return stack.getCapability(ForgeCapabilities.ENERGY, null)
                .map(e -> Math.min((int)(13 * (e.getEnergyStored() * 0.01d) / (e.getMaxEnergyStored() * 0.01d)), 13))
                .orElse(0);
    }

    /*
        CODE REFERENCED FROM MINING GADGETS
        https://github.com/Direwolf20-MC/MiningGadgets/blob/master/src/main/java/com/direwolf20/mininggadgets/common/items/MiningGadget.java
     */
    @Override
    public int getBarColor(ItemStack stack) {
        return stack.getCapability(ForgeCapabilities.ENERGY)
                .map(e -> Mth.hsvToRgb(Math.max(0.0F, (float) e.getEnergyStored() / (float) e.getMaxEnergyStored()) / 3.0F, 1.0F, 1.0F))
                .orElse(super.getBarColor(stack));
    }
}
