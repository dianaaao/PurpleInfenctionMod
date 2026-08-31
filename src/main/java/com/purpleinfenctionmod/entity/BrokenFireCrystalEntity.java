package com.purpleinfenctionmod.entity;

import com.purpleinfenctionmod.component.ModComponents;
import com.purpleinfenctionmod.item.ModItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BrokenFireCrystalEntity extends Entity implements GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.broken_fire_crystal.idle");

    public BrokenFireCrystalEntity(EntityType<?> type, World world) {
        super(type, world);
        this.setNoGravity(true);
        this.noClip = true;
    }

    @Override
    protected void initDataTracker() {
        // пока нечего отслеживать
    }

    @Override
    public void tick() {
        super.tick();
        this.setVelocity(0, 0, 0);
    }

    @Override
    protected void readCustomDataFromNbt(net.minecraft.nbt.NbtCompound nbt) {}

    @Override
    protected void writeCustomDataToNbt(net.minecraft.nbt.NbtCompound nbt) {}

    @Override
    public boolean isFireImmune() {
        return true;
    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (this.getWorld().isClient) {
            return ActionResult.SUCCESS;
        }

        ItemStack heldItem = player.getStackInHand(hand);

        if (!heldItem.isOf(ModItems.FRAGMENT_OF_OLD_CRYSTAL)) {
            return ActionResult.PASS;
        }

        if (!(this.getWorld() instanceof ServerWorld serverWorld)) {
            return ActionResult.PASS;
        }

        if (!player.getAbilities().creativeMode) {
            heldItem.decrement(1);
        }

        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        float yaw = this.getYaw();

        this.discard();

        CrystalEntity repaired = ModEntities.OLD_FIRE_CRYSTAL.create(serverWorld);
        if (repaired != null) {
            repaired.refreshPositionAndAngles(x, y, z, yaw, 0);
            serverWorld.spawnEntity(repaired);
        }

        // Deactivate infection globally.
        com.purpleinfenctionmod.world.InfectionWorldState
                .get(serverWorld)
                .setCrystalFixed(true);

        // Activate infected power for the player who repaired it.
        ModComponents.INFECTED_POWER
                .maybeGet(player)
                .ifPresent(comp -> comp.setActive(true));
        serverWorld.spawnParticles(
                net.minecraft.particle.ParticleTypes.END_ROD,
                x, y + 1, z,
                30, 0.5, 0.8, 0.5, 0.05
        );

        serverWorld.spawnParticles(
                net.minecraft.particle.ParticleTypes.END_ROD,
                x, y + 1, z,
                30, 0.5, 0.8, 0.5, 0.05
        );
        serverWorld.playSound(
                null, this.getBlockPos(),
                net.minecraft.sound.SoundEvents.BLOCK_BEACON_ACTIVATE,
                net.minecraft.sound.SoundCategory.BLOCKS,
                1.0f, 1.2f
        );

        return ActionResult.SUCCESS;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "idleController", 0,
                state -> {
                    state.getController().setAnimation(IDLE);
                    return PlayState.CONTINUE;
                }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
