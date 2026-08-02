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
        new ParameterUtils.ParameterPointListBuilder()
            .temperature(ParameterUtils.Temperature.FULL_RANGE)
            .humidity(ParameterUtils.Humidity.FULL_RANGE)
            .continentalness(ParameterUtils.Continentalness.FAR_INLAND)
            .erosion(ParameterUtils.Erosion.FULL_RANGE)
            .weirdness(ParameterUtils.Weirdness.FULL_RANGE)
            .depth(ParameterUtils.Depth.FULL_RANGE) // <--- Идёт от неба до бедрока!
            .build()
            .forEach(point -> mapper.accept(Pair.of(point, ModBiomes.INFECTED_KEY)));
    }
}