package com.Matarra.boundlessflux.item.upgrades;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class ItemSweepingUpgrade extends BaseItemUpgrade
{
    public ItemSweepingUpgrade(Properties pProperties) {
        super(pProperties);
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
        tooltip.add(Component.literal("Sweeping Edge VI"));

        // APPEND COMPATIBILITIES
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("Compatible with any Boundless Sword").withStyle(ChatFormatting.ITALIC));
    }

}
