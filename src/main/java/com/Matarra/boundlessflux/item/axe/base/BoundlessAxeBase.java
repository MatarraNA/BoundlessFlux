package com.Matarra.boundlessflux.item.axe.base;

import com.Matarra.boundlessflux.config.BoundlessCommonConfig;
import com.Matarra.boundlessflux.enchant.ModEnchantments;
import com.Matarra.boundlessflux.item.item_energy.CapabilityEnergyProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

public class BoundlessAxeBase extends AxeItem
{
    public BoundlessAxeBase(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);

        // only show energy if world or player exists
        Minecraft mc = Minecraft.getInstance();
        if (world == null || mc.player == null) {
            return;
        }

        // set energy text
        stack.getCapability(ForgeCapabilities.ENERGY, null)
                .ifPresent(energy -> {

                    // Current Energy
                    MutableComponent energyText = Component.literal("Energy: " + String.format("%,d", energy.getEnergyStored()) + " / " + String.format("%,d", energy.getMaxEnergyStored()));
                    tooltip.add(energyText.withStyle(ChatFormatting.GREEN));

                    // Current SPEED MULTIPLIER
                    MutableComponent speedText = Component.literal("Speed Multiplier: " + String.format("%.2f", getSpeedMulitplier(stack)) + "x");
                    tooltip.add(speedText.withStyle(ChatFormatting.YELLOW));
                });
        tooltip.add(Component.literal("Max Energy Cap: " + String.format("%,d", BoundlessCommonConfig.MAX_ENERGY_CAP_TOOL.get()))
                .withStyle(ChatFormatting.RED));
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }

    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack pStack) {
        return super.getName(pStack).copy().withStyle(ChatFormatting.GOLD);
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return true;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapabilityEnergyProvider(stack, BoundlessCommonConfig.DEFAULT_MAX_ENERGY_TOOL.get());
    }

    /*
        CODE REFERENCED FROM MINING GADGETS
        https://github.com/Direwolf20-MC/MiningGadgets/blob/master/src/main/java/com/direwolf20/mininggadgets/common/items/MiningGadget.java
     */
    @Override
    public int getBarWidth(ItemStack stack) {
        return stack.getCapability(ForgeCapabilities.ENERGY, null)
                .map(e -> Math.min((int)(13 * (e.getEnergyStored() * 0.01d) / (e.getMaxEnergyStored() * 0.01d)), 13))
                .orElse(0);
    }

    /*
        CODE REFERENCED FROM MINING GADGETS
        https://github.com/Direwolf20-MC/MiningGadgets/blob/master/src/main/java/com/direwolf20/mininggadgets/common/items/MiningGadget.java
     */
    @Override
    public int getBarColor(ItemStack stack) {
        return stack.getCapability(ForgeCapabilities.ENERGY)
                .map(e -> Mth.hsvToRgb(Math.max(0.0F, (float) e.getEnergyStored() / (float) e.getMaxEnergyStored()) / 3.0F, 1.0F, 1.0F))
                .orElse(super.getBarColor(stack));
    }

    @Override
    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        float speed = super.getDestroySpeed(pStack, pState);
        if( speed > 1f ) return getSpeedMulitplier(pStack) * speed;
        return speed;
    }

    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {

        // get the block face before destroying it
        HitResult res = pEntityLiving.pick(10f, 0f, false);
        BlockHitResult bResult = null;
        if( res.getType() == HitResult.Type.BLOCK )
        {
            bResult = (BlockHitResult) res;
        }

        // actually handle mining the block
        boolean minedBlock = super.mineBlock(pStack, pLevel, pState, pPos, pEntityLiving);

        // ensure none of this happens client side
        if( pLevel.isClientSide() ) return minedBlock;

        // award this tool energy and consume energy as well on block break
        TakeAndAwardEnergy(pStack);

        // handle vein miner enhancement
        if(pStack.getEnchantmentLevel(ModEnchantments.FALLER.get()) > 0 && pState.is(BlockTags.LOGS) )
        {
            // is a type log, now vein mine this tree? structure? wooden thing?
            LinkedList<BlockPos> veinList = new LinkedList<>();
            veinList.add(pPos);

            int vein_mine_counter = BoundlessCommonConfig.VEIN_MINE_BLOCK_LIMIT.get();

            while(veinList.peekFirst() != null && vein_mine_counter > 0 )
            {
                // get the top block in the list
                BlockPos pop = veinList.pop();

                // get the block for this pos
                BlockState state = pLevel.getBlockState(pop);

                // if this block is air, then skip
                if( state.isAir() && pop != pPos ) continue;

                // it is not air, ensure it is same block once more, then mine
                if(pLevel.getBlockState(pop).is(pState.getBlock()))
                {
                    state.getBlock().playerDestroy(pLevel, (Player) pEntityLiving, pop, state, null, pStack );
                    pLevel.destroyBlock(pop, false);
                    TakeAndAwardEnergy(pStack);
                    vein_mine_counter--;
                }

                // gather all blocks around it that match
                for( int x = -1; x <= 1; x++ )
                {
                    for( int y = -1; y <= 1; y++ )
                    {
                        for( int z = -1; z <= 1; z++ )
                        {
                            // get the block in this position, while skipping the origin
                            if( x == 0 && y == 0 && z == 0 ) continue;

                            // get the pos
                            BlockPos newPos = new BlockPos(x + pop.getX(), y + pop.getY(), z + pop.getZ() );

                            // is it the same block?
                            BlockState newState = pLevel.getBlockState(newPos);
                            if( !newState.is(pState.getBlock()) ) continue;

                            // it is the same block, add it to the list
                            if( !veinList.contains(newPos))
                                veinList.add(newPos);
                        }
                    }
                }
            }
        }

        // handle hammer enhancement
        if(pStack.getEnchantmentLevel(ModEnchantments.HAMMER.get()) > 0  && bResult != null)
        {
            // get side of block player is facing
            Player player = (Player)pEntityLiving;
            Direction dir = bResult.getDirection();

            for( int x = -1; x <= 1; x++ )
            {
                if( (dir == Direction.WEST || dir == Direction.EAST) && x != 0 )
                    continue;

                for( int y = -1; y <= 1; y++ )
                {
                    if( (dir == Direction.UP || dir == Direction.DOWN) && y != 0 )
                        continue;

                    for( int z = -1; z <= 1; z++ )
                    {
                        if( (dir == Direction.NORTH || dir == Direction.SOUTH) && z != 0 )
                            continue;

                        // skip same block
                        if( y == 0 && x == 0 && z == 0 ) continue;

                        BlockPos pos = new BlockPos(pPos.getX() + x, y + pPos.getY(), z + pPos.getZ());
                        BlockState state = pLevel.getBlockState(pos);

                        // is it air?
                        if( state.isAir() ) continue;

                        // can it be mined?
                        if( state.getDestroySpeed(pLevel, pos) <= 0f ) continue;

                        // do the magic
                        if( !state.canHarvestBlock(pLevel, pos, player) ) continue;

                        state.getBlock().playerDestroy(pLevel, player, pos, state, null, pStack );
                        pLevel.destroyBlock(pos, false);

                        // award energy
                        TakeAndAwardEnergy(pStack);
                    }
                }
            }
        }

        return minedBlock;
    }

    // Calculate how much damage the tool speed multiplier should be based on its current energy
    protected float getSpeedMulitplier(ItemStack stack) {
        return stack.getCapability(ForgeCapabilities.ENERGY, null)
                .map(e ->(float) (e.getEnergyStored() * BoundlessCommonConfig.PERCENT_ENERGY_CONSUMED_BLOCKBREAK_TOOL.get()
                        * BoundlessCommonConfig.PERCENT_ENERGY_CONVERTED_TO_SPEED_TOOL.get()))
                .orElse(0f);
    }

    /*
        Consumes energy on block break, and awards energy to its max energy buffer
        Returns amount of energy consumed
     */
    protected float TakeAndAwardEnergy(ItemStack pStack)
    {
        // do magic here to deduct flux cost of mining the block
        int energyConsumed = pStack.getCapability(ForgeCapabilities.ENERGY, null)
                .map(e -> e.receiveEnergy((int)(e.getEnergyStored() * BoundlessCommonConfig.PERCENT_ENERGY_CONSUMED_BLOCKBREAK_TOOL.get()) * -1, false))
                .orElse(0);
        energyConsumed *= -1; // convert it back to a positive number

        // now add this energy BACK to the MAX energy for the tool IF ENERGY GAIN IS ENABLED
        boolean energyGainEnabled;
        if( !pStack.hasTag() ) energyGainEnabled = true;
        else if(!pStack.getTag().contains("energy_gain")) energyGainEnabled = true;
        else energyGainEnabled = pStack.getTag().getBoolean("energy_gain");

        // award max energy
        LazyOptional<IEnergyStorage> energyCap = pStack.getCapability(ForgeCapabilities.ENERGY, null);
        if( energyCap.isPresent() && energyGainEnabled )
        {
            // does this weapon have 50% more energy gain enchant?
            if( pStack.getEnchantmentLevel(ModEnchantments.ENERGY_GAIN.get()) > 0 )
                energyConsumed = (int)(energyConsumed * 1.5D);

            IEnergyStorage cap = energyCap.resolve().get();
            int current_max = cap.getMaxEnergyStored();
            if( (long)current_max + (long)energyConsumed > BoundlessCommonConfig.MAX_ENERGY_CAP_TOOL.get() )
                pStack.getOrCreateTag().putInt("max_energy", BoundlessCommonConfig.MAX_ENERGY_CAP_TOOL.get());
            else
                pStack.getOrCreateTag().putInt("max_energy", current_max + energyConsumed);

        }

        return energyConsumed;
    }
}
