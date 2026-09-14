package net.satisfy.beachparty.core.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

/**
 * A harmless dyeable "sword": the knockback it applies lives in {@code CommonEvents} (attack callback).
 */
public class PoolNoodleItem extends Item {
    public static final int DEFAULT_COLOR = 1017855;

    public PoolNoodleItem(Properties properties) {
        // sword(material, attack damage, attack speed) relative to the base values: 0 damage, -1.4 speed like before
        super(properties.sword(ToolMaterial.WOOD, -ToolMaterial.WOOD.attackDamageBonus(), -1.4F)
                .component(DataComponents.DYED_COLOR, new DyedItemColor(DEFAULT_COLOR)));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        tooltip.accept(Component.translatable("tooltip.beachparty.dyeable").withStyle(style -> style.withColor(TextColor.fromRgb(0xD4B483))));
    }
}
