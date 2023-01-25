package com.Matarra.boundlessflux.entity.projectile;

import net.minecraft.world.entity.player.Player;

public interface IBoundlessArrow
{
    public void setThunderEnchant(boolean isThor);
    public void setPlayer(Player player);
    public void setThunderDamage(double damage);
}
