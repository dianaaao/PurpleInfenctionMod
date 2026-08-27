package com.purpleinfenctionmod.world.biome;

import java.util.List;

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext.GenerationSettingsContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

public final class ModBiomeModifiers {

    private static final List<RegistryKey<PlacedFeature>> INFECTED_DUPLICATE_ORE_FEATURES = List.of(
        key("ores/coal/coal_lower"),
        key("ores/coal/coal_upper"),
        key("ores/copper/copper_large"),
        key("ores/copper/copper"),
        key("ores/diamond/diamond_buried"),
        key("ores/diamond/diamond_large"),
        key("ores/diamond/diamond_medium"),
        key("ores/diamond/diamond"),
        key("ores/emerald/emerald"),
        key("ores/gold/gold_extra"),
        key("ores/gold/gold_lower"),
        key("ores/gold/gold"),
        key("ores/iron/iron_middle"),
        key("ores/iron/iron_small"),
        key("ores/iron/iron_upper"),
        key("ores/lapis/lapis_buried"),
        key("ores/lapis/lapis"),
        key("ores/redstone/redstone_buried"),
        key("ores/redstone/redstone")
    );

    private static RegistryKey<PlacedFeature> key(String path) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE,
            new Identifier("purpleinfenctionmod", path));
    }

    public static void register() {
        BiomeModifications.create(new Identifier("purpleinfenctionmod", "double_vanilla_ores_in_infected_biome"))
            .add(
                ModificationPhase.ADDITIONS,
                BiomeSelectors.includeByKey(ModBiomes.INFECTED_KEY),
                context -> {
                    GenerationSettingsContext gen = context.getGenerationSettings();
                    for (RegistryKey<PlacedFeature> feature : INFECTED_DUPLICATE_ORE_FEATURES) {
                        gen.addFeature(GenerationStep.Feature.UNDERGROUND_ORES, feature);
                    }
                }
            );
    }
}