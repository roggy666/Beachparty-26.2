package net.satisfy.beachparty.core.recipe;

import com.mojang.serialization.Codec;
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

public class MiniFridgeRecipe extends BeachpartyRecipe {
    private final int craftingTime;

    public MiniFridgeRecipe(List<Ingredient> inputs, ItemStackTemplate output, int craftingTime) {
        super(inputs, output);
        this.craftingTime = craftingTime;
    }

    public int getCraftingTime() {
        return craftingTime;
    }

    @Override
    protected int firstInputSlot() {
        return 1;
    }

    @Override
    protected int lastInputSlot() {
        return 1;
    }

    @Override
    public @NotNull RecipeSerializer<MiniFridgeRecipe> getSerializer() {
        return RecipeTypeRegistry.MINI_FRIDGE_RECIPE_SERIALIZER;
    }

    @Override
    public @NotNull RecipeType<MiniFridgeRecipe> getType() {
        return RecipeTypeRegistry.MINI_FRIDGE_RECIPE_TYPE;
    }

    @Override
    public @NotNull RecipeBookCategory recipeBookCategory() {
        return RecipeTypeRegistry.MINI_FRIDGE_CATEGORY;
    }

    public static final MapCodec<MiniFridgeRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.listOf().fieldOf("ingredients").flatXmap(list -> {
                if (list.isEmpty()) return DataResult.error(() -> "No ingredients for MiniFridge recipe");
                if (list.size() > 1) return DataResult.error(() -> "Too many ingredients for MiniFridge recipe");
                return DataResult.success(List.copyOf(list));
            }, list -> DataResult.success(List.copyOf(list))).forGetter(MiniFridgeRecipe::getIngredients),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(recipe -> recipe.output),
            Codec.INT.fieldOf("crafting_time").forGetter(MiniFridgeRecipe::getCraftingTime)
    ).apply(instance, MiniFridgeRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MiniFridgeRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()).map(List::copyOf, List::copyOf), MiniFridgeRecipe::getIngredients,
            ItemStackTemplate.STREAM_CODEC, recipe -> recipe.output,
            ByteBufCodecs.VAR_INT, MiniFridgeRecipe::getCraftingTime,
            MiniFridgeRecipe::new
    );
}
