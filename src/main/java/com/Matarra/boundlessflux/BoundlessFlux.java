package com.Matarra.boundlessflux;

import com.Matarra.boundlessflux.setup.ClientSetup;
import com.Matarra.boundlessflux.setup.ModSetup;
import com.Matarra.boundlessflux.setup.Registration;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BoundlessFlux.MODID)
public class BoundlessFlux
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "boundlessflux";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public BoundlessFlux()
    {
        // registration
        Registration.init();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(ModSetup::init);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modEventBus.addListener(ClientSetup::init));
    }
}
