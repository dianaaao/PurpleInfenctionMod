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
import com.purpleinfenctionmod.client.model.UpgradedRespiratorModel;
import com.purpleinfenctionmod.entity.ModEntities;
import com.purpleinfenctionmod.item.ModItems;
import com.purpleinfenctionmod.network.InfectedLookNetworking;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.entity.EntityType;
import com.purpleinfenctionmod.client.model.HeadMushroomsModel;
import com.purpleinfenctionmod.client.feature.HeadMushroomsFeatureRenderer;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
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
        BlockRenderLayerMap.INSTANCE.putBlock(
            ModBlocks.INFECTED_GLOW_LICHEN,
            RenderLayer.getCutout()
        );
        BlockRenderLayerMap.INSTANCE.putBlock(
            ModBlocks.INFECTED_CAVE_VINES_PLANT,
            RenderLayer.getCutout()
        );
        BlockRenderLayerMap.INSTANCE.putBlock(
            ModBlocks.INFECTED_CAVE_VINES,
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

        EntityModelLayerRegistry.registerModelLayer(
            UpgradedRespiratorModel.LAYER,
            UpgradedRespiratorModel::getTexturedModelData
        );

        EntityRendererRegistry.register(ModEntities.INFECTED_ZOMBIE, InfectedZombieRenderer::new);
        EntityRendererRegistry.register(ModEntities.INFECTED_SKELETON, InfectedSkeletonRenderer::new);
        EntityRendererRegistry.register(ModEntities.INFECTED_CREEPER, InfectedCreeperRenderer::new);

        EntityRendererRegistry.register(ModEntities.ROTTING_SPORE_FUNGUS, RottingSporeFungusRenderer::new);
        EntityRendererRegistry.register(ModEntities.SPORE_CREATURE, SporeCreatureRenderer::new);

        ArmorRenderer.register(
            (matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {

                RespiratorModel model = new RespiratorModel(
                    MinecraftClient.getInstance()
                        .getEntityModelLoader()
                        .getModelPart(RespiratorModel.LAYER)
                );

                matrices.push();

                // Position and rotate the model with the player's head.
                contextModel.head.rotate(matrices);

                // Correct the model's static 90° orientation.
                matrices.multiply(
                    RotationAxis.POSITIVE_Y.rotationDegrees(-90.0F)
                );

                ArmorRenderer.renderPart(
                    matrices,
                    vertexConsumers,
                    light,
                    stack,
                    model,
                    new Identifier(
                        "purpleinfenctionmod",
                        "textures/models/armor/respirator.png"
                    )
                );
                

                matrices.pop();
            },
            ModItems.RESPIRATOR
        );
        ArmorRenderer.register(
            (matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {

                UpgradedRespiratorModel model = new UpgradedRespiratorModel(
                    MinecraftClient.getInstance()
                        .getEntityModelLoader()
                        .getModelPart(UpgradedRespiratorModel.LAYER)
                );

                matrices.push();

                contextModel.head.rotate(matrices);
                // matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90.0F));
                matrices.translate(0.0, -0.3, 0.0);

                ArmorRenderer.renderPart(
                    matrices,
                    vertexConsumers,
                    light,
                    stack,
                    model,
                    new Identifier(
                        "purpleinfenctionmod",
                        "textures/models/armor/upgraded_respirator.png"
                    )
                );

                matrices.pop();
            },
            ModItems.CRYSTAL_RESPIRATOR
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
        InfectedLookClient.initialize();

        ClientPlayNetworking.registerGlobalReceiver(
                InfectedLookNetworking.CASTLE_TARGET,
                (client, handler, buf, responseSender) -> {

                    var castlePos = buf.readBlockPos();

                    client.execute(() -> {
                        InfectedLookClient.setCastleTarget(castlePos);
                    });
                }
        );
        ClientPlayNetworking.registerGlobalReceiver(
        InfectedLookNetworking.CLEAR_CASTLE_TARGET,
        (client, handler, buf, responseSender) -> {

            client.execute(() -> {
                InfectedLookClient.clearCastleTarget();
            });
        }
);
    }
}
