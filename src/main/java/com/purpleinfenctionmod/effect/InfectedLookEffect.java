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

    private static final TagKey<Structure> CASTLE =
            TagKey.of(
                    RegistryKeys.STRUCTURE,
                    new Identifier(
                            "purpleinfenctionmod",
                            "castle"
                    )
            );

    private static final Map<UUID, BlockPos> CASTLE_TARGETS =
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
    if (!CASTLE_TARGETS.containsKey(uuid)) {

        BlockPos castle = world.locateStructure(
                CASTLE,
                player.getBlockPos(),
                438,
                false
        );

        if (castle != null) {

            CASTLE_TARGETS.put(uuid, castle);

            InfectedLookNetworking.sendCastleTarget(
                    player,
                    castle
            );

            System.out.println(
                    "[purpleinfenctionmod] "
                            + "infected_look target: "
                            + castle
            );
        }
    }
}

    public static void removeTarget(PlayerEntity player) {

        UUID uuid = player.getUuid();

        if (CASTLE_TARGETS.remove(uuid) != null) {

            if (player instanceof ServerPlayerEntity serverPlayer) {

                InfectedLookNetworking.clearCastleTarget(
                        serverPlayer
                );
            }
        }
    }
    
}