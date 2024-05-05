package com.Matarra.boundlessflux.item.pickaxe;

import com.Matarra.boundlessflux.item.pickaxe.base.BoundedPickaxeBase;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class BoundedPickaxe extends BoundedPickaxeBase
{

    public BoundedPickaxe(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);

        // append flavor text after everything
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("Boundless untapped power awaits...").withStyle(ChatFormatting.ITALIC, ChatFormatting.GOLD));
    }
}
