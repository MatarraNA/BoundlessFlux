package com.Matarra.boundlessflux.network;

import com.Matarra.boundlessflux.BoundlessFlux;
import com.Matarra.boundlessflux.network.packets.PacketSwordStyleButtons;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

/*
    LEARNED HOW TO DO NETWORKING FROM MINING GADGETS GITHUB
    https://github.com/Direwolf20-MC/MiningGadgets/blob/master/src/main/java/com/direwolf20/mininggadgets/common/network/PacketHandler.java
 */
public class PacketHandler
{
    private static final String PROTOCOL_VERSION = Integer.toString(2);
    private static short index = 0;

    public static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(BoundlessFlux.MODID, "boundless_flux_network"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    public static void register() {
        int id = 0;

        // Server side
        HANDLER.registerMessage(id++, PacketSwordStyleButtons.class,     PacketSwordStyleButtons::encode,       PacketSwordStyleButtons::decode,       PacketSwordStyleButtons.Handler::handle);
    }

    public static void sendToServer(Object msg) {
        HANDLER.sendToServer(msg);
    }
}
