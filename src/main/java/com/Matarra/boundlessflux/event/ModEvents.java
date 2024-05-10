package com.Matarra.boundlessflux.event;

import com.Matarra.boundlessflux.BoundlessFlux;
import com.Matarra.boundlessflux.config.BoundlessCommonConfig;
import com.Matarra.boundlessflux.enchant.ModEnchantments;
import com.Matarra.boundlessflux.tags.ModTags;
import mcjty.theoneprobe.api.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.checkerframework.checker.units.qual.C;

import java.awt.*;
import java.util.Collection;
import java.util.Random;

public class ModEvents
{
    @Mod.EventBusSubscriber(modid = BoundlessFlux.MODID)
    public static class ForgeEvents
    {
        protected static void spawnFireworkRocket(Level level, Vec3 pos)
        {
            // generate colors
            Random rand = new Random();
            int[] colors = new int[rand.nextInt(3)];
            for( int i = 0; i < colors.length; i++ )
            {
                colors[i] = (int)(rand.nextDouble() * 0x1000000);
            }

            // rocket nbt
            ListTag explosionsList = new ListTag();
            CompoundTag explosionsTag = new CompoundTag();
            explosionsTag.putByte("Type", (byte) rand.nextInt(5));
            explosionsTag.putByte("Flicker", (byte) 1);
            explosionsTag.putByte("Trail", (byte) 1);
            explosionsTag.putByte("Twinkle", (byte) 1);
            explosionsTag.putIntArray("Colors", colors); // generate random color
            explosionsTag.putIntArray("FadeColors", new int[] {16777215}); // fade to white since I thought it looked nice
            explosionsList.add(explosionsTag);

            CompoundTag fireworksTag = new CompoundTag();
            fireworksTag.putInt("Flight", 1);
            fireworksTag.put("Explosions", explosionsList);

            //NOW we create a rocket item stack and add ONLY the fireworks tag to it. The rest of the needed tags will be read for the entity later
            ItemStack fireworkItem = new ItemStack(Items.FIREWORK_ROCKET);
            CompoundTag fireWorkitemTag = fireworkItem.getOrCreateTag();
            fireWorkitemTag.put("Fireworks", fireworksTag);

            //Here we use the already crated rocket itemstack to feed the entity: It will read all the tags (Including the needed ID and stackcount from the stack itself) Lastly, we also specify the lifetime of the rocket. This goes directly before the item data.
            FireworkRocketEntity rocket = new FireworkRocketEntity(level, pos.x(), pos.y(), pos.z(), fireworkItem);
            CompoundTag fireworkEntityTag = new CompoundTag();
            fireworkEntityTag.putInt("LifeTime", rand.nextInt(30) + 10);
            rocket.addAdditionalSaveData(fireworkEntityTag);
            level.addFreshEntity(rocket); //Spawn the rocket entity
        }

        @SubscribeEvent
        public static void onLivingDeath(LivingDeathEvent event) {
            if( event.getSource() == null ) return;
            if( event.getEntity() == null ) return;

            // check if the entity died by a player
            if( !(event.getSource().getEntity() instanceof Player) ) return;
            Player player = ((Player) event.getSource().getEntity());

            // is the player holding the sword?
            if( player.getMainHandItem().getTags().filter(x->x.equals(ModTags.BOUNDLESS_SWORD_POST)).count() > 0 )
            {
                // we know now it is a boundless sword
                ItemStack pStack = player.getMainHandItem();

                // spawn fireworks?
                if( pStack.getEnchantmentLevel(ModEnchantments.FIREWORK.get()) > 0 )
                    spawnFireworkRocket(player.getLevel(), event.getEntity().position());

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

                    // does this weapon have 50% more energy gain enchant?
                    if( pStack.getEnchantmentLevel(ModEnchantments.ENERGY_GAIN.get()) > 0 )
                        energy_consumed = (int)(energy_consumed * 1.5D);

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

                // spawn fireworks?
                if( pStack.getEnchantmentLevel(ModEnchantments.FIREWORK.get()) > 0 )
                    spawnFireworkRocket(player.getLevel(), event.getEntity().position());

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

                    // does this weapon have 50% more energy gain enchant?
                    if( pStack.getEnchantmentLevel(ModEnchantments.ENERGY_GAIN.get()) > 0 )
                        energy_consumed = (int)(energy_consumed * 1.5D);

                    if( (long)current_max + (long)energy_consumed > BoundlessCommonConfig.MAX_ENERGY_CAP_BOW.get() )
                        pStack.getOrCreateTag().putInt("max_energy", BoundlessCommonConfig.MAX_ENERGY_CAP_BOW.get());
                    else
                        pStack.getOrCreateTag().putInt("max_energy", current_max + energy_consumed);

                }
            }
        }

        @SubscribeEvent
        public static void onLivingDrops(LivingDropsEvent event)
        {
            if( event == null ) return;
            if( event.isCanceled() ) return;
            if( event.getEntity() == null ) return;
            if( event.getEntity().level.isClientSide() ) return;
            if( event.getEntity() instanceof EnderDragon dragon )
            {
                // handle egg drop
                EndDragonFight fight = dragon.getDragonFight();
                if( BoundlessCommonConfig.DRAGON_EGG_SPAWN_OVERRIDE.get() && fight != null && fight.hasPreviouslyKilledDragon() )
                {
                    // spawn the egg
                    Level level = event.getEntity().getLevel();
                    level.setBlockAndUpdate(level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, EndPodiumFeature.END_PODIUM_LOCATION), Blocks.DRAGON_EGG.defaultBlockState());
                }
            }
        }
    }
}
