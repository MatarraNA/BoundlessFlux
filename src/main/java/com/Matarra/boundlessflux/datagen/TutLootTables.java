package com.Matarra.boundlessflux.datagen;

import com.Matarra.boundlessflux.block.ModBlocks;
import com.Matarra.boundlessflux.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.common.Mod;

public class TutLootTables extends BaseLootTableProvider {

    public TutLootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables()
    {
//        lootTables.put(Registration.GENERATOR.get(), createStandardTable("generator", Registration.GENERATOR.get(), Registration.GENERATOR_BE.get()));
        lootTables.put(ModBlocks.SWORD_STATION.get(), createStandardTable("sword_station", ModBlocks.SWORD_STATION.get(), ModBlocks.SWORD_STATION_BE.get()));
//        lootTables.put(Registration.MYSTERIOUS_ORE_OVERWORLD.get(), createSilkTouchTable("mysterious_ore_overworld", Registration.MYSTERIOUS_ORE_OVERWORLD.get(), Registration.RAW_MYSTERIOUS_CHUNK.get(), 1, 3));
//        lootTables.put(Registration.MYSTERIOUS_ORE_NETHER.get(), createSilkTouchTable("mysterious_ore_nether", Registration.MYSTERIOUS_ORE_NETHER.get(), Registration.RAW_MYSTERIOUS_CHUNK.get(), 1, 3));
//        lootTables.put(Registration.MYSTERIOUS_ORE_END.get(), createSilkTouchTable("mysterious_ore_end", Registration.MYSTERIOUS_ORE_END.get(), Registration.RAW_MYSTERIOUS_CHUNK.get(), 1, 3));
//        lootTables.put(Registration.MYSTERIOUS_ORE_DEEPSLATE.get(), createSilkTouchTable("mysterious_ore_deepslate", Registration.MYSTERIOUS_ORE_DEEPSLATE.get(), Registration.RAW_MYSTERIOUS_CHUNK.get(), 1, 3));
    }
}
