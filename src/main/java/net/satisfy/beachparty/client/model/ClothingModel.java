package net.satisfy.beachparty.client.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;

/**
 * Base for the wearable models. Parts that should follow the player are named like the vanilla humanoid parts
 * ({@code head}, {@code body}, {@code left_arm}, ...) so Fabric's transform copying model can pose them from the
 * player's model each frame; the old per-model scale/translate calls live on a parent "holder" part instead.
 */
public abstract class ClothingModel extends Model<HumanoidRenderState> {
    public static final String HEAD = "head";
    public static final String BODY = "body";
    public static final String LEFT_ARM = "left_arm";
    public static final String RIGHT_ARM = "right_arm";
    public static final String LEFT_LEG = "left_leg";
    public static final String RIGHT_LEG = "right_leg";

    protected ClothingModel(ModelPart root) {
        super(root, RenderTypes::armorCutoutNoCull);
    }

    @Override
    public void setupAnim(HumanoidRenderState state) {
    }

    /** An empty part whose pose (offset in pixels, scale) is applied before the copied humanoid transform of its children. */
    protected static PartDefinition holder(PartDefinition root, String name, PartPose pose) {
        return root.addOrReplaceChild(name, CubeListBuilder.create(), pose);
    }
}
