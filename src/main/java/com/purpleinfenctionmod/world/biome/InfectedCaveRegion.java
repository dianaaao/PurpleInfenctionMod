package com.purpleinfenctionmod.world.biome;
// package com.purpleinfenctionmod.world;

// import com.mojang.datafixers.util.Pair;
// import net.minecraft.registry.Registry;
// import net.minecraft.registry.RegistryKey;
// import net.minecraft.util.Identifier;
// import net.minecraft.world.biome.Biome;
// import net.minecraft.world.biome.source.util.MultiNoiseUtil;
// import terrablender.api.ParameterUtils;
// import terrablender.api.Region;
// import terrablender.api.RegionType;

// import java.util.function.Consumer;

// public class InfectedCaveRegion extends Region {

//     public InfectedCaveRegion(Identifier name, int weight) {
//         super(name, RegionType.OVERWORLD, weight);
//     }

//     @Override
//     public void addBiomes(Registry<Biome> registry, Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper) {
//         new ParameterUtils.ParameterPointListBuilder()
//             // Важно: Повторяем ТЕ ЖЕ САМЫЕ параметры климата!
//             .temperature(ParameterUtils.Temperature.NEUTRAL)
//             .humidity(ParameterUtils.Humidity.DRY)
//             .continentalness(ParameterUtils.Continentalness.INLAND)
//             .erosion(ParameterUtils.Erosion.EROSION_0) // Ровно то же значение!
//             .weirdness(ParameterUtils.Weirdness.FULL_RANGE)
//             .depth(MultiNoiseUtil.ParameterRange.of(0.25F, 1.5F)) // ТОЛЬКО ПЕЩЕРЫ
//             .build()
//             .forEach(point -> mapper.accept(Pair.of(point, ModBiomes.INFECTED_CAVE_KEY)));
//     }
// }