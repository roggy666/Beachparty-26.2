package net.satisfy.beachparty.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.satisfy.beachparty.core.world.ConfiguredFeatures;

import java.util.Optional;

public class PalmSproutBlock extends SaplingBlock {
    private static final TreeGrower PALM = new TreeGrower("palm", Optional.empty(), Optional.of(ConfiguredFeatures.PALM_TREE_KEY), Optional.empty());

    public PalmSproutBlock(BlockBehaviour.Properties properties) {
        super(PALM, properties);
    }

    public static BlockBehaviour.Properties sproutProperties() {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_SAPLING).mapColor(MapColor.PLANT).noCollision().randomTicks().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY);
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(BlockTags.SAND);
    }
}
