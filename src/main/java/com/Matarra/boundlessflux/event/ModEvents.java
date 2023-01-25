package com.Matarra.boundlessflux.event;

import com.Matarra.boundlessflux.BoundlessFlux;
import com.Matarra.boundlessflux.config.BoundlessCommonConfig;
import com.Matarra.boundlessflux.tags.ModTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

public class ModEvents
{
    @Mod.EventBusSubscriber(modid = BoundlessFlux.MODID)
    public static class ForgeEvents
    {
        @SubscribeEvent
        public static void onLivingDeath(LivingDeathEvent event) {
            if( event.getSource() == null ) return;
            if( event.getEntity() == null ) return;

            // check if the entity died by a player boundless sword
            if( !(event.getSource().getEntity() instanceof Player) ) return;
            Player player = ((Player) event.getSource().getEntity());

            // is the player holding the sword?
            if( player.getMainHandItem().getTags().filter(x->x.equals(ModTags.BOUNDLESS_SWORD_POST)).count() > 0 )
            {
                // we know now it is a boundless sword
                ItemStack pStack = player.getMainHandItem();

                // player is holding the sword, add energy
                // check if tag for energy gain is disabled
                boolean energyGainEnabled;
                if( !pStack.hasTag() ) energyGainEnabled = true;
                else if(!pStack.getTag().contains("energy_gain")) energyGainEnabled = true;
                else energyGainEnabled = pStack.getTag().getBoolean("energy_gain");

                // get the capability
                LazyOptional<IEnergyStorage> energyCap = pStack.getCapability(ForgeCapabilities.ENERGY, null);
                if( energyCap.isPresent() && energyGainEnabled )
                {
                    IEnergyStorage cap = energyCap.resolve().get();
                    int current_max = cap.getMaxEnergyStored();
                    int current_energy = cap.getEnergyStored();
                    int energy_consumed = (int)(current_energy * BoundlessCommonConfig.PERCENT_ENERGY_CONSUMED_ONHIT_SWORD.get());
                    if( (long)current_max + (long)energy_consumed > BoundlessCommonConfig.MAX_ENERGY_CAP_SWORD.get() )
                        pStack.getOrCreateTag().putInt("max_energy", BoundlessCommonConfig.MAX_ENERGY_CAP_SWORD.get());
                    else
                        pStack.getOrCreateTag().putInt("max_energy", current_max + energy_consumed);

                }
            }
            else if( player.getMainHandItem().getTags().filter(x->x.equals(ModTags.BOUNDLESS_BOW_POST)).count() > 0)
            {
                // we know now it is a boundless bow
                ItemStack pStack = player.getMainHandItem();

                // player is holding the sword, add energy
                // check if tag for energy gain is disabled
                boolean energyGainEnabled;
                if( !pStack.hasTag() ) energyGainEnabled = true;
                else if(!pStack.getTag().contains("energy_gain")) energyGainEnabled = true;
                else energyGainEnabled = pStack.getTag().getBoolean("energy_gain");

                // get the capability
                LazyOptional<IEnergyStorage> energyCap = pStack.getCapability(ForgeCapabilities.ENERGY, null);
                if( energyCap.isPresent() && energyGainEnabled )
                {
                    IEnergyStorage cap = energyCap.resolve().get();
                    int current_max = cap.getMaxEnergyStored();
                    int current_energy = cap.getEnergyStored();
                    int energy_consumed = (int)(current_energy * BoundlessCommonConfig.PERCENT_ENERGY_CONSUMED_ONFIRE_BOW.get());
                    if( (long)current_max + (long)energy_consumed > BoundlessCommonConfig.MAX_ENERGY_CAP_BOW.get() )
                        pStack.getOrCreateTag().putInt("max_energy", BoundlessCommonConfig.MAX_ENERGY_CAP_BOW.get());
                    else
                        pStack.getOrCreateTag().putInt("max_energy", current_max + energy_consumed);

                }
            }
        }
    }
}
