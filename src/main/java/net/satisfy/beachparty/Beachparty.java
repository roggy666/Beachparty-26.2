package net.satisfy.beachparty;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.satisfy.beachparty.core.event.PalmStripping;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.satisfy.beachparty.core.compat.accessories.BeachpartyAccessories;
import net.satisfy.beachparty.core.config.BeachpartyConfig;
import net.satisfy.beachparty.core.event.CommonEvents;
import net.satisfy.beachparty.core.registry.*;
import net.satisfy.beachparty.core.util.BeachpartyIdentifier;
import net.satisfy.beachparty.core.world.PlacedFeatures;
import net.satisfy.beachparty.core.world.villager.SandyMerchantTrades;

import java.util.function.Predicate;

public class Beachparty implements ModInitializer {
    public static final String MOD_ID = "beachparty";

    @Override
    public void onInitialize() {
        AutoConfig.register(BeachpartyConfig.class, GsonConfigSerializer::new);

        ObjectRegistry.init();
        EntityTypeRegistry.init();
        TabRegistry.init();
        PlacerTypeRegistry.init();
        MobEffectRegistry.init();
        SoundEventRegistry.init();
        ScreenHandlerTypeRegistry.init();
        RecipeTypeRegistry.init();
        SandyMerchantTrades.init();

        // composting is an item component and stripping a data-driven axe transformer in 26.3, see PalmStripping
        PalmStripping.init();

        CommonEvents.init();
        BeachpartyAccessories.init();
        addBiomeModifications();
    }

    private static void addBiomeModifications() {
        BeachpartyConfig config = BeachpartyConfig.get();

        BiomeModification world = BiomeModifications.create(BeachpartyIdentifier.identifier("world_features"));
        Predicate<BiomeSelectionContext> beachBiomes = BiomeSelectors.tag(TagKey.create(Registries.BIOME, BeachpartyIdentifier.identifier("beach")));

        if (config.spawnSeashells) {
            world.add(ModificationPhase.ADDITIONS, beachBiomes, ctx ->
                    ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.SEASHELLS_KEY));
        }

        if (config.spawnPalms) {
            world.add(ModificationPhase.ADDITIONS, beachBiomes, ctx ->
                    ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.PALM_TREE_KEY));
        }

        if (config.spawnSandwaves) {
            world.add(ModificationPhase.ADDITIONS, beachBiomes, ctx ->
                    ctx.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PlacedFeatures.SANDWAVES_KEY));
        }
    }
}
