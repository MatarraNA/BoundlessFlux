package com.Matarra.boundlessflux.block.sword_style;

import com.Matarra.boundlessflux.block.ModBlocks;
import com.Matarra.boundlessflux.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SwordStationBE extends BlockEntity {

    // Never create lazy optionals in getCapability. Always place them as fields in the tile entity:
    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    public SwordStationBE(BlockPos pos, BlockState state) {
        super(ModBlocks.SWORD_STATION_BE.get(), pos, state);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("Inventory")) {
            itemHandler.deserializeNBT(tag.getCompound("Inventory"));
        }
        super.load(tag);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.put("Inventory", itemHandler.serializeNBT());
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(4) {
            @Override
            protected void onContentsChanged(int slot) {
                // To make sure the TE persists when the chunk is saved later we need to
                // mark it dirty every time the item handler changes
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                // does our handler exist?
                if(!handler.isPresent() ) return false;

                // stack is empty somehow?
                if (stack.isEmpty()) return false;

                // already contains this exact item?
                for (int i = 0; i < 4; i++) {
                    if (getStackInSlot(i).getItem().equals(stack.getItem())) return false;
                }

                // only allow weapons in slot one
                if (slot == 0)
                    return stack.getTags()
                            .filter(x -> x.equals(ModTags.BOUNDLESS_SWORD_POST) || x.equals(ModTags.BOUNDLESS_BOW_POST)).count() > 0;
                else if (slot > 0 && slot < 4) {
                    // is an item in the first slot?
                    ItemStack weaponItem = getStackInSlot(0);
                    if (weaponItem.isEmpty()) return false;

                    // is it a sword?
                    if (weaponItem.getTags().filter(x -> x.equals(ModTags.BOUNDLESS_SWORD_POST)).count() > 0) {
                        // only allow SWORD UPGRADES here
                        if (stack.getTags().filter(x -> x.equals(ModTags.BOUNDLESS_UPGRADE_SWORD)).count() > 0)
                            return true;
                    }

                    // is it a bow?
                    else if (weaponItem.getTags().filter(x -> x.equals(ModTags.BOUNDLESS_BOW_POST)).count() > 0) {
                        // only allow BOW UPGRADES here
                        if (stack.getTags().filter(x -> x.equals(ModTags.BOUNDLESS_UPGRADE_BOW)).count() > 0)
                            return true;
                    }
                }

                // makes it here, cannot go in any slot
                return false;
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER && side == null) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }

    public void tickServer()
    {
        // server tick functionality if needed
    }
}