package net.satisfy.beachparty.core.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.satisfy.beachparty.core.util.BeachpartyIdentifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import net.minecraft.world.item.component.TooltipDisplay;
import java.util.function.Consumer;

public class SeashellItem extends BlockItem {
    public SeashellItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level world, @NotNull Player player, @NotNull InteractionHand hand) {
        InteractionResult resultHolder = super.use(world, player, hand);
        ItemStack stack = player.getItemInHand(hand);
        player.swing(hand, stack.getInteractAnimation(), true);
        if (!world.isClientSide()) {
            world.playSound(player, player.blockPosition().above(),
                    SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.PLAYERS, 1, 1);
            final MinecraftServer minecraftServer = player.level().getServer();
            if (minecraftServer != null && player.level() instanceof ServerLevel server) {
                LootParams lootContext = new LootParams.Builder(server)
                        .withParameter(LootContextParams.THIS_ENTITY, player)
                        .withParameter(LootContextParams.ORIGIN, player.position())
                        .create(LootContextParamSets.GIFT);
                LootTable treasure = minecraftServer.reloadableRegistries().getLootTable(ResourceKey.create(Registries.LOOT_TABLE, BeachpartyIdentifier.identifier("gameplay/seashell")));
                treasure.getRandomItems(lootContext).forEach(player::addItem);
            }
        }
        stack.shrink(1);
        return resultHolder;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag tooltipFlag) {
        tooltip.accept(Component.translatable("tooltip.beachparty.canbeplaced").withStyle(style -> style.withColor(TextColor.fromRgb(0xD4B483))));
    }
}
