package net.satisfy.beachparty.client.model;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.satisfy.beachparty.core.util.BeachpartyIdentifier;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

/**
 * Pelican shaped rubber ring.
 */
public class RubberRingPelicanModel extends ClothingModel {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BeachpartyIdentifier.identifier("rubber_ring_pelican"), "main");

    private static final String HOLDER = "ring_holder";
    private final ModelPart holder;

    public RubberRingPelicanModel(ModelPart root) {
        super(root);
        this.holder = root.getChild(HOLDER);
    }

    // the ring sits lower and further back while sneaking (offsets converted from the old block-space translate)
    @Override
    public void setupAnim(HumanoidRenderState state) {
        if (state.isCrouching) {
            this.holder.setPos(6.4F, 14.4F, 0.0F);
        }
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition holder = holder(root, HOLDER, PartPose.offset(6.4F, 11.2F, -6.4F));

        holder.addOrReplaceChild(BODY, CubeListBuilder.create().texOffs(31, 18).addBox(-10.0F, -4.0F, 2.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(7, 0).addBox(-13.0F, -4.0F, -1.0F, 14.0F, 4.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-8.0F, -8.0F, -5.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(1, 18).addBox(-8.0F, -6.0F, -13.0F, 4.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

        return LayerDefinition.create(mesh, 64, 64);
    }
}
