package com.Matarra.boundlessflux.item.bow.base;

import com.Matarra.boundlessflux.config.BoundlessCommonConfig;
import com.Matarra.boundlessflux.entity.BoundlessLightning;
import com.Matarra.boundlessflux.entity.ModEntities;
import com.Matarra.boundlessflux.item.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class BoundedBowBase extends BowItem
{
    public ArrowItem customArrowItem = null;
    public BoundedBowBase(Properties pProperties) {
        super(pProperties);
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

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player player) {
            boolean flag = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, pStack) > 0;
            ItemStack itemstack = player.getProjectile(pStack);

            int i = this.getUseDuration(pStack) - pTimeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(pStack, pLevel, player, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

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
                        abstractarrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * 3f, 1F);
                        if (f == 1.0F) {
                            abstractarrow.setCritArrow(true);
                        }

                        // SET DAMAGE HERE
                        abstractarrow.setBaseDamage(2f);

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

                    pLevel.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
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

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        try
        {
            // attempt lightning spawn
            if( !pLevel.isClientSide() )
            {
                // TODO, UPDATE THIS HORRIFIC NESTED IF STATEMENT STRUCTURE
                // is the moon directly above you?
                long curTimeConv = pLevel.getDayTime() % 24000;

                // is there sky access?
                int brightness = pLevel.getBrightness(LightLayer.SKY, pPlayer.blockPosition());
                if( curTimeConv > 17750 && curTimeConv < 18250 && brightness > 14 )
                {
                    // is the player looking straight up?
                    pPlayer.getLookAngle();
                    if( pPlayer.getLookAngle().y > 0.985f )
                    {
                        // is the weapon in the main hand?
                        if( pHand == InteractionHand.MAIN_HAND )
                        {
                            // does the player have the gem in the offhand?
                            ItemStack offItem = pPlayer.getOffhandItem();
                            if( offItem != null && offItem.getItem().equals(ModItems.BOUNDLESS_GEM.get()) )
                            {
                                // is the gem full power?
                                boolean gemIsFull = offItem.getCapability(ForgeCapabilities.ENERGY, null)
                                        .map(e -> e.getEnergyStored() == e.getMaxEnergyStored() ? true : false)
                                        .orElse(false);

                                if( gemIsFull )
                                {
                                    BoundlessLightning lightning = (BoundlessLightning) ModEntities.BOUNDLESS_LIGHTNING_RED.get().spawn((ServerLevel) pLevel, null, pPlayer, pPlayer.blockPosition(),
                                            MobSpawnType.TRIGGERED, true, true);
                                    lightning.sourceEntity = pPlayer;

                                    // swap the players weapon with a boundless BOW!
                                    ItemStack newItem = new ItemStack(ModItems.FIRST_BOW_BOUNDLESS.get());
                                    pPlayer.setItemInHand(pHand, newItem);

                                    // destroy the offhand gem
                                    pPlayer.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
                                }
                            }
                        }
                    }
                }
            }
        }
        catch ( Exception ex ) {}

        return super.use(pLevel, pPlayer, pHand);
    }
}
