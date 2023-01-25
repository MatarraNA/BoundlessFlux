package com.Matarra.boundlessflux.entity;

import com.Matarra.boundlessflux.BoundlessFlux;
import com.Matarra.boundlessflux.entity.projectile.*;
import com.Matarra.boundlessflux.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BoundlessFlux.MODID);

    // ARROWS
    public static final RegistryObject<EntityType<FirstArrow>> FIRST_ARROW = ENTITIES.register("first_arrow",
            () -> EntityType.Builder.<FirstArrow>of(FirstArrow::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build(new ResourceLocation(BoundlessFlux.MODID,
                            "first_arrow").toString()));
    public static final RegistryObject<EntityType<ShurikenArrow>> SHURIKEN_ARROW = ENTITIES.register("shuriken_arrow",
            () -> EntityType.Builder.<ShurikenArrow>of(ShurikenArrow::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build(new ResourceLocation(BoundlessFlux.MODID,
                            "shuriken_arrow").toString()));
    public static final RegistryObject<EntityType<KunaiArrow>> KUNAI_ARROW = ENTITIES.register("kunai_arrow",
            () -> EntityType.Builder.<KunaiArrow>of(KunaiArrow::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build(new ResourceLocation(BoundlessFlux.MODID,
                            "kunai_arrow").toString()));
    public static final RegistryObject<EntityType<CardArrow>> CARD_ARROW = ENTITIES.register("card_arrow",
            () -> EntityType.Builder.<CardArrow>of(CardArrow::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build(new ResourceLocation(BoundlessFlux.MODID,
                            "card_arrow").toString()));
    public static final RegistryObject<EntityType<RaygunArrow>> RAYGUN_ARROW = ENTITIES.register("raygun_arrow",
            () -> EntityType.Builder.<RaygunArrow>of(RaygunArrow::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build(new ResourceLocation(BoundlessFlux.MODID,
                            "raygun_arrow").toString()));

    // LIGHTNING
    public static final RegistryObject<EntityType<BoundlessLightning>> BOUNDLESS_LIGHTNING_GOLD = ENTITIES.register("boundless_lightning_gold",
            () -> EntityType.Builder.of(BoundlessLightning::new, MobCategory.MISC)
                    .fireImmune()
                    .build("boundless_lightning_gold"));
    public static final RegistryObject<EntityType<BoundlessLightning>> BOUNDLESS_LIGHTNING_RED = ENTITIES.register("boundless_lightning_red",
            () -> EntityType.Builder.of(BoundlessLightning::new, MobCategory.MISC)
                    .fireImmune()
                    .build("boundless_lightning_red"));
    public static final RegistryObject<EntityType<BoundlessLightning>> BOUNDLESS_LIGHTNING_GREEN = ENTITIES.register("boundless_lightning_green",
            () -> EntityType.Builder.of(BoundlessLightning::new, MobCategory.MISC)
                    .fireImmune()
                    .build("boundless_lightning_green"));
    public static final RegistryObject<EntityType<BoundlessLightning>> BOUNDLESS_LIGHTNING_PURPLE = ENTITIES.register("boundless_lightning_purple",
            () -> EntityType.Builder.of(BoundlessLightning::new, MobCategory.MISC)
                    .fireImmune()
                    .build("boundless_lightning_purple"));
    public static final RegistryObject<EntityType<BoundlessLightning>> BOUNDLESS_LIGHTNING_AQUA = ENTITIES.register("boundless_lightning_aqua",
            () -> EntityType.Builder.of(BoundlessLightning::new, MobCategory.MISC)
                    .fireImmune()
                    .build("boundless_lightning_aqua"));
}
