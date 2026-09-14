package net.satisfy.beachparty.core.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.satisfy.beachparty.core.util.BeachpartyIdentifier;
import net.satisfy.beachparty.core.world.placers.CrookedTrunkPlacer;
import net.satisfy.beachparty.core.world.placers.PalmFoliagePlacer;

public class PlacerTypeRegistry {
    // constructors are private in vanilla, opened through beachparty.accesswidener
    public static final FoliagePlacerType<PalmFoliagePlacer> PALM_FOLIAGE_PLACER = Registry.register(BuiltInRegistries.FOLIAGE_PLACER_TYPE, BeachpartyIdentifier.identifier("palm_foliage_placer"), new FoliagePlacerType<>(PalmFoliagePlacer.CODEC));
    public static final TrunkPlacerType<CrookedTrunkPlacer> CROOKED_TRUNK_PLACER = Registry.register(BuiltInRegistries.TRUNK_PLACER_TYPE, BeachpartyIdentifier.identifier("crooked_trunk_placer"), new TrunkPlacerType<>(CrookedTrunkPlacer.CODEC));

    public static void init() {
    }
}
