package com.purpleinfenctionmod.entity;

import java.util.EnumSet;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;

public class FollowOwnerGoal extends Goal {

    private final MushroomPetEntity pet;
    private final double speed;
    private final float minDistance;
    private final float maxDistance;
    private PlayerEntity owner;

    public FollowOwnerGoal(MushroomPetEntity pet, double speed, float minDistance, float maxDistance) {
        this.pet = pet;
        this.speed = speed;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    @Override
    public boolean canStart() {
        PlayerEntity candidate = pet.getOwner();
        if (candidate == null) return false;

        double distance = pet.distanceTo(candidate);
        if (distance < minDistance) return false;

        this.owner = candidate;
        return true;
    }

    @Override
    public boolean shouldContinue() {
        if (owner == null || !owner.isAlive()) return false;
        return pet.distanceTo(owner) > minDistance;
    }

    @Override
    public void tick() {
        pet.getLookControl().lookAt(owner, 10.0F, pet.getMaxLookPitchChange());

        double distance = pet.distanceTo(owner);

        if (distance > maxDistance) {
            // Слишком далеко — телепортируемся ближе
            pet.refreshPositionAndAngles(owner.getX(), owner.getY(), owner.getZ(), pet.getYaw(), pet.getPitch());
            return;
        }

        if (distance > minDistance) {
            pet.getNavigation().startMovingTo(owner, speed);
        } else {
            pet.getNavigation().stop();
        }
    }

    @Override
    public void stop() {
        this.owner = null;
        pet.getNavigation().stop();
    }
}
