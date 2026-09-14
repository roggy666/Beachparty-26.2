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
 * Axolotl shaped rubber ring.
 */
public class RubberRingAxolotlModel extends ClothingModel {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BeachpartyIdentifier.identifier("rubber_ring_axolotl"), "main");

    private static final String HOLDER = "ring_holder";
    private final ModelPart holder;

    public RubberRingAxolotlModel(ModelPart root) {
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

        PartDefinition body = holder.addOrReplaceChild(BODY, CubeListBuilder.create().texOffs(32, 18).addBox(-10.0F, -4.0F, 2.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(8, 0).addBox(-13.0F, -4.0F, -1.0F, 14.0F, 4.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 18).addBox(-10.0F, -5.0F, -5.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 18).mirror().addBox(-6.0F, -3.0F, 10.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(6, 8).addBox(-13.0F, -8.0F, -2.0F, 5.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(6, 8).mirror().addBox(-4.0F, -8.0F, -2.0F, 5.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.ZERO);
        body.addOrReplaceChild("right_foot_r1", CubeListBuilder.create().texOffs(5, 0).addBox(-2.0F, -5.0F, -1.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, -2.0F, 0.0F, 0.0F, -0.4363F));
        body.addOrReplaceChild("left_foot_r1", CubeListBuilder.create().texOffs(5, 0).addBox(-2.0F, -5.0F, -1.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.0F, 3.0F, -2.0F, 0.0F, 0.0F, 0.5236F));

        return LayerDefinition.create(mesh, 64, 64);
    }
}
