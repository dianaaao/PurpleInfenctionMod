package com.purpleinfenctionmod.client.entity;

import net.minecraft.client.render.entity.CreeperEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.util.Identifier;

public class InfectedCreeperRenderer extends CreeperEntityRenderer {
    private static final Identifier TEXTURE =
            new Identifier("purpleinfenctionmod", "textures/entity/creeper/infected_creeper.png");

    public InfectedCreeperRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(CreeperEntity entity) {
        return TEXTURE;
    }
}