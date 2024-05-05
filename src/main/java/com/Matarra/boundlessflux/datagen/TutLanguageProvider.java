package com.Matarra.boundlessflux.datagen;

import com.Matarra.boundlessflux.BoundlessFlux;
import com.Matarra.boundlessflux.block.ModBlocks;
import com.Matarra.boundlessflux.enchant.ModEnchantments;
import com.Matarra.boundlessflux.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import static com.Matarra.boundlessflux.setup.ModSetup.TAB_NAME;

public class TutLanguageProvider extends LanguageProvider {

    public TutLanguageProvider(DataGenerator gen, String locale) {
        super(gen, BoundlessFlux.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup." + TAB_NAME, "Boundless Flux");
        add(ModBlocks.SWORD_STATION.get(), "Weapon Entity Entangler Bench");
        add(ModItems.BOUNDLESS_GEM.get(), "Energy Crystal");
        add(ModItems.FIRST_SWORD.get(), "Bounded Sword");
        add(ModItems.FIRST_BOUNDLESS.get(), "Boundless Sword");
        add(ModItems.RUNIC_BOUNDLESS.get(), "Runic Blade");
        add(ModItems.KEYBLADE_BOUNDLESS.get(), "Kingdom Key D");
        add(ModItems.GENJI_BOUNDLESS.get(), "Ryu-Ichimonji");
        add(ModItems.MASAMUNE_BOUNDLESS.get(), "Masamune");
        add(ModItems.CANDYCANE_BOUNDLESS.get(), "Candy Cane");
        add(ModItems.TITAN_BOUNDLESS.get(), "ODM Blade");
        add(ModItems.UPGRADE_LOOTING.get(), "Looting Enhancement");
        add(ModItems.UPGRADE_SWEEPING.get(), "Sweeping Enhancement");
        add(ModItems.UPGRADE_FIRE.get(), "Fire Enhancement");
        add(ModItems.UPGRADE_HAMMER.get(), "Hammer 3x3 Enhancement");
        add(ModItems.UPGRADE_ENERGY_GAIN.get(), "Energy Gain Enhancement");
        add(ModItems.UPGRADE_SILK.get(), "Silk Touch Enhancement");
        add(ModItems.UPGRADE_FORTUNE.get(), "Fortune Enhancement");
        add(ModItems.FIRST_BOW.get(), "Bounded Bow");
        add(ModItems.FIRST_BOW_BOUNDLESS.get(), "Boundless Bow");
        add(ModItems.SHURIKEN_BOUNDLESS.get(), "Shuriken");
        add(ModItems.KUNAI_BOUNDLESS.get(), "Kunai");
        add(ModItems.RAYGUN_BOUNDLESS.get(), "Raygun");
        add(ModItems.CARD_BOUNDLESS.get(), "Throwing Card");
        add(ModEnchantments.STORM_CALL.get(), "Storm Call");
        add(ModEnchantments.HAMMER.get(), "Hammer 3x3");
        add(ModEnchantments.ENERGY_GAIN.get(), "Energy Gain");
        add(ModItems.UPGRADE_STORM_CALL.get(), "Storm Call Enhancement");
        add(ModItems.UPGRADE_FLAME.get(), "Flame Enhancement");
        add(ModItems.UPGRADE_INFINITY.get(), "Infinity Enhancement");

        // PICKS
        add(ModItems.BOUNDED_PICKAXE.get(), "Bounded Pickaxe");
        add(ModItems.BOUNDLESS_PICKAXE.get(), "Boundless Pickaxe");
        add(ModItems.JELLYFISH_PICKAXE.get(), "Jellyfish Net");
        add(ModItems.CANDYCANE_PICKAXE.get(), "Candy Pickaxe");
        add(ModItems.DRILL_PICKAXE.get(), "Drill");
        add(ModItems.SCYTHE_PICKAXE.get(), "Scythe");

        // SHOVEL
        add(ModItems.BOUNDED_SHOVEL.get(), "Bounded Shovel");
        add(ModItems.BOUNDLESS_SHOVEL.get(), "Boundless Shovel");

        // ENTANGLEMENT
        add(ModItems.LOOSELY_ENTANGLED_ITEM.get(), "Loosely Entangled Mass");
        add(ModItems.LESSER_ENTANGLED_ITEM.get(), "Lesser Entangled Matter");
        add(ModItems.GREATER_ENTANGLED_ITEM.get(), "Greater Entangled Matter");
        add(ModItems.REFINED_ENTANGLED_ITEM.get(), "Refined Entangled Matter");
        add(ModItems.PRISTINE_ENTANGLED_ITEM.get(), "Pristine Entangled Matter");
        add(ModItems.AWAKENED_ENTANGLED_ITEM.get(), "Awakened Entangled Matter");
    }
}
