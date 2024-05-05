package com.Matarra.boundlessflux.item.shovel.base;

import com.Matarra.boundlessflux.entity.BoundlessLightning;
import com.Matarra.boundlessflux.entity.ModEntities;
import com.Matarra.boundlessflux.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class BoundedShovelBase extends ShovelItem
{

    public BoundedShovelBase(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public Component getName(ItemStack pStack) {
        return super.getName(pStack).copy().withStyle(ChatFormatting.AQUA);
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false; // this tool cannot take damage
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        // attempt lightning spawn
        if( !pPlayer.level.isClientSide() )
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
                    if( pUsedHand == InteractionHand.MAIN_HAND )
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

                                // swap the players pick with a boundless pickaxe!
                                ItemStack newItem = new ItemStack(ModItems.BOUNDLESS_SHOVEL.get());
                                pPlayer.setItemInHand(pUsedHand, newItem);

                                // destroy the offhand gem
                                pPlayer.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
                            }
                        }
                    }
                }
            }
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
