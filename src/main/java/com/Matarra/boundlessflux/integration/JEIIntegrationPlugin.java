package com.Matarra.boundlessflux.integration;

import com.Matarra.boundlessflux.BoundlessFlux;
import com.Matarra.boundlessflux.block.ModBlocks;
import com.Matarra.boundlessflux.item.ModItems;
import com.Matarra.boundlessflux.tags.ModTags;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

@JeiPlugin
public class JEIIntegrationPlugin implements IModPlugin
{
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(BoundlessFlux.MODID, "jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        IModPlugin.super.registerRecipes(registration);

        // add the description to all of our boundless swords
        ForgeRegistries.ITEMS.tags().getTag(ModTags.BOUNDLESS_SWORD_POST).stream().forEach(x->
        {
            registration.addIngredientInfo(new ItemStack(x), VanillaTypes.ITEM_STACK,
                    Component.literal("To upgrade your Bounded Sword to a boundless variant, " +
                            "you must take your Bounded Sword along with a fully charged Energy Crystal " +
                            "in your offhand. Then at exactly midnight, you must right click while " +
                            "looking directly at the moon. Energy will strike you elevating your weapon to " +
                            "the boundless tier."));
        });

        // add the description to all of our boundless bows
        ForgeRegistries.ITEMS.tags().getTag(ModTags.BOUNDLESS_BOW_POST).stream().forEach(x->
        {
            registration.addIngredientInfo(new ItemStack(x), VanillaTypes.ITEM_STACK,
                    Component.literal("To upgrade your Bounded Bow to a boundless variant, " +
                            "you must take your Bounded Bow along with a fully charged Energy Crystal " +
                            "in your offhand. Then at exactly midnight, you must right click while " +
                            "looking directly at the moon. Energy will strike you elevating your weapon to " +
                            "the boundless tier."));
        });

        // add the description to all of our boundless pickaxes
        ForgeRegistries.ITEMS.tags().getTag(ModTags.BOUNDLESS_PICKAXE_POST).stream().forEach(x->
        {
            registration.addIngredientInfo(new ItemStack(x), VanillaTypes.ITEM_STACK,
                    Component.literal("To upgrade your Bounded Pickaxe to a boundless variant, " +
                            "you must take your Bounded Pickaxe along with a fully charged Energy Crystal " +
                            "in your offhand. Then at exactly midnight, you must right click while " +
                            "looking directly at the moon. Energy will strike you elevating your tool to " +
                            "the boundless tier."));
        });

        // add the description to all of our boundless SHOVELS
        ForgeRegistries.ITEMS.tags().getTag(ModTags.BOUNDLESS_SHOVEL_POST).stream().forEach(x->
        {
            registration.addIngredientInfo(new ItemStack(x), VanillaTypes.ITEM_STACK,
                    Component.literal("To upgrade your Bounded Shovel to a boundless variant, " +
                            "you must take your Bounded Shovel along with a fully charged Energy Crystal " +
                            "in your offhand. Then at exactly midnight, you must right click while " +
                            "looking directly at the moon. Energy will strike you elevating your tool to " +
                            "the boundless tier."));
        });

        // add the description to all of our boundless AXES
        ForgeRegistries.ITEMS.tags().getTag(ModTags.BOUNDLESS_AXE_POST).stream().forEach(x->
        {
            registration.addIngredientInfo(new ItemStack(x), VanillaTypes.ITEM_STACK,
                    Component.literal("To upgrade your Bounded Axe to a boundless variant, " +
                            "you must take your Bounded Axe along with a fully charged Energy Crystal " +
                            "in your offhand. Then at exactly midnight, you must right click while " +
                            "looking directly at the moon. Energy will strike you elevating your tool to " +
                            "the boundless tier."));
        });

        // add the description to all of our upgrades
        ForgeRegistries.ITEMS.tags().getTag(ModTags.BOUNDLESS_UPGRADE_SWORD).stream().forEach(x->
        {
            registration.addIngredientInfo(new ItemStack(x), VanillaTypes.ITEM_STACK,
                    Component.literal("To use this sword upgrade, insert it into the " +
                            "Weapon Entity Entangler Bench along with a compatible boundless " +
                            "weapon."));
        });
        ForgeRegistries.ITEMS.tags().getTag(ModTags.BOUNDLESS_UPGRADE_BOW).stream().forEach(x->
        {
            registration.addIngredientInfo(new ItemStack(x), VanillaTypes.ITEM_STACK,
                    Component.literal("To use this bow upgrade, insert it into the " +
                            "Weapon Entity Entangler Bench along with a compatible boundless " +
                            "weapon."));
        });
        ForgeRegistries.ITEMS.tags().getTag(ModTags.BOUNDLESS_UPGRADE_AXE).stream().forEach(x->
        {
            registration.addIngredientInfo(new ItemStack(x), VanillaTypes.ITEM_STACK,
                    Component.literal("To use this axe upgrade, insert it into the " +
                            "Weapon Entity Entangler Bench along with a compatible boundless " +
                            "axe."));
        });
        ForgeRegistries.ITEMS.tags().getTag(ModTags.BOUNDLESS_UPGRADE_PICKAXE).stream().forEach(x->
        {
            registration.addIngredientInfo(new ItemStack(x), VanillaTypes.ITEM_STACK,
                    Component.literal("To use this pickaxe upgrade, insert it into the " +
                            "Weapon Entity Entangler Bench along with a compatible boundless " +
                            "pickaxe."));
        });
        ForgeRegistries.ITEMS.tags().getTag(ModTags.BOUNDLESS_UPGRADE_BLOCKBREAK_TOOL).stream().forEach(x->
        {
            registration.addIngredientInfo(new ItemStack(x), VanillaTypes.ITEM_STACK,
                    Component.literal("To use this tool upgrade, insert it into the " +
                            "Weapon Entity Entangler Bench along with a compatible boundless " +
                            "tool."));
        });
    }
}
