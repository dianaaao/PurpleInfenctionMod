package com.purpleinfenctionmod.world.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

import java.util.stream.Stream;

public class RingBiomeSource extends BiomeSource {

    public static final Codec<RingBiomeSource> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            MultiNoiseBiomeSource.CODEC
                .fieldOf("wrapped")
                .forGetter(source -> source.wrapped),

            Biome.REGISTRY_CODEC
                .fieldOf("infected")
                .forGetter(source -> source.infected)
        ).apply(instance, RingBiomeSource::new)
    );

    private final MultiNoiseBiomeSource wrapped;
    private final RegistryEntry<Biome> infected;

    public RingBiomeSource(
        MultiNoiseBiomeSource wrapped,
        RegistryEntry<Biome> infected
    ) {
        this.wrapped = wrapped;
        this.infected = infected;
    }

    @Override
    protected Stream<RegistryEntry<Biome>> biomeStream() {
        return Stream.concat(
            wrapped.getBiomes().stream(),
            Stream.of(infected)
        );
    }

    @Override
    protected Codec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

    @Override
    public RegistryEntry<Biome> getBiome(
        int x,
        int y,
        int z,
        MultiNoiseUtil.MultiNoiseSampler noiseSampler
    ) {
        RegistryEntry<Biome> original =
            wrapped.getBiome(x, y, z, noiseSampler);

        // BiomeSource coordinates are quart coordinates.
        // 1 quart = 4 blocks.
        int blockX = x * 4;
        int blockZ = z * 4;

        if (InfectedBiomeFilter.isWithinInfectedZone(blockX, blockZ)) {
            return infected;
        }

        return original;
    }
}