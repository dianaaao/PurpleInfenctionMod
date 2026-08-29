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
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.heightprovider.HeightProvider;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePiecesList;
import net.minecraft.util.math.BlockBox;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.StructureAccessor;

// import java.util.Random;/
import java.util.Optional;
import net.minecraft.util.math.random.Random;
public class NightOnlyJigsawStructure extends Structure{
    
    public static final Codec<NightOnlyJigsawStructure> CODEC =
            RecordCodecBuilder.<NightOnlyJigsawStructure>mapCodec(instance ->
                    instance.group(
                            Structure.configCodecBuilder(instance),

                            StructurePool.REGISTRY_CODEC
                                    .fieldOf("start_pool")
                                    .forGetter(structure -> structure.startPool),

                            Identifier.CODEC
                                    .optionalFieldOf("start_jigsaw_name")
                                    .forGetter(structure -> structure.startJigsawName),

                            Codec.intRange(0, 30)
                                    .fieldOf("size")
                                    .forGetter(structure -> structure.size),

                            HeightProvider.CODEC
                                    .fieldOf("start_height")
                                    .forGetter(structure -> structure.startHeight),

                            Codec.BOOL
                                    .fieldOf("use_expansion_hack")
                                    .forGetter(structure -> structure.useExpansionHack),

                            Heightmap.Type.CODEC
                                    .optionalFieldOf("project_start_to_heightmap")
                                    .forGetter(structure -> structure.projectStartToHeightmap),

                            Codec.intRange(1, 128)
                                    .fieldOf("max_distance_from_center")
                                    .forGetter(structure -> structure.maxDistanceFromCenter)

                    ).apply(instance, NightOnlyJigsawStructure::new)
            ).codec();

    private final RegistryEntry<StructurePool> startPool;
    private final Optional<Identifier> startJigsawName;
    private final int size;
    private final HeightProvider startHeight;
    private final boolean useExpansionHack;
    private final Optional<Heightmap.Type> projectStartToHeightmap;
    private final int maxDistanceFromCenter;

    public NightOnlyJigsawStructure(
            Structure.Config config,
            RegistryEntry<StructurePool> startPool,
            Optional<Identifier> startJigsawName,
            int size,
            HeightProvider startHeight,
            boolean useExpansionHack,
            Optional<Heightmap.Type> projectStartToHeightmap,
            int maxDistanceFromCenter
    ) {
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
    public Optional<Structure.StructurePosition> getStructurePosition(
            Structure.Context context
    ) {
        
        BlockPos centerPos = context.chunkPos().getStartPos();

        // Check surface height
        int surfaceY = context.chunkGenerator().getHeight(
                centerPos.getX(),
                centerPos.getZ(),
                Heightmap.Type.WORLD_SURFACE_WG,
                context.world(),
                context.noiseConfig()
        );

        // Get block below surface
        BlockState blockState = context.chunkGenerator().getColumnSample(
                centerPos.getX(),
                centerPos.getZ(),
                context.world(),
                context.noiseConfig()
        ).getState(surfaceY - 1);

        // Don't generate on water
        if (blockState.isOf(Blocks.WATER)
                || blockState.getFluidState().isStill()) {
            return Optional.empty();
        }

        // Generate Jigsaw structure
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
    }// NEW:@Override
@Override
public void postPlace(
        StructureWorldAccess world,
        StructureAccessor structureAccessor,
        ChunkGenerator chunkGenerator,
        Random random,
        BlockBox box,
        ChunkPos chunkPos,
        StructurePiecesList pieces
) {

    // Compute a tight bounding box from the actual placed pieces,
    // instead of using the loose generation box (which can span
    // the full world height and isn't the real castle footprint).
    int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, minZ = Integer.MAX_VALUE;
    int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE, maxZ = Integer.MIN_VALUE;

    for (StructurePiece piece : pieces.pieces()) {
        BlockBox b = piece.getBoundingBox();
        minX = Math.min(minX, b.getMinX());
        minY = Math.min(minY, b.getMinY());
        minZ = Math.min(minZ, b.getMinZ());
        maxX = Math.max(maxX, b.getMaxX());
        maxY = Math.max(maxY, b.getMaxY());
        maxZ = Math.max(maxZ, b.getMaxZ());
    }

    // НОВОЕ: если по какой-то причине кусков нет (пустой pieces),
    // раньше сюда подставлялся "рыхлый" box генерации, который может
    // растянуться на всю высоту мира и до max_distance_from_center в
    // стороны. Это гигантский объём, и попытка сохранить его как шаблон
    // в captureCastle() вешала/крашила игру. Теперь просто пропускаем
    // постановку в очередь вместо использования такого box.
    if (minX > maxX) {
        System.out.println(
                "[PurpleInfenctionMod] NightOnlyJigsawStructure.postPlace() "
                        + "got no pieces for chunk " + chunkPos
                        + " - skipping castle registration (loose box was " + box + ")"
        );
        return;
    }

    BlockBox tightBox = new BlockBox(minX, minY, minZ, maxX, maxY, maxZ);

    System.out.println(
            "[PurpleInfenctionMod] "
                    + "NightOnlyJigsawStructure.postPlace() "
                    + "chunk="
                    + chunkPos.x
                    + ","
                    + chunkPos.z
                    + " tightBox="
                    + tightBox
                    + " (loose box was "
                    + box
                    + ")"
    );

    CastleNightManager.queueCastle(
            tightBox,
            world.getSeed()
    );
}
    @Override
    public StructureType<?> getType() {
        return ModStructures.NIGHT_ONLY_JINSAW_STRUCTURE;
    }
}