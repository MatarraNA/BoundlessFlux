package com.Matarra.boundlessflux.item.misc;

import com.Matarra.boundlessflux.config.BoundlessCommonConfig;
import com.Matarra.boundlessflux.item.item_energy.CapabilityEnergyProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class BoundlessGem extends Item
{
    public BoundlessGem(Properties pProperties) {
        super(pProperties);
    }

    // add glint upon it being fully charged
    @Override
    public boolean isFoil(ItemStack pStack) {
        return pStack.getCapability(ForgeCapabilities.ENERGY, null)
                .map(e -> e.getEnergyStored() == e.getMaxEnergyStored())
                .orElse(false);
    }

    // change item name upon it being fully charged
    @Override
    public Component getName(ItemStack pStack) {
        return pStack.getCapability(ForgeCapabilities.ENERGY, null)
                .map(e -> e.getEnergyStored() == e.getMaxEnergyStored() ?
                        super.getName(pStack).copy().withStyle(ChatFormatting.GOLD) :
                        super.getName(pStack).copy().withStyle(ChatFormatting.AQUA))
                .orElse(super.getName(pStack).copy());
    }

    /*
            ENERGY
     */
    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);

        // only show energy if world and player exists
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
                .map(e -> Math.min(13 * e.getEnergyStored() / e.getMaxEnergyStored(), 13))
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
