package com.Matarra.boundlessflux.entity.projectile;

import com.Matarra.boundlessflux.BoundlessFlux;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShurikenArrowRenderer extends ArrowRenderer<ShurikenArrow> {
    public ShurikenArrowRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(ShurikenArrow pEntity) {
        return new ResourceLocation(BoundlessFlux.MODID, "textures/entity/projectiles/shuriken_arrow.png");
    }
}
