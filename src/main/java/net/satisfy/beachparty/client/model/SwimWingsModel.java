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

/**
 * Inflatable arm bands. Each arm gets its own offset (in pixels, converted from the old block-space translate) and scale.
 */
public class SwimWingsModel extends ClothingModel {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BeachpartyIdentifier.identifier("swim_wings"), "main");

    public SwimWingsModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition rightHolder = holder(root, "right_wing_holder", PartPose.offset(6.0F, 0.0F, 0.0F).scaled(1.1F));
        rightHolder.addOrReplaceChild(RIGHT_ARM, CubeListBuilder.create().texOffs(0, 3).addBox(-8.0F, 1.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.ZERO);

        PartDefinition leftHolder = holder(root, "left_wing_holder", PartPose.offset(7.2F, -0.8F, 0.0F).scaled(1.1F));
        leftHolder.addOrReplaceChild(LEFT_ARM, CubeListBuilder.create().texOffs(0, 3).mirror().addBox(-8.0F, 1.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.ZERO);

        return LayerDefinition.create(mesh, 16, 16);
    }
}
