package com.Matarra.boundlessflux.block.sword_style;

import com.Matarra.boundlessflux.block.ModBlocks;
import com.Matarra.boundlessflux.enchant.ModEnchantments;
import com.Matarra.boundlessflux.item.ModItems;
import com.Matarra.boundlessflux.network.packets.PacketSwordStyleButtons;
import com.Matarra.boundlessflux.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class SwordStationContainer extends AbstractContainerMenu
{
    private final BlockEntity blockEntity;
    private final Player playerEntity;
    private final IItemHandler playerInventory;

    public SwordStationContainer(int windowId, BlockPos pos, Inventory playerInventory, Player player) {
        super(ModBlocks.SWORD_STATION_CONTAINER.get(), windowId);
        blockEntity = player.getCommandSenderWorld().getBlockEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);

        if (blockEntity != null) {
            blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0, 80, 77));

                // add the upgrade slots
                addSlot(new SlotItemHandler(h, 1, 185, 8));
                addSlot(new SlotItemHandler(h, 2, 203, 8));
                addSlot(new SlotItemHandler(h, 3, 221, 8));
            });
        }

        layoutPlayerInventorySlots(8, 99);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex)
    {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = slots.get(pIndex);
        int numBonusSlots = 4; // this block has 4 slots

        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            stack = slotStack.copy();

            if (pIndex < numBonusSlots) {
                if (!moveItemStackTo(slotStack, 27 + numBonusSlots, 36 + numBonusSlots, false))
                    if (!moveItemStackTo(slotStack, numBonusSlots, 27 + numBonusSlots, false))
                        return ItemStack.EMPTY;
            }
            else
            {
                if (!moveItemStackTo(slotStack, 0, numBonusSlots, false))
                    return ItemStack.EMPTY;
            }

            if (slotStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            }
            else {
                slot.setChanged();
            }

            if (slotStack.getCount() == stack.getCount())
                return ItemStack.EMPTY;

            slot.onTake(pPlayer, slotStack);
        }

        return stack;
    }

    @Override
    public void clicked(int pMouseX, int pMouseY, ClickType pClickType, Player pPlayer) {
        if (pClickType != ClickType.PICKUP && pClickType != ClickType.QUICK_MOVE) return;

        ItemStack itemX = ItemStack.EMPTY;
        ItemStack itemY = ItemStack.EMPTY;

        try
        {
            itemX = slots.get(pMouseX).getItem().copy();
            itemY = slots.get(pMouseY).getItem().copy();
        }
        catch (Exception ex) {}

        super.clicked(pMouseX, pMouseY, pClickType, pPlayer);

        try
        {
            switch (pClickType)
            {
                case PICKUP ->
                {
                    // if picking up sword from slot 0, clear everything
                    if( pMouseX == 0 && !slots.get(pMouseX).hasItem() )
                    {
                        // clear the slots since a sword was picked up from slot 0
                        for( int i = 1; i < 4; i++ )
                        {
                            slots.get(i).set(ItemStack.EMPTY);
                            slots.get(i).setChanged();
                        }
                    }

                    // if placing a sword IN slot 0, deserialize it
                    if(pMouseX == 0 && slots.get(pMouseX).hasItem() && slots.get(pMouseX).getItem().getTags().filter(x->
                            x.equals(ModTags.BOUNDLESS_SWORD_POST) ||
                            x.equals(ModTags.BOUNDLESS_PICKAXE_POST) ||
                            x.equals(ModTags.BOUNDLESS_AXE_POST) ||
                            x.equals(ModTags.BOUNDLESS_BOW_POST)).count() > 0)
                    {
                        deserializeWeaponUpgrades();
                    }
                    else if( pMouseX > 0 && pMouseX < 4  )
                    {
                        // the upgrade slots were manipulated, so serialize our weapon
                        serializeWeaponUpgrades();
                    }
                }

                case QUICK_MOVE ->
                {
                    // if quick moving a sword from slot 0, clear everything
                    if( pMouseX == 0 && !slots.get(pMouseX).hasItem() )
                    {
                        // clear the slots since a sword was quick moved from slot 0
                        for( int i = 1; i < 4; i++ )
                        {
                            slots.get(i).set(ItemStack.EMPTY);
                            slots.get(i).setChanged();
                        }
                    }
                    // if quick moving a sword INTO slot 1
                    else if(!itemX.isEmpty() && slots.get(0).hasItem() && slots.get(0).getItem().getItem().equals(itemX.getItem()))
                    {
                        deserializeWeaponUpgrades();
                    }
                    else if( pMouseX > 0 && pMouseX < 4 && !slots.get(pMouseX).hasItem() )
                    {
                        // an UPGRADE was quick moved OUT of the CONTAINER
                        serializeWeaponUpgrades();
                    }
                    else if(!itemX.isEmpty() && !slots.get(pMouseX).hasItem() )
                    {
                        // an UPGRADE was quick moved INTO the CONTAINER
                        serializeWeaponUpgrades();
                    }
                }
            }
        }
        catch (Exception ex) {}
    }

    // save inventory AND applies enchants to the blade / bow
    private void serializeWeaponUpgrades()
    {
        // after completing the movement, save NBT data to the item
        ItemStack weaponItem = slots.get(0).getItem();
        if( weaponItem.isEmpty() ) return;

        // serialize the items in our handler and insert them into the weapon for safe keeping
        ItemStackHandler serializingHandler = new ItemStackHandler(4);
        for( int i = 0; i < 4; i++ )
        {
            if( i == 0 ) continue;
            serializingHandler.setStackInSlot(i, slots.get(i).getItem());
        }
        weaponItem.getOrCreateTag().put("Inventory", serializingHandler.serializeNBT());

        // iterate through and fix all enchantments now on the weapon
        weaponItem.getEnchantmentTags().clear();
        for( int i = 0; i < 4; i++ )
        {
            if( i == 0 ) continue;

            // Enchant the weapon with... enchantments!
            Item item = slots.get(i).getItem().getItem();
            if( item.equals(ModItems.UPGRADE_LOOTING.get()))
                weaponItem.enchant(Enchantments.MOB_LOOTING, 6);
            else if( item.equals(ModItems.UPGRADE_SWEEPING.get()))
                weaponItem.enchant(Enchantments.SWEEPING_EDGE, 6);
            else if( item.equals(ModItems.UPGRADE_FIRE.get()))
                weaponItem.enchant(Enchantments.FIRE_ASPECT, 4);

            // BOW ENCHANTS
            else if( item.equals(ModItems.UPGRADE_STORM_CALL.get()))
                weaponItem.enchant(ModEnchantments.STORM_CALL.get(), 1);
            else if( item.equals(ModItems.UPGRADE_FLAME.get()))
                weaponItem.enchant(Enchantments.FLAMING_ARROWS, 1);
            else if( item.equals(ModItems.UPGRADE_INFINITY.get()))
                weaponItem.enchant(Enchantments.INFINITY_ARROWS, 1);

            // AXE ENCHANTS
            else if( item.equals(ModItems.UPGRADE_FALLER.get()))
                weaponItem.enchant(ModEnchantments.FALLER.get(), 1);

            // PICKAXE ENCHANTS
            else if( item.equals(ModItems.UPGRADE_VEIN_MINER_ORE.get()))
                weaponItem.enchant(ModEnchantments.ORE_VEIN_MINER.get(), 1);

            // GENERIC ENCHANTS
            else if( item.equals(ModItems.UPGRADE_ENERGY_GAIN.get()))
                weaponItem.enchant(ModEnchantments.ENERGY_GAIN.get(), 1);

            // BLOCKBREAK ENCHANTS
            else if( item.equals(ModItems.UPGRADE_SILK.get()))
                weaponItem.enchant(Enchantments.SILK_TOUCH, 1);
            else if( item.equals(ModItems.UPGRADE_FORTUNE.get()))
                weaponItem.enchant(Enchantments.BLOCK_FORTUNE, 6);
            else if( item.equals(ModItems.UPGRADE_HAMMER.get()))
                weaponItem.enchant(ModEnchantments.HAMMER.get(), 1);
        }
    }

    // populate the UPGRADE slots with the stored inventory on the weapon
    private void deserializeWeaponUpgrades()
    {
        // first clear the slots to ensure they never dupe
        for( int i = 0; i < 4; i++ )
        {
            if( i == 0 ) continue;

            // set the slots
            slots.get(i).set(ItemStack.EMPTY);
        }

        // deserialize the NBT tags on the sword and create the items for them
        ItemStack weaponItem = slots.get(0).getItem();
        if(weaponItem.isEmpty()) return;
        if( !weaponItem.hasTag() ) return;
        if( !weaponItem.getTag().contains("Inventory") ) return;

        // get the weapn handler
        ItemStackHandler weaponHandler = new ItemStackHandler(4);

        // deserialize sword data
        weaponHandler.deserializeNBT(weaponItem.getTag().getCompound("Inventory"));

        // now populate the slots
        for( int i = 0; i < 4; i++ )
        {
            if( i == 0 ) continue;

            // set the slots
            slots.get(i).set(weaponHandler.getStackInSlot(i));
        }
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerEntity, ModBlocks.SWORD_STATION.get());
    }

    // Callback for when a button is pressed, this is run on the SERVER
    public void containerButtonPressedServer(PacketSwordStyleButtons.ButtonEnum btnType)
    {
        // check if the item exists still in the slot
        if( slots.get(0) == null ) return;
        if( !slots.get(0).hasItem() ) return;
        ItemStack item = slots.get(0).getItem();

        // is this for the checkbox?
        if( btnType == PacketSwordStyleButtons.ButtonEnum.CHECKBOX_ONKILL_CHECKED ||
                btnType == PacketSwordStyleButtons.ButtonEnum.CHECKBOX_ONKILL_UNCHECKED )
        {
            // check if the item exists still in the slot
            if( item.getTags().filter(x->
                    x.equals(ModTags.BOUNDLESS_SWORD_POST) ||
                    x.equals(ModTags.BOUNDLESS_PICKAXE_POST) ||
                    x.equals(ModTags.BOUNDLESS_SHOVEL_POST) ||
                    x.equals(ModTags.BOUNDLESS_AXE_POST) ||
                    x.equals(ModTags.BOUNDLESS_BOW_POST)).count() < 1 ) return;

            // set the bool tag
            if( btnType == PacketSwordStyleButtons.ButtonEnum.CHECKBOX_ONKILL_CHECKED )
                item.getOrCreateTag().putBoolean("energy_gain", true);
            else
                item.getOrCreateTag().putBoolean("energy_gain", false);
        }

        // get ALL swords and ALL bows that this machine can swap
        List<Item> list = null;
        if( item.getTags().filter(x->x.equals(ModTags.BOUNDLESS_SWORD_POST)).count() > 0 )
            list = ForgeRegistries.ITEMS.tags().getTag(ModTags.BOUNDLESS_SWORD_POST).stream().toList();
        if( item.getTags().filter(x->x.equals(ModTags.BOUNDLESS_BOW_POST)).count() > 0 )
            list = ForgeRegistries.ITEMS.tags().getTag(ModTags.BOUNDLESS_BOW_POST).stream().toList();
        if( item.getTags().filter(x->x.equals(ModTags.BOUNDLESS_PICKAXE_POST)).count() > 0 )
            list = ForgeRegistries.ITEMS.tags().getTag(ModTags.BOUNDLESS_PICKAXE_POST).stream().toList();
        if( item.getTags().filter(x->x.equals(ModTags.BOUNDLESS_SHOVEL_POST)).count() > 0 )
            list = ForgeRegistries.ITEMS.tags().getTag(ModTags.BOUNDLESS_SHOVEL_POST).stream().toList();
        if( item.getTags().filter(x->x.equals(ModTags.BOUNDLESS_AXE_POST)).count() > 0 )
            list = ForgeRegistries.ITEMS.tags().getTag(ModTags.BOUNDLESS_AXE_POST).stream().toList();

        if( list == null ) return;

        // get current index
        int indexOfCur = list.indexOf(item.getItem());
        if( indexOfCur == -1 ) return;

        // which way should it iterate
        if( btnType == PacketSwordStyleButtons.ButtonEnum.LEFT )
        {
            indexOfCur--;
            if( indexOfCur < 0 )
            {
                // set it to max
                indexOfCur = (int)list.stream().count() - 1;
            }
        }
        else if( btnType == PacketSwordStyleButtons.ButtonEnum.RIGHT )
        {
            indexOfCur++;
            if( indexOfCur >= list.stream().count() )
            {
                indexOfCur = 0;
            }
        }

        // now swap the item to the new one
        CompoundTag oldNbt = item.serializeNBT();

        // update NBT tags
        ItemStack newItem = new ItemStack(list.get(indexOfCur));
        newItem.deserializeNBT(oldNbt);

        // replace item
        slots.get(0).set(newItem);
        slots.get(0).setChanged();
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }
}