package com.purpleinfenctionmod.entity;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class PigeonEntity extends AnimalEntity implements GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.pigeon.idle");
    private static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.pigeon.walk");
    private static final RawAnimation FLY = RawAnimation.begin().thenLoop("animation.pigeon.fly");
    private static final RawAnimation TAKEOFF = RawAnimation.begin().thenPlay("animation.pigeon.takeoff");

    private static final TrackedData<Boolean> FLYING =
            DataTracker.registerData(
                    PigeonEntity.class,
                    TrackedDataHandlerRegistry.BOOLEAN
            );

    private int flightTimer = 0;
    private int nextFlightCheck = 200 + this.random.nextInt(400);
    private double targetX;
    private double targetZ;

    public PigeonEntity(EntityType<? extends AnimalEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FLYING, false);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 4.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.4);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation navigation = new BirdNavigation(this, world);
        navigation.setCanPathThroughDoors(false);
        navigation.setCanSwim(false);
        navigation.setCanEnterOpenDoors(true);
        return navigation;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new WanderAroundFarGoal(this, 1.0, 1.0F));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(3, new LookAroundGoal(this));
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient) return;

        if (isFlying()) {
            flightTimer--;

            double groundY = findGroundY();
            double heightAboveGround = this.getY() - groundY;

            // Направляем к целевой точке горизонтально
            double dx = targetX - this.getX();
            double dz = targetZ - this.getZ();
            double horizontalDist = Math.sqrt(dx * dx + dz * dz);

            double moveX = 0;
            double moveZ = 0;
            if (horizontalDist > 0.3) {
                moveX = (dx / horizontalDist) * 0.15;
                moveZ = (dz / horizontalDist) * 0.15;
            }

            double moveY;
            if (flightTimer > 30) {
                moveY = 0.15;
            } else if (flightTimer > 10) {
                moveY = 0.0;
            } else {
                moveY = heightAboveGround > 3 ? -0.15 : -0.05;
            }

            this.setVelocity(moveX, moveY, moveZ);

            // Поворачиваем модель в сторону движения
            if (horizontalDist > 0.3) {
                float yaw = (float) (Math.toDegrees(Math.atan2(-dx, dz)));
                this.setYaw(yaw);
                this.setBodyYaw(yaw);
            }

            if (this.isOnGround() || heightAboveGround <= 0.2 || flightTimer <= 0) {
                endFlight();
            }
        } else {
            nextFlightCheck--;
            if (nextFlightCheck <= 0 && this.isOnGround()) {
                startFlight();
            }
        }
    }

    private double findGroundY() {
        net.minecraft.util.math.BlockPos.Mutable pos = this.getBlockPos().mutableCopy();
        for (int i = 0; i < 20; i++) {
            if (!this.getWorld().getBlockState(pos).isAir()) {
                return pos.getY() + 1;
            }
            pos.move(net.minecraft.util.math.Direction.DOWN);
        }
        return this.getY() - 20;
    }

    private void startFlight() {
        this.dataTracker.set(FLYING, true);
        flightTimer = 60 + this.random.nextInt(40);
        this.setNoGravity(true);
        this.triggerAnim("stateController", "takeoff");

        // Выбираем случайную точку в стороне, куда полетит голубь
        double angle = this.random.nextDouble() * Math.PI * 2;
        double distance = 3.0 + this.random.nextDouble() * 5.0; // 3-8 блоков в сторону
        targetX = this.getX() + Math.cos(angle) * distance;
        targetZ = this.getZ() + Math.sin(angle) * distance;

        this.setVelocity(0, 0.3, 0);
    }

    private void endFlight() {
        this.dataTracker.set(FLYING, false);
        this.setNoGravity(false);
        this.setVelocity(this.getVelocity().x, 0, this.getVelocity().z); 
        nextFlightCheck = 200 + this.random.nextInt(400);
    }

    public boolean isFlying() {
        return this.dataTracker.get(FLYING);
    }

    @Override
    public boolean isFlappingWings() {
        return isFlying();
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "moveController", 5, this::moveAnimPredicate));
        controllers.add(new AnimationController<>(this, "stateController", 0, state -> PlayState.STOP)
                .triggerableAnim("takeoff", TAKEOFF));
    }

    private PlayState moveAnimPredicate(AnimationState<PigeonEntity> state) {
        if (isFlying()) {
            state.getController().setAnimation(FLY);
        } else if (state.isMoving()) {
            state.getController().setAnimation(WALK);
        } else {
            state.getController().setAnimation(IDLE);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, net.minecraft.entity.damage.DamageSource damageSource) {
        return false;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
