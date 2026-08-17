package com.purpleinfenctionmod.client;

import com.purpleinfenctionmod.block.ModBlocks;
import com.purpleinfenctionmod.client.entity.InfectedCreeperRenderer;
import com.purpleinfenctionmod.client.entity.InfectedSkeletonRenderer;
import com.purpleinfenctionmod.client.entity.InfectedZombieRenderer;
import com.purpleinfenctionmod.client.entity.MushroomMobRenderer;
import com.purpleinfenctionmod.client.entity.RottingSporeFungusRenderer;
import com.purpleinfenctionmod.client.entity.SporeCreatureRenderer;
import com.purpleinfenctionmod.client.gui.DecontrollHudOverlay;
import com.purpleinfenctionmod.client.model.RespiratorModel;
import com.purpleinfenctionmod.entity.ModEntities;
import com.purpleinfenctionmod.item.ModItems;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.entity.EntityType;
import com.purpleinfenctionmod.client.model.HeadMushroomsModel;
import com.purpleinfenctionmod.client.feature.HeadMushroomsFeatureRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.render.entity.PlayerEntityRenderer;



public class PurpleInfenctionModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        HudRenderCallback.EVENT.register(new DecontrollHudOverlay());
        ShaderDiscontrollHandler.register();
        MouseDiscontrollHandler.register();
        BlockRenderLayerMap.INSTANCE.putBlock(
            ModBlocks.INFECTED_SMALL_MUSHROOM,
            RenderLayer.getCutout()
        );
        BlockRenderLayerMap.INSTANCE.putBlock(
            ModBlocks.FIRE_FLOWER,
            RenderLayer.getCutout()
        );
        EntityRendererRegistry.register(
            ModEntities.MUSHROOM_MOB, 
            MushroomMobRenderer::new
        );

        EntityModelLayerRegistry.registerModelLayer(
            RespiratorModel.LAYER,
            RespiratorModel::getTexturedModelData
        );

        EntityRendererRegistry.register(ModEntities.INFECTED_ZOMBIE, InfectedZombieRenderer::new);
        EntityRendererRegistry.register(ModEntities.INFECTED_SKELETON, InfectedSkeletonRenderer::new);
        EntityRendererRegistry.register(ModEntities.INFECTED_CREEPER, InfectedCreeperRenderer::new);

        EntityRendererRegistry.register(ModEntities.ROTTING_SPORE_FUNGUS, RottingSporeFungusRenderer::new);
        EntityRendererRegistry.register(ModEntities.SPORE_CREATURE, SporeCreatureRenderer::new);

        ArmorRenderer.register(
            (matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
                RespiratorModel model = new RespiratorModel(
                    MinecraftClient.getInstance().getEntityModelLoader().getModelPart(RespiratorModel.LAYER)
                );

                model.setAngles(contextModel.head.yaw, contextModel.head.pitch);

                ArmorRenderer.renderPart(
                    matrices,
                    vertexConsumers,
                    light,
                    stack,
                    model,
                    new Identifier("purpleinfenctionmod", "textures/models/armor/respirator.png")
                );
            },
            ModItems.RESPIRATOR
        );

        EntityModelLayerRegistry.registerModelLayer(HeadMushroomsModel.LAYER, HeadMushroomsModel::getTexturedModelData);

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register(
            (entityType, entityRenderer, registrationHelper, context) -> {
                if (entityRenderer instanceof PlayerEntityRenderer playerRenderer) {
                    registrationHelper.register(
                        new HeadMushroomsFeatureRenderer(playerRenderer)
                    );
                }
            }
        );
    }
}
