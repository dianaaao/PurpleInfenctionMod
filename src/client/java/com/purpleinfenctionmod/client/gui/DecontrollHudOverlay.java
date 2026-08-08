package com.purpleinfenctionmod.client.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.purpleinfenctionmod.component.ModComponents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.Identifier;

public class DecontrollHudOverlay implements HudRenderCallback {

    public static final String MOD_ID = "purpleinfenctionmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Modern Fabric syntax: Identifier.of() replaces constructor in 1.20.5+
    private static final Identifier BAR_FRAME =
        Identifier.of(MOD_ID, "textures/gui/infection_bar_original.png");
    private static final Identifier BAR_FILLED =
        Identifier.of(MOD_ID, "textures/gui/infection_bar_original_filled.png");

    private static final int BAR_WIDTH = 80;
    private static final int BAR_HEIGHT = 9;

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();

        var player = client.player;
        if (player == null || client.options.hudHidden) {
            return;
        }

        // Hide overlay in Creative/Spectator or when player is not in survival mode
        if (player.isCreative() || player.isSpectator()) {
            return;
        }

        float stability = ModComponents.DECONTROLL.maybeGet(player)
            .map(c -> c.getStability())
            .orElse(1.0f);

        if (stability >= 1.0f) {
            return;
        }

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        // Vanilla hunger bar is 91px wide, starting at (screenWidth / 2) + 9
        // Aligning your 80px bar right over the hunger bar:
        int x = (screenWidth / 2) + 91 - BAR_WIDTH; 

        // Vanilla hunger bar sits at screenHeight - 39.
        // Putting this directly above hunger:
        int y = screenHeight - 49;

        // Optional: Move up 10px if player is underwater so it doesn't overlap Air Bubbles
        if (player.getAir() < player.getMaxAir()) {
            y -= 10;
        }

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        // Draw Frame
        drawContext.drawTexture(
            BAR_FRAME,
            x, y,
            0, 0,
            BAR_WIDTH, BAR_HEIGHT,
            BAR_WIDTH, BAR_HEIGHT
        );

        // Draw Progress Fill
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