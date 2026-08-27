package com.purpleinfenctionmod.client.entity;

import com.purpleinfenctionmod.entity.SporeProjectileEntity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class SporeProjectileRenderer extends EntityRenderer<SporeProjectileEntity> {

    private final ItemRenderer itemRenderer;
    private static final ItemStack DISPLAY_STACK = new ItemStack(Items.SLIME_BALL);

    public SporeProjectileRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(SporeProjectileEntity entity, float yaw, float tickDelta, MatrixStack matrices,
                        VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.scale(0.5F, 0.5F, 0.5F);

        itemRenderer.renderItem(
                DISPLAY_STACK,
                net.minecraft.client.render.model.json.ModelTransformationMode.GROUND,
                light,
                net.minecraft.client.render.OverlayTexture.DEFAULT_UV,
                matrices,
                vertexConsumers,
                entity.getWorld(),
                0
        );

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(SporeProjectileEntity entity) {
        return null;
    }
}
