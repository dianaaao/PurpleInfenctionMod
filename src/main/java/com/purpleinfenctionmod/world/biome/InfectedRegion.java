package com.purpleinfenctionmod.world.biome;

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
    public void addBiomes(
        Registry<Biome> registry,
        Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper
    ) {
        new ParameterUtils.ParameterPointListBuilder()
            .temperature(ParameterUtils.Temperature.NEUTRAL)
            .humidity(ParameterUtils.Humidity.NEUTRAL)
            .continentalness(ParameterUtils.Continentalness.INLAND)
            .erosion(ParameterUtils.Erosion.EROSION_0)
            .weirdness(ParameterUtils.Weirdness.PEAK_NORMAL)
            .depth(ParameterUtils.Depth.SURFACE)
            .build()
            .forEach(point ->
                mapper.accept(
                    Pair.of(point, ModBiomes.INFECTED_KEY)
                )
            );
    }
}