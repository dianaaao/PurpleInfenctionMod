package com.purpleinfenctionmod.client.feature;

import com.purpleinfenctionmod.client.model.HeadMushroomsModel;
import com.purpleinfenctionmod.component.ModComponents;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;

public class HeadMushroomsFeatureRenderer
        extends FeatureRenderer<
            AbstractClientPlayerEntity,
            PlayerEntityModel<AbstractClientPlayerEntity>
        > {

    private static final Identifier TEXTURE =
            new Identifier(
                "purpleinfenctionmod",
                "textures/entity/head_mushrooms.png"
            );

    private static final float STABILITY_THRESHOLD = 0.5f;

    private final HeadMushroomsModel model;

    public HeadMushroomsFeatureRenderer(
            FeatureRendererContext<
                AbstractClientPlayerEntity,
                PlayerEntityModel<AbstractClientPlayerEntity>
            > context) {

        super(context);

        this.model = new HeadMushroomsModel(
                MinecraftClient.getInstance()
                        .getEntityModelLoader()
                        .getModelPart(HeadMushroomsModel.LAYER)
        );
    }

    @Override
    public void render(
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            AbstractClientPlayerEntity entity,
            float limbAngle,
            float limbDistance,
            float tickDelta,
            float animationProgress,
            float headYaw,
            float headPitch) {

        boolean infected = ModComponents.DECONTROLL.maybeGet(entity)
                .map(comp -> comp.getStability() < STABILITY_THRESHOLD)
                .orElse(false);

        if (!infected) {
            return;
        }

        matrices.push();

        // Прикрепляем грибы к голове игрока
        this.getContextModel().getHead().rotate(matrices);

        var vertexConsumer = vertexConsumers.getBuffer(
                RenderLayer.getEntityCutoutNoCull(TEXTURE)
        );

        model.render(
                matrices,
                vertexConsumer,
                light,
                OverlayTexture.DEFAULT_UV,
                1.0F,
                1.0F,
                1.0F,
                1.0F
        );

        matrices.pop();
    }
}