package com.Matarra.boundlessflux.enchant;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class StormCallEnchant extends Enchantment {
    public StormCallEnchant(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean allowedInCreativeTab(Item book, CreativeModeTab tab) {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
