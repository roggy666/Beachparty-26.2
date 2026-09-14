package net.satisfy.beachparty.core.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

/**
 * Clothing without a dye layer (hats, sunglasses, rubber rings). Armor stats come from the Equippable component that
 * {@code Item.Properties#humanoidArmor} adds, this class only carries the custom model texture and the visibility toggle.
 */
public class BeachpartyArmorItem extends Item {
    private static final String VISIBLE_KEY = "Visible";
    private final Identifier texture;

    public BeachpartyArmorItem(Properties properties, Identifier texture) {
        super(properties);
        this.texture = texture;
    }

    public Identifier getTexture() {
        return texture;
    }

    public static boolean isVisible(ItemStack stack) {
        CompoundTag data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        return data.getBooleanOr(VISIBLE_KEY, true);
    }

    public static void toggleVisibility(ItemStack stack) {
        CompoundTag data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        data.putBoolean(VISIBLE_KEY, !data.getBooleanOr(VISIBLE_KEY, true));
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(data));
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack slotStack, ItemStack holdingStack, Slot slot, ClickAction clickAction, Player player, SlotAccess slotAccess) {
        if (clickAction == ClickAction.SECONDARY && holdingStack.isEmpty()) {
            toggleVisibility(slotStack);
            return true;
        }
        return super.overrideOtherStackedOnMe(slotStack, holdingStack, slot, clickAction, player, slotAccess);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        appendClothingTooltip(this, stack, tooltip);
    }

    static void appendClothingTooltip(Item item, ItemStack stack, Consumer<Component> tooltip) {
        tooltip.accept(Component.translatable("tooltip.beachparty.accessoriesslot").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFAF3E0))));
        tooltip.accept(Component.translatable("tooltip.beachparty.effect." + item.getDescriptionId()).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xD4B483))));
        tooltip.accept(Component.empty());

        String toggleKey = isVisible(stack) ? "tooltip.beachparty.toggle.hide" : "tooltip.beachparty.toggle.show";
        tooltip.accept(Component.translatable(toggleKey).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x5CB85C))));
    }
}
