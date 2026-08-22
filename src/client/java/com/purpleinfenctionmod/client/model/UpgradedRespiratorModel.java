package com.purpleinfenctionmod.client.model;

// import net.minecraft.client.model.Dilation;
// import net.minecraft.client.model.ModelData;
// import net.minecraft.client.model.ModelPart;
// import net.minecraft.client.model.ModelPartBuilder;
// import net.minecraft.client.model.ModelPartData;
// import net.minecraft.client.model.ModelTransform;
// import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import net.minecraft.client.model.*;

public class UpgradedRespiratorModel extends Model {

    public static final EntityModelLayer LAYER =
        new EntityModelLayer(new Identifier("purpleinfenctionmod", "crystal_respirator"), "main");

    private final ModelPart head;

    public UpgradedRespiratorModel(ModelPart root) {
        super(RenderLayer::getEntityCutoutNoCull);
        this.head = root.getChild("head");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData partdefinition = modelData.getRoot();

        ModelPartData head = partdefinition.addChild("head",
            ModelPartBuilder.create()
                .uv(0, 0).cuboid(-2.0F, 3.225F, -7.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
                .uv(9, 27).cuboid(-2.0F, 3.25F, -2.5F, 4.0F, 1.0F, 0.075F, new Dilation(0.0F))
                .uv(17, 12).cuboid(-4.0F, 0.25F, -2.5F, 8.0F, 3.0F, 0.075F, new Dilation(0.0F))
                .uv(13, 23).cuboid(-4.0F, 3.175F, -2.575F, 8.0F, 0.075F, 0.075F, new Dilation(0.0F))
                .uv(13, 23).cuboid(-4.0F, 0.25F, -2.575F, 8.0F, 0.075F, 0.075F, new Dilation(0.0F))
                .uv(13, 18).cuboid(4.0F, 0.325F, -2.575F, 0.075F, 2.85F, 0.075F, new Dilation(0.0F))
                .uv(13, 18).cuboid(-4.075F, 0.325F, -2.575F, 0.075F, 2.85F, 0.075F, new Dilation(0.0F)),
            ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData group2 = head.addChild("group2",
            ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        group2.addChild("cube_r1",
            ModelPartBuilder.create()
                .uv(0, 18).cuboid(-13.0F, -5.35F, 3.75F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(17, 16).cuboid(-6.0F, -5.35F, 3.75F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)),
            ModelTransform.of(8.0F, 11.175F, -6.4F, 0.3927F, 0.0F, 0.0F));

        group2.addChild("cube_r2",
            ModelPartBuilder.create()
                .uv(0, 25).cuboid(-12.75F, -4.125F, 5.75F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(24, 24).cuboid(-5.25F, -4.125F, 5.75F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)),
            ModelTransform.of(8.0F, 9.95F, -5.9F, 0.3927F, 0.0F, 0.0F));

        ModelPartData group3 = head.addChild("group3",
            ModelPartBuilder.create()
                .uv(13, 24).cuboid(-2.0F, 4.225F, -3.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(17, 9).cuboid(-4.0F, 4.225F, 3.975F, 8.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 9).cuboid(3.0F, 4.225F, -2.25F, 1.0F, 1.0F, 7.0F, new Dilation(0.0F))
                .uv(17, 0).cuboid(-4.0F, 4.225F, -2.25F, 1.0F, 1.0F, 7.0F, new Dilation(0.0F)),
            ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay,
                        float red, float green, float blue, float alpha) {
        head.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
