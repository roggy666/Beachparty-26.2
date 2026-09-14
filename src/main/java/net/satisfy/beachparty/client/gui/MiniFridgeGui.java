package net.satisfy.beachparty.client.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.satisfy.beachparty.client.gui.handler.MiniFridgeGuiHandler;
import net.satisfy.beachparty.core.util.BeachpartyIdentifier;

public class MiniFridgeGui extends AbstractContainerScreen<MiniFridgeGuiHandler> {
    public static final Identifier BG = BeachpartyIdentifier.identifier("textures/gui/freezer_gui.png");
    public static final int ARROW_Y = 35;
    public static final int ARROW_X = 79;

    public MiniFridgeGui(MiniFridgeGuiHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BG, leftPos, topPos, 0.0F, 0.0F, imageWidth, imageHeight, 256, 256);
        renderProgressArrow(guiGraphics);
    }

    protected void renderProgressArrow(GuiGraphicsExtractor guiGraphics) {
        int progressX = menu.getShakeXProgress();
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BG, leftPos + ARROW_X, topPos + ARROW_Y, 177.0F, 14.0F, progressX, 14, 256, 256);
    }
}
