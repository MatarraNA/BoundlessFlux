package com.Matarra.boundlessflux.item.bow.base;

import com.Matarra.boundlessflux.config.BoundlessCommonConfig;
import com.Matarra.boundlessflux.enchant.ModEnchantments;
import com.Matarra.boundlessflux.entity.projectile.FirstArrow;
import com.Matarra.boundlessflux.entity.projectile.IBoundlessArrow;
import com.Matarra.boundlessflux.item.ModItems;
import com.Matarra.boundlessflux.item.item_energy.CapabilityEnergyProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class BoundlessBowBase extends BowItem
{
    public float projectileSpeed = 4f;
    public ArrowItem customArrowItem = null;
    public SoundEvent customShootSound = SoundEvents.ARROW_SHOOT;

    public BoundlessBowBase(Properties pProperties) {
        super(pProperties);
    }

    // HOVER TEXT
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

                    tooltip.add(Component.literal("Max Energy Cap: " + String.format("%,d", BoundlessCommonConfig.MAX_ENERGY_CAP_BOW.get()))
                            .withStyle(ChatFormatting.RED));

                    tooltip.add(Component.literal("Damage: " + String.format("%.2f", energy.getEnergyStored() *
                            BoundlessCommonConfig.PERCENT_ENERGY_CONSUMED_ONFIRE_BOW.get() *
                            BoundlessCommonConfig.PERCENT_ENERGY_CONVERTED_TO_DAMAGE_BOW.get())).withStyle(ChatFormatting.YELLOW));
                });
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    // ensure no durability
    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    // ensure enchant glint is active without allowing enchants
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

    /*
        MAIN BOW FUNCTIONALITY
     */
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        boolean flag = !pPlayer.getProjectile(itemstack).isEmpty();

        // does the item have infinity enchant?
        boolean inf_flag = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, itemstack) > 0;

        InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, pLevel, pPlayer, pHand, flag);
        if (ret != null) return ret;

        if (!pPlayer.getAbilities().instabuild && !flag && !inf_flag) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player player) {
            boolean flag = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, pStack) > 0;
            ItemStack itemstack = player.getProjectile(pStack);

            int i = this.getUseDuration(pStack) - pTimeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(pStack, pLevel, player, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            // CONSUME ENERGY HERE, AND SCALE DAMAGE WITH IT
            int energyConsumed = pStack.getCapability(ForgeCapabilities.ENERGY, null)
                    .map(e -> e.receiveEnergy((int)(e.getEnergyStored() * BoundlessCommonConfig.PERCENT_ENERGY_CONSUMED_ONHIT_SWORD.get()) * -1, false))
                    .orElse(0);
            energyConsumed *= -1; // convert it back to a positive number

            // convert to damage now
            double baseDamage = energyConsumed * BoundlessCommonConfig.PERCENT_ENERGY_CONVERTED_TO_DAMAGE_BOW.get();

            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = getPowerForTime(i);
                if (!((double)f < 0.1D)) {
                    boolean flag1 = player.getAbilities().instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, pStack, player));
                    if (!pLevel.isClientSide) {

                        ArrowItem arrowItem;
                        if( customArrowItem != null)
                            arrowItem = customArrowItem;
                        else
                            arrowItem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                        AbstractArrow abstractarrow = arrowItem.createArrow(pLevel, itemstack, player);
                        abstractarrow = customArrow(abstractarrow);
                        abstractarrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * projectileSpeed, 0.1F);
                        if (f == 1.0F) {
                            abstractarrow.setCritArrow(true);
                        }

                        // custom arrow properties
                        IBoundlessArrow boundlessArrow = (IBoundlessArrow) abstractarrow;
                        if( boundlessArrow != null )
                        {
                            // has storm call enchant?
                            if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.STORM_CALL.get(), pStack) > 0)
                            {
                                boundlessArrow.setThunderEnchant(true);
                                boundlessArrow.setThunderDamage(baseDamage * f); // f is a value from 0-1, so do max damage when fully charged
                            }

                            // source player
                            boundlessArrow.setPlayer(player);
                        }

                        // SET DAMAGE HERE
                        abstractarrow.setBaseDamage(baseDamage / projectileSpeed );

                        int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, pStack);
                        if (k > 0) {
                            abstractarrow.setKnockback(k);
                        }

                        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, pStack) > 0) {
                            abstractarrow.setSecondsOnFire(100);
                        }

                        pStack.hurtAndBreak(1, player, (p_40665_) -> {
                            p_40665_.broadcastBreakEvent(player.getUsedItemHand());
                        });
                        if (flag1 || player.getAbilities().instabuild && (itemstack.is(Items.SPECTRAL_ARROW) || itemstack.is(Items.TIPPED_ARROW))) {
                            abstractarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                        }

                        pLevel.addFreshEntity(abstractarrow);
                    }

                    pLevel.playSound((Player)null, player.getX(), player.getY(), player.getZ(), customShootSound, SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!flag1 && !player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            player.getInventory().removeItem(itemstack);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    /*
        NAME / DESCRIPTION
     */
    @Override
    public @NotNull Component getName(@NotNull ItemStack pStack) {
        return super.getName(pStack).copy().withStyle(ChatFormatting.GOLD);
    }

    /*
            ENERGY
     */
    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return true;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapabilityEnergyProvider(stack, BoundlessCommonConfig.DEFAULT_MAX_ENERGY_BOW.get());
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
