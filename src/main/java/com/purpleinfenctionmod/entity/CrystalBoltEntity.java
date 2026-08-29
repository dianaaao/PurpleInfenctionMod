package com.purpleinfenctionmod.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class CrystalBoltEntity extends ThrownItemEntity {

    private static final float DAMAGE_AMOUNT = 6.0f;
    private static final float HEAL_AMOUNT = 4.0f;

    public CrystalBoltEntity(EntityType<? extends CrystalBoltEntity> type, World world) {
        super(type, world);
    }

    public CrystalBoltEntity(World world, LivingEntity owner) {
        super(ModEntities.CRYSTAL_BOLT, owner, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.END_ROD; // used for the default rendering fallback; visuals can be a custom renderer later
    }

    @Override
    protected void onEntityHit(EntityHitResult hitResult) {
        super.onEntityHit(hitResult);

        if (this.getWorld().isClient) {
            return;
        }

        Entity hitEntity = hitResult.getEntity();
        Entity ownerEntity = this.getOwner();

        if (hitEntity instanceof LivingEntity target) {

            target.damage(
                    this.getDamageSources().magic(),
                    DAMAGE_AMOUNT
            );

            if (ownerEntity instanceof PlayerEntity player) {
                player.heal(HEAL_AMOUNT);
            }
        }

        this.getWorld().playSound(
                null, this.getBlockPos(),
                SoundEvents.BLOCK_BEACON_ACTIVATE,
                SoundCategory.PLAYERS,
                0.6f, 1.4f
        );

        if (this.getWorld() instanceof net.minecraft.server.world.ServerWorld serverWorld) {
            serverWorld.spawnParticles(
                    ParticleTypes.END_ROD,
                    this.getX(), this.getY(), this.getZ(),
                    12, 0.2, 0.2, 0.2, 0.02
            );
        }

        this.discard();
    }

    @Override
    protected void onBlockHit(BlockHitResult hitResult) {
        super.onBlockHit(hitResult);

        if (!this.getWorld().isClient) {
            this.discard();
        }
    }
}