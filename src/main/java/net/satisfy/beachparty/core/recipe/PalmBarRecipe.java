package net.satisfy.beachparty.core.recipe;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.satisfy.beachparty.core.registry.RecipeTypeRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PalmBarRecipe extends BeachpartyRecipe {
    public static final int MAX_INGREDIENTS = 4;

    public PalmBarRecipe(List<Ingredient> inputs, ItemStackTemplate output) {
        super(inputs, output);
    }

    @Override
    protected int firstInputSlot() {
        return 1;
    }

    @Override
    protected int lastInputSlot() {
        return 4;
    }

    @Override
    public @NotNull RecipeSerializer<PalmBarRecipe> getSerializer() {
        return RecipeTypeRegistry.PALM_BAR_RECIPE_SERIALIZER;
    }

    @Override
    public @NotNull RecipeType<PalmBarRecipe> getType() {
        return RecipeTypeRegistry.PALM_BAR_RECIPE_TYPE;
    }

    @Override
    public @NotNull RecipeBookCategory recipeBookCategory() {
        return RecipeTypeRegistry.PALM_BAR_CATEGORY;
    }

    public static final MapCodec<PalmBarRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.listOf().fieldOf("ingredients").flatXmap(list -> {
                if (list.isEmpty()) return DataResult.error(() -> "No ingredients for PalmBar recipe");
                if (list.size() > MAX_INGREDIENTS) return DataResult.error(() -> "Too many ingredients for PalmBar recipe");
                return DataResult.success(List.copyOf(list));
            }, list -> DataResult.success(List.copyOf(list))).forGetter(PalmBarRecipe::getIngredients),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(recipe -> recipe.output)
    ).apply(instance, PalmBarRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, PalmBarRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()).map(List::copyOf, List::copyOf), PalmBarRecipe::getIngredients,
            ItemStackTemplate.STREAM_CODEC, recipe -> recipe.output,
            PalmBarRecipe::new
    );
}
