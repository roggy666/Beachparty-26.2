package net.satisfy.beachparty.core.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.satisfy.beachparty.core.registry.ObjectRegistry;

import java.util.Map;

/**
 * Axe stripping of palm logs and wood.
 * <p>
 * Since 26.3 stripping is driven by the {@code minecraft:axe} block transformer, a single
 * data-pack registry entry that mods cannot append to without replacing the vanilla file.
 * This mirrors what {@code BlockTransformer#transformBlock} does for the vanilla logs.
 */
public final class PalmStripping {

    private static final Map<Block, Block> STRIPPED = Map.of(
            ObjectRegistry.PALM_LOG, ObjectRegistry.STRIPPED_PALM_LOG,
            ObjectRegistry.PALM_WOOD, ObjectRegistry.STRIPPED_PALM_WOOD
    );

    private PalmStripping() {}

    public static void init() {
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            ItemStack stack = player.getItemInHand(hand);
            if (!stack.is(ItemTags.AXES) || player.isSpectator()) return InteractionResult.PASS;

            BlockPos pos = hitResult.getBlockPos();
            BlockState state = level.getBlockState(pos);
            Block stripped = STRIPPED.get(state.getBlock());
            if (stripped == null) return InteractionResult.PASS;

            BlockState newState = stripped.defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));

            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
            }

            level.setBlock(pos, newState, Block.UPDATE_ALL_IMMEDIATE);
            level.playSound(player, pos, SoundEvents.AXE_STRIP.value(), SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, newState));
            stack.hurtAndBreak(1, player, hand.asEquipmentSlot());

            return InteractionResult.SUCCESS;
        });
    }
}
