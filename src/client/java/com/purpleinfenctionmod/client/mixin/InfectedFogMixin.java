package com.purpleinfenctionmod.client.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.purpleinfenctionmod.world.biome.ModBiomes;

import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(BackgroundRenderer.class)
public class InfectedFogMixin {

    @Inject(method = "applyFog", at = @At("TAIL"))
    private static void purpleinfenctionmod$denseFog(Camera camera, BackgroundRenderer.FogType fogType,
                                                       float viewDistance, boolean thickFog,
                                                       float tickDelta, CallbackInfo ci) {
        Entity entity = camera.getFocusedEntity();
        World world = entity.getWorld();

        if (world.getBiome(entity.getBlockPos()).matchesKey(ModBiomes.INFECTED_KEY)) {
            // shrink the fog window — smaller start = fog begins closer to the camera
            RenderSystem.setShaderFogStart(viewDistance * 0.15F);
            RenderSystem.setShaderFogEnd(viewDistance * 0.55F);
        }
    }
}