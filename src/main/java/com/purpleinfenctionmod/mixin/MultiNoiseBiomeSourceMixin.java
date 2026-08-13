package com.purpleinfenctionmod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.purpleinfenctionmod.world.biome.InfectedBiomeFilter;
import com.purpleinfenctionmod.world.biome.ModBiomes;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

    @Mixin(MultiNoiseBiomeSource.class)
public class MultiNoiseBiomeSourceMixin {
    private RegistryEntry<Biome> cachedInfectedBiomeEntry;

    private static int debugCalls = 0;

    @Inject(
        method = "getBiome",
        at = @At("RETURN")
    )
    private void debugGetBiome(
        int x,
        int y,
        int z,
        MultiNoiseUtil.MultiNoiseSampler noiseSampler,
        CallbackInfoReturnable<RegistryEntry<Biome>> cir
    ) {
        if (debugCalls++ < 30) {
            RegistryEntry<Biome> biome = cir.getReturnValue();

            System.out.println(
                "[InfectedMixin] #" + debugCalls +
                " x=" + x +
                " y=" + y +
                " z=" + z +
                " biome=" +
                (biome != null ? biome.getKey() : "null")
            );
        }
    }
}
