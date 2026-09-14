package net.satisfy.beachparty.core.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.satisfy.beachparty.core.effect.OceanWalkEffect;
import net.satisfy.beachparty.core.util.BeachpartyIdentifier;

public class MobEffectRegistry {
    public static final Holder<MobEffect> OCEAN_WALK = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, BeachpartyIdentifier.identifier("ocean_walk"), new OceanWalkEffect());

    public static void init() {
    }
}
