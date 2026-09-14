package net.satisfy.beachparty.core.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import java.util.List;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.satisfy.beachparty.core.util.BeachpartyUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Shared shape of the palm bar and mini fridge recipes: an unordered set of ingredients that has to be found in a
 * slot range of the block's inventory, producing one result stack.
 */
public abstract class BeachpartyRecipe implements Recipe<RecipeInput> {
    protected final List<Ingredient> inputs;
    // a template rather than a stack: item components aren't bound yet while recipes are parsed
    protected final ItemStackTemplate output;
    private PlacementInfo placementInfo;

    protected BeachpartyRecipe(List<Ingredient> inputs, ItemStackTemplate output) {
        this.inputs = inputs;
        this.output = output;
    }

    /** First and last inventory slot (inclusive) the ingredients are looked up in. */
    protected abstract int firstInputSlot();

    protected abstract int lastInputSlot();

    @Override
    public boolean matches(RecipeInput input, Level level) {
        return BeachpartyUtil.matchesRecipe(input, inputs, firstInputSlot(), lastInputSlot());
    }

    @Override
    public @NotNull ItemStack assemble(RecipeInput input) {
        return this.output.create();
    }

    public @NotNull ItemStack getResultItem() {
        return this.output.create();
    }

    public @NotNull List<Ingredient> getIngredients() {
        return this.inputs;
    }

    @Override
    public @NotNull PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
            this.placementInfo = PlacementInfo.create(this.inputs);
        }
        return this.placementInfo;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public @NotNull String group() {
        return "";
    }
}
