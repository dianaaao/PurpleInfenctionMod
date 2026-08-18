package com.purpleinfenctionmod.entity;

import java.util.EnumSet;

import net.minecraft.entity.EntityType;
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



public class RottingSporeFungusEntity extends PathAwareEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.spore-fungus.walk");
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.spore-fungus.idle");
    private static final RawAnimation SUMMON = RawAnimation.begin().thenPlay("animation.spore-fungus.attack");

    private static final double DETECTION_RANGE = 5.0;
    private static final int SUMMON_COOLDOWN_TICKS = 100; // 5 секунд
    private int summonCooldown = 0;

    public RottingSporeFungusEntity(EntityType<? extends PathAwareEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 150.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.18)
                .add(EntityAttributes.GENERIC_ARMOR, 6.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 20.0);
    }

    private final ServerBossBar bossBar = new ServerBossBar(
        Text.literal("Rotting Spore Fungus"),
        BossBar.Color.PURPLE,
        BossBar.Style.PROGRESS
    );

    @Override
    protected void initGoals() {
        // Моб не атакует сам — только бродит и реагирует на приближение/урон
        this.goalSelector.add(1, new SummonMinionsGoal(this));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.7));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));
        // targetSelector намеренно пустой — моб никого не выбирает целью для собственной атаки
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
        if (summonCooldown > 0) {
            summonCooldown--;
        }

        bossBar.setPercent(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void onStartedTrackingBy(net.minecraft.server.network.ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        bossBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(net.minecraft.server.network.ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        bossBar.removePlayer(player);
    }

    private void performSummon() {
        summonCooldown = SUMMON_COOLDOWN_TICKS;
        this.triggerAnim("summonController", "summon");

        if (!(this.getWorld() instanceof ServerWorld serverWorld)) return;

        int count = 3 + this.random.nextInt(3); // от 3 до 5
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

    // Кастомный Goal — следит за приближением игрока
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
            return false; // мгновенное разовое срабатывание
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "moveController", 5, this::moveAnimPredicate));
        controllers.add(new AnimationController<>(this, "summonController", 0, state -> PlayState.STOP)
                .triggerableAnim("summon", SUMMON));
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
