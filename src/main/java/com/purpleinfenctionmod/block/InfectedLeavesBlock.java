package com.purpleinfenctionmod.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;

public class InfectedLeavesBlock extends LeavesBlock {

    public InfectedLeavesBlock(Settings settings) {
        super(settings);
    }

    // Вместо сложной замены свойств переопределяем проверку дистанции
    @Override
    public boolean hasRandomTicks(BlockState state) {
        // Листва начинает осыпаться, только если она не установлена игроком и не persistent
        return !state.get(PERSISTENT);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // Если в радиусе 12 блоков нет бревна — осыпается
        if (!hasLogNearby(world, pos, 12)) {
            dropStacks(state, world, pos);
            world.removeBlock(pos, false);
        }
    }

    private boolean hasLogNearby(WorldAccess world, BlockPos pos, int maxDistance) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int x = -maxDistance; x <= maxDistance; x++) {
            for (int y = -maxDistance; y <= maxDistance; y++) {
                for (int z = -maxDistance; z <= maxDistance; z++) {
                    mutable.set(pos, x, y, z);
                    if (world.getBlockState(mutable).isIn(net.minecraft.registry.tag.BlockTags.LOGS)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}