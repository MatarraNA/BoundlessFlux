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

        tag(ModTags.BOUNDLESS_UPGRADE_SWORD)
                .add(ModItems.UPGRADE_SWEEPING.get())
                .add(ModItems.UPGRADE_FIRE.get())
                .add(ModItems.UPGRADE_LOOTING.get());

        tag(ModTags.BOUNDLESS_UPGRADE_BOW)
                .add(ModItems.UPGRADE_FLAME.get())
                .add(ModItems.UPGRADE_INFINITY.get())
                .add(ModItems.UPGRADE_STORM_CALL.get());
    }

    @Override
    public String getName() {
        return "Tutorial Tags";
    }
}
