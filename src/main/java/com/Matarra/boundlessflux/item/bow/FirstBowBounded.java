package com.Matarra.boundlessflux.item.bow;

import com.Matarra.boundlessflux.entity.BoundlessLightning;
import com.Matarra.boundlessflux.entity.ModEntities;
import com.Matarra.boundlessflux.item.ModItems;
import com.Matarra.boundlessflux.item.bow.base.BoundedBowBase;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class FirstBowBounded extends BoundedBowBase {
    public FirstBowBounded(Properties pProperties) {
        super(pProperties);
        this.customArrowItem = ModItems.FIRST_ARROW.get();
    }

    @Override
    public Component getName(ItemStack pStack) {
        return super.getName(pStack).copy().withStyle(ChatFormatting.AQUA);
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
