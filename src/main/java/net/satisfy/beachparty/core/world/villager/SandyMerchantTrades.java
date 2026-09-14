package net.satisfy.beachparty.core.world.villager;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PoiHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.trading.TradeSet;
import net.satisfy.beachparty.core.registry.ObjectRegistry;
import net.satisfy.beachparty.core.util.BeachpartyIdentifier;

import java.util.function.Predicate;

/**
 * The Sandy Merchant villager. The offers themselves are data driven now and live in
 * data/beachparty/villager_trade, tags/villager_trade and trade_set (one trade set per level).
 */
public class SandyMerchantTrades {
    private static final Identifier POI_ID = BeachpartyIdentifier.identifier("sandymerchant_poi");
    private static final ResourceKey<PoiType> POI_KEY = ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, POI_ID);
    private static final ResourceKey<VillagerProfession> PROFESSION_KEY = ResourceKey.create(Registries.VILLAGER_PROFESSION, BeachpartyIdentifier.identifier("sandymerchant"));

    public static final PoiType SANDYMERCHANT_POI = PoiHelper.register(POI_ID, 1, 12, ObjectRegistry.PALM_BAR);
    public static final VillagerProfession SANDYMERCHANT = Registry.register(BuiltInRegistries.VILLAGER_PROFESSION, PROFESSION_KEY, new VillagerProfession(
            Component.translatable("entity.minecraft.villager.beachparty.sandymerchant"),
            isJobSite(),
            isJobSite(),
            ImmutableSet.of(),
            ImmutableSet.of(),
            SoundEvents.VILLAGER_WORK_FISHERMAN,
            tradeSets()
    ));

    private static Predicate<Holder<PoiType>> isJobSite() {
        return holder -> holder.is(POI_KEY);
    }

    private static Int2ObjectMap<ResourceKey<TradeSet>> tradeSets() {
        Int2ObjectMap<ResourceKey<TradeSet>> sets = new Int2ObjectOpenHashMap<>();
        for (int level = 1; level <= 5; level++) {
            sets.put(level, ResourceKey.create(Registries.TRADE_SET, BeachpartyIdentifier.identifier("sandymerchant/level_" + level)));
        }
        return sets;
    }

    public static void init() {
    }
}
