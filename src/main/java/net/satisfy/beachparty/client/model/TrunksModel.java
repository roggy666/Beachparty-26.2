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
 * Swim trunks: waistband on the body plus both legs, 10% larger than the player.
 */
public class TrunksModel extends ClothingModel {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BeachpartyIdentifier.identifier("trunks"), "main");

    public TrunksModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition holder = holder(root, "trunks_holder", PartPose.ZERO.scaled(1.1F));
        holder.addOrReplaceChild(BODY, CubeListBuilder.create().texOffs(0, 14).addBox(-4.0F, 9.6F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.325F)), PartPose.ZERO);
        holder.addOrReplaceChild(RIGHT_LEG, CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(-1.9F, 12.0F, 0.0F));
        holder.addOrReplaceChild(LEFT_LEG, CubeListBuilder.create().texOffs(16, 0).mirror().addBox(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offset(1.9F, 12.0F, 0.0F));

        return LayerDefinition.create(mesh, 32, 32);
    }
}
