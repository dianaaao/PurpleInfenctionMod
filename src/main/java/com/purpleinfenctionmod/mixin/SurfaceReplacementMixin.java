package com.purpleinfenctionmod.mixin;

import com.purpleinfenctionmod.block.ModBlocks;
import com.purpleinfenctionmod.world.biome.ModBiomes;

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

    // Внедряемся в самый конец метода buildSurface (когда ванильная поверхность уже расставлена)
    @Inject(method = "buildSurface", at = @At("RETURN"))
    private void replaceInfectedBiomeBlocks(net.minecraft.world.ChunkRegion region, net.minecraft.world.gen.StructureAccessor structures, net.minecraft.world.gen.noise.NoiseConfig noiseConfig, Chunk chunk, CallbackInfo ci) {
        BlockPos.Mutable mutablePos = new BlockPos.Mutable();
        int startX = chunk.getPos().getStartX();
        int startZ = chunk.getPos().getStartZ();
        
        int bottomY = chunk.getBottomY();
        int topY = chunk.getTopY();

        // Пробегаем по координатам чанка
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = startX + x;
                int worldZ = startZ + z;

                // Сканируем сверху вниз
                for (int y = topY - 1; y >= bottomY; y--) {
                    mutablePos.set(worldX, y, worldZ);
                    BlockState state = chunk.getBlockState(mutablePos);

                    // Пропускаем воздух и камень для оптимизации (их больше всего)
                    if (state.isAir()) {
                        continue;
                    }

                    if (state.isOf(Blocks.STONE)) {
                        // Проверяем, находится ли этот конкретный блок в биоме
                        if (chunk.getBiomeForNoiseGen(worldX >> 2, y >> 2, worldZ >> 2).matchesKey(ModBiomes.INFECTED_KEY)) {
                            chunk.setBlockState(mutablePos, ModBlocks.INFECTED_STONE.getDefaultState(), false);
                            // chunk.setBlockState(mutablePos, Blocks.AIR.getDefaultState(), false);
                        }
                    } else if (state.isOf(Blocks.DEEPSLATE)) {
                        // Проверяем, находится ли этот конкретный блок в биоме
                        if (chunk.getBiomeForNoiseGen(worldX >> 2, y >> 2, worldZ >> 2).matchesKey(ModBiomes.INFECTED_KEY)) {
                            chunk.setBlockState(mutablePos, ModBlocks.INFECTED_DEEPSLATE.getDefaultState(), false);
                            // chunk.setBlockState(mutablePos, Blocks.AIR.getDefaultState(), false);
                        }
                    } 
                    // Если нашли ванильную землю
                    else if (state.isOf(Blocks.DIRT)) {
                        if (chunk.getBiomeForNoiseGen(worldX >> 2, y >> 2, worldZ >> 2).matchesKey(ModBiomes.INFECTED_KEY)) {
                            chunk.setBlockState(mutablePos, ModBlocks.INFECTED_DIRT.getDefaultState(), false);
                        }
                    }

                    // Если нашли ванильную траву
                    else if (state.isOf(Blocks.GRASS_BLOCK)) {
                        // Проверяем, находится ли этот конкретный блок в твоем биоме
                        if (chunk.getBiomeForNoiseGen(worldX >> 2, y >> 2, worldZ >> 2).matchesKey(ModBiomes.INFECTED_KEY)) {
                            chunk.setBlockState(mutablePos, ModBlocks.INFECTED_GRASS.getDefaultState(), false);
                        }
                    } 
                }
            }
        }
    }
}