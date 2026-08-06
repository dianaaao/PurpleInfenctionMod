package com.purpleinfenctionmod.client;

import java.util.List;
import java.util.Random;

import com.purpleinfenctionmod.component.ModComponents;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.util.Identifier;

public class ShaderDiscontrollHandler {
    private static final Random RANDOM = new Random();
    private static final float ACTIVATION_THRESHOLD = 0.5f;
    private static final int SWAP_INTERVAL_TICKS = 200; // 10 seconds at 20 TPS

    private static final List<Identifier> RANDOM_SHADERS = List.of(
        new Identifier("minecraft", "shaders/post/spider.json"),
        new Identifier("minecraft", "shaders/post/invert.json"),
        new Identifier("minecraft", "shaders/post/nausea.json"),
        new Identifier("minecraft", "shaders/post/pale.json"),
        new Identifier("minecraft", "shaders/post/creeper.json"),
        new Identifier("minecraft", "shaders/post/desaturate.json")
    );

    private static int ticksUntilNextSwap = SWAP_INTERVAL_TICKS;
    private static boolean shaderActive = false;

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            float stability = ModComponents.DECONTROLL.maybeGet(client.player)
                .map(c -> c.getStability())
                .orElse(1.0f);

            if (stability >= ACTIVATION_THRESHOLD) {
                if (shaderActive) {
                    client.gameRenderer.disablePostProcessor();
                    shaderActive = false;
                }
                ticksUntilNextSwap = SWAP_INTERVAL_TICKS; // reset so it doesn't fire immediately on relapse
                return;
            }

            ticksUntilNextSwap--;
            if (ticksUntilNextSwap > 0) return;

            ticksUntilNextSwap = SWAP_INTERVAL_TICKS;

            Identifier chosen = RANDOM_SHADERS.get(RANDOM.nextInt(RANDOM_SHADERS.size()));
            client.gameRenderer.loadPostProcessor(chosen);
            shaderActive = true;
        });
    }
}