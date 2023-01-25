package com.Matarra.boundlessflux.datagen;

import com.Matarra.boundlessflux.BoundlessFlux;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.data.event.GatherDataEvent;

@Mod.EventBusSubscriber(modid = BoundlessFlux.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(event.includeServer(), new TutRecipes(generator));
        generator.addProvider(event.includeServer(), new TutLootTables(generator));
        TutBlockTags blockTags = new TutBlockTags(generator, event.getExistingFileHelper());
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new TutItemTags(generator, blockTags, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new TutBlockStates(generator, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new TutItemModels(generator, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new TutLanguageProvider(generator, "en_us"));
    }
}
