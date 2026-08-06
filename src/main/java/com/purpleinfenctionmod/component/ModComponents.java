package com.purpleinfenctionmod.component;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.util.Identifier;

public class ModComponents implements EntityComponentInitializer {
    public static final ComponentKey<DecontrollComponent> DECONTROLL =
        ComponentRegistry.getOrCreate(
            new Identifier("purpleinfenctionmod", "decontroll"),
            DecontrollComponent.class
        );

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(DECONTROLL, PlayerDecontrollComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}