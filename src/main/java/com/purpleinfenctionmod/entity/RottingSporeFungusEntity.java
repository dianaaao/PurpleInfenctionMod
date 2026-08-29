package com.purpleinfenctionmod.entity;

import java.util.EnumSet;
import java.util.List;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
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


import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;



public class RottingSporeFungusEntity extends PathAwareEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.spore-fungus.walk");
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.spore-fungus.idle");
    private static final RawAnimation SUMMON = RawAnimation.begin().thenPlay("animation.spore-fungus.attack");

    private static final double DETECTION_RANGE = 5.0;
    private static final double RANGED_ATTACK_RANGE = 20.0;

    private int summonCooldown = 0;
    private int rangedCooldown = 0;
    private int volleyShotsRemaining = 0;
    private int volleyShotTimer = 0;

    private int currentPhase = 1;

    private final ServerBossBar bossBar = new ServerBossBar(
            Text.literal("Rotting Spore Fungus"),
            BossBar.Color.PURPLE,
            BossBar.Style.PROGRESS
    );

    public RottingSporeFungusEntity(EntityType<? extends PathAwareEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 150.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.18)
                .add(EntityAttributes.GENERIC_ARMOR, 4.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 24.0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SummonMinionsGoal(this));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.7));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));

        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean result = super.damage(source, amount);
        if (result && this.getWorld() instanceof ServerWorld && summonCooldown <= 0) {
            performSummon();
        }
        return result;
    }

    @Override
    public void tick() {
        super.tick();

        if (summonCooldown > 0) summonCooldown--;
        if (rangedCooldown > 0) rangedCooldown--;

        bossBar.setPercent(Math.max(0, Math.min(1, this.getHealth() / this.getMaxHealth())));

        updatePhase();
        tryRangedAttack();
    }

    private void updatePhase() {
        float healthRatio = this.getHealth() / this.getMaxHealth();
        int newPhase;

        if (healthRatio > 0.66f) {
            newPhase = 1;
        } else if (healthRatio > 0.33f) {
            newPhase = 2;
        } else {
            newPhase = 3;
        }

        if (newPhase != currentPhase) {
            currentPhase = newPhase;
            onPhaseChanged();
        }
    }

    private void onPhaseChanged() {
        switch (currentPhase) {
            case 1 -> bossBar.setColor(BossBar.Color.PURPLE);
            case 2 -> bossBar.setColor(BossBar.Color.PINK);
            case 3 -> bossBar.setColor(BossBar.Color.RED);
        }

        if (this.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.getServer().getPlayerManager().broadcast(
                    Text.literal("Rotting Spore Fungus become stronger!"), false);
        }
    }

    private int getRangedCooldownTicks() {
        return switch (currentPhase) {
            case 1 -> 100; // 5 сек
            case 2 -> 70;  // 3.5 сек
            default -> 40; // 2 сек
        };
    }

    private int getSummonCooldownTicks() {
        return switch (currentPhase) {
            case 1 -> 200; // 10 сек
            case 2 -> 140; // 7 сек
            default -> 90; // 4.5 сек
        };
    }

    private float getProjectileDamage() {
        return switch (currentPhase) {
            case 1 -> 3.0f;
            case 2 -> 4.5f;
            default -> 6.0f;
        };
    }

    private void tryRangedAttack() {
        if (this.getWorld().isClient) return;

        LivingEntity target = this.getTarget();
        if (target == null) return;

        // Если залп уже идёт — обрабатываем следующий выстрел серии
        if (volleyShotsRemaining > 0) {
            volleyShotTimer--;
            if (volleyShotTimer <= 0) {
                fireProjectile(target);
                volleyShotsRemaining--;

                if (volleyShotsRemaining > 0) {
                    volleyShotTimer = 40 + this.random.nextInt(21); // 2-3 сек до следующего выстрела в залпе
                } else {
                    rangedCooldown = getRangedCooldownTicks(); // залп закончен — обычный кулдаун
                }
            }
            return;
        }

        // Залпа сейчас нет — проверяем, можно ли начать новый
        if (rangedCooldown > 0) return;

        double distance = this.distanceTo(target);
        if (distance < 4.0 || distance > RANGED_ATTACK_RANGE) return;

        // Запускаем залп из 3 выстрелов
        volleyShotsRemaining = 3;
        fireProjectile(target);
        volleyShotsRemaining--;
        volleyShotTimer = 40 + this.random.nextInt(21);
    }

    private void fireProjectile(LivingEntity target) {
        if (!(this.getWorld() instanceof ServerWorld serverWorld)) return;

        SporeProjectileEntity projectile = new SporeProjectileEntity(serverWorld, this);
        projectile.setStats(getProjectileDamage(), 60 + (currentPhase - 1) * 20, currentPhase - 1);

        double startX = this.getX();
        double startY = this.getBodyY(0.7);
        double startZ = this.getZ();
        projectile.setPosition(startX, startY, startZ);

        double dx = target.getX() - startX;
        double dy = target.getBodyY(0.5) - startY;
        double dz = target.getZ() - startZ;

        projectile.setVelocity(dx, dy, dz, 1.6f, 1.0f);

        serverWorld.spawnEntity(projectile);
        this.triggerAnim("attackController", "attack");

        // ВРЕМЕННАЯ ДИАГНОСТИКА
        // serverWorld.getServer().getPlayerManager().broadcast(
        //         net.minecraft.text.Text.literal(String.format(
        //                 "Boss Y=%.2f, bodyY(0.7)=%.2f, startY=%.2f, target Y=%.2f",
        //                 this.getY(), this.getBodyY(0.7), startY, target.getBodyY(0.5)
        //         )), false);
    }

    private void performSummon() {
        summonCooldown = getSummonCooldownTicks();
        this.triggerAnim("summonController", "summon");

        if (!(this.getWorld() instanceof ServerWorld serverWorld)) return;

        int count = 3 + this.random.nextInt(3);
        for (int i = 0; i < count; i++) {
            double offsetX = (this.random.nextDouble() - 0.5) * 4.0;
            double offsetZ = (this.random.nextDouble() - 0.5) * 4.0;

            SporeCreatureEntity minion = ModEntities.SPORE_CREATURE.create(serverWorld);
            if (minion == null) continue;

            minion.refreshPositionAndAngles(
                    this.getX() + offsetX, this.getY(), this.getZ() + offsetZ,
                    this.getYaw(), 0
            );

            List<PlayerEntity> nearby = serverWorld.getEntitiesByClass(
                    PlayerEntity.class,
                    new Box(this.getBlockPos()).expand(DETECTION_RANGE + 5),
                    p -> true
            );
            if (!nearby.isEmpty()) {
                minion.setTarget(nearby.get(0));
            }

            serverWorld.spawnEntity(minion);
        }
    }

    private static class SummonMinionsGoal extends Goal {
        private final RottingSporeFungusEntity fungus;

        SummonMinionsGoal(RottingSporeFungusEntity fungus) {
            this.fungus = fungus;
            this.setControls(EnumSet.noneOf(Control.class));
        }

        @Override
        public boolean canStart() {
            if (fungus.summonCooldown > 0) return false;
            PlayerEntity nearest = fungus.getWorld().getClosestPlayer(fungus, DETECTION_RANGE);
            return nearest != null;
        }

        @Override
        public void start() {
            fungus.performSummon();
        }

        @Override
        public boolean shouldContinue() {
            return false;
        }
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        bossBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        bossBar.removePlayer(player);
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);

        if (this.getWorld().isClient) return;
        if (!(this.getWorld() instanceof ServerWorld serverWorld)) return;

        MushroomPetEntity pet = ModEntities.MUSHROOM_PET.create(serverWorld);
        if (pet == null) return;

        pet.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0);

        if (damageSource.getAttacker() instanceof PlayerEntity player) {
            pet.setOwner(player);
        } else {
            PlayerEntity nearest = serverWorld.getClosestPlayer(this, 32.0);
            if (nearest != null) pet.setOwner(nearest);
        }

        serverWorld.spawnEntity(pet);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "moveController", 5, this::moveAnimPredicate));
        controllers.add(new AnimationController<>(this, "summonController", 0, state -> PlayState.STOP)
                .triggerableAnim("summon", SUMMON));
        controllers.add(new AnimationController<>(this, "attackController", 0, state -> PlayState.STOP)
                .triggerableAnim("attack", SUMMON)); // переиспользуем ту же анимацию броска
    }

    private PlayState moveAnimPredicate(AnimationState<RottingSporeFungusEntity> state) {
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
