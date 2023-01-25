package com.Matarra.boundlessflux.sound;

import com.Matarra.boundlessflux.BoundlessFlux;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds
{
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BoundlessFlux.MODID);

    // SOUNDS, REMEMBER TO ALSO REGISTER IT IN SOUNDS.JSON
    public static final RegistryObject<SoundEvent> CARD_THROW = registerSoundEvent("card_throw");
    public static final RegistryObject<SoundEvent> CARD_HIT = registerSoundEvent("card_hit");
    public static final RegistryObject<SoundEvent> SHURIKEN_THROW = registerSoundEvent("shuriken_throw");
    public static final RegistryObject<SoundEvent> SHURIKEN_HIT = registerSoundEvent("shuriken_hit");
    public static final RegistryObject<SoundEvent> RAYGUN_FIRE = registerSoundEvent("raygun_fire");

    public static RegistryObject<SoundEvent> registerSoundEvent(String name)
    {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(BoundlessFlux.MODID, name)));
    }

    public static void register(IEventBus bus)
    {
        SOUND_EVENTS.register(bus);
    }
}
