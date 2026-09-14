package net.satisfy.beachparty.core.block.entity;

import net.minecraft.core.*;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.beachparty.client.gui.handler.MiniFridgeGuiHandler;
import net.satisfy.beachparty.core.recipe.MiniFridgeRecipe;
import net.satisfy.beachparty.core.registry.EntityTypeRegistry;
import net.satisfy.beachparty.core.registry.ObjectRegistry;
import net.satisfy.beachparty.core.registry.RecipeTypeRegistry;
import net.satisfy.beachparty.core.world.ImplementedInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import net.minecraft.world.Containers;

public class MiniFridgeBlockEntity extends BlockEntity implements ImplementedInventory, BlockEntityTicker<MiniFridgeBlockEntity>, MenuProvider {
    public static final int CAPACITY = 2;
    private static final int[] SLOTS_FOR_INPUT = new int[]{1};
    private static final int[] SLOTS_FOR_OUTPUT = new int[]{0};
    private static final int OUTPUT_SLOT = 0;
    private static final int INPUT_SLOT = 1;
    protected float experience;
    private NonNullList<ItemStack> inventory;
    private int fermentationTime = 0;
    private int totalFermentationTime;

    private final ContainerData propertyDelegate = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> fermentationTime;
                case 1 -> totalFermentationTime;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> fermentationTime = value;
                case 1 -> totalFermentationTime = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public MiniFridgeBlockEntity(BlockPos pos, BlockState state) {
        super(EntityTypeRegistry.MINI_FRIDGE_BLOCK_ENTITY, pos, state);
        this.inventory = NonNullList.withSize(CAPACITY, ItemStack.EMPTY);
    }

    @Override
    protected void loadAdditional(ValueInput nbt) {
        super.loadAdditional(nbt);
        this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, this.inventory);
        this.fermentationTime = nbt.getIntOr("FermentationTime", 0);
        this.totalFermentationTime = nbt.getIntOr("TotalFermentationTime", 0);
        this.experience = nbt.getFloatOr("Experience", 0.0F);
    }

    @Override
    protected void saveAdditional(ValueOutput compoundTag) {
        super.saveAdditional(compoundTag);
        ContainerHelper.saveAllItems(compoundTag, this.inventory);
        compoundTag.putInt("FermentationTime", this.fermentationTime);
        compoundTag.putInt("TotalFermentationTime", this.totalFermentationTime);
        compoundTag.putFloat("Experience", this.experience);
    }

    @Override
    public void tick(Level world, BlockPos pos, BlockState state, MiniFridgeBlockEntity blockEntity) {
        if (world.isClientSide()) return;
        boolean dirty = false;
        Optional<MiniFridgeRecipe> recipeType = ((ServerLevel) world).recipeAccess()
                .getRecipeFor(RecipeTypeRegistry.MINI_FRIDGE_RECIPE_TYPE, asRecipeInput(), world)
                .map(RecipeHolder::value);
        if (recipeType.isPresent() && canCraft(recipeType.get())) {
            if (this.fermentationTime == 0) {
                this.totalFermentationTime = recipeType.get().getCraftingTime();
            }
            this.fermentationTime++;
            if (this.fermentationTime >= this.totalFermentationTime) {
                this.fermentationTime = 0;
                craft(recipeType.get());
                dirty = true;
            }
        } else {
            this.fermentationTime = 0;
        }
        if (dirty) {
            setChanged();
        }
    }

    private boolean canCraft(MiniFridgeRecipe recipe) {
        if (recipe == null || recipe.getResultItem().isEmpty()) {
            return false;
        }
        ItemStack inputStack = this.getItem(INPUT_SLOT);
        ItemStack outputStack = this.getItem(OUTPUT_SLOT);
        return !inputStack.isEmpty() && (outputStack.isEmpty() || ItemStack.isSameItemSameComponents(outputStack, recipe.getResultItem()));
    }

    private void craft(MiniFridgeRecipe recipe) {
        if (!canCraft(recipe)) {
            return;
        }
        final ItemStack recipeOutput = recipe.getResultItem();
        final ItemStack outputSlotStack = this.getItem(OUTPUT_SLOT);
        if (outputSlotStack.isEmpty()) {
            setItem(OUTPUT_SLOT, recipeOutput.copy());
        }
        ItemStack inputStack = this.getItem(INPUT_SLOT);
        inputStack.shrink(1);
        setItem(INPUT_SLOT, inputStack.isEmpty() ? ItemStack.EMPTY : inputStack);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public int @NotNull [] getSlotsForFace(Direction side) {
        if (side == Direction.DOWN) {
            return SLOTS_FOR_OUTPUT;
        } else {
            return SLOTS_FOR_INPUT;
        }
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);
        if (slot == INPUT_SLOT) {
            this.totalFermentationTime = 50;
            this.fermentationTime = 0;
            setChanged();
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction dir) {
        return slot == INPUT_SLOT;
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
        return slot == OUTPUT_SLOT;
    }

    @Override
    public boolean stillValid(Player player) {
        assert this.level != null;
        return this.level.getBlockEntity(this.worldPosition) == this &&
                player.distanceToSqr(this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5) <= 64.0;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return ObjectRegistry.MINI_FRIDGE.getName();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new MiniFridgeGuiHandler(syncId, inv, this, this.propertyDelegate);
    }

    private RecipeInput asRecipeInput() {
        return new RecipeInput() {
            @Override
            public ItemStack getItem(int index) {
                return MiniFridgeBlockEntity.this.inventory.get(index);
            }

            @Override
            public int size() {
                return MiniFridgeBlockEntity.this.inventory.size();
            }
        };
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        if (this.level != null) Containers.dropContents(this.level, pos, this);
    }
}
