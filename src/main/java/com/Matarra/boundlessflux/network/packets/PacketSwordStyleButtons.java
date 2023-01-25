package com.Matarra.boundlessflux.network.packets;

import com.Matarra.boundlessflux.block.sword_style.SwordStationContainer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.awt.*;
import java.util.function.Supplier;

public class PacketSwordStyleButtons
{
    public enum ButtonEnum {LEFT, RIGHT, CHECKBOX_ONKILL_CHECKED, CHECKBOX_ONKILL_UNCHECKED}
    private ButtonEnum buttonSelected = ButtonEnum.LEFT;

    public PacketSwordStyleButtons(ButtonEnum btnSelected)
    {
        buttonSelected = btnSelected;
    }

    public static void encode(PacketSwordStyleButtons msg, FriendlyByteBuf buffer) {
        buffer.writeEnum(msg.buttonSelected);
    }

    public static PacketSwordStyleButtons decode(FriendlyByteBuf buffer) {
        return new PacketSwordStyleButtons(buffer.readEnum(ButtonEnum.class));
    }

    public static class Handler {
        public static void handle(PacketSwordStyleButtons msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer sender = ctx.get().getSender();
                if (sender == null)
                    return;

                AbstractContainerMenu container = sender.containerMenu;
                if (container == null) return;

                SwordStationContainer sContainer = ((SwordStationContainer) container);
                if( sContainer == null ) return;

                // finally handle the callback
                sContainer.containerButtonPressedServer(msg.buttonSelected);
            });

            ctx.get().setPacketHandled(true);
        }
    }
}
