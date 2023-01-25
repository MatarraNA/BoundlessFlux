package com.Matarra.boundlessflux.tags;

import com.Matarra.boundlessflux.BoundlessFlux;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags
{
    public static final TagKey<Item> BOUNDED_SWORD_PRE = ItemTags.create(new ResourceLocation(BoundlessFlux.MODID, "bounded_sword_pre"));
    public static final TagKey<Item> BOUNDED_BOW_PRE = ItemTags.create(new ResourceLocation(BoundlessFlux.MODID, "bounded_bow_pre"));
    public static final TagKey<Item> BOUNDLESS_SWORD_POST = ItemTags.create(new ResourceLocation(BoundlessFlux.MODID, "boundless_sword_post"));
    public static final TagKey<Item> BOUNDLESS_BOW_POST = ItemTags.create(new ResourceLocation(BoundlessFlux.MODID, "boundless_bow_post"));
    public static final TagKey<Item> BOUNDLESS_UPGRADE_SWORD = ItemTags.create(new ResourceLocation(BoundlessFlux.MODID, "boundless_upgrade"));
    public static final TagKey<Item> BOUNDLESS_UPGRADE_BOW = ItemTags.create(new ResourceLocation(BoundlessFlux.MODID, "boundless_upgrade_bow"));

}
