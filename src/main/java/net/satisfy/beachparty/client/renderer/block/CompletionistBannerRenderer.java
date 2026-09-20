package net.satisfy.beachparty.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.satisfy.beachparty.core.block.CompletionistBannerBlock;
import net.satisfy.beachparty.core.block.CompletionistWallBannerBlock;
import net.satisfy.beachparty.core.block.entity.CompletionistBannerEntity;
import net.satisfy.beachparty.core.util.BeachpartyIdentifier;
import org.jetbrains.annotations.NotNull;

public class CompletionistBannerRenderer implements BlockEntityRenderer<CompletionistBannerEntity, CompletionistBannerRenderer.State> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BeachpartyIdentifier.identifier("banner"), "main");

    public static final String FLAG = "flag";
    private static final String POLE = "pole";
    private static final String BAR = "bar";
    private static final float SCALE = 0.66f;

    private final SpriteGetter sprites;
    private final Model.Simple pole;
    private final Model.Simple bar;
    private final FlagModel flag;

    public CompletionistBannerRenderer(BlockEntityRendererProvider.Context context) {
        this.sprites = context.sprites();
        ModelPart modelPart = context.bakeLayer(LAYER_LOCATION);
        this.pole = new Model.Simple(modelPart.getChild(POLE), RenderTypes::entitySolid);
        this.bar = new Model.Simple(modelPart.getChild(BAR), RenderTypes::entitySolid);
        this.flag = new FlagModel(modelPart.getChild(FLAG));
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild(FLAG, CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, 0.0F, -1.0F, 20.0F, 40.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -44.0F, -1.0F, -0.0349F, 0.0F, 0.0F));
        partDefinition.addOrReplaceChild(POLE, CubeListBuilder.create().texOffs(44, 0).addBox(-1.0f, -30.0f, -1.0f, 2.0f, 42.0f, 2.0f), PartPose.ZERO);
        partDefinition.addOrReplaceChild(BAR, CubeListBuilder.create().texOffs(0, 42).addBox(-10.0f, -32.0f, -1.0f, 20.0f, 2.0f, 2.0f), PartPose.ZERO);
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public @NotNull State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(CompletionistBannerEntity banner, State state, float partialTick, Vec3 cameraPos, ModelFeatureRenderer.CrumblingOverlay crumbling) {
        BlockEntityRenderer.super.extractRenderState(banner, state, partialTick, cameraPos, crumbling);
        BlockState blockState = banner.getBlockState();
        state.texture = ((CompletionistBannerBlock) blockState.getBlock()).getRenderTexture();
        state.wall = blockState.getBlock() instanceof CompletionistWallBannerBlock;
        state.rotation = state.wall
                ? -blockState.getValue(CompletionistWallBannerBlock.FACING).toYRot() + 180.0f
                : (float) (-blockState.getValue(CompletionistBannerBlock.ROTATION) * 360) / 16.0f;
        long time = banner.getLevel() != null ? banner.getLevel().getGameTime() : 0L;
        BlockPos blockPos = banner.getBlockPos();
        state.phase = ((float) Math.floorMod(blockPos.getX() * 7L + blockPos.getY() * 9L + blockPos.getZ() * 13L + time, 100L) + partialTick) / 100.0f;
    }

    @Override
    public void submit(State state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        poseStack.pushPose();
        if (state.wall) {
            poseStack.translate(0.5, -0.1666666716337204, 0.5);
            poseStack.rotateDegrees(Axis.YP, state.rotation);
            poseStack.translate(0.0, -0.3125, -0.4375);
        } else {
            poseStack.translate(0.5, 0.5, 0.5);
            poseStack.rotateDegrees(Axis.YP, state.rotation);
        }
        poseStack.scale(SCALE, -SCALE, -SCALE);

        int light = state.lightCoords;
        int overlay = OverlayTexture.NO_OVERLAY;
        if (!state.wall) {
            collector.submitModel(this.pole, Unit.INSTANCE, poseStack, light, overlay, -1, Sheets.BANNER_BASE, this.sprites, 0);
        }
        collector.submitModel(this.bar, Unit.INSTANCE, poseStack, light, overlay, -1, Sheets.BANNER_BASE, this.sprites, 0);
        collector.submitModel(this.flag, state.phase, poseStack, state.texture, light, overlay, 0);
        poseStack.popPose();
    }

    public static class State extends BlockEntityRenderState {
        public Identifier texture;
        public boolean wall;
        public float rotation;
        public float phase;
    }

    /** Waving flag; the phase drives the swing so the pose is applied at render time, not at submit time. */
    static class FlagModel extends Model<Float> {
        FlagModel(ModelPart root) {
            super(root, RenderTypes::entitySolid);
        }

        @Override
        public void setupAnim(Float phase) {
            super.setupAnim(phase);
            this.root.xRot = (-0.0125f + 0.01f * Mth.cos((float) Math.PI * 2 * phase)) * (float) Math.PI;
            this.root.y = -32.0f;
        }
    }
}
