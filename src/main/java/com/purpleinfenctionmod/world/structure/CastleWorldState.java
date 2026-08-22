package com.purpleinfenctionmod.world.structure;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.block.Block;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.state.property.Property;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CastleWorldState extends PersistentState {

    private final List<CastleData> castles = new ArrayList<>();

    public List<CastleData> getCastles() {
        return castles;
    }

    // NEW:
public void addCastle(BlockBox identityBox, List<BlockBox> pieceBoxes, ServerWorld world) {

    // Prevent duplicates.
    for (CastleData existing : castles) {
        if (existing.getBox().equals(identityBox)) {
            return;
        }
    }

    CastleData castle = new CastleData(identityBox);

    Set<BlockPos> seen = new HashSet<>();
    BlockPos.Mutable pos = new BlockPos.Mutable();

    for (BlockBox box : pieceBoxes) {
        for (int x = box.getMinX(); x <= box.getMaxX(); x++) {
            for (int y = box.getMinY(); y <= box.getMaxY(); y++) {
                for (int z = box.getMinZ(); z <= box.getMaxZ(); z++) {

                    BlockPos immutable = new BlockPos(x, y, z);

                    if (!seen.add(immutable)) {
                        continue;
                    }

                    pos.set(x, y, z);

                    BlockState state = world.getBlockState(pos);

                    if (state.isAir()) {
                        continue;
                    }

                    BlockEntity blockEntity =
                            world.getBlockEntity(pos);

                    NbtCompound blockEntityNbt = null;

                    if (blockEntity != null) {
                        blockEntityNbt = blockEntity.createNbt();
                    }

                    castle.addBlock(
                            immutable,
                            state,
                            blockEntityNbt
                    );
                }
            }
        }
    }

    castles.add(castle);

    markDirty();

    System.out.println(
            "[PurpleInfenctionMod] Saved castle: "
                    + identityBox
                    + " blocks="
                    + castle.getBlocks().size()
    );
}
public static CastleWorldState get(ServerWorld world) {

    PersistentStateManager manager =
            world.getPersistentStateManager();

    Registry<Block> blockRegistry =
            world.getRegistryManager()
                    .get(RegistryKeys.BLOCK);

    return manager.getOrCreate(
            nbt -> CastleWorldState.fromNbt(
                    nbt,
                    blockRegistry
            ),
            CastleWorldState::new,
            "purpleinfenctionmod_castles"
    );
}

        @Override
public NbtCompound writeNbt(NbtCompound nbt) {

    NbtList castleList = new NbtList();

    for (CastleData castle : castles) {

        NbtCompound castleNbt = new NbtCompound();

        BlockBox box = castle.getBox();

        castleNbt.putInt("minX", box.getMinX());
        castleNbt.putInt("minY", box.getMinY());
        castleNbt.putInt("minZ", box.getMinZ());

        castleNbt.putInt("maxX", box.getMaxX());
        castleNbt.putInt("maxY", box.getMaxY());
        castleNbt.putInt("maxZ", box.getMaxZ());

        castleNbt.putBoolean(
                "hidden",
                castle.isHidden()
        );

        NbtList blockList = new NbtList();

        for (SavedBlock block : castle.getBlocks()) {

            NbtCompound blockNbt = new NbtCompound();

            BlockPos pos = block.getPos();

            blockNbt.putInt("x", pos.getX());
            blockNbt.putInt("y", pos.getY());
            blockNbt.putInt("z", pos.getZ());

            /*
             * Save block ID.
             */
            Identifier id =
                Registries.BLOCK.getId(
                        block.getState().getBlock()
                );

            blockNbt.putString(
                    "block",
                    id.toString()
            );

            /*
             * Save block properties.
             */
            NbtCompound propertiesNbt =
                    new NbtCompound();

            for (Property<?> property :
                    block.getState().getProperties()) {

                saveProperty(
                        propertiesNbt,
                        property,
                        block.getState()
                );
            }

            blockNbt.put(
                    "properties",
                    propertiesNbt
            );

            /*
             * Save block entity.
             */
            if (block.hasBlockEntity()) {

                blockNbt.put(
                        "block_entity",
                        block.getBlockEntityNbt().copy()
                );
            }

            blockList.add(blockNbt);
        }

        castleNbt.put(
                "blocks",
                blockList
        );

        castleList.add(castleNbt);
    }

    nbt.put(
            "castles",
            castleList
    );

    return nbt;
}
private static <T extends Comparable<T>> void saveProperty(
        NbtCompound nbt,
        Property<T> property,
        BlockState state
) {

    T value = state.get(property);

    nbt.putString(
            property.getName(),
            property.name(value)
    );
}
public static CastleWorldState fromNbt(
        NbtCompound nbt,
        Registry<Block> blockRegistry
) {

    CastleWorldState state =
            new CastleWorldState();

    if (!nbt.contains(
            "castles",
            NbtElement.LIST_TYPE
    )) {
        return state;
    }

    NbtList castleList =
            nbt.getList(
                    "castles",
                    NbtElement.COMPOUND_TYPE
            );

    for (int i = 0; i < castleList.size(); i++) {

        NbtCompound castleNbt =
                castleList.getCompound(i);

        BlockBox box = new BlockBox(
                castleNbt.getInt("minX"),
                castleNbt.getInt("minY"),
                castleNbt.getInt("minZ"),

                castleNbt.getInt("maxX"),
                castleNbt.getInt("maxY"),
                castleNbt.getInt("maxZ")
        );

        CastleData castle =
                new CastleData(box);

        castle.setHidden(
                castleNbt.getBoolean("hidden")
        );

        if (castleNbt.contains(
                "blocks",
                NbtElement.LIST_TYPE
        )) {

            NbtList blockList =
                    castleNbt.getList(
                            "blocks",
                            NbtElement.COMPOUND_TYPE
                    );

            for (int j = 0;
                 j < blockList.size();
                 j++) {

                NbtCompound blockNbt =
                        blockList.getCompound(j);

                BlockPos pos =
                        new BlockPos(
                                blockNbt.getInt("x"),
                                blockNbt.getInt("y"),
                                blockNbt.getInt("z")
                        );

                /*
                 * Get block ID.
                 */
                String blockId =
                        blockNbt.getString("block");

                Identifier identifier =
                        Identifier.tryParse(blockId);

                if (identifier == null) {
                    continue;
                }

                Block block =
                        blockRegistry.get(identifier);

                if (block == null) {
                    System.out.println(
                            "[PurpleInfenctionMod] "
                            + "Unknown block: "
                            + blockId
                    );

                    continue;
                }

                /*
                 * Start with default state.
                 */
                BlockState blockState =
                        block.getDefaultState();

                /*
                 * Restore properties.
                 */
                if (blockNbt.contains(
                        "properties",
                        NbtElement.COMPOUND_TYPE
                )) {

                    NbtCompound properties =
                            blockNbt.getCompound(
                                    "properties"
                            );

                    blockState =
                            restoreProperties(
                                    blockState,
                                    properties
                            );
                }

                /*
                 * Restore block entity NBT.
                 */
                NbtCompound blockEntityNbt =
                        null;

                if (blockNbt.contains(
                        "block_entity",
                        NbtElement.COMPOUND_TYPE
                )) {

                    blockEntityNbt =
                            blockNbt.getCompound(
                                    "block_entity"
                            ).copy();
                }

                castle.addBlock(
                        pos,
                        blockState,
                        blockEntityNbt
                );
            }
        }

        state.castles.add(castle);
    }

    return state;
}
private static BlockState restoreProperties(
        BlockState state,
        NbtCompound properties
) {

    for (Property<?> property :
            state.getProperties()) {

        String propertyName =
                property.getName();

        if (!properties.contains(
                propertyName,
                NbtElement.STRING_TYPE
        )) {
            continue;
        }

        String value =
                properties.getString(
                        propertyName
                );

        state =
                setProperty(
                        state,
                        property,
                        value
                );
    }

    return state;
}
private static <T extends Comparable<T>> BlockState setProperty(
        BlockState state,
        Property<T> property,
        String value
) {

    java.util.Optional<T> optionalValue =
            property.parse(value);

    if (optionalValue.isPresent()) {

        state = state.with(
                property,
                optionalValue.get()
        );
    }

    return state;
}

    public static class CastleData {

        private final BlockBox box;

        private boolean hidden;

        private final List<SavedBlock> blocks =
                new ArrayList<>();

        public CastleData(BlockBox box) {
            this.box = box;
            this.hidden = false;
        }

        public BlockBox getBox() {
            return box;
        }

        public boolean isHidden() {
            return hidden;
        }

        public void setHidden(boolean hidden) {
            this.hidden = hidden;
        }

        public void addBlock(
                BlockPos pos,
                BlockState state,
                NbtCompound blockEntityNbt
        ) {

            blocks.add(
                    new SavedBlock(
                            pos,
                            state,
                            blockEntityNbt
                    )
            );
        }

        public List<SavedBlock> getBlocks() {
            return blocks;
        }
    }

    public static class SavedBlock {

        private final BlockPos pos;
        private final BlockState state;
        private final NbtCompound blockEntityNbt;

        public SavedBlock(
                BlockPos pos,
                BlockState state,
                NbtCompound blockEntityNbt
        ) {
            this.pos = pos;
            this.state = state;
            this.blockEntityNbt = blockEntityNbt;
        }

        public BlockPos getPos() {
            return pos;
        }

        public BlockState getState() {
            return state;
        }

        public NbtCompound getBlockEntityNbt() {
            return blockEntityNbt;
        }

        public boolean hasBlockEntity() {
            return blockEntityNbt != null;
        }
    }
}