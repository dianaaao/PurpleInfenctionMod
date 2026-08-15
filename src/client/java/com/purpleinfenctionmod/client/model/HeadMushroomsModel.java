package com.purpleinfenctionmod.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class HeadMushroomsModel extends Model {
    public static final EntityModelLayer LAYER =
            new EntityModelLayer(new Identifier("purpleinfenctionmod", "head_mushrooms"), "main");

    private final ModelPart infection;

    public HeadMushroomsModel(ModelPart root) {
        super(RenderLayer::getEntityCutoutNoCull);
        this.infection = root.getChild("infection");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData partdefinition = modelData.getRoot();

        partdefinition.addChild("infection", ModelPartBuilder.create()
                .uv(0, 15).cuboid(1.5F, -33.5F, -3.0F, 0.5F, 2.5F, 0.5F, new Dilation(0.0F))
                .uv(9, 4).cuboid(0.5F, -34.75F, 0.0F, 0.5F, 3.75F, 0.5F, new Dilation(0.0F))
                .uv(14, 13).cuboid(-1.5F, -34.0F, 2.0F, 0.5F, 3.0F, 0.5F, new Dilation(0.0F))
                .uv(9, 10).cuboid(-2.5F, -34.75F, -2.0F, 0.5F, 3.75F, 0.5F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-0.25F, -35.15F, -0.75F, 2.0F, 0.65F, 2.0F, new Dilation(0.0F))
                .uv(0, 4).cuboid(-3.25F, -34.9F, -2.75F, 2.0F, 0.65F, 2.0F, new Dilation(0.0F))
                .uv(0, 8).cuboid(-2.25F, -34.4F, 1.25F, 2.0F, 0.65F, 2.0F, new Dilation(0.0F))
                .uv(9, 0).cuboid(0.75F, -33.65F, -3.75F, 2.0F, 0.65F, 2.0F, new Dilation(0.0F))
                .uv(14, 4).cuboid(0.25F, -35.5F, -0.25F, 1.0F, 0.75F, 1.0F, new Dilation(0.0F))
                .uv(14, 10).cuboid(-2.75F, -35.225F, -2.25F, 1.0F, 0.75F, 1.0F, new Dilation(0.0F))
                .uv(0, 12).cuboid(1.25F, -34.075F, -3.25F, 1.0F, 0.75F, 1.0F, new Dilation(0.0F))
                .uv(14, 7).cuboid(-1.75F, -34.75F, 1.75F, 1.0F, 0.75F, 1.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        infection.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}