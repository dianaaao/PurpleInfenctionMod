package com.purpleinfenctionmod.mixin;

import com.purpleinfenctionmod.block.ModBlocks;
import com.purpleinfenctionmod.world.biome.InfectedBiomeFilter;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NoiseChunkGenerator.class)
public class SurfaceReplacementMixin {

    @Inject(method = "buildSurface", at = @At("RETURN"))
    private void replaceInfectedBiomeBlocks(
            net.minecraft.world.ChunkRegion region,
            net.minecraft.world.gen.StructureAccessor structures,
            net.minecraft.world.gen.noise.NoiseConfig noiseConfig,
            Chunk chunk,
            CallbackInfo ci
    ) {
        BlockPos.Mutable mutablePos = new BlockPos.Mutable();
        int startX = chunk.getPos().getStartX();
        int startZ = chunk.getPos().getStartZ();

        int bottomY = chunk.getBottomY();
        int topY = chunk.getTopY();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = startX + x;
                int worldZ = startZ + z;

                // Compute this ONCE per column instead of once per block.
                // The infected ring only depends on X/Z, never on height.
                boolean columnInfected = InfectedBiomeFilter.isWithinInfectedZone(
                        worldX + 0.5,
                        worldZ + 0.5
                );

                if (!columnInfected) {
                    // Nothing to replace in this column at all — skip entirely.
                    continue;
                }

                for (int y = topY - 1; y >= bottomY; y--) {
                    mutablePos.set(worldX, y, worldZ);
                    BlockState state = chunk.getBlockState(mutablePos);

                    if (state.isAir()) {
                        continue;
                    }

                    if (state.isOf(Blocks.STONE)) {
                        chunk.setBlockState(mutablePos, ModBlocks.INFECTED_STONE.getDefaultState(), false);
                    } else if (state.isOf(Blocks.DEEPSLATE)) {
                        chunk.setBlockState(mutablePos, ModBlocks.INFECTED_DEEPSLATE.getDefaultState(), false);
                    } else if (state.isOf(Blocks.DIRT)) {
                        chunk.setBlockState(mutablePos, ModBlocks.INFECTED_DIRT.getDefaultState(), false);
                    } else if (state.isOf(Blocks.GRASS_BLOCK)) {
                        chunk.setBlockState(mutablePos, ModBlocks.INFECTED_GRASS.getDefaultState(), false);
                    }
                }
            }
        }
    }
}