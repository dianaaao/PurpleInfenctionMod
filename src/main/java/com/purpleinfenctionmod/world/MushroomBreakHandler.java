package com.purpleinfenctionmod.world;

import com.purpleinfenctionmod.block.ModBlocks;
import com.purpleinfenctionmod.entity.ModEntities;
import com.purpleinfenctionmod.entity.MushroomMobEntity;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import java.util.*;



public class MushroomBreakHandler {
    
    private static final int MAX_STRUCTURE_SIZE = 500;

    // Блоки, для которых судьба уже решена ("ломать нормально") — больше не бросаем монетку
    private static final Set<BlockPos> resolvedSafe = new HashSet<>();

    public static void register() {
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> {
            if (!isMushroomPart(state)) return true;
            if (!(world instanceof ServerWorld serverWorld)) return true;

            // Уже решено раньше как "безопасный" блок этой структуры — просто ломаем нормально
            if (resolvedSafe.remove(pos)) {
                return true;
            }

            // Первый клик по ещё не решённой структуре — считаем всю структуру и бросаем монетку один раз
            Set<BlockPos> structure = findStructure(serverWorld, pos);

            if (serverWorld.getRandom().nextInt(2) == 0) {
                // Ломается нормально — помечаем все блоки структуры как решённые
                resolvedSafe.addAll(structure);
                resolvedSafe.remove(pos); // текущий блок и так сейчас сломается штатно
                return true;
            } else {
                // Распад — убираем всю структуру и спавним моба
                for (BlockPos p : structure) {
                    serverWorld.removeBlock(p, false);
                }
                spawnMob(serverWorld, pos);
                return false;
            }
        });
    }

    private static boolean isMushroomPart(BlockState state) {
        return state.isOf(ModBlocks.INFECTED_MUSHROOM_STEM)
                || state.isOf(ModBlocks.INFECTED_MUSHROOM_HAT)
                || state.isOf(ModBlocks.INFECTED_BROWN_MUSHROOM);
    }

    private static Set<BlockPos> findStructure(ServerWorld world, BlockPos origin) {
        Set<BlockPos> visited = new HashSet<>();
        Deque<BlockPos> queue = new ArrayDeque<>();
        queue.add(origin);
        visited.add(origin);

        while (!queue.isEmpty() && visited.size() < MAX_STRUCTURE_SIZE) {
            BlockPos current = queue.poll();
            for (BlockPos neighbor : getNeighbors(current)) {
                if (visited.contains(neighbor)) continue;
                if (isMushroomPart(world.getBlockState(neighbor))) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return visited;
    }

    private static void spawnMob(ServerWorld world, BlockPos pos) {
        MushroomMobEntity mob = ModEntities.MUSHROOM_MOB.create(world);
        if (mob != null) {
            mob.refreshPositionAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);
            world.spawnEntity(mob);
        }
    }

    private static List<BlockPos> getNeighbors(BlockPos pos) {
        List<BlockPos> list = new ArrayList<>();
        for (int dx = -1; dx <= 1; dx++)
            for (int dy = -1; dy <= 1; dy++)
                for (int dz = -1; dz <= 1; dz++)
                    if (!(dx == 0 && dy == 0 && dz == 0))
                        list.add(pos.add(dx, dy, dz));
        return list;
    }
}
