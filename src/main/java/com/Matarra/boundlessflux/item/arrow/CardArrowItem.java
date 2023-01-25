package com.Matarra.boundlessflux.item.arrow;

import com.Matarra.boundlessflux.entity.projectile.CardArrow;
import com.Matarra.boundlessflux.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CardArrowItem extends ArrowItem
{
    public CardArrowItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public AbstractArrow createArrow(Level pLevel, ItemStack pStack, LivingEntity pShooter) {
        CardArrow arrow = new CardArrow(pShooter, pLevel, ModItems.CARD_ARROW.get());
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
        return enchant <= 0 ? false : this.getClass() == CardArrowItem.class;
    }
}
