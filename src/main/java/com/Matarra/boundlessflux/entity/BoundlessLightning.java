package com.Matarra.boundlessflux.entity;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.LightningBoltRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class BoundlessLightning extends LightningBolt
{
    public Entity sourceEntity;
    private final List<Entity> hitEntities;

    // ASSIGN SOURCE ENTITY AFTER CREATING BOLT
    public BoundlessLightning(EntityType<? extends LightningBolt> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setVisualOnly(true);
        hitEntities = new ArrayList<>(10);
    }

    @Override
    public void tick()
    {
        super.tick();

        if( this.tickCount == 1 )
        {
            List<Entity> list1 = this.level.getEntities(this, new AABB(this.getX() - 2.0D, this.getY() - 2.0D, this.getZ() - 2.0D, this.getX() + 2.0D, this.getY() + 4.0D + 2.0D, this.getZ() + 2.0D), Entity::isAlive);
            for (Entity entity : list1) {
                if (!hitEntities.contains(entity))
                {
                    if( sourceEntity instanceof Player )
                    {
                        if( !(entity instanceof ItemEntity) ) // dont destroy items on the ground
                            entity.hurt(DamageSource.playerAttack((Player) sourceEntity), this.getDamage());
                    }
                    else
                    {
                        if( !(entity instanceof ItemEntity) ) // dont destroy items on the ground
                            entity.hurt(DamageSource.LIGHTNING_BOLT, this.getDamage());
                    }
                }
            }
            this.hitEntities.addAll(list1);
        }

    }
}
