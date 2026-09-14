package net.satisfy.beachparty.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.minecraft.resources.Identifier;
import net.satisfy.beachparty.client.model.BeachBallModel;
import net.satisfy.beachparty.core.entity.BeachBallEntity;
import net.satisfy.beachparty.core.util.BeachpartyIdentifier;
import org.jetbrains.annotations.NotNull;

public class BeachBallRenderer extends MobRenderer<BeachBallEntity, LivingEntityRenderState, BeachBallModel> {
    private static final Identifier DEFAULT_TEXTURE = BeachpartyIdentifier.identifier("textures/entity/beach_ball.png");
    // the skin depends on the entity's custom name, which the generic living state doesn't carry
    private static final RenderStateDataKey<Identifier> TEXTURE = RenderStateDataKey.create();

    public BeachBallRenderer(EntityRendererProvider.Context context) {
        super(context, new BeachBallModel(context.bakeLayer(BeachBallModel.LAYER_LOCATION)), 0.2f);
    }

    @Override
    public @NotNull LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public void extractRenderState(BeachBallEntity entity, LivingEntityRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.setData(TEXTURE, textureFor(entity));
    }

    @Override
    public @NotNull Identifier getTextureLocation(LivingEntityRenderState state) {
        return state.getDataOrDefault(TEXTURE, DEFAULT_TEXTURE);
    }

    private static Identifier textureFor(BeachBallEntity entity) {
        String name = entity.getCustomName() != null ? entity.getCustomName().getString() : "";

        return switch (name) {
            case "MissLilitu" -> BeachpartyIdentifier.identifier("textures/entity/beach_ball_misslilitu.png");
            case "CR-055" -> BeachpartyIdentifier.identifier("textures/entity/beach_ball_cr055.png");
            case "Jason" -> BeachpartyIdentifier.identifier("textures/entity/beach_ball_jason13.png");
            case "Satisfy" -> BeachpartyIdentifier.identifier("textures/entity/beach_ball_satisfy.png");
            case "Nekonesse" -> BeachpartyIdentifier.identifier("textures/entity/beach_ball_nekonesse.png");
            case "MarbledNull" -> BeachpartyIdentifier.identifier("textures/entity/beach_ball_marblednull.png");
            case "Steve" -> BeachpartyIdentifier.identifier("textures/entity/beach_ball_steve.png");
            case "Pixar" -> BeachpartyIdentifier.identifier("textures/entity/beach_ball_pixar.png");
            case "Dirt" -> BeachpartyIdentifier.identifier("textures/entity/beach_ball_dirt.png");
            default -> DEFAULT_TEXTURE;
        };
    }
}
