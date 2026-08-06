package com.purpleinfenctionmod.world;

import com.purpleinfenctionmod.block.ModBlocks;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

public class PlacedBlockDecayHandler {

    private static final int DECAY_TICKS = 3 * 60 * 20;

    private static final Map<ServerWorld, Map<BlockPos, Integer>> trackedBlocks = new IdentityHashMap<>();

    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(PlacedBlockDecayHandler::tick);
    }


    public static void trackPlacedBlock(ServerWorld world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (ModBlocks.isInfectedBlock(block)) {
            return;
        }

        if (!world.getBiome(pos).matchesKey(ModBiomes.INFECTED_KEY)) {
            return;
        }

        trackedBlocks
            .computeIfAbsent(world, w -> new HashMap<>())
            .put(pos.toImmutable(), DECAY_TICKS);
    }

    private static void tick(ServerWorld world) {
        Map<BlockPos, Integer> worldMap = trackedBlocks.get(world);
        if (worldMap == null || worldMap.isEmpty()) return;

        Iterator<Map.Entry<BlockPos, Integer>> iterator = worldMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<BlockPos, Integer> entry = iterator.next();
            int ticksLeft = entry.getValue() - 1;

            if (ticksLeft <= 0) {
                BlockPos pos = entry.getKey();
                if (!world.isAir(pos)) {
                    world.removeBlock(pos, false);
                }
                iterator.remove();
            } else {
                entry.setValue(ticksLeft);
            }
        }
    }
}