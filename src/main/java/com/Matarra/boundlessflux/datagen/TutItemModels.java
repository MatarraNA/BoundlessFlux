package com.Matarra.boundlessflux.datagen;

import com.Matarra.boundlessflux.BoundlessFlux;
import com.Matarra.boundlessflux.block.ModBlocks;
import com.Matarra.boundlessflux.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class TutItemModels extends ItemModelProvider {

    public TutItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, BoundlessFlux.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        // ENTANGLED
        singleTexture(ModItems.LOOSELY_ENTANGLED_ITEM.getId().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("item/entangled/loose_core"));
        singleTexture(ModItems.LESSER_ENTANGLED_ITEM.getId().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("item/entangled/lesser_core"));
        singleTexture(ModItems.GREATER_ENTANGLED_ITEM.getId().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("item/entangled/greater_core"));
        singleTexture(ModItems.REFINED_ENTANGLED_ITEM.getId().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("item/entangled/refined_core"));
        singleTexture(ModItems.PRISTINE_ENTANGLED_ITEM.getId().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("item/entangled/pristine_core"));
        singleTexture(ModItems.AWAKENED_ENTANGLED_ITEM.getId().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("item/entangled/awakened_core"));

        // Sword Station BLOCK model
        withExistingParent(ModBlocks.SWORD_STATION.getId().getPath(), modLoc("block/sword_station/main"));

        // misc items
        singleTexture(ModItems.BOUNDLESS_GEM.getId().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("item/boundless_gem"));

        // UPGRADES
        singleTexture(ModItems.UPGRADE_LOOTING.getId().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("item/upgrades/upgrade_looting"));
        singleTexture(ModItems.UPGRADE_SWEEPING.getId().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("item/upgrades/upgrade_sweeping"));
        singleTexture(ModItems.UPGRADE_FIRE.getId().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("item/upgrades/upgrade_fire"));
        singleTexture(ModItems.UPGRADE_STORM_CALL.getId().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("item/upgrades/upgrade_storm"));
        singleTexture(ModItems.UPGRADE_FLAME.getId().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("item/upgrades/upgrade_flame"));
        singleTexture(ModItems.UPGRADE_INFINITY.getId().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("item/upgrades/upgrade_infinity"));
        singleTexture(ModItems.UPGRADE_ENERGY_GAIN.getId().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("item/upgrades/upgrade_energy_gain"));
        singleTexture(ModItems.UPGRADE_FORTUNE.getId().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("item/upgrades/upgrade_fortune"));
        singleTexture(ModItems.UPGRADE_HAMMER.getId().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("item/upgrades/upgrade_3x3"));
        singleTexture(ModItems.UPGRADE_SILK.getId().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("item/upgrades/upgrade_silk"));
        singleTexture(ModItems.UPGRADE_FALLER.getId().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("item/upgrades/upgrade_faller"));
        singleTexture(ModItems.UPGRADE_VEIN_MINER_ORE.getId().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("item/upgrades/upgrade_vein_miner"));
        singleTexture(ModItems.UPGRADE_FIREWORK.getId().getPath(),
                mcLoc("item/generated"),
                "layer0", modLoc("item/upgrades/upgrade_firework"));

        // PICKAXES
        singleTexture(ModItems.BOUNDED_PICKAXE.getId().getPath(),
                mcLoc("item/handheld"),
                "layer0", modLoc("item/pickaxe/boundless_pickaxe"));
        singleTexture(ModItems.BOUNDLESS_PICKAXE.getId().getPath(),
                mcLoc("item/handheld"),
                "layer0", modLoc("item/pickaxe/boundless_pickaxe"));
        singleTexture(ModItems.JELLYFISH_PICKAXE.getId().getPath(),
                mcLoc("item/handheld"),
                "layer0", modLoc("item/pickaxe/jellyfish_net"));
        singleTexture(ModItems.CANDYCANE_PICKAXE.getId().getPath(),
                mcLoc("item/handheld"),
                "layer0", modLoc("item/pickaxe/candy_cane_pickaxe"));
        singleTexture(ModItems.DRILL_PICKAXE.getId().getPath(),
                mcLoc("item/handheld"),
                "layer0", modLoc("item/pickaxe/drill"));
        singleTexture(ModItems.SCYTHE_PICKAXE.getId().getPath(),
                mcLoc("item/handheld"),
                "layer0", modLoc("item/pickaxe/scythe"));

        // SHOVELS
        singleTexture(ModItems.BOUNDED_SHOVEL.getId().getPath(),
                mcLoc("item/handheld"),
                "layer0", modLoc("item/shovel/boundless_shovel"));
        singleTexture(ModItems.BOUNDLESS_SHOVEL.getId().getPath(),
                mcLoc("item/handheld"),
                "layer0", modLoc("item/shovel/boundless_shovel"));
        singleTexture(ModItems.SPATULA_SHOVEL.getId().getPath(),
                mcLoc("item/handheld"),
                "layer0", modLoc("item/shovel/golden_spatula"));
        singleTexture(ModItems.MAGNIFYING_SHOVEL.getId().getPath(),
                mcLoc("item/handheld"),
                "layer0", modLoc("item/shovel/magnifying"));
        singleTexture(ModItems.ICECREAM_SHOVEL.getId().getPath(),
                mcLoc("item/handheld"),
                "layer0", modLoc("item/shovel/icecream_cone"));

        // AXES
        singleTexture(ModItems.BOUNDED_AXE.getId().getPath(),
                mcLoc("item/handheld"),
                "layer0", modLoc("item/axe/bounded_axe"));
        singleTexture(ModItems.BOUNDLESS_AXE.getId().getPath(),
                mcLoc("item/handheld"),
                "layer0", modLoc("item/axe/bounded_axe"));
        singleTexture(ModItems.GRAVITY_HAMMER_AXE.getId().getPath(),
                mcLoc("item/handheld"),
                "layer0", modLoc("item/axe/gravity_hammer"));
        singleTexture(ModItems.STOP_SIGN_AXE.getId().getPath(),
                mcLoc("item/handheld"),
                "layer0", modLoc("item/axe/stop_sign"));

        // weapons
        singleTexture(ModItems.FIRST_SWORD.getId().getPath(),
                mcLoc("item/handheld"),
                "layer0", modLoc("item/first_sword"));
        singleTexture(ModItems.FIRST_BOUNDLESS.getId().getPath(),
                mcLoc("item/handheld"),
                "layer0", modLoc("item/first_sword"));
        singleTexture(ModItems.RUNIC_BOUNDLESS.getId().getPath(),
                mcLoc("item/handheld"),
                "layer0", modLoc("item/runic_blade"));
        singleTexture(ModItems.KEYBLADE_BOUNDLESS.getId().getPath(),
                mcLoc("item/handheld"),
                "layer0", modLoc("item/kingdom_key"));
        singleTexture(ModItems.GENJI_BOUNDLESS.getId().getPath(),
                mcLoc("item/handheld"),
                "layer0", modLoc("item/genji_sword"));
        singleTexture(ModItems.MASAMUNE_BOUNDLESS.getId().getPath(),
                mcLoc("item/handheld"),
                "layer0", modLoc("item/masamune"));
        singleTexture(ModItems.CANDYCANE_BOUNDLESS.getId().getPath(),
                mcLoc("item/handheld"),
                "layer0", modLoc("item/candy_cane"));
        singleTexture(ModItems.TITAN_BOUNDLESS.getId().getPath(),
                mcLoc("item/handheld"),
                "layer0", modLoc("item/titan_sword"));
    }
}
