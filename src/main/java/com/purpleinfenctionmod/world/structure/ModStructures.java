package com.purpleinfenctionmod.world.structure;

import com.purpleinfenctionmod.PurpleInfenctionMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.StructureType;

public class ModStructures {

    public static final StructureType<LandOnlyJigsawStructure> LAND_ONLY_JIGSAW =
            Registry.register(
                    Registries.STRUCTURE_TYPE,
                    new Identifier(
                            PurpleInfenctionMod.MOD_ID,
                            "land_only_jigsaw"
                    ),
                    () -> LandOnlyJigsawStructure.CODEC
            );

    public static final StructureType<NightOnlyJigsawStructure> NIGHT_ONLY_JINSAW_STRUCTURE =
            Registry.register(
                    Registries.STRUCTURE_TYPE,
                    new Identifier(
                            PurpleInfenctionMod.MOD_ID,
                            "night_only_jinsaw_structure"
                    ),
                    () -> NightOnlyJigsawStructure.CODEC
            );


    public static void registerStructures() {
        PurpleInfenctionMod.LOGGER.info(
                "Registering structures for " + PurpleInfenctionMod.MOD_ID
        );
    }
}