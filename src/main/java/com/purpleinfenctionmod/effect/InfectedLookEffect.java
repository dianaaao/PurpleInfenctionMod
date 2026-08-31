package com.purpleinfenctionmod.effect;

import com.purpleinfenctionmod.network.InfectedLookNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.Structure;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InfectedLookEffect extends StatusEffect {

    private static final TagKey<Structure> ARENA =
            TagKey.of(
                    RegistryKeys.STRUCTURE,
                    new Identifier(
                            "purpleinfenctionmod",
                            "arena"
                    )
            );

    private static final Map<UUID, BlockPos> ARENA_TARGETS =
            new HashMap<>();

    public InfectedLookEffect() {
        super(StatusEffectCategory.NEUTRAL, 0x800080);
    }

    @Override
    public boolean canApplyUpdateEffect(
            int duration,
            int amplifier
    ) {
        return true;
    }

    @Override
public void applyUpdateEffect(
        LivingEntity entity,
        int amplifier
) {

    if (!(entity instanceof ServerPlayerEntity player)) {
        return;
    }

    ServerWorld world = player.getServerWorld();
    UUID uuid = player.getUuid();

    /*
     * Search only once.
     */
    if (!ARENA_TARGETS.containsKey(uuid)) {

        BlockPos arena = world.locateStructure(
                ARENA,
                player.getBlockPos(),
                438,
                false
        );

        if (arena != null) {

            ARENA_TARGETS.put(uuid, arena);

            InfectedLookNetworking.sendArenaTarget(
                    player,
                    arena
            );

            System.out.println(
                    "[purpleinfenctionmod] "
                            + "infected_look target: "
                            + arena
            );
        }
    }
}

    public static void removeTarget(PlayerEntity player) {

        UUID uuid = player.getUuid();

        if (ARENA_TARGETS.remove(uuid) != null) {

            if (player instanceof ServerPlayerEntity serverPlayer) {

                InfectedLookNetworking.clearArenaTarget(
                        serverPlayer
                );
            }
        }
    }
    
}