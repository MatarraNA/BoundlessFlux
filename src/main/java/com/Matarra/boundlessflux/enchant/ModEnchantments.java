package com.Matarra.boundlessflux.enchant;

import com.Matarra.boundlessflux.BoundlessFlux;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments
{
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, BoundlessFlux.MODID);
    public static RegistryObject<Enchantment> STORM_CALL = ENCHANTMENTS.register("storm_call",
            () -> new StormCallEnchant(Enchantment.Rarity.RARE, EnchantmentCategory.BOW, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> HAMMER = ENCHANTMENTS.register("hammer_3x3",
            () -> new StormCallEnchant(Enchantment.Rarity.RARE, EnchantmentCategory.BREAKABLE, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> ENERGY_GAIN = ENCHANTMENTS.register("energy_gain",
            () -> new EnergyGainEnchant(Enchantment.Rarity.RARE, EnchantmentCategory.BREAKABLE, EquipmentSlot.MAINHAND));
    public static void register(IEventBus bus)
    {
        ENCHANTMENTS.register(bus);
    }
}
