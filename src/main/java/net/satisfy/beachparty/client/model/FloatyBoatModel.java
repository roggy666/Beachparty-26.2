package net.satisfy.beachparty.client.model;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

/**
 * Geometry of the floaty (inflatable ring) boat. It uses the vanilla part names so the vanilla {@code BoatModel}
 * and {@code BoatRenderer} can render it; the hull sits 1.1 blocks lower than a normal boat, which is baked into the poses.
 */
public final class FloatyBoatModel {
    private static final float HULL_Y_OFFSET = -1.1F * 16.0F;

    private FloatyBoatModel() {
    }

    private static void addHull(PartDefinition root) {
        root.addOrReplaceChild("bottom", CubeListBuilder.create()
                        .texOffs(0, 0).mirror()
                        .addBox(-14.0F, -7.0F, -17.0F, 28.0F, 16.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(0.0F, 6.0F + HULL_Y_OFFSET, 0.0F, 1.5708F, 0.0F, 0.0F));

        root.addOrReplaceChild("front", CubeListBuilder.create()
                        .texOffs(0, 29).mirror()
                        .addBox(-8.0F, 14.0F, -3.0F, 14.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(15.0F, HULL_Y_OFFSET, 0.0F, 0.0F, 1.5708F, 0.0F));

        root.addOrReplaceChild("back", CubeListBuilder.create()
                        .texOffs(0, 19).mirror()
                        .addBox(-6.0F, 14.0F, -3.0F, 14.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(-15.0F, HULL_Y_OFFSET, 0.0F, 0.0F, -1.5708F, 0.0F));

        root.addOrReplaceChild("right", CubeListBuilder.create()
                        .texOffs(0, 39).mirror()
                        .addBox(-14.0F, 14.0F, -3.0F, 28.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(0.0F, HULL_Y_OFFSET, -9.0F, 0.0F, -3.1416F, 0.0F));

        root.addOrReplaceChild("left", CubeListBuilder.create()
                        .texOffs(0, 49).mirror()
                        .addBox(-14.0F, 14.0F, -1.0F, 28.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offset(0.0F, HULL_Y_OFFSET, 9.0F));

        root.addOrReplaceChild("left_paddle", CubeListBuilder.create().texOffs(62, 0).addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F).addBox(-1.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F), PartPose.offsetAndRotation(3.0F, -5.0F, 9.0F, 0.0F, 0.0F, 0.19634955F));
        root.addOrReplaceChild("right_paddle", CubeListBuilder.create().texOffs(62, 0).addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F).addBox(0.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F), PartPose.offsetAndRotation(3.0F, -5.0F, -9.0F, 0.0F, 3.1415927F, 0.19634955F));
    }

    public static LayerDefinition createBodyModel() {
        MeshDefinition mesh = new MeshDefinition();
        addHull(mesh.getRoot());
        return LayerDefinition.create(mesh, 128, 64);
    }

    public static LayerDefinition createChestBodyModel() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        addHull(root);
        // same chest as the vanilla chest boat
        root.addOrReplaceChild("chest_bottom", CubeListBuilder.create().texOffs(0, 76).addBox(0.0F, 0.0F, 0.0F, 12.0F, 8.0F, 12.0F), PartPose.offsetAndRotation(-2.0F, -5.0F, -6.0F, 0.0F, -1.5707964F, 0.0F));
        root.addOrReplaceChild("chest_lid", CubeListBuilder.create().texOffs(0, 59).addBox(0.0F, 0.0F, 0.0F, 12.0F, 4.0F, 12.0F), PartPose.offsetAndRotation(-2.0F, -9.0F, -6.0F, 0.0F, -1.5707964F, 0.0F));
        root.addOrReplaceChild("chest_lock", CubeListBuilder.create().texOffs(0, 59).addBox(0.0F, 0.0F, 0.0F, 2.0F, 4.0F, 1.0F), PartPose.offsetAndRotation(-1.0F, -6.0F, -1.0F, 0.0F, -1.5707964F, 0.0F));
        return LayerDefinition.create(mesh, 128, 128);
    }
}
