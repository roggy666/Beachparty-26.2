package net.satisfy.beachparty.core.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.satisfy.beachparty.core.recipe.MiniFridgeRecipe;
import net.satisfy.beachparty.core.recipe.PalmBarRecipe;
import net.satisfy.beachparty.core.util.BeachpartyIdentifier;

public class RecipeTypeRegistry {
    public static final RecipeType<PalmBarRecipe> PALM_BAR_RECIPE_TYPE = registerType("palm_bar_mixing");
    public static final RecipeSerializer<PalmBarRecipe> PALM_BAR_RECIPE_SERIALIZER = registerSerializer("palm_bar_mixing", new RecipeSerializer<>(PalmBarRecipe.CODEC, PalmBarRecipe.STREAM_CODEC));
    public static final RecipeBookCategory PALM_BAR_CATEGORY = registerCategory("palm_bar_mixing");

    public static final RecipeType<MiniFridgeRecipe> MINI_FRIDGE_RECIPE_TYPE = registerType("mini_fridge_freezing");
    public static final RecipeSerializer<MiniFridgeRecipe> MINI_FRIDGE_RECIPE_SERIALIZER = registerSerializer("mini_fridge_freezing", new RecipeSerializer<>(MiniFridgeRecipe.CODEC, MiniFridgeRecipe.STREAM_CODEC));
    public static final RecipeBookCategory MINI_FRIDGE_CATEGORY = registerCategory("mini_fridge_freezing");

    private static RecipeBookCategory registerCategory(String name) {
        return Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, BeachpartyIdentifier.identifier(name), new RecipeBookCategory());
    }

    private static <T extends Recipe<?>> RecipeSerializer<T> registerSerializer(String name, RecipeSerializer<T> serializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, BeachpartyIdentifier.identifier(name), serializer);
    }

    private static <T extends Recipe<?>> RecipeType<T> registerType(String name) {
        RecipeType<T> type = new RecipeType<>() {
            @Override
            public String toString() {
                return name;
            }
        };
        return Registry.register(BuiltInRegistries.RECIPE_TYPE, BeachpartyIdentifier.identifier(name), type);
    }

    public static void init() {
    }
}
