package com.Matarra.boundlessflux.datagen;

import com.Matarra.boundlessflux.BoundlessFlux;
import com.Matarra.boundlessflux.item.ModItems;
import com.Matarra.boundlessflux.tags.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class TutItemTags extends ItemTagsProvider {

    public TutItemTags(DataGenerator generator, BlockTagsProvider blockTags, ExistingFileHelper helper) {
        super(generator, blockTags, BoundlessFlux.MODID, helper);
    }

    @Override
    protected void addTags() {
        // BOWS
        tag(Tags.Items.TOOLS_BOWS)
                .add(ModItems.FIRST_BOW.get())
                .add(ModItems.FIRST_BOW_BOUNDLESS.get())
                .add(ModItems.SHURIKEN_BOUNDLESS.get())
                .add(ModItems.KUNAI_BOUNDLESS.get())
                .add(ModItems.CARD_BOUNDLESS.get())
                .add(ModItems.RAYGUN_BOUNDLESS.get());

        tag(ModTags.BOUNDED_BOW_PRE)
                .add(ModItems.FIRST_BOW.get());

        tag(ModTags.BOUNDLESS_BOW_POST)
                .add(ModItems.SHURIKEN_BOUNDLESS.get())
                .add(ModItems.RAYGUN_BOUNDLESS.get())
                .add(ModItems.KUNAI_BOUNDLESS.get())
                .add(ModItems.CARD_BOUNDLESS.get())
                .add(ModItems.FIRST_BOW_BOUNDLESS.get());

        tag(Tags.Items.TOOLS_SWORDS)
                .add(ModItems.FIRST_SWORD.get())
                .add(ModItems.FIRST_BOUNDLESS.get())
                .add(ModItems.RUNIC_BOUNDLESS.get())
                .add(ModItems.GENJI_BOUNDLESS.get())
                .add(ModItems.MASAMUNE_BOUNDLESS.get())
                .add(ModItems.CANDYCANE_BOUNDLESS.get())
                .add(ModItems.TITAN_BOUNDLESS.get())
                .add(ModItems.KEYBLADE_BOUNDLESS.get());

        tag(ModTags.BOUNDED_SWORD_PRE)
                .add(ModItems.FIRST_SWORD.get());

        tag(ModTags.BOUNDLESS_SWORD_POST)
                .add(ModItems.FIRST_BOUNDLESS.get())
                .add(ModItems.RUNIC_BOUNDLESS.get())
                .add(ModItems.GENJI_BOUNDLESS.get())
                .add(ModItems.MASAMUNE_BOUNDLESS.get())
                .add(ModItems.CANDYCANE_BOUNDLESS.get())
                .add(ModItems.TITAN_BOUNDLESS.get())
                .add(ModItems.KEYBLADE_BOUNDLESS.get());

        tag(Tags.Items.TOOLS_PICKAXES)
                .add(ModItems.BOUNDED_PICKAXE.get())
                .add(ModItems.BOUNDLESS_PICKAXE.get())
                .add(ModItems.CANDYCANE_PICKAXE.get())
                .add(ModItems.DRILL_PICKAXE.get())
                .add(ModItems.SCYTHE_PICKAXE.get())
                .add(ModItems.JELLYFISH_PICKAXE.get());

        tag(ModTags.BOUNDED_PICKAXE_PRE)
                .add(ModItems.BOUNDED_PICKAXE.get());

        tag(ModTags.BOUNDLESS_PICKAXE_POST)
                .add(ModItems.BOUNDLESS_PICKAXE.get())
                .add(ModItems.CANDYCANE_PICKAXE.get())
                .add(ModItems.DRILL_PICKAXE.get())
                .add(ModItems.SCYTHE_PICKAXE.get())
                .add(ModItems.JELLYFISH_PICKAXE.get());

        tag(Tags.Items.TOOLS_SHOVELS)
                .add(ModItems.BOUNDED_SHOVEL.get())
                .add(ModItems.SPATULA_SHOVEL.get())
                .add(ModItems.MAGNIFYING_SHOVEL.get())
                .add(ModItems.ICECREAM_SHOVEL.get())
                .add(ModItems.BOUNDLESS_SHOVEL.get());

        tag(ModTags.BOUNDED_SHOVEL_PRE)
                .add(ModItems.BOUNDED_SHOVEL.get());

        tag(ModTags.BOUNDLESS_SHOVEL_POST)
                .add(ModItems.SPATULA_SHOVEL.get())
                .add(ModItems.MAGNIFYING_SHOVEL.get())
                .add(ModItems.ICECREAM_SHOVEL.get())
                .add(ModItems.BOUNDLESS_SHOVEL.get());

        tag(Tags.Items.TOOLS_AXES)
                .add(ModItems.BOUNDLESS_AXE.get())
                .add(ModItems.GRAVITY_HAMMER_AXE.get())
                .add(ModItems.STOP_SIGN_AXE.get())
                .add(ModItems.BOUNDED_AXE.get());

        tag(ModTags.BOUNDED_AXE_PRE)
                .add(ModItems.BOUNDED_AXE.get());

        tag(ModTags.BOUNDLESS_AXE_POST)
                .add(ModItems.GRAVITY_HAMMER_AXE.get())
                .add(ModItems.STOP_SIGN_AXE.get())
                .add(ModItems.BOUNDLESS_AXE.get());

        tag(ModTags.BOUNDLESS_UPGRADE_SWORD)
                .add(ModItems.UPGRADE_SWEEPING.get())
                .add(ModItems.UPGRADE_FIRE.get())
                .add(ModItems.UPGRADE_ENERGY_GAIN.get())
                .add(ModItems.UPGRADE_FIREWORK.get())
                .add(ModItems.UPGRADE_LOOTING.get());

        tag(ModTags.BOUNDLESS_UPGRADE_BOW)
                .add(ModItems.UPGRADE_FLAME.get())
                .add(ModItems.UPGRADE_INFINITY.get())
                .add(ModItems.UPGRADE_ENERGY_GAIN.get())
                .add(ModItems.UPGRADE_FIREWORK.get())
                .add(ModItems.UPGRADE_STORM_CALL.get());

        tag(ModTags.BOUNDLESS_UPGRADE_PICKAXE)
                .add(ModItems.UPGRADE_VEIN_MINER_ORE.get());

        tag(ModTags.BOUNDLESS_UPGRADE_AXE)
                .add(ModItems.UPGRADE_FALLER.get());

        tag(ModTags.BOUNDLESS_UPGRADE_BLOCKBREAK_TOOL)
                .add(ModItems.UPGRADE_FORTUNE.get())
                .add(ModItems.UPGRADE_HAMMER.get())
                .add(ModItems.UPGRADE_ENERGY_GAIN.get())
                .add(ModItems.UPGRADE_SILK.get());
    }

    @Override
    public String getName() {
        return "Tutorial Tags";
    }
}
