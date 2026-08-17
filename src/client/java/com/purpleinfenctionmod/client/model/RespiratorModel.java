package com.purpleinfenctionmod.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class RespiratorModel extends Model {

	public static final EntityModelLayer LAYER =
		new EntityModelLayer(
			new Identifier("purpleinfenctionmod", "respirator"),
			"main"
		);

	private final ModelPart head;

	public RespiratorModel(ModelPart root) {
		super(RenderLayer::getEntityCutoutNoCull);

		this.head = root.getChild("head");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData partdefinition = modelData.getRoot();

		ModelPartData head = partdefinition.addChild(
			"head",
			ModelPartBuilder.create()
				.uv(0, 10)
				.cuboid(
					-7.0F,
					-3.225F,
					-2.0F,
					4.0F,
					4.0F,
					4.0F,
					new Dilation(0.0F)
				),
			ModelTransform.pivot(0.0F, 0.0F, 0.0F)
		);

		ModelPartData group2 = head.addChild(
			"group2",
			ModelPartBuilder.create(),
			ModelTransform.pivot(0.0F, 0.0F, 0.0F)
		);

		group2.addChild(
			"cube_r1",
			ModelPartBuilder.create()
				.uv(20, 5)
				.cuboid(
					-2.0F, -2.0F, 0.0F,
					2.0F, 2.0F, 2.0F,
					new Dilation(0.0F)
				),
			ModelTransform.of(
				0.05F, -1.5F, -4.85F,
				0.0F, 0.0F, -0.3927F
			)
		);

		group2.addChild(
			"cube_r2",
			ModelPartBuilder.create()
				.uv(16, 14)
				.cuboid(
					-3.0F, -3.0F, 0.0F,
					3.0F, 3.0F, 3.0F,
					new Dilation(0.0F)
				),
			ModelTransform.of(
				-1.25F, -0.025F, -5.0F,
				0.0F, 0.0F, -0.3927F
			)
		);

		ModelPartData group3 = head.addChild(
			"group3",
			ModelPartBuilder.create()
				.uv(20, 0)
				.cuboid(
					-3.0F, -2.225F, -2.0F,
					1.0F, 1.0F, 4.0F,
					new Dilation(0.0F)
				)
				.uv(0, 0)
				.cuboid(
					4.0F, -2.225F, -4.025F,
					1.0F, 1.0F, 8.0F,
					new Dilation(0.0F)
				)
				.uv(17, 10)
				.cuboid(
					-2.0F, -2.225F, -4.725F,
					7.0F, 1.0F, 1.0F,
					new Dilation(0.0F)
				)
				.uv(17, 12)
				.cuboid(
					-2.0F, -2.225F, 3.75F,
					7.0F, 1.0F, 1.0F,
					new Dilation(0.0F)
				),
			ModelTransform.pivot(0.0F, 0.0F, 0.0F)
		);

		group3.addChild(
			"cube_r3",
			ModelPartBuilder.create()
				.uv(12, 20)
				.cuboid(
					-2.0F, -2.0F, 4.875F,
					2.0F, 2.0F, 2.0F,
					new Dilation(0.0F)
				),
			ModelTransform.of(
				0.05F, -1.5F, -2.0F,
				0.0F, 0.0F, -0.3927F
			)
		);

		group3.addChild(
			"cube_r4",
			ModelPartBuilder.create()
				.uv(0, 18)
				.cuboid(
					-3.0F, -3.0F, 0.0F,
					3.0F, 3.0F, 3.0F,
					new Dilation(0.0F)
				),
			ModelTransform.of(
				-1.25F, -0.025F, 2.0F,
				0.0F, 0.0F, -0.3927F
			)
		);

		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void render(
		MatrixStack matrices,
		VertexConsumer vertices,
		int light,
		int overlay,
		float red,
		float green,
		float blue,
		float alpha
	) {
		head.render(
			matrices,
			vertices,
			light,
			overlay,
			red,
			green,
			blue,
			alpha
		);
	}
}