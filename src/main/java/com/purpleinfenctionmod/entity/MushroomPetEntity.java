package com.purpleinfenctionmod.entity;

import java.util.UUID;

import net.minecraft.entity.EntityType;
// import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;



public class MushroomPetEntity extends PathAwareEntity implements GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.mushroom_pet.walk");
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.mushroom_pet.idle");
    private static final RawAnimation MOUTH_OPEN = RawAnimation.begin().thenPlay("animation.mushroom_pet.mouth_open");

    private String ownerName;
    private final SimpleInventory inventory = new SimpleInventory(27);

    public MushroomPetEntity(EntityType<? extends PathAwareEntity> type, World world) {
        super(type, world);
        this.setPersistent();
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 25.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0);
    }

    public void setOwner(PlayerEntity player) {
        this.ownerName = player.getGameProfile().getName();
    }

    public PlayerEntity getOwner() {
        if (ownerName == null) return null;
        if (this.getWorld() instanceof net.minecraft.server.world.ServerWorld serverWorld) {
            return serverWorld.getServer().getPlayerManager().getPlayer(ownerName);
        }
        return null;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new FollowOwnerGoal(this, 1.2, 3.0F, 10.0F));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));
    }

    @Override
    public ActionResult interactAt(PlayerEntity player, Vec3d hitPos, Hand hand) {
        if (!this.getWorld().isClient) {
            this.triggerAnim("mouthController", "mouth_open");

            if (player instanceof ServerPlayerEntity serverPlayer) {
                serverPlayer.openHandledScreen(new NamedScreenHandlerFactory() {
                    @Override
                    public Text getDisplayName() {
                        return Text.literal("Mushroom Pet Inventory");
                    }

                    @Override
                    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                        return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, inventory);
                    }
                });
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    public boolean damage(net.minecraft.entity.damage.DamageSource source, float amount) {
        return false; // полностью игнорируем любой урон
    }

    @Override
    public boolean isInvulnerableTo(net.minecraft.entity.damage.DamageSource damageSource) {
        return true;
    }

    @Override
    public void onDeath(net.minecraft.entity.damage.DamageSource source) {
        super.onDeath(source);
        if (!this.getWorld().isClient) {
            // Раз это "рюкзак" — при смерти питомца высыпаем содержимое, чтобы игрок не терял вещи навсегда
            for (int i = 0; i < inventory.size(); i++) {
                ItemStack stack = inventory.getStack(i);
                if (!stack.isEmpty()) {
                    this.dropStack(stack);
                }
            }
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("OwnerName")) {
            this.ownerName = nbt.getString("OwnerName");
        }
        if (nbt.contains("Inventory")) {
            NbtCompound inventoryNbt = nbt.getCompound("Inventory");
            DefaultedList<ItemStack> stacks = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);
            Inventories.readNbt(inventoryNbt, stacks);
            for (int i = 0; i < inventory.size(); i++) {
                inventory.setStack(i, stacks.get(i));
            }
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.ownerName != null) {
            nbt.putString("OwnerName", this.ownerName);
        }

        NbtCompound inventoryNbt = new NbtCompound();
        DefaultedList<ItemStack> stacks = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);
        for (int i = 0; i < inventory.size(); i++) {
            stacks.set(i, inventory.getStack(i));
        }
        Inventories.writeNbt(inventoryNbt, stacks);
        nbt.put("Inventory", inventoryNbt);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "moveController", 5, this::moveAnimPredicate));
        controllers.add(new AnimationController<>(this, "mouthController", 0, state -> PlayState.STOP)
                .triggerableAnim("mouth_open", MOUTH_OPEN));
    }

    private PlayState moveAnimPredicate(AnimationState<MushroomPetEntity> state) {
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
