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
                    new Identifier(PurpleInfenctionMod.MOD_ID, "land_only_jigsaw"), 
                    () -> LandOnlyJigsawStructure.CODEC
            );

    public static void registerStructures() {
        PurpleInfenctionMod.LOGGER.info("Registering structures for " + PurpleInfenctionMod.MOD_ID);
    }
}