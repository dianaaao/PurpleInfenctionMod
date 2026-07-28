package com.purpleinfenctionmod;

import net.fabricmc.api.ModInitializer;
// import net.minecraft.server.command.CommandManager;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
// import net.minecraft.server.command.CommandManager;
// import net.minecraft.text.Text;

import com.purpleinfenctionmod.block.ModBlocks;
import com.purpleinfenctionmod.block.ModItems;

public class PurpleInfenctionMod implements ModInitializer {
	public static final String MOD_ID = "purpleinfenctionmod";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		
		// CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {

		// 	dispatcher.register(CommandManager.literal("ModInfo")

        //         .executes(context -> {

        //             context.getSource().sendFeedback(() -> 
        //                 Text.literal("Infected Biom Mod v1.0"),
        //                 false //наше сообщение увидет только тот, кто его отправил
        //             ); 
        //             return 1; // команда вернулсь успешно
        //         })
        //     );
		// });
		
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();

		LOGGER.info("Hello Fabric world!");
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}
