package com.Matarra.boundlessflux.datagen;

import com.Matarra.boundlessflux.BoundlessFlux;
import com.Matarra.boundlessflux.block.ModBlocks;
import com.Matarra.boundlessflux.item.ModItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class TutRecipes extends RecipeProvider {

    public TutRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModItems.LOOSELY_ENTANGLED_ITEM.get())
                .pattern("rrr")
                .pattern("rcl")
                .pattern("lll")
                .define('r', Tags.Items.DUSTS_REDSTONE)
                .define('c', ItemTags.COALS)
                .define('l', Tags.Items.GEMS_LAPIS)
                .group(BoundlessFlux.MODID)
                .unlockedBy("coal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COAL))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.LESSER_ENTANGLED_ITEM.get())
                .pattern(" i ")
                .pattern("ici")
                .pattern(" i ")
                .define('i', Tags.Items.INGOTS_IRON)
                .define('c', ModItems.LOOSELY_ENTANGLED_ITEM.get())
                .group(BoundlessFlux.MODID)
                .unlockedBy("coal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COAL))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.GREATER_ENTANGLED_ITEM.get())
                .pattern("cic")
                .pattern("i i")
                .pattern("cic")
                .define('i', Tags.Items.INGOTS_GOLD)
                .define('c', ModItems.LESSER_ENTANGLED_ITEM.get())
                .group(BoundlessFlux.MODID)
                .unlockedBy("coal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COAL))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.REFINED_ENTANGLED_ITEM.get())
                .pattern("cic")
                .pattern("i i")
                .pattern("cic")
                .define('i', Tags.Items.GEMS_DIAMOND)
                .define('c', ModItems.GREATER_ENTANGLED_ITEM.get())
                .group(BoundlessFlux.MODID)
                .unlockedBy("coal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COAL))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.PRISTINE_ENTANGLED_ITEM.get())
                .pattern("cic")
                .pattern("i i")
                .pattern("cic")
                .define('i', Tags.Items.NETHER_STARS)
                .define('c', ModItems.REFINED_ENTANGLED_ITEM.get())
                .group(BoundlessFlux.MODID)
                .unlockedBy("coal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COAL))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.AWAKENED_ENTANGLED_ITEM.get())
                .pattern("cic")
                .pattern("isi")
                .pattern("cic")
                .define('i', Items.END_CRYSTAL)
                .define('s', Items.DRAGON_EGG)
                .define('c', ModItems.PRISTINE_ENTANGLED_ITEM.get())
                .group(BoundlessFlux.MODID)
                .unlockedBy("coal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COAL))
                .save(consumer);

        // PICKAXE
        ShapedRecipeBuilder.shaped(ModItems.BOUNDED_PICKAXE.get())
                .pattern("www")
                .pattern(" s ")
                .pattern(" s ")
                .define('s', Items.STICK)
                .define('w', ModItems.AWAKENED_ENTANGLED_ITEM.get())
                .group(BoundlessFlux.MODID)
                .unlockedBy("coal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COAL))
                .save(consumer);

        // SHOVEL
        ShapedRecipeBuilder.shaped(ModItems.BOUNDED_SHOVEL.get())
                .pattern(" w ")
                .pattern(" s ")
                .pattern(" s ")
                .define('s', Items.STICK)
                .define('w', ModItems.AWAKENED_ENTANGLED_ITEM.get())
                .group(BoundlessFlux.MODID)
                .unlockedBy("coal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COAL))
                .save(consumer);

        // sword
        ShapedRecipeBuilder.shaped(ModItems.FIRST_SWORD.get())
                .pattern(" w ")
                .pattern(" w ")
                .pattern(" s ")
                .define('s', Items.STICK)
                .define('w', ModItems.AWAKENED_ENTANGLED_ITEM.get())
                .group(BoundlessFlux.MODID)
                .unlockedBy("coal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COAL))
                .save(consumer);

        // bow
        ShapedRecipeBuilder.shaped(ModItems.FIRST_BOW.get())
                .pattern(" ws")
                .pattern("w s")
                .pattern(" ws")
                .define('s', Items.STRING)
                .define('w', ModItems.AWAKENED_ENTANGLED_ITEM.get())
                .group(BoundlessFlux.MODID)
                .unlockedBy("coal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COAL))
                .save(consumer);

        //boundless gem
        ShapedRecipeBuilder.shaped(ModItems.BOUNDLESS_GEM.get())
                .pattern("rrr")
                .pattern("rsr")
                .pattern("rrr")
                .define('r', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .define('s', ModItems.AWAKENED_ENTANGLED_ITEM.get())
                .group(BoundlessFlux.MODID)
                .unlockedBy("coal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COAL))
                .save(consumer);

        // UPGRADES
        ShapedRecipeBuilder.shaped(ModItems.UPGRADE_INFINITY.get())
                .pattern("ini")
                .pattern("nsn")
                .pattern("ini")
                .define('n', Tags.Items.NUGGETS_IRON)
                .define('i', Tags.Items.INGOTS_IRON)
                .define('s', Tags.Items.STORAGE_BLOCKS_EMERALD)
                .group(BoundlessFlux.MODID)
                .unlockedBy("coal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COAL))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.UPGRADE_FLAME.get())
                .pattern("ini")
                .pattern("nsn")
                .pattern("ini")
                .define('n', Tags.Items.NUGGETS_IRON)
                .define('i', Tags.Items.INGOTS_IRON)
                .define('s', Items.FLINT_AND_STEEL)
                .group(BoundlessFlux.MODID)
                .unlockedBy("coal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COAL))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.UPGRADE_FIRE.get())
                .pattern("ini")
                .pattern("nsn")
                .pattern("ini")
                .define('n', Tags.Items.NUGGETS_IRON)
                .define('i', Tags.Items.INGOTS_IRON)
                .define('s', Items.CRYING_OBSIDIAN)
                .group(BoundlessFlux.MODID)
                .unlockedBy("coal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COAL))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.UPGRADE_STORM_CALL.get())
                .pattern("ini")
                .pattern("nsn")
                .pattern("ini")
                .define('n', Tags.Items.NUGGETS_IRON)
                .define('i', Tags.Items.INGOTS_IRON)
                .define('s', Items.BEACON)
                .group(BoundlessFlux.MODID)
                .unlockedBy("coal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COAL))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.UPGRADE_SWEEPING.get())
                .pattern("ini")
                .pattern("nsn")
                .pattern("ini")
                .define('n', Tags.Items.NUGGETS_IRON)
                .define('i', Tags.Items.INGOTS_IRON)
                .define('s', Items.DIAMOND_SWORD)
                .group(BoundlessFlux.MODID)
                .unlockedBy("coal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COAL))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.UPGRADE_LOOTING.get())
                .pattern("ini")
                .pattern("nsn")
                .pattern("ini")
                .define('n', Tags.Items.NUGGETS_IRON)
                .define('i', Tags.Items.INGOTS_IRON)
                .define('s', Items.GHAST_TEAR)
                .group(BoundlessFlux.MODID)
                .unlockedBy("coal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COAL))
                .save(consumer);

        // BLOCKS
        ShapedRecipeBuilder.shaped(ModBlocks.SWORD_STATION_ITEM.get())
                .pattern("bgb")
                .pattern("grg")
                .pattern("bgb")
                .define('r', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .define('b', Items.BLACK_CONCRETE)
                .define('g', Items.GRAY_CONCRETE)
                .group(BoundlessFlux.MODID)
                .unlockedBy("coal", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COAL))
                .save(consumer);
    }
}
