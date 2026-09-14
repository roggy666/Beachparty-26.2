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
 * Plain rubber ring (blue, pink, striped share the model and differ by texture).
 */
public class RubberRingColoredModel extends ClothingModel {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BeachpartyIdentifier.identifier("rubber_ring_colored"), "main");

    private static final String HOLDER = "ring_holder";
    private final ModelPart holder;

    public RubberRingColoredModel(ModelPart root) {
        super(root);
        this.holder = root.getChild(HOLDER);
    }

    // the ring sits lower and further back while sneaking (offsets converted from the old block-space translate)
    @Override
    public void setupAnim(HumanoidRenderState state) {
        if (state.isCrouching) {
            this.holder.setPos(-1.6F, 6.4F, 11.2F);
        }
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition holder = holder(root, HOLDER, PartPose.offset(-1.6F, 9.6F, 8.0F));

        holder.addOrReplaceChild(BODY, CubeListBuilder.create().texOffs(3, 0).addBox(-5.5F, -2.5F, -15.5F, 14.0F, 4.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(39, 26).addBox(5.5F, 1.5F, -4.5F, -8.0F, -4.0F, -8.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

        return LayerDefinition.create(mesh, 64, 64);
    }
}
