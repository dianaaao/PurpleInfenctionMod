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
    public static final ComponentKey<InfectedPowerComponent> INFECTED_POWER =
        ComponentRegistry.getOrCreate(
            new Identifier("purpleinfenctionmod", "infected_power"),
            InfectedPowerComponent.class
        );

    @Override
    public void registerEntityComponentFactories(@javax.annotation.Nonnull EntityComponentFactoryRegistry registry) {
        
        if (RespawnCopyStrategy.ALWAYS_COPY!=null && DECONTROLL!=null){
            registry.registerForPlayers(DECONTROLL, PlayerDecontrollComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
        }
        if (RespawnCopyStrategy.ALWAYS_COPY != null && INFECTED_POWER != null) {
            registry.registerForPlayers(INFECTED_POWER, PlayerInfectedPowerComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
        }
    }
}