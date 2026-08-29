package com.purpleinfenctionmod.client.entity;

import com.purpleinfenctionmod.client.model.InfectedVexModel;
import com.purpleinfenctionmod.entity.InfectedVexEntity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class InfectedVexRenderer extends MobEntityRenderer<InfectedVexEntity, InfectedVexModel<InfectedVexEntity>> {

    private static final Identifier TEXTURE =
            new Identifier("purpleinfenctionmod", "textures/entity/crystal_vex_texture.png");

    public InfectedVexRenderer(EntityRendererFactory.Context context) {
        super(context, new InfectedVexModel<>(context.getPart(InfectedVexModel.LAYER)), 0.3F);
    }

    @Override
    public Identifier getTexture(InfectedVexEntity entity) {
        return TEXTURE;
    }

}
