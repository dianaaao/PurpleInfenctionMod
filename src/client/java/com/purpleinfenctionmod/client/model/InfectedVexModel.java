package com.purpleinfenctionmod.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class InfectedVexModel<T extends Entity> extends EntityModel<T> {

    public static final EntityModelLayer LAYER =
        new EntityModelLayer(new Identifier("purpleinfenctionmod", "infected_vex"), "main");

    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightWing;
    private final ModelPart leftWing;

    public InfectedVexModel(ModelPart root) {
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.rightWing = root.getChild("right_wing");
        this.leftWing = root.getChild("left_wing");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        root.addChild("head",
            ModelPartBuilder.create()
                .uv(0, 0).cuboid(-2.5F, -11.0F, -2.5F, 5.0F, 5.0F, 5.0F, new Dilation(0.0F))
                .uv(32, 6).cuboid(-1.8F, -13.5F, -1.35F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(15, 32).cuboid(-1.8F, -13.25F, 0.65F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(20, 32).cuboid(-2.225F, -14.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(34, 11).cuboid(-3.025F, -12.25F, 1.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(29, 25).cuboid(-1.3F, -14.75F, -0.35F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
                .uv(29, 31).cuboid(-3.25F, -13.0F, -1.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
                .uv(34, 16).cuboid(-3.0F, -12.25F, -2.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 32).cuboid(-3.5F, -12.0F, -0.25F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
                .uv(32, 0).cuboid(2.5F, -12.0F, -0.25F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
                .uv(5, 32).cuboid(0.3F, -14.75F, -0.35F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
                .uv(34, 21).cuboid(0.8F, -13.5F, -1.35F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(34, 26).cuboid(0.8F, -13.25F, 0.65F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(34, 31).cuboid(1.225F, -14.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(34, 36).cuboid(2.025F, -12.25F, 1.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(10, 32).cuboid(2.25F, -13.0F, -1.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
                .uv(37, 0).cuboid(2.0F, -12.25F, -2.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)),
            ModelTransform.pivot(0.0F, 6.0F, 0.0F));

        root.addChild("body",
            ModelPartBuilder.create()
                .uv(0, 25).cuboid(-1.5F, -6.0F, -1.0F, 3.0F, 4.0F, 2.0F, new Dilation(0.0F))
                .uv(21, 0).cuboid(-1.5F, -5.0F, -1.0F, 3.0F, 5.0F, 2.0F, new Dilation(-0.2F)),
            ModelTransform.pivot(0.0F, 6.0F, 0.0F));

        root.addChild("right_arm",
            ModelPartBuilder.create()
                .uv(11, 25).cuboid(1.5F, -6.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F))
                .uv(21, 8).cuboid(2.75F, -5.5F, -0.75F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(26, 8).cuboid(2.75F, -3.75F, -0.25F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)),
            ModelTransform.pivot(-2.0F, 5.5F, 0.0F));

        root.addChild("left_arm",
            ModelPartBuilder.create()
                .uv(20, 25).cuboid(-3.5F, -6.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F))
                .uv(37, 5).cuboid(-3.75F, -3.75F, -0.25F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(37, 8).cuboid(-3.75F, -5.5F, -0.75F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)),
            ModelTransform.pivot(2.0F, 5.5F, 0.0F));

        root.addChild("left_wing",
            ModelPartBuilder.create()
                .uv(0, 11).cuboid(-0.5F, -5.0F, 1.0F, 0.0F, 5.0F, 8.0F, new Dilation(0.0F)),
            ModelTransform.pivot(0.5F, 5.0F, -1.0F));

        root.addChild("right_wing",
            ModelPartBuilder.create()
                .uv(17, 11).cuboid(0.5F, -5.0F, 1.0F, 0.0F, 5.0F, 8.0F, new Dilation(0.0F)),
            ModelTransform.pivot(-0.5F, 5.0F, -1.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        // Пока без анимации — просто оставляем нейтральную позу
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay,
                        float red, float green, float blue, float alpha) {
        head.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        body.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        rightArm.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        leftArm.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        rightWing.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        leftWing.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
