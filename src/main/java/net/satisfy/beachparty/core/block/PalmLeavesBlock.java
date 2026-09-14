package net.satisfy.beachparty.core.block;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.satisfy.beachparty.core.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.OptionalInt;
import net.minecraft.world.level.ScheduledTickAccess;


public class PalmLeavesBlock extends LeavesBlock implements BonemealableBlock {
    public static final IntegerProperty DISTANCE_9 = IntegerProperty.create("distance_9", 1, 9);

    public static final MapCodec<PalmLeavesBlock> CODEC = simpleCodec(PalmLeavesBlock::new);

    @Override
    public @NotNull MapCodec<? extends LeavesBlock> codec() {
        return CODEC;
    }

    public PalmLeavesBlock(Properties properties) {
        super(0.01F, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(DISTANCE_9, 9).setValue(PERSISTENT, false).setValue(DISTANCE, 7).setValue(WATERLOGGED, false));
    }

    private static BlockState updateDistance(BlockState pState, LevelAccessor pLevel, BlockPos pPos) {
        int i = 9;
        BlockPos.MutableBlockPos blockpos$autocloseable = new BlockPos.MutableBlockPos();

        for (Direction direction : Direction.values()) {
            blockpos$autocloseable.setWithOffset(pPos, direction);
            i = Math.min(i, getDistanceAt(pLevel.getBlockState(blockpos$autocloseable)) + 1);
            if (i == 1) {
                break;
            }
        }

        return pState.setValue(DISTANCE_9, i);
    }

    private static int getDistanceAt(BlockState blockState) {
        return getOptionalDistanceAt(blockState).orElse(9);
    }

    public static @NotNull OptionalInt getOptionalDistanceAt(BlockState blockState) {
        if (blockState.is(BlockTags.LOGS)) {
            return OptionalInt.of(0);
        } else {
            return blockState.hasProperty(DISTANCE_9) ? OptionalInt.of(blockState.getValue(DISTANCE_9)) : OptionalInt.empty();
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(DISTANCE_9) == 9 && !state.getValue(PERSISTENT);
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (this.decaying(blockState)) {
            LeavesBlock.dropResources(blockState, serverLevel, blockPos);
            serverLevel.removeBlock(blockPos, false);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(DISTANCE_9);
    }

    @Override
    public @NotNull BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        BlockState blockstate = this.defaultBlockState().setValue(PERSISTENT, true).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
        return updateDistance(blockstate, pContext.getLevel(), pContext.getClickedPos());
    }

    @Override
    protected boolean decaying(BlockState pState) {
        return !pState.getValue(PERSISTENT) && pState.getValue(DISTANCE_9) == 9;
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        pLevel.setBlock(pPos, updateDistance(pState, pLevel, pPos), 3);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState pState, LevelReader pLevel, ScheduledTickAccess scheduledTickAccess, BlockPos pCurrentPos, Direction pFacing, BlockPos pFacingPos, BlockState pFacingState, RandomSource random) {
        if (pState.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        int i = getDistanceAt(pFacingState) + 1;
        if (i != 1 || pState.getValue(DISTANCE_9) != i) {
            scheduledTickAccess.scheduleTick(pCurrentPos, this, 1);
        }

        return pState;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return levelReader.getBlockState(blockPos.below()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if (level.getBlockState(pos.below()).isAir()) {
            level.setBlock(pos.below(), ObjectRegistry.HANGING_COCONUT.defaultBlockState().setValue(HangingCoconutBlock.AGE, 0), 2);
        }
    }

    @Override
    protected void spawnFallingLeavesParticle(Level level, BlockPos pos, RandomSource random) {
        // palm fronds don't shed leaf particles
    }
}
