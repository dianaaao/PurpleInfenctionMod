package com.purpleinfenctionmod.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;



public class SporeProjectileEntity extends ThrownItemEntity {

    private float damage = 3.0f;
    private int poisonDuration = 60; // 3 секунды
    private int poisonAmplifier = 0;

    public SporeProjectileEntity(EntityType<? extends SporeProjectileEntity> type, World world) {
        super(type, world);
    }

    public SporeProjectileEntity(World world, LivingEntity owner) {
        super(ModEntities.SPORE_PROJECTILE, owner, world);
    }

    public void setStats(float damage, int poisonDuration, int poisonAmplifier) {
        this.damage = damage;
        this.poisonDuration = poisonDuration;
        this.poisonAmplifier = poisonAmplifier;
    }

    @Override
    protected Item getDefaultItem() {
        return Items.SLIME_BALL; // визуальная заглушка — зелёный шарик
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);

        if (this.getWorld().isClient) return;

        if (hitResult instanceof EntityHitResult entityHitResult
                && entityHitResult.getEntity() instanceof LivingEntity target) {

            if (target != this.getOwner()) {
                target.damage(this.getDamageSources().thrown(this, this.getOwner()), damage);
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, poisonDuration, poisonAmplifier));
            }
        }

        if (this.getWorld() instanceof net.minecraft.server.world.ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.WITCH, this.getX(), this.getY(), this.getZ(), 10, 0.2, 0.2, 0.2, 0.02);
        }

        this.discard();
    }

    @Override
    public ItemStack getStack() {
        return new ItemStack(Items.SLIME_BALL);
    }
}
