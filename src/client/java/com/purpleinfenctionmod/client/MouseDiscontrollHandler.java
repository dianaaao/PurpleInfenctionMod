package com.purpleinfenctionmod.client;

import com.purpleinfenctionmod.component.ModComponents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import java.util.Random;

public class MouseDiscontrollHandler {
    private static final Random RANDOM = new Random();

    // Tune these to taste
    private static final float MAX_YAW_JITTER = 4.0f;   // degrees per tick at 0 stability
    private static final float MAX_PITCH_JITTER = 2.0f;
    private static final float ACTIVATION_THRESHOLD = 0.7f; // no jitter above this stability

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            var player = client.player;
            if (player == null || client.world == null) return;
            if (client.currentScreen != null) return; // don't jitter while in a menu/inventory

            float stability = ModComponents.DECONTROLL.maybeGet(player)
                .map(c -> c.getStability())
                .orElse(1.0f);

            if (stability >= ACTIVATION_THRESHOLD) return;

            // 0 at threshold, 1 at zero stability
            float severity = 1.0f - (stability / ACTIVATION_THRESHOLD);

            float yawJitter = (RANDOM.nextFloat() - 0.5f) * 2.5f * MAX_YAW_JITTER * severity;
            float pitchJitter = (RANDOM.nextFloat() - 0.5f) * 2.5f * MAX_PITCH_JITTER * severity;

            client.player.setYaw(client.player.getYaw() + yawJitter);
            client.player.setPitch(clampPitch(client.player.getPitch() + pitchJitter));
        });
    }

    private static float clampPitch(float pitch) {
        return Math.max(-90.0f, Math.min(90.0f, pitch));
    }
}