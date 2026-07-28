package com.purpleinfenctionmod.world;

import com.mojang.datafixers.util.Pair;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class InfectedRegion extends Region {

    public InfectedRegion(Identifier name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper) {
        this.addBiome(mapper, 
            ParameterUtils.Temperature.FULL_RANGE, 
            ParameterUtils.Humidity.FULL_RANGE, 
            ParameterUtils.Continentalness.FAR_INLAND, 
            ParameterUtils.Erosion.FULL_RANGE, 
            ParameterUtils.Weirdness.FULL_RANGE, 
            ParameterUtils.Depth.SURFACE, 
            0.0F, 
            ModBiomes.INFECTED_KEY
        );
    }

}