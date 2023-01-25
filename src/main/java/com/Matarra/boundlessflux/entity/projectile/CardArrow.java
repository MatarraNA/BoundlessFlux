package com.Matarra.boundlessflux.entity.projectile;

import com.Matarra.boundlessflux.entity.BoundlessLightning;
import com.Matarra.boundlessflux.entity.ModEntities;
import com.Matarra.boundlessflux.item.ModItems;
import com.Matarra.boundlessflux.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class CardArrow extends AbstractArrow implements IBoundlessArrow
{
    protected Item referenceItem;
    protected boolean isThorArrow;
    protected Player player;
    protected double lightningDamage;

    public CardArrow(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.referenceItem = ModItems.CARD_ARROW.get();
    }

    public CardArrow(LivingEntity pShooter, Level pLevel, Item referenceItem) {
        super(ModEntities.CARD_ARROW.get(), pShooter, pLevel);
        this.referenceItem = referenceItem;
        System.out.println("Test");
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return ModSounds.CARD_HIT.get();
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return new ItemStack(Items.ARROW);
    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {

        // SUMMON LIGHTNING THOR
        if( !this.level.isClientSide )
        {
            if( isThorArrow )
            {
                BoundlessLightning lightning = ModEntities.BOUNDLESS_LIGHTNING_GOLD.get().spawn((ServerLevel) this.getLevel(),
                        null, null, player, new BlockPos(pResult.getLocation()), MobSpawnType.TRIGGERED, false, false);
                if( lightning != null )
                {
                    lightning.sourceEntity = player;
                    lightning.setDamage(((float) this.lightningDamage));
                }
            }
        }

        super.onHit(pResult);
    }

    @Override
    public void setThunderEnchant(boolean isThor) {
        this.isThorArrow = isThor;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void setThunderDamage(double damage) {
        this.lightningDamage = damage;
    }
}
