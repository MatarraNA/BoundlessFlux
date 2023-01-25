package com.Matarra.boundlessflux.setup;

import com.Matarra.boundlessflux.BoundlessFlux;
import com.Matarra.boundlessflux.block.ModBlocks;
import com.Matarra.boundlessflux.client.SwordStationScreen;
import com.Matarra.boundlessflux.entity.LightningColors.*;
import com.Matarra.boundlessflux.entity.ModEntities;
import com.Matarra.boundlessflux.entity.projectile.*;
import com.Matarra.boundlessflux.item.ModItemProperties;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = BoundlessFlux.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup
{
    public static void init(FMLClientSetupEvent event)
    {
        event.enqueueWork( () -> {
            MenuScreens.register(ModBlocks.SWORD_STATION_CONTAINER.get(), SwordStationScreen::new);
        });

        // client registration
        ModItemProperties.registerItemProperties();
    }

    @SubscribeEvent
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.BOUNDLESS_LIGHTNING_GOLD.get(), BoundlessLightningGoldRenderer::new);
        event.registerEntityRenderer(ModEntities.BOUNDLESS_LIGHTNING_RED.get(), BoundlessLightningRedRenderer::new);
        event.registerEntityRenderer(ModEntities.BOUNDLESS_LIGHTNING_GREEN.get(), BoundlessLightningGreenRenderer::new);
        event.registerEntityRenderer(ModEntities.BOUNDLESS_LIGHTNING_PURPLE.get(), BoundlessLightningPurpleRenderer::new);
        event.registerEntityRenderer(ModEntities.BOUNDLESS_LIGHTNING_AQUA.get(), BoundlessLightningAquaRenderer::new);
    }

    @SubscribeEvent
    public static void onClientSetup(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(ModEntities.FIRST_ARROW.get(), FirstArrowRenderer::new);
        event.registerEntityRenderer(ModEntities.SHURIKEN_ARROW.get(), ShurikenArrowRenderer::new);
        event.registerEntityRenderer(ModEntities.KUNAI_ARROW.get(), KunaiArrowRenderer::new);
        event.registerEntityRenderer(ModEntities.RAYGUN_ARROW.get(), RaygunArrowRenderer::new);
        event.registerEntityRenderer(ModEntities.CARD_ARROW.get(), CardArrowRenderer::new);
    }
}
