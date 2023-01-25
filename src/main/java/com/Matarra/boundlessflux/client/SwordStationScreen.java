package com.Matarra.boundlessflux.client;

import com.Matarra.boundlessflux.BoundlessFlux;
import com.Matarra.boundlessflux.block.sword_style.SwordStationContainer;
import com.Matarra.boundlessflux.item.ModItems;
import com.Matarra.boundlessflux.network.PacketHandler;
import com.Matarra.boundlessflux.network.packets.PacketSwordStyleButtons;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.awt.*;
import java.lang.reflect.Field;

public class SwordStationScreen extends AbstractContainerScreen<SwordStationContainer> {
    private final ResourceLocation GUI = new ResourceLocation(BoundlessFlux.MODID, "textures/gui/sword_style_gui.png");

    // The current yaw rotation for the spinning sword
    private float current_yaw = -180f;
    // Spin speed for the spinning sword
    private float anim_spin_speed = 3f;

    // GUI BUTTONS
    private Button buttonPrevious;
    private Button buttonNext;
    private Checkbox gainOnKillCheckbox;

    public SwordStationScreen(SwordStationContainer container, Inventory inv, Component name) {
        super(container, inv, name);

        // image width for this bg image is different than default
        imageWidth = 245;
        imageHeight = 180;
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);

        // render the current selected sword
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight ) / 2;

        // should an item be rendered to the screen?
        if( menu.slots != null && menu.slots.get(0) != null && menu.slots.get(0).hasItem() )
        {
            // relX is left most pixel on the GUI texture, relY is top most pixel
            // (65,10) is the top left of where the sword should spin
            // (22,22) accounts for the scaling. Scaling equates to about 45, so divide it by 2.
            drawItem(menu.slots.get(0).getItem().getItem(), relX + 67 + 21, relY + 24 + 21, 60f, current_yaw,0f, true);

            // enable our buttons!
            if( buttonNext != null && buttonPrevious != null && gainOnKillCheckbox != null )
            {
                buttonNext.active = true;
                buttonPrevious.active = true;
                gainOnKillCheckbox.active = true;
            }

            // add in our labels!
            drawCenteredString(matrixStack, Minecraft.getInstance().font, menu.slots.get(0).getItem().getHoverName(), relX + this.imageWidth/2 - 69/2, relY + 6, 0xFFAA00);

            // set the checkbox using reflection to match whether this item accepts energy
            try
            {
                Class cls = Checkbox.class;
                Field field = cls.getDeclaredField("selected");
                field.setAccessible(true);

                // does this item accept energy?
                boolean energyGainEnabled = false;
                ItemStack weaponStack = menu.getSlot(0).getItem();
                if( weaponStack.isEmpty() ) energyGainEnabled = false;
                else if( !weaponStack.hasTag() ) energyGainEnabled = true; // default the energy gain to true
                else if(!weaponStack.getTag().contains("energy_gain")) energyGainEnabled = true;
                else energyGainEnabled = weaponStack.getTag().getBoolean("energy_gain");

                field.set(gainOnKillCheckbox, energyGainEnabled);
            }
            catch (Exception ex) {}
        }
        else
        {
            // add in our labels!
            drawCenteredString(matrixStack, Minecraft.getInstance().font, "Insert a Boundless Weapon", relX + this.imageWidth/2 - 69/2, relY + 6, 0xFFAA00);

            // it has no items so disable our buttons!
            if( buttonNext != null && buttonPrevious != null && gainOnKillCheckbox != null )
            {
                buttonNext.active = false;
                buttonPrevious.active = false;
                gainOnKillCheckbox.active = false;

                // set the checkbox using reflection to be false
                try
                {
                    Class cls = Checkbox.class;
                    Field field = cls.getDeclaredField("selected");
                    field.setAccessible(true);
                    field.set(gainOnKillCheckbox, false);
                }
                catch (Exception ex) {}
            }
        }
    }

    /**
     * CREDIT TO SuperMartijn642 FOR DRAW ITEM
     * https://github.com/SuperMartijn642/Rechiseled/blob/bce4fc5ab742ca9de3caa290335db0f5c0786355/src/main/java/com/supermartijn642/rechiseled/screen/ScreenItemRender.java#L23
     */
    public static void drawItem(Item item, double x, double y, double scale, float yaw, float pitch, boolean doShading){
        scale /= Math.sqrt(2 + 1d / (16 * 16));

        RenderSystem.getModelViewStack().pushPose();
        RenderSystem.getModelViewStack().scale(1, -1, 1);
        RenderSystem.applyModelViewMatrix();

        PoseStack poseStack = new PoseStack();
        poseStack.translate(x, -y, 350);
        poseStack.scale((float)scale, (float)scale, (float)scale);
        poseStack.mulPose(new Quaternion(pitch, yaw, 0, true));

        if(doShading)
            Lighting.setupFor3DItems();

        MultiBufferSource.BufferSource renderTypeBuffer = Minecraft.getInstance().renderBuffers().bufferSource();
        BakedModel model =  Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(item);
        if(model != null)
            Minecraft.getInstance().getItemRenderer().render(item.getDefaultInstance(), ItemTransforms.TransformType.GUI, false, poseStack, renderTypeBuffer, 15728880, OverlayTexture.NO_OVERLAY, model);
        renderTypeBuffer.endBatch();

        RenderSystem.enableDepthTest();
        if(doShading)
            Lighting.setupForFlatItems();

        RenderSystem.getModelViewStack().popPose();
        RenderSystem.applyModelViewMatrix();
    }

    @Override
    protected void containerTick() {
        super.containerTick();

        // update our rotation every tick
        current_yaw += anim_spin_speed;
        if( current_yaw > 180f ) current_yaw = -180f;
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
        //drawString(matrixStack, Minecraft.getInstance().font, "Random Test String for fun", 10, 10, 0xffffff);
    }

    @Override
    protected void init() {
        super.init();

        // create buttons here?
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight ) / 2;
        buttonPrevious = addRenderableWidget(new Button(relX + 7, relY + 75, 68, 20, Component.literal("Previous"), (x) -> onSwordStationButtonPressed(PacketSwordStyleButtons.ButtonEnum.LEFT)));
        buttonNext = addRenderableWidget(new Button(relX + 101, relY + 75, 68, 20, Component.literal("Next"), (x) -> onSwordStationButtonPressed(PacketSwordStyleButtons.ButtonEnum.RIGHT)));
        buttonPrevious.active = false;
        buttonNext.active = false;

        // create our checkbox
        gainOnKillCheckbox = addRenderableWidget(new Checkbox(relX + 181, relY + 37, 56, 20,
                Component.literal("+On Kill"), false)
        {
            @Override
            public void onPress() {
                super.onPress();

                // send a packet saying that we pressed the checkbox
                if( this.selected() )
                    onSwordStationButtonPressed(PacketSwordStyleButtons.ButtonEnum.CHECKBOX_ONKILL_CHECKED);
                else
                    onSwordStationButtonPressed(PacketSwordStyleButtons.ButtonEnum.CHECKBOX_ONKILL_UNCHECKED);
            }
        });

        gainOnKillCheckbox.active = false;
    }

    // callback on buttons being pressed
    private void onSwordStationButtonPressed(PacketSwordStyleButtons.ButtonEnum btnType)
    {
        // send a packet to the server that we want to cycle backwards!
        PacketHandler.sendToServer(new PacketSwordStyleButtons(btnType));
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;

        this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
    }
}
