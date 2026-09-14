package net.satisfy.beachparty.core.registry;

import net.fabricmc.fabric.api.registry.CompostableRegistry;

public class CompostablesRegistry {
    public static void init() {
        CompostableRegistry.INSTANCE.add(ObjectRegistry.PALM_SPROUT, 0.6F);
        CompostableRegistry.INSTANCE.add(ObjectRegistry.PALM_LEAVES, 0.6F);
        CompostableRegistry.INSTANCE.add(ObjectRegistry.COCONUT, 0.3F);
        CompostableRegistry.INSTANCE.add(ObjectRegistry.COCONUT_OPEN, 0.3F);
        CompostableRegistry.INSTANCE.add(ObjectRegistry.COOKED_MUSSEL_MEAT, 0.3F);
        CompostableRegistry.INSTANCE.add(ObjectRegistry.RAW_MUSSEL_MEAT, 0.3F);
        CompostableRegistry.INSTANCE.add(ObjectRegistry.COCONUT_COCKTAIL, 0.3F);
        CompostableRegistry.INSTANCE.add(ObjectRegistry.SWEETBERRIES_COCKTAIL, 0.3F);
        CompostableRegistry.INSTANCE.add(ObjectRegistry.COCOA_COCKTAIL, 0.3F);
        CompostableRegistry.INSTANCE.add(ObjectRegistry.PUMPKIN_COCKTAIL, 0.3F);
        CompostableRegistry.INSTANCE.add(ObjectRegistry.MELON_COCKTAIL, 0.3F);
        CompostableRegistry.INSTANCE.add(ObjectRegistry.HONEY_COCKTAIL, 0.3F);
    }
}
