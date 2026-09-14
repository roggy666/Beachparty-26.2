package net.satisfy.beachparty.core.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * Clothing that can be dyed (the item has to be in the minecraft:dyeable tag). Colour is read from the DYED_COLOR
 * component and falls back to the default colour of the piece.
 */
public class DyeableBeachpartyArmorItem extends BeachpartyArmorItem {
    private final int defaultColor;
    private final @Nullable Identifier overlayTexture;

    public DyeableBeachpartyArmorItem(Properties properties, int defaultColor, Identifier texture) {
        this(properties, defaultColor, texture, null);
    }

    public DyeableBeachpartyArmorItem(Properties properties, int defaultColor, Identifier texture, @Nullable Identifier overlayTexture) {
        super(properties, texture);
        this.defaultColor = defaultColor;
        this.overlayTexture = overlayTexture;
    }

    public int getColor(ItemStack stack) {
        DyedItemColor dyed = stack.get(DataComponents.DYED_COLOR);
        return dyed != null ? dyed.rgb() : defaultColor;
    }

    public int getDefaultColor() {
        return defaultColor;
    }

    @Nullable
    public Identifier getOverlayTexture() {
        return overlayTexture;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        appendClothingTooltip(this, stack, tooltip);
    }
}
