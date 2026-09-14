package net.satisfy.beachparty.core.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.satisfy.beachparty.core.util.BeachpartyIdentifier;

@SuppressWarnings("unused")
public class TabRegistry {
    public static final ResourceKey<CreativeModeTab> BEACHPARTY_TAB_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, BeachpartyIdentifier.identifier("beachparty"));

    public static final CreativeModeTab BEACHPARTY_TAB = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, BEACHPARTY_TAB_KEY, CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
            .icon(() -> new ItemStack(ObjectRegistry.COCONUT_COCKTAIL))
            .title(Component.translatable("creativetab.beachparty.tab").withStyle(style -> style.withColor(TextColor.fromRgb(0xc0924a))))
            .displayItems((parameters, out) -> {
                out.accept(ObjectRegistry.PALM_LOG);
                out.accept(ObjectRegistry.PALM_WOOD);
                out.accept(ObjectRegistry.STRIPPED_PALM_LOG);
                out.accept(ObjectRegistry.STRIPPED_PALM_WOOD);
                out.accept(ObjectRegistry.PALM_FLOORBOARD);
                out.accept(ObjectRegistry.PALM_PLANKS);
                out.accept(ObjectRegistry.PALM_STAIRS);
                out.accept(ObjectRegistry.PALM_SLAB);
                out.accept(ObjectRegistry.PALM_FENCE);
                out.accept(ObjectRegistry.PALM_FENCE_GATE);
                out.accept(ObjectRegistry.PALM_DOOR);
                out.accept(ObjectRegistry.PALM_TRAPDOOR);
                out.accept(ObjectRegistry.PALM_PRESSURE_PLATE);
                out.accept(ObjectRegistry.PALM_BUTTON);
                out.accept(ObjectRegistry.PALM_GLASS_PANE);
                out.accept(ObjectRegistry.PALM_GLASS);
                out.accept(ObjectRegistry.PALM_CABINET);
                out.accept(ObjectRegistry.PALM_BAR);
                out.accept(ObjectRegistry.PALM_BAR_STOOL);
                out.accept(ObjectRegistry.BEACH_CHAIR);
                out.accept(ObjectRegistry.PALM_CHAIR);
                out.accept(ObjectRegistry.PALM_TABLE);
                out.accept(ObjectRegistry.HOODED_BEACH_CHAIR);
                out.accept(ObjectRegistry.BEACH_SUN_LOUNGER);
                out.accept(ObjectRegistry.THATCH);
                out.accept(ObjectRegistry.THATCH_STAIRS);
                out.accept(ObjectRegistry.THATCH_SLAB);
                out.accept(ObjectRegistry.WET_HAY_BLOCK);
                out.accept(ObjectRegistry.SANDWAVES);
                out.accept(ObjectRegistry.PALM_LEAVES);
                out.accept(ObjectRegistry.PALM_TORCH_ITEM);
                out.accept(ObjectRegistry.TALL_PALM_TORCH);
                out.accept(ObjectRegistry.MINI_FRIDGE);
                out.accept(ObjectRegistry.PALM_SPROUT);
                out.accept(ObjectRegistry.PALM_SIGN_ITEM);
                out.accept(ObjectRegistry.PALM_HANGING_SIGN_ITEM);
                out.accept(ObjectRegistry.PALM_BOAT);
                out.accept(ObjectRegistry.PALM_CHEST_BOAT);
                out.accept(ObjectRegistry.FLOATY_BOAT);
                out.accept(ObjectRegistry.RADIO);
                out.accept(ObjectRegistry.BEACH_GOAL);
                out.accept(ObjectRegistry.BEACH_TOWEL);
                out.accept(ObjectRegistry.BEACH_PARASOL);
                out.accept(ObjectRegistry.OVERGROWN_DISC);
                out.accept(ObjectRegistry.MUSIC_DISC_BEACHPARTY);
                out.accept(ObjectRegistry.MUSIC_DISC_CARIBBEAN_BEACH);
                out.accept(ObjectRegistry.MUSIC_DISC_PRIDELANDS);
                out.accept(ObjectRegistry.MUSIC_DISC_VOCALISTA);
                out.accept(ObjectRegistry.MUSIC_DISC_WILD_VEINS);
                out.accept(ObjectRegistry.MESSAGE_IN_A_BOTTLE_ITEM);
                out.accept(ObjectRegistry.SEASHELL);
                out.accept(ObjectRegistry.SAND_BUCKET_FILLED);
                out.accept(ObjectRegistry.SAND_BUCKET_EMPTY);
                out.accept(ObjectRegistry.COCONUT);
                out.accept(ObjectRegistry.COCONUT_OPEN);
                out.accept(ObjectRegistry.COCONUT_COCKTAIL);
                out.accept(ObjectRegistry.SWEETBERRIES_COCKTAIL);
                out.accept(ObjectRegistry.COCOA_COCKTAIL);
                out.accept(ObjectRegistry.PUMPKIN_COCKTAIL);
                out.accept(ObjectRegistry.MELON_COCKTAIL);
                out.accept(ObjectRegistry.HONEY_COCKTAIL);
                out.accept(ObjectRegistry.RAW_MUSSEL_MEAT);
                out.accept(ObjectRegistry.COOKED_MUSSEL_MEAT);
                out.accept(ObjectRegistry.BEACH_BALL);
                out.accept(ObjectRegistry.BEACH_HAT);
                out.accept(ObjectRegistry.SUNGLASSES);
                out.accept(ObjectRegistry.TRUNKS);
                out.accept(ObjectRegistry.BIKINI);
                out.accept(ObjectRegistry.CROCS);
                out.accept(ObjectRegistry.SWIM_WINGS);
                out.accept(ObjectRegistry.RUBBER_RING_BLUE);
                out.accept(ObjectRegistry.RUBBER_RING_PINK);
                out.accept(ObjectRegistry.RUBBER_RING_STRIPPED);
                out.accept(ObjectRegistry.RUBBER_RING_AXOLOTL);
                out.accept(ObjectRegistry.RUBBER_RING_PELICAN);
                out.accept(ObjectRegistry.POOL_NOODLE);
                out.accept(ObjectRegistry.BEACHPARTY_BANNER);

            })
            .build());

    public static void init() {
    }
}
