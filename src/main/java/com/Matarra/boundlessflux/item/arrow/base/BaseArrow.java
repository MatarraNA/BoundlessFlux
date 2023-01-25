package com.Matarra.boundlessflux.item.arrow.base;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BaseArrow extends Arrow
{
    public BaseArrow(EntityType<? extends Arrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
}
