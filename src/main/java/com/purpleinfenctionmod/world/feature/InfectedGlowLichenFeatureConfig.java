package com.purpleinfenctionmod.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class InfectedGlowLichenFeatureConfig {

    public static final Codec<InfectedGlowLichenFeatureConfig> CODEC =
        RecordCodecBuilder.create(instance ->
            instance.group(
                Codec.intRange(1, 256)
                    .fieldOf("tries")
                    .forGetter(config -> config.tries)
            ).apply(instance, InfectedGlowLichenFeatureConfig::new)
        );

    public final int tries;

    public InfectedGlowLichenFeatureConfig(int tries) {
        this.tries = tries;
    }
}