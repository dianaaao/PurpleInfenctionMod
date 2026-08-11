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

    // Вес задается ОДИН РАЗ на весь регион!
    public InfectedRegion(Identifier name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper) {
        
        // Общие шум-параметры для всей колонки
        var temp = ParameterUtils.Temperature.FULL_RANGE;
        var humidity = ParameterUtils.Humidity.FULL_RANGE;
        var continentalness = ParameterUtils.Continentalness.span(ParameterUtils.Continentalness.INLAND, ParameterUtils.Continentalness.FAR_INLAND);
        var erosion = ParameterUtils.Erosion.FULL_RANGE;
        var weirdness = ParameterUtils.Weirdness.FULL_RANGE;

        // 1. Поверхность Заражения
        new ParameterUtils.ParameterPointListBuilder()
            .temperature(temp)
            .humidity(humidity)
            .continentalness(continentalness)
            .erosion(erosion)
            .weirdness(weirdness)
            .depth(ParameterUtils.Depth.SURFACE)
            .build()
            .forEach(point -> mapper.accept(Pair.of(point, ModBiomes.INFECTED_KEY)));

        // 2. Пещеры Заражения (Используют ТЕ ЖЕ шумы, но для глубины)
        new ParameterUtils.ParameterPointListBuilder()
            .temperature(temp)
            .humidity(humidity)
            .continentalness(continentalness)
            .erosion(erosion)
            .weirdness(weirdness)
            .depth(ParameterUtils.Depth.UNDERGROUND)
            .build()
            .forEach(point -> mapper.accept(Pair.of(point, ModBiomes.INFECTED_KEY)));
    }
}