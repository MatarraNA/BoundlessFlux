package com.Matarra.boundlessflux.item.arrow;

import com.Matarra.boundlessflux.config.BoundlessCommonConfig;
import com.Matarra.boundlessflux.entity.projectile.FirstArrow;
import com.Matarra.boundlessflux.item.ModItems;
import com.Matarra.boundlessflux.item.arrow.base.BaseArrow;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FirstArrowItem extends ArrowItem
{
    public FirstArrowItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public AbstractArrow createArrow(Level pLevel, ItemStack pStack, LivingEntity pShooter) {
        FirstArrow arrow = new FirstArrow(pShooter, pLevel, ModItems.FIRST_ARROW.get());
        return arrow;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);

        // only show energy if world or player exists
        Minecraft mc = Minecraft.getInstance();
        if (pLevel == null || mc.player == null) {
            return;
        }

        // set text
        pTooltipComponents.add(Component.literal("Exists only for the model, not for use").withStyle(ChatFormatting.RED));
    }

    @Override
    public boolean isInfinite(ItemStack stack, ItemStack bow, net.minecraft.world.entity.player.Player player) {
        int enchant = net.minecraft.world.item.enchantment.EnchantmentHelper.getItemEnchantmentLevel(net.minecraft.world.item.enchantment.Enchantments.INFINITY_ARROWS, bow);
        return enchant <= 0 ? false : this.getClass() == FirstArrowItem.class;
    }
}
