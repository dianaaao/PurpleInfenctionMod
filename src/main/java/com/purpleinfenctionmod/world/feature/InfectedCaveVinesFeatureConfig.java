package com.purpleinfenctionmod.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureConfig;

public record InfectedCaveVinesFeatureConfig(float berryChance, int minLength, int maxLength) implements FeatureConfig {
    public static final Codec<InfectedCaveVinesFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("berry_chance").forGetter(InfectedCaveVinesFeatureConfig::berryChance),
            Codec.intRange(1, 25).fieldOf("min_length").forGetter(InfectedCaveVinesFeatureConfig::minLength),
            Codec.intRange(1, 25).fieldOf("max_length").forGetter(InfectedCaveVinesFeatureConfig::maxLength)
    ).apply(instance, InfectedCaveVinesFeatureConfig::new));
}