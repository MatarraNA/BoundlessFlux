package com.Matarra.boundlessflux.entity.projectile;

import com.Matarra.boundlessflux.BoundlessFlux;
import com.Matarra.boundlessflux.item.ModItems;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FirstArrowRenderer extends ArrowRenderer<FirstArrow> {
    public FirstArrowRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(FirstArrow pEntity) {
        return new ResourceLocation(BoundlessFlux.MODID, "textures/entity/projectiles/first_arrow.png");
    }
}
