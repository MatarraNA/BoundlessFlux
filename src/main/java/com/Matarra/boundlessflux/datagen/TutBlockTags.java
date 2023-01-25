package com.Matarra.boundlessflux.datagen;

import com.Matarra.boundlessflux.BoundlessFlux;
import com.Matarra.boundlessflux.block.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class TutBlockTags extends BlockTagsProvider {

    public TutBlockTags(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, BoundlessFlux.MODID, helper);
    }

    @Override
    protected void addTags() {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.SWORD_STATION.get());
    }

    @Override
    public String getName() {
        return "Tutorial Tags";
    }
}
