package com.purpleinfenctionmod.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import org.joml.Vector3f;
import net.minecraft.util.math.Vec3d;

public class PoisonCloudManager {
    
    private static final List<Cloud> activeClouds = new ArrayList<>();

    private static class Cloud {
        final ServerWorld world;
        final Vec3d pos;
        final double radius;
        int ticksLeft;

        Cloud(ServerWorld world, Vec3d pos, double radius, int duration) {
            this.world = world;
            this.pos = pos;
            this.radius = radius;
            this.ticksLeft = duration;
        }
    }

    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            Iterator<Cloud> it = activeClouds.iterator();
            while (it.hasNext()) {
                Cloud cloud = it.next();
                if (cloud.world != world) continue;

                cloud.ticksLeft--;
                if (cloud.ticksLeft <= 0) {
                    it.remove();
                    continue;
                }

                // Раз в 10 тиков (полсекунды) — накладываем яд на игроков в радиусе
                if (cloud.ticksLeft % 10 == 0) {
                    List<PlayerEntity> players = world.getEntitiesByClass(
                            PlayerEntity.class,
                            new Box(cloud.pos.x - cloud.radius, cloud.pos.y - 1, cloud.pos.z - cloud.radius,
                                    cloud.pos.x + cloud.radius, cloud.pos.y + 2, cloud.pos.z + cloud.radius),
                            p -> p.getPos().distanceTo(cloud.pos) <= cloud.radius
                    );
                    for (PlayerEntity player : players) {
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 40, 0));
                    }
                }

                // Сине-фиолетовые частицы облака
                DustParticleEffect particle = new DustParticleEffect(new Vector3f(0.55f, 0.25f, 0.85f), 1.5f);
                for (int i = 0; i < 3; i++) {
                    double ox = (world.random.nextDouble() - 0.5) * cloud.radius;
                    double oz = (world.random.nextDouble() - 0.5) * cloud.radius;
                    world.spawnParticles(particle, cloud.pos.x + ox, cloud.pos.y + 0.3, cloud.pos.z + oz, 1, 0, 0.05, 0, 0.0);
                }
            }
        });
    }

    public static void spawnCloud(ServerWorld world, Vec3d pos, double radius, int durationTicks) {
        activeClouds.add(new Cloud(world, pos, radius, durationTicks));
    }
}
