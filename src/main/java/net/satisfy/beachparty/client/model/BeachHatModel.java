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
 * Straw hat, rendered 5% larger than the head it follows.
 */
public class BeachHatModel extends ClothingModel {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BeachpartyIdentifier.identifier("beach_hat"), "main");

    public BeachHatModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition holder = holder(root, "hat_holder", PartPose.ZERO.scaled(1.05F));
        PartDefinition head = holder.addOrReplaceChild(HEAD, CubeListBuilder.create()
                .texOffs(-17, 13).addBox(-8.5F, -6.0F, -8.5F, 17.0F, 0.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.5F, -10.0F, -4.5F, 9.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.ZERO);
        head.addOrReplaceChild("kop", CubeListBuilder.create().texOffs(0, 28).addBox(8.5F, -6F, -3.5F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

        return LayerDefinition.create(mesh, 64, 64);
    }
}
