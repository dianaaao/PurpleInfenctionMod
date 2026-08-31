package com.purpleinfenctionmod.client.render;

import com.purpleinfenctionmod.entity.CrystalBoltEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class CrystalBoltEntityRenderer extends EntityRenderer<CrystalBoltEntity> {

    private static final ItemStack RENDER_STACK = new ItemStack(Items.END_ROD);

    private final ItemRenderer itemRenderer;

    public CrystalBoltEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
        this.shadowRadius = 0.1f;
    }

    @Override
    public void render(
            CrystalBoltEntity entity,
            float yaw,
            float tickDelta,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light
    ) {
        matrices.push();

        float renderYaw = MathHelper.lerpAngleDegrees(
        tickDelta, entity.prevYaw, entity.getYaw()
);

        float renderPitch = MathHelper.lerp(
                tickDelta, entity.prevPitch, entity.getPitch()
        );

        // Rotate the model so its long axis follows the projectile direction.
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f - renderYaw));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(renderPitch));

        matrices.scale(0.75f, 0.75f, 0.75f);

        itemRenderer.renderItem(
                RENDER_STACK,
                ModelTransformationMode.GROUND,
                light,
                OverlayTexture.DEFAULT_UV,
                matrices,
                vertexConsumers,
                entity.getWorld(),
                entity.getId()
        );

        matrices.pop();

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(CrystalBoltEntity entity) {
        // Unused since render() is fully overridden, but the base class requires a value.
        return new Identifier("textures/misc/forcefield.png");
    }
}