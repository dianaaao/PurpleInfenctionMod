package com.purpleinfenctionmod.world.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.heightprovider.HeightProvider;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

import java.util.Optional;

public class LandOnlyJigsawStructure extends Structure {

    public static final Codec<LandOnlyJigsawStructure> CODEC = RecordCodecBuilder.<LandOnlyJigsawStructure>mapCodec(instance ->
        instance.group(
                LandOnlyJigsawStructure.configCodecBuilder(instance),
                StructurePool.REGISTRY_CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                Identifier.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
                Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.size),
                HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
                Codec.BOOL.fieldOf("use_expansion_hack").forGetter(structure -> structure.useExpansionHack),
                Heightmap.Type.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
                Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter)
        ).apply(instance, LandOnlyJigsawStructure::new)
    ).codec();

    private final RegistryEntry<StructurePool> startPool;
    private final Optional<Identifier> startJigsawName;
    private final int size;
    private final HeightProvider startHeight;
    private final boolean useExpansionHack;
    private final Optional<Heightmap.Type> projectStartToHeightmap;
    private final int maxDistanceFromCenter;

    public LandOnlyJigsawStructure(Structure.Config config,
                                   RegistryEntry<StructurePool> startPool,
                                   Optional<Identifier> startJigsawName,
                                   int size,
                                   HeightProvider startHeight,
                                   boolean useExpansionHack,
                                   Optional<Heightmap.Type> projectStartToHeightmap,
                                   int maxDistanceFromCenter) {
        super(config);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.size = size;
        this.startHeight = startHeight;
        this.useExpansionHack = useExpansionHack;
        this.projectStartToHeightmap = projectStartToHeightmap;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
    }

    @Override
    public Optional<Structure.StructurePosition> getStructurePosition(Structure.Context context) {
        BlockPos centerPos = context.chunkPos().getStartPos();

        // 1. Get surface Y level at chunk center to check for water
        int surfaceY = context.chunkGenerator().getHeight(
                centerPos.getX(),
                centerPos.getZ(),
                Heightmap.Type.WORLD_SURFACE_WG,
                context.world(),
                context.noiseConfig()
        );

        // 2. Check the block right below surface level
        BlockState blockState = context.chunkGenerator().getColumnSample(
                centerPos.getX(),
                centerPos.getZ(),
                context.world(),
                context.noiseConfig()
        ).getState(surfaceY - 1);

        // 3. Cancel spawn if liquid or water
        if (blockState.isOf(Blocks.WATER) || blockState.getFluidState().isStill()) {
            return Optional.empty();
        }

        // 4. Generate jigsaw pieces starting at Y=0 and let projectStartToHeightmap handle the Y placement
        return StructurePoolBasedGenerator.generate(
                context,
                this.startPool,
                this.startJigsawName,
                this.size,
                centerPos.withY(0),
                this.useExpansionHack,
                this.projectStartToHeightmap,
                this.maxDistanceFromCenter
        );
    }

    @Override
    public StructureType<?> getType() {
        return ModStructures.LAND_ONLY_JIGSAW;
    }
}