package com.purpleinfenctionmod.client.gui;

import org.slf4j.LoggerFactory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.purpleinfenctionmod.component.ModComponents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
public class DecontrollHudOverlay implements HudRenderCallback {

	public static final String MOD_ID = "purpleinfenctionmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static final Identifier BAR_FRAME =
        new Identifier("purpleinfenctionmod", "textures/gui/infection_bar_original.png");
    private static final Identifier BAR_FILLED =
        new Identifier("purpleinfenctionmod", "textures/gui/infection_bar_original_filled.png");
    private static final int BAR_WIDTH = 80;
    private static final int BAR_HEIGHT = 9;

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.options.hudHidden){
            LOGGER.info("null or hidden",client.player == null, client.options.hudHidden);
            return;
        };

        float stability = ModComponents.DECONTROLL.maybeGet(client.player)
            .map(c -> c.getStability())
            .orElse(1.0f);
        if (stability >= 1.0f){
            LOGGER.info("stability is 1.0f");
            return;
        };

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();
        int x = (screenWidth / 2) - (BAR_WIDTH / 2) + (BAR_WIDTH / 2);
        int y = screenHeight - 49;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        drawContext.drawTexture(
            BAR_FRAME,
            x, y,
            0, 0,
            BAR_WIDTH, BAR_HEIGHT,
            BAR_WIDTH, BAR_HEIGHT
        );

        int filledWidth = Math.round(BAR_WIDTH * stability);
        if (filledWidth > 0) {
            drawContext.drawTexture(
                BAR_FILLED,
                x, y,
                0, 0,
                filledWidth, BAR_HEIGHT,
                BAR_WIDTH, BAR_HEIGHT
            );
        }

        RenderSystem.disableBlend();
    }
}