package com.purpleinfenctionmod.world.structure;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.block.Block;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.registry.Registry;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class CastleWorldState extends PersistentState {

    // НОВОЕ: защита от случайно огромного бокса (например, если где-то
    // ещё передадут "рыхлый" box вместо точного). saveFromWorld() на
    // объёме в миллионы блоков может подвесить сервер на сохранении/записи
    // NBT. Порог подберите под реальный размер ваших замков (castle_1/2/3) -
    // 500000 блоков с запасом хватает на замок примерно 60x60x140.
    private static final long MAX_CAPTURE_VOLUME = 500_000L;

    private final List<CastleData> castles = new ArrayList<>();

    public List<CastleData> getCastles() {
        return castles;
    }

    /**
     * Captures the given box as a single StructureTemplate snapshot.
     * No per-block scanning/reading loop — one efficient vanilla call.
     */
    public void captureCastle(BlockBox identityBox, ServerWorld world) {

        for (CastleData existing : castles) {
            if (existing.getBox().equals(identityBox)) {
                return;
            }
        }

        Vec3i size = new Vec3i(
                identityBox.getMaxX() - identityBox.getMinX() + 1,
                identityBox.getMaxY() - identityBox.getMinY() + 1,
                identityBox.getMaxZ() - identityBox.getMinZ() + 1
        );

        // НОВОЕ: проверка объёма перед тем, как делать дорогой saveFromWorld().
        long volume = (long) size.getX() * (long) size.getY() * (long) size.getZ();
        if (volume > MAX_CAPTURE_VOLUME) {
            System.out.println(
                    "[PurpleInfenctionMod] Castle box too large to capture ("
                            + volume + " blocks, box=" + identityBox
                            + ") - skipping to avoid freeze."
            );
            return;
        }

        BlockPos start = new BlockPos(
                identityBox.getMinX(),
                identityBox.getMinY(),
                identityBox.getMinZ()
        );

        StructureTemplate template = new StructureTemplate();
        template.saveFromWorld(world, start, size, false, Blocks.STRUCTURE_VOID);

        NbtCompound templateNbt = template.writeNbt(new NbtCompound());

        CastleData castle = new CastleData(identityBox);
        castle.setTemplateNbt(templateNbt);

        castles.add(castle);
        markDirty();

        System.out.println(
                "[PurpleInfenctionMod] Captured castle template: " + identityBox
        );
    }

    public static CastleWorldState get(ServerWorld world) {

        PersistentStateManager manager = world.getPersistentStateManager();

        return manager.getOrCreate(
                CastleWorldState::fromNbt,
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

            castleNbt.putBoolean("hidden", castle.isHidden());

            if (castle.getTemplateNbt() != null) {
                castleNbt.put("template", castle.getTemplateNbt().copy());
            }

            castleList.add(castleNbt);
        }

        nbt.put("castles", castleList);

        return nbt;
    }

    public static CastleWorldState fromNbt(NbtCompound nbt) {

        CastleWorldState state = new CastleWorldState();

        if (!nbt.contains("castles", NbtElement.LIST_TYPE)) {
            return state;
        }

        NbtList castleList = nbt.getList("castles", NbtElement.COMPOUND_TYPE);

        for (int i = 0; i < castleList.size(); i++) {

            NbtCompound castleNbt = castleList.getCompound(i);

            BlockBox box = new BlockBox(
                    castleNbt.getInt("minX"),
                    castleNbt.getInt("minY"),
                    castleNbt.getInt("minZ"),
                    castleNbt.getInt("maxX"),
                    castleNbt.getInt("maxY"),
                    castleNbt.getInt("maxZ")
            );

            CastleData castle = new CastleData(box);
            castle.setHidden(castleNbt.getBoolean("hidden"));

            if (castleNbt.contains("template", NbtElement.COMPOUND_TYPE)) {
                castle.setTemplateNbt(castleNbt.getCompound("template").copy());
            }

            state.castles.add(castle);
        }

        return state;
    }

    public static class CastleData {

        private final BlockBox box;
        private boolean hidden;
        private NbtCompound templateNbt;
        private transient StructureTemplate cachedTemplate;

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

        public void setTemplateNbt(NbtCompound templateNbt) {
            this.templateNbt = templateNbt;
            this.cachedTemplate = null; // invalidate cache
        }

        public NbtCompound getTemplateNbt() {
            return templateNbt;
        }

        /**
         * Lazily builds (and caches) the StructureTemplate from stored NBT.
         */
        public StructureTemplate getTemplate(ServerWorld world) {

            if (cachedTemplate != null) {
                return cachedTemplate;
            }

            if (templateNbt == null) {
                return null;
            }

            StructureTemplate template = new StructureTemplate();

            Registry<Block> blockRegistry =
                    world.getRegistryManager().get(RegistryKeys.BLOCK);

            template.readNbt(blockRegistry.getReadOnlyWrapper(), templateNbt);

            cachedTemplate = template;
            return cachedTemplate;
        }
    }
}