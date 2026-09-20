package net.satisfy.beachparty.core.world;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.satisfy.beachparty.core.util.BeachpartyIdentifier;

public class ConfiguredFeatures {
    public static final ResourceKey<Feature> PALM_TREE_KEY = registerKey("palm_tree");


    public static ResourceKey<Feature> registerKey(String name) {
        return ResourceKey.create(Registries.FEATURE, BeachpartyIdentifier.identifier(name));
    }
}