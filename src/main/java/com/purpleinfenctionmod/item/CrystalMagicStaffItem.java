package com.purpleinfenctionmod.item;

import com.purpleinfenctionmod.entity.CrystalBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class CrystalMagicStaffItem extends Item {

    public CrystalMagicStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient) {

            CrystalBoltEntity bolt = new CrystalBoltEntity(world, user);

            bolt.setVelocity(
                    user, user.getPitch(), user.getYaw(),
                    0.0f, 1.6f, 1.0f
            );

            ((ServerWorld) world).spawnEntity(bolt);

            world.playSound(
                    null, user.getBlockPos(),
                    SoundEvents.ENTITY_BLAZE_SHOOT,
                    SoundCategory.PLAYERS,
                    1.0f, 1.2f
            );

            if (!user.getAbilities().creativeMode) {
                stack.damage(1, user, p -> p.sendToolBreakStatus(hand));
            }
        }

        user.getItemCooldownManager().set(this, 10); // 0.5s cooldown between shots

        return TypedActionResult.success(stack, world.isClient);
    }
}