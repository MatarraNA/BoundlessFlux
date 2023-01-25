package com.Matarra.boundlessflux.item.item_energy;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.energy.EnergyStorage;

/*
    CODE REFERENCED FROM MINING GADGETS
 */
public class EnergyItem extends EnergyStorage
{
    private ItemStack stack;

    public EnergyItem(ItemStack stack, int capacity) {
        super(getMaxCapacity(stack, capacity), Integer.MAX_VALUE, Integer.MAX_VALUE);

        this.stack = stack;
        this.energy = stack.hasTag() && stack.getTag().contains("energy") ? stack.getTag().getInt("energy") : 0;
    }

    private static int getMaxCapacity(ItemStack stack, int capacity) {
        if( !stack.hasTag() || !stack.getTag().contains("max_energy") )
            return capacity;

        return stack.getTag().getInt("max_energy");
    }

    @Override
    public int getMaxEnergyStored() {
        return getMaxCapacity(stack, capacity);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        // limit the amount of received energy to a percentage of the maximum energy, so it
        // always takes a while to fully charge
        float maxTransmissionRecv = getMaxCapacity(stack, 1000000) * 0.01f;
        if( maxReceive > maxTransmissionRecv )
            maxReceive = (int)maxTransmissionRecv;

        int stored = this.getEnergyStored() + maxReceive;
        if (stored < 0) {
            return 0;
        }

        int amount = super.receiveEnergy(maxReceive, simulate);
        if( !simulate )
            stack.getOrCreateTag().putInt("energy", this.energy);

        return amount;
    }
}