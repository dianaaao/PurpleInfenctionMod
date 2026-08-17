package com.purpleinfenctionmod.entity;

import software.bernie.geckolib.core.animation.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;



public class SporeCreatureEntity extends PathAwareEntity implements GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.spore_creature.walk");
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.spore_creature.idle");
    private static final RawAnimation INFLATE = RawAnimation.begin().thenPlay("animation.spore_creature.inflate");

    private static final double EXPLODE_RANGE = 1.3;
    private static final int INFLATE_DURATION_TICKS = 12; // ~0.6 сек при 20 tps, подгони под длину анимации

    private boolean inflating = false;
    private int inflateTimer = 0;

    public SporeCreatureEntity(EntityType<? extends PathAwareEntity> type, World world) {
        super(type, world);
        this.setPersistent();
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.32)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));

        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient) return;

        if (inflating) {
            inflateTimer--;
            // Пока раздувается — не двигается (для читаемости момента взрыва)
            this.getNavigation().stop();
            if (inflateTimer <= 0) {
                explode();
            }
            return;
        }

        PlayerEntity target = this.getWorld().getClosestPlayer(this, EXPLODE_RANGE + 6);
        if (target != null) {
            this.getNavigation().startMovingTo(target, 1.1);

            if (this.squaredDistanceTo(target) <= EXPLODE_RANGE * EXPLODE_RANGE) {
                startInflate();
            }
        }
    }

    private void startInflate() {
        inflating = true;
        inflateTimer = INFLATE_DURATION_TICKS;
        this.triggerAnim("inflateController", "inflate");
        this.getWorld().playSound(null, this.getBlockPos(), SoundEvents.ENTITY_SPIDER_HURT, SoundCategory.HOSTILE, 0.8f, 0.6f);
    }

    private void explode() {
        if (!(this.getWorld() instanceof ServerWorld serverWorld)) return;

        // Небольшой прямой урон в точке взрыва тем, кто вплотную
        PlayerEntity nearest = serverWorld.getClosestPlayer(this, EXPLODE_RANGE + 1);
        if (nearest != null && this.squaredDistanceTo(nearest) <= (EXPLODE_RANGE + 1) * (EXPLODE_RANGE + 1)) {
            nearest.damage(this.getDamageSources().mobAttack(this), 3.0f);
        }

        // Облако яда — только по игрокам, 2.5 блока радиус, 4 секунды (80 тиков)
        PoisonCloudManager.spawnCloud(serverWorld, this.getPos(), 2.5, 80);

        serverWorld.spawnParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 0.4, this.getZ(), 12, 0.3, 0.3, 0.3, 0.02);
        serverWorld.playSound(null, this.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 0.6f, 1.3f);

        this.discard();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "moveController", 5, this::moveAnimPredicate));
        controllers.add(new AnimationController<>(this, "inflateController", 0, state -> PlayState.STOP)
                .triggerableAnim("inflate", INFLATE));
    }

    private PlayState moveAnimPredicate(AnimationState<SporeCreatureEntity> state) {
        if (inflating) return PlayState.STOP; // не мешаем inflate-анимации
        if (state.isMoving()) {
            state.getController().setAnimation(WALK);
        } else {
            state.getController().setAnimation(IDLE);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
