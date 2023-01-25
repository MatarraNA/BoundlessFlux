package com.Matarra.boundlessflux.setup;

import com.Matarra.boundlessflux.BoundlessFlux;
import com.Matarra.boundlessflux.block.ModBlocks;
import com.Matarra.boundlessflux.config.BoundlessCommonConfig;
import com.Matarra.boundlessflux.enchant.ModEnchantments;
import com.Matarra.boundlessflux.entity.ModEntities;
import com.Matarra.boundlessflux.item.ModItems;
import com.Matarra.boundlessflux.network.PacketHandler;
import com.Matarra.boundlessflux.sound.ModSounds;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class Registration
{
    public static void init()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        // register the config early
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BoundlessCommonConfig.SPEC, BoundlessFlux.MODID + ".toml");
        ModItems.ITEMS.register(bus);
        ModBlocks.BLOCKS.register(bus);
        ModBlocks.BLOCK_ENTITIES.register(bus);
        ModBlocks.CONTAINERS.register(bus);
        ModEntities.ENTITIES.register(bus);
        ModEnchantments.register(bus);
        ModSounds.register(bus);
        PacketHandler.register();
    }
}
