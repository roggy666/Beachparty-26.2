package net.satisfy.beachparty.client.renderer.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.satisfy.beachparty.client.model.*;
import net.satisfy.beachparty.core.item.BeachpartyArmorItem;
import net.satisfy.beachparty.core.item.DyeableBeachpartyArmorItem;
import net.satisfy.beachparty.core.registry.ObjectRegistry;

import java.util.Map;

/**
 * Renders every piece of Beachparty clothing, both when worn in an armor slot and (through Accessories' armor
 * rendering binding) when worn in an accessory slot. The models copy the player's limb transforms via Fabric's
 * transform copying helper, so one model instance per item is enough.
 */
public class ClothingArmorRenderer implements ArmorRenderer {
    private final Map<Item, ClothingModel> models;

    public ClothingArmorRenderer(EntityRendererProvider.Context context) {
        EntityModelSet set = context.getModelSet();

        ClothingModel coloredRing = new RubberRingColoredModel(set.bakeLayer(RubberRingColoredModel.LAYER_LOCATION));

        this.models = Map.ofEntries(
                Map.entry(ObjectRegistry.BEACH_HAT, new BeachHatModel(set.bakeLayer(BeachHatModel.LAYER_LOCATION))),
                Map.entry(ObjectRegistry.SUNGLASSES, new SunglassesModel(set.bakeLayer(SunglassesModel.LAYER_LOCATION))),
                Map.entry(ObjectRegistry.RUBBER_RING_BLUE, coloredRing),
                Map.entry(ObjectRegistry.RUBBER_RING_PINK, coloredRing),
                Map.entry(ObjectRegistry.RUBBER_RING_STRIPPED, coloredRing),
                Map.entry(ObjectRegistry.RUBBER_RING_AXOLOTL, new RubberRingAxolotlModel(set.bakeLayer(RubberRingAxolotlModel.LAYER_LOCATION))),
                Map.entry(ObjectRegistry.RUBBER_RING_PELICAN, new RubberRingPelicanModel(set.bakeLayer(RubberRingPelicanModel.LAYER_LOCATION))),
                Map.entry(ObjectRegistry.BIKINI, new BikiniModel(set.bakeLayer(BikiniModel.LAYER_LOCATION))),
                Map.entry(ObjectRegistry.SWIM_WINGS, new SwimWingsModel(set.bakeLayer(SwimWingsModel.LAYER_LOCATION))),
                Map.entry(ObjectRegistry.TRUNKS, new TrunksModel(set.bakeLayer(TrunksModel.LAYER_LOCATION))),
                Map.entry(ObjectRegistry.CROCS, new CrocsModel(set.bakeLayer(CrocsModel.LAYER_LOCATION)))
        );
    }

    public static Item[] items() {
        return new Item[]{
                ObjectRegistry.BEACH_HAT, ObjectRegistry.SUNGLASSES,
                ObjectRegistry.RUBBER_RING_BLUE, ObjectRegistry.RUBBER_RING_PINK, ObjectRegistry.RUBBER_RING_STRIPPED,
                ObjectRegistry.RUBBER_RING_AXOLOTL, ObjectRegistry.RUBBER_RING_PELICAN,
                ObjectRegistry.BIKINI, ObjectRegistry.SWIM_WINGS, ObjectRegistry.TRUNKS, ObjectRegistry.CROCS
        };
    }

    @Override
    public void render(PoseStack poseStack, SubmitNodeCollector collector, ItemStack stack, HumanoidRenderState state, EquipmentSlot slot, int light, HumanoidModel<HumanoidRenderState> contextModel) {
        if (!(stack.getItem() instanceof BeachpartyArmorItem item) || !BeachpartyArmorItem.isVisible(stack)) return;

        ClothingModel model = this.models.get(item);
        if (model == null) return;

        int color = ARGB.opaque(item instanceof DyeableBeachpartyArmorItem dyeable ? dyeable.getColor(stack) : 0xFFFFFF);
        submit(poseStack, collector, state, light, contextModel, model, item.getTexture(), color);

        // the (non dyeable) overlay layer of the trunks
        if (item instanceof DyeableBeachpartyArmorItem dyeable && dyeable.getOverlayTexture() != null) {
            submit(poseStack, collector, state, light, contextModel, model, dyeable.getOverlayTexture(), ARGB.opaque(0xFFFFFF));
        }
    }

    private static void submit(PoseStack poseStack, SubmitNodeCollector collector, HumanoidRenderState state, int light, HumanoidModel<HumanoidRenderState> contextModel, ClothingModel model, Identifier texture, int color) {
        ArmorRenderer.submitTransformCopyingModel(contextModel, state, model, state, true, collector, poseStack,
                RenderTypes.armorCutoutNoCull(texture), light, OverlayTexture.NO_OVERLAY, color, null, state.outlineColor, null);
    }

    @Override
    public boolean shouldRenderDefaultHeadItem(LivingEntity entity, ItemStack stack) {
        return false;
    }
}
