package com.Matarra.boundlessflux.block;

import com.Matarra.boundlessflux.BoundlessFlux;
import com.Matarra.boundlessflux.block.sword_style.SwordStationBE;
import com.Matarra.boundlessflux.block.sword_style.SwordStationBlock;
import com.Matarra.boundlessflux.block.sword_style.SwordStationContainer;
import com.Matarra.boundlessflux.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BoundlessFlux.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BoundlessFlux.MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, BoundlessFlux.MODID);

    // register our blocks / block_items
    public static final RegistryObject<SwordStationBlock> SWORD_STATION = BLOCKS.register("sword_station", () -> new SwordStationBlock());
    public static final RegistryObject<Item> SWORD_STATION_ITEM = fromBlock(SWORD_STATION, ChatFormatting.AQUA, false);
    public static final RegistryObject<BlockEntityType<SwordStationBE>> SWORD_STATION_BE = BLOCK_ENTITIES.register("sword_station", () -> BlockEntityType.Builder.of(SwordStationBE::new, SWORD_STATION.get()).build(null));

    // REGISTER CONTAINERS
    public static final RegistryObject<MenuType<SwordStationContainer>> SWORD_STATION_CONTAINER = CONTAINERS.register("sword_station",
            () -> IForgeMenuType.create((windowId, inv, data) -> new SwordStationContainer(windowId, data.readBlockPos(), inv, inv.player)));

    // Conveniance functions: Take a RegistryObject<Block> and make a corresponding RegistryObject<Item> from it
    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block) {
        return ModItems.ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), ModItems.ITEM_PROPERTIES));
    }
    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block, ChatFormatting customNameFormat, boolean allowEnchants) {
        return ModItems.ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), ModItems.ITEM_PROPERTIES)
        {
            @Override
            public Component getName(ItemStack pStack) {
                return super.getName(pStack).copy().withStyle(customNameFormat);
            }

            @Override
            public boolean isEnchantable(ItemStack pStack) {
                return allowEnchants;
            }

            @Override
            public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
                return allowEnchants;
            }
        });
    }
}
