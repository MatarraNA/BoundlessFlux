package com.Matarra.boundlessflux.item;

import com.Matarra.boundlessflux.BoundlessFlux;
import com.Matarra.boundlessflux.entity.projectile.FirstArrow;
import com.Matarra.boundlessflux.entity.projectile.ShurikenArrow;
import com.Matarra.boundlessflux.item.arrow.*;
import com.Matarra.boundlessflux.item.bow.*;
import com.Matarra.boundlessflux.item.bow.base.BoundedBowBase;
import com.Matarra.boundlessflux.item.misc.BoundlessGem;
import com.Matarra.boundlessflux.item.swords.*;
import com.Matarra.boundlessflux.item.upgrades.*;
import com.Matarra.boundlessflux.setup.ModSetup;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BoundlessFlux.MODID);

    // Some common properties for our blocks and items
    public static final Item.Properties ITEM_PROPERTIES = new Item.Properties().tab(ModSetup.ITEM_GROUP);
    public static final Item.Properties ITEM_PROPERTIES_NOTAB = new Item.Properties().tab(null);
    public static final Item.Properties ITEM_PROPERTIES_NOSTACK = new Item.Properties().tab(ModSetup.ITEM_GROUP).stacksTo(1);

    // COMPONENTS
    public static final RegistryObject<Item> LOOSELY_ENTANGLED_ITEM = ITEMS.register("loosely_entangled_matter", () ->
            new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> LESSER_ENTANGLED_ITEM = ITEMS.register("lesser_entangled_matter", () ->
            new Item(ITEM_PROPERTIES)
            {
                @Override
                public Component getName(ItemStack pStack) {
                    return super.getName(pStack).copy().withStyle(ChatFormatting.GREEN);
                }
            });
    public static final RegistryObject<Item> GREATER_ENTANGLED_ITEM = ITEMS.register("greater_entangled_matter", () ->
            new Item(ITEM_PROPERTIES)
            {
                @Override
                public Component getName(ItemStack pStack) {
                    return super.getName(pStack).copy().withStyle(ChatFormatting.BLUE);
                }
            });
    public static final RegistryObject<Item> REFINED_ENTANGLED_ITEM = ITEMS.register("refined_entangled_matter", () ->
            new Item(ITEM_PROPERTIES)
            {
                @Override
                public Component getName(ItemStack pStack) {
                    return super.getName(pStack).copy().withStyle(ChatFormatting.LIGHT_PURPLE);
                }
            });
    public static final RegistryObject<Item> PRISTINE_ENTANGLED_ITEM = ITEMS.register("pristine_entangled_matter", () ->
            new Item(ITEM_PROPERTIES)
            {
                @Override
                public Component getName(ItemStack pStack) {
                    return super.getName(pStack).copy().withStyle(ChatFormatting.YELLOW);
                }
            });
    public static final RegistryObject<Item> AWAKENED_ENTANGLED_ITEM = ITEMS.register("awakened_entangled_matter", () ->
            new Item(ITEM_PROPERTIES)
            {
                @Override
                public Component getName(ItemStack pStack) {
                    return super.getName(pStack).copy().withStyle(ChatFormatting.GOLD);
                }
            });

    // ARROWS
    public static final RegistryObject<FirstArrowItem> FIRST_ARROW = ITEMS.register("first_arrow",
            () -> new FirstArrowItem(ITEM_PROPERTIES_NOTAB));
    public static final RegistryObject<ShurikenArrowItem> SHURIKEN_ARROW = ITEMS.register("shuriken_arrow",
            () -> new ShurikenArrowItem(ITEM_PROPERTIES_NOTAB));
    public static final RegistryObject<KunaiArrowItem> KUNAI_ARROW = ITEMS.register("kunai_arrow",
            () -> new KunaiArrowItem(ITEM_PROPERTIES_NOTAB));
    public static final RegistryObject<CardArrowItem> CARD_ARROW = ITEMS.register("card_arrow",
            () -> new CardArrowItem(ITEM_PROPERTIES_NOTAB));
    public static final RegistryObject<RaygunArrowItem> RAYGUN_ARROW = ITEMS.register("raygun_arrow",
            () -> new RaygunArrowItem(ITEM_PROPERTIES_NOTAB));

    // BOWS
    public static final RegistryObject<FirstBowBounded> FIRST_BOW = ITEMS.register("first_bow",
            () -> new FirstBowBounded(ITEM_PROPERTIES));
    public static final RegistryObject<FirstBowBoundless> FIRST_BOW_BOUNDLESS = ITEMS.register("first_bow_boundless",
            () -> new FirstBowBoundless(ITEM_PROPERTIES));
    public static final RegistryObject<ShurikenBoundless> SHURIKEN_BOUNDLESS = ITEMS.register("shuriken_boundless",
            () -> new ShurikenBoundless(ITEM_PROPERTIES));
    public static final RegistryObject<KunaiBoundless> KUNAI_BOUNDLESS = ITEMS.register("kunai_boundless",
            () -> new KunaiBoundless(ITEM_PROPERTIES));
    public static final RegistryObject<CardBoundless> CARD_BOUNDLESS = ITEMS.register("card_boundless",
            () -> new CardBoundless(ITEM_PROPERTIES));
    public static final RegistryObject<RaygunBoundless> RAYGUN_BOUNDLESS = ITEMS.register("raygun_boundless",
            () -> new RaygunBoundless(ITEM_PROPERTIES));

    // MISC ITEMS
    public static final RegistryObject<BoundlessGem> BOUNDLESS_GEM = ITEMS.register("boundless_gem", () ->
            new BoundlessGem(ITEM_PROPERTIES_NOSTACK));

    // UPGRADES
    public static final RegistryObject<ItemLootingUpgrade> UPGRADE_LOOTING = ITEMS.register("upgrade_looting", () ->
            new ItemLootingUpgrade(ITEM_PROPERTIES_NOSTACK));
    public static final RegistryObject<ItemSweepingUpgrade> UPGRADE_SWEEPING = ITEMS.register("upgrade_sweeping", () ->
            new ItemSweepingUpgrade(ITEM_PROPERTIES_NOSTACK));
    public static final RegistryObject<ItemFireUpgrade> UPGRADE_FIRE = ITEMS.register("upgrade_fire", () ->
            new ItemFireUpgrade(ITEM_PROPERTIES_NOSTACK));
    public static final RegistryObject<ItemStormCallUpgrade> UPGRADE_STORM_CALL = ITEMS.register("upgrade_storm", () ->
            new ItemStormCallUpgrade(ITEM_PROPERTIES_NOSTACK));
    public static final RegistryObject<ItemInfinityUpgrade> UPGRADE_INFINITY = ITEMS.register("upgrade_infinity", () ->
            new ItemInfinityUpgrade(ITEM_PROPERTIES_NOSTACK));
    public static final RegistryObject<ItemFlameUpgrade> UPGRADE_FLAME = ITEMS.register("upgrade_flame", () ->
            new ItemFlameUpgrade(ITEM_PROPERTIES_NOSTACK));

    // BOUNDED SWORDS
    public static final RegistryObject<FirstBounded> FIRST_SWORD = ITEMS.register("first_sword", () ->
            new FirstBounded(CustomTiers.BOUNDED_TIER, 9, -2f, ITEM_PROPERTIES));

    // BOUNDLESS SWORDS
    public static final RegistryObject<RunicBoundless> RUNIC_BOUNDLESS = ITEMS.register("runic_boundless", () ->
            new RunicBoundless(CustomTiers.BOUNDLESS_TIER, 9, -2f, ITEM_PROPERTIES));
    public static final RegistryObject<KeybladeBoundless> KEYBLADE_BOUNDLESS = ITEMS.register("keyblade_boundless", () ->
            new KeybladeBoundless(CustomTiers.BOUNDLESS_TIER, 9, -2f, ITEM_PROPERTIES));

    public static final RegistryObject<GenjiBoundless> GENJI_BOUNDLESS = ITEMS.register("genji_boundless", () ->
            new GenjiBoundless(CustomTiers.BOUNDLESS_TIER, 9, -2f, ITEM_PROPERTIES));

    public static final RegistryObject<MasamuneBoundless> MASAMUNE_BOUNDLESS = ITEMS.register("masamune_boundless", () ->
            new MasamuneBoundless(CustomTiers.BOUNDLESS_TIER, 9, -2f, ITEM_PROPERTIES));

    public static final RegistryObject<CandyCaneBoundless> CANDYCANE_BOUNDLESS = ITEMS.register("candycane_boundless", () ->
            new CandyCaneBoundless(CustomTiers.BOUNDLESS_TIER, 9, -2f, ITEM_PROPERTIES));

    public static final RegistryObject<TitanBoundless> TITAN_BOUNDLESS = ITEMS.register("titan_blade", () ->
            new TitanBoundless(CustomTiers.BOUNDLESS_TIER, 9, -2f, ITEM_PROPERTIES));


    public static final RegistryObject<FirstBoundless> FIRST_BOUNDLESS = ITEMS.register("first_sword_boundless", () ->
            new FirstBoundless(CustomTiers.BOUNDLESS_TIER, 9, -2f, ITEM_PROPERTIES));

    public static class CustomTiers
    {
        public static final Tier BOUNDED_TIER = new ForgeTier(
                2,
                9,
                0f,
                0f,
                0,
                null,
                () -> Ingredient.of(Ingredient.EMPTY.getItems())
        );

        public static final ForgeTier BOUNDLESS_TIER = new ForgeTier(
                2,
                9,
                0f,
                0f,
                0,
                null,
                () -> Ingredient.of(Ingredient.EMPTY.getItems())
        );
    }
}
