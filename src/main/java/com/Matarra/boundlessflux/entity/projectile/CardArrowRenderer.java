package com.Matarra.boundlessflux.entity.projectile;

import com.Matarra.boundlessflux.BoundlessFlux;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CardArrowRenderer extends ArrowRenderer<CardArrow> {
    public CardArrowRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(CardArrow pEntity) {
        return new ResourceLocation(BoundlessFlux.MODID, "textures/entity/projectiles/card_arrow.png");
    }
}
