package com.Matarra.boundlessflux.item.bow;

import com.Matarra.boundlessflux.item.ModItems;
import com.Matarra.boundlessflux.item.bow.base.BoundlessBowBase;
import com.Matarra.boundlessflux.sound.ModSounds;
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

public class KunaiBoundless extends BoundlessBowBase {
    public KunaiBoundless(Properties pProperties)
    {
        super(pProperties);
        this.customArrowItem = ModItems.KUNAI_ARROW.get();
        this.customShootSound = ModSounds.SHURIKEN_THROW.get();
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

        // append flavor text after energy text
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("クナイ (Kunai)").withStyle(ChatFormatting.ITALIC, ChatFormatting.GOLD));
    }
}
