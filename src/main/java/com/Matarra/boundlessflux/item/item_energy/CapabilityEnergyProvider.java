package com.Matarra.boundlessflux.item.item_energy;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 CODE REFERENCED FROM MINING GADGETS
 */
public class CapabilityEnergyProvider implements ICapabilityProvider
{
    private ItemStack stack;
    private int energyCapacity;
    private LazyOptional<IEnergyStorage> capability = LazyOptional.of(() -> new EnergyItem(stack, energyCapacity));

    public CapabilityEnergyProvider(ItemStack stack, int energyCapacity) {
        this.stack = stack;
        this.energyCapacity = energyCapacity;
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == ForgeCapabilities.ENERGY ? capability.cast() : LazyOptional.empty();
    }
}