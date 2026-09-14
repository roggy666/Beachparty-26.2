package net.satisfy.beachparty.client;

import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.StandingSignRenderer;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.item.Item;
import net.satisfy.beachparty.client.gui.MiniFridgeGui;
import net.satisfy.beachparty.client.gui.PalmBarGui;
import net.satisfy.beachparty.client.model.*;
import net.satisfy.beachparty.client.renderer.armor.ClothingArmorRenderer;
import net.satisfy.beachparty.client.renderer.block.CompletionistBannerRenderer;
import net.satisfy.beachparty.client.renderer.entity.BeachBallRenderer;
import net.satisfy.beachparty.client.renderer.entity.ChairRenderer;
import net.satisfy.beachparty.core.compat.accessories.BeachpartyAccessories;
import net.satisfy.beachparty.core.effect.OceanWalkEffect;
import net.satisfy.beachparty.core.registry.EntityTypeRegistry;
import net.satisfy.beachparty.core.registry.ObjectRegistry;
import net.satisfy.beachparty.core.registry.ScreenHandlerTypeRegistry;
import net.satisfy.beachparty.core.util.BeachpartyIdentifier;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class BeachpartyClient implements ClientModInitializer {
    // BoatRenderer derives the texture from the layer path: textures/entity/boat/palm.png etc.
    public static final ModelLayerLocation PALM_BOAT_LAYER = boatLayer("boat/palm");
    public static final ModelLayerLocation PALM_CHEST_BOAT_LAYER = boatLayer("chest_boat/palm");
    public static final ModelLayerLocation FLOATY_BOAT_LAYER = boatLayer("boat/floaty");
    public static final ModelLayerLocation FLOATY_CHEST_BOAT_LAYER = boatLayer("chest_boat/floaty");

    @Override
    public void onInitializeClient() {
        registerModelLayers();
        registerEntityRenderers();

        MenuScreens.register(ScreenHandlerTypeRegistry.PALM_BAR_GUI_HANDLER, PalmBarGui::new);
        MenuScreens.register(ScreenHandlerTypeRegistry.MINI_FRIDGE_GUI_HANDLER, MiniFridgeGui::new);

        // the vanilla sign renderers are typed to SignBlockEntity, so they need a raw hop to attach to our subclass
        registerSignRenderer(EntityTypeRegistry.BEACHPARTY_SIGN, StandingSignRenderer::new);
        registerSignRenderer(EntityTypeRegistry.BEACHPARTY_HANGING_SIGN, HangingSignRenderer::new);
        BlockEntityRendererRegistry.register(EntityTypeRegistry.BEACHPARTY_BANNER, CompletionistBannerRenderer::new);

        // one renderer draws the clothing both as armor and, via Accessories, in accessory slots
        Item[] clothing = ClothingArmorRenderer.items();
        ArmorRenderer.register(ClothingArmorRenderer::new, clothing);
        for (Item item : clothing) {
            AccessoriesRendererRegistry.registerArmorRendering(item);
        }

        // ocean walk of the crocs is movement, so it runs on the client for the local player
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && BeachpartyAccessories.isEquipped(client.player, ObjectRegistry.CROCS)) {
                OceanWalkEffect.tick(client.player);
            }
        });
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static <E extends BlockEntity> void registerSignRenderer(BlockEntityType<E> type, Function<BlockEntityRendererProvider.Context, ? extends BlockEntityRenderer<?, ?>> factory) {
        BlockEntityRendererRegistry.register(type, (BlockEntityRendererProvider) factory::apply);
    }

    private static void registerEntityRenderers() {
        EntityRendererRegistry.register(EntityTypeRegistry.BEACH_BALL, BeachBallRenderer::new);
        EntityRendererRegistry.register(EntityTypeRegistry.CHAIR, ChairRenderer::new);
        EntityRendererRegistry.register(EntityTypeRegistry.COCONUT, ThrownItemRenderer::new);
        EntityRendererRegistry.register(EntityTypeRegistry.PALM_BOAT, context -> new BoatRenderer(context, PALM_BOAT_LAYER));
        EntityRendererRegistry.register(EntityTypeRegistry.PALM_CHEST_BOAT, context -> new BoatRenderer(context, PALM_CHEST_BOAT_LAYER));
        EntityRendererRegistry.register(EntityTypeRegistry.FLOATY_BOAT, context -> new BoatRenderer(context, FLOATY_BOAT_LAYER));
        EntityRendererRegistry.register(EntityTypeRegistry.FLOATY_CHEST_BOAT, context -> new BoatRenderer(context, FLOATY_CHEST_BOAT_LAYER));
    }

    private static void registerModelLayers() {
        ModelLayerRegistry.registerModelLayer(BeachHatModel.LAYER_LOCATION, BeachHatModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(SunglassesModel.LAYER_LOCATION, SunglassesModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(RubberRingColoredModel.LAYER_LOCATION, RubberRingColoredModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(RubberRingAxolotlModel.LAYER_LOCATION, RubberRingAxolotlModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(RubberRingPelicanModel.LAYER_LOCATION, RubberRingPelicanModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(BikiniModel.LAYER_LOCATION, BikiniModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(SwimWingsModel.LAYER_LOCATION, SwimWingsModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(TrunksModel.LAYER_LOCATION, TrunksModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(CrocsModel.LAYER_LOCATION, CrocsModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(BeachBallModel.LAYER_LOCATION, BeachBallModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(CompletionistBannerRenderer.LAYER_LOCATION, CompletionistBannerRenderer::createBodyLayer);

        ModelLayerRegistry.registerModelLayer(PALM_BOAT_LAYER, BoatModel::createBoatModel);
        ModelLayerRegistry.registerModelLayer(PALM_CHEST_BOAT_LAYER, BoatModel::createChestBoatModel);
        ModelLayerRegistry.registerModelLayer(FLOATY_BOAT_LAYER, FloatyBoatModel::createBodyModel);
        ModelLayerRegistry.registerModelLayer(FLOATY_CHEST_BOAT_LAYER, FloatyBoatModel::createChestBodyModel);
    }

    private static ModelLayerLocation boatLayer(String path) {
        return new ModelLayerLocation(BeachpartyIdentifier.identifier(path), "main");
    }
}
