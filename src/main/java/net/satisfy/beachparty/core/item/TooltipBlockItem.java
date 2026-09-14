package net.satisfy.beachparty.core.item;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.function.Consumer;

/**
 * Block item with static tooltip lines. Blocks lost {@code appendHoverText} in 26.x, so decorative hints live here.
 */
public class TooltipBlockItem extends BlockItem {
    public static final int HINT_COLOR = 0xD4B483;
    public static final String CAN_BE_PLACED = "tooltip.beachparty.canbeplaced";

    private final List<String> lines;

    public TooltipBlockItem(Block block, Properties properties, String... translationKeys) {
        super(block, properties);
        this.lines = List.of(translationKeys);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        for (String key : this.lines) {
            tooltip.accept(key.isEmpty() ? Component.empty() : Component.translatable(key).withStyle(style -> style.withColor(TextColor.fromRgb(HINT_COLOR))));
        }
    }
}
