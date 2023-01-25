package com.Matarra.boundlessflux.setup;

import com.Matarra.boundlessflux.item.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModSetup
{
    public static final String TAB_NAME = "boundless";

    public static void init(FMLCommonSetupEvent event)
    {
        
    }

    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(TAB_NAME) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.FIRST_SWORD.get());
        }
    };
}
