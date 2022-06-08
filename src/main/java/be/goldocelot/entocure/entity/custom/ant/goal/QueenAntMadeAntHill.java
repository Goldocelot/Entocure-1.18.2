package be.goldocelot.entocure.entity.custom.ant.goal;

import be.goldocelot.entocure.block.ModBlocks;
import be.goldocelot.entocure.block.entity.custom.AnthillEntity;
import be.goldocelot.entocure.entity.custom.ant.AntEntity;
import be.goldocelot.entocure.entity.custom.ant.QueenAntEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class QueenAntMadeAntHill extends MoveToBlockGoal {
    private static final int WAIT_TICKS = 40;
    protected int ticksWaited;

    public QueenAntEntity ant;

    public QueenAntMadeAntHill(QueenAntEntity pant, double pSpeedModifier, int pSearchRange, int pVerticalSearchRange) {
        super(pant, pSpeedModifier, pSearchRange, pVerticalSearchRange);
        ant = pant;
    }

    public double acceptedDistance() {
        return 2D;
    }

    public void tick() {
        if (this.isReachedTarget()) {
            if (this.ticksWaited >= WAIT_TICKS) {
                this.onReachedTarget();
                stop();
            } else {
                ++this.ticksWaited;
            }
        }
        super.tick();
    }

    protected void onReachedTarget() {
        if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(ant.level, ant)) {
            BlockState blockstate = ant.level.getBlockState(this.blockPos);
            if (blockstate.is(Blocks.GRASS_BLOCK)) {
                this.makeHill();
            }

        }
    }

    private void makeHill() {
        ant.level.setBlockAndUpdate(blockPos.above(),ModBlocks.DIRT_ANTHILL.get().defaultBlockState());
        BlockEntity blockEntity = ant.level.getBlockEntity(blockPos.above());
        if (blockEntity instanceof AnthillEntity) {
            AnthillEntity anthillEntity = (AnthillEntity)blockEntity;
            ant.setAnthill(anthillEntity);
            anthillEntity.addOccupant(ant);
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
        BlockState blockstate = pLevel.getBlockState(pPos);
        return blockstate.is(Blocks.GRASS_BLOCK);
    }

    public boolean canUse() {
        return !ant.getMainHandItem().isEmpty() && super.canUse() && ant.getActionTickCount() >= AntEntity.ACTION_DELAY && !ant.hasAnthill();
    }

    public void start() {
        this.ticksWaited = 0;
        super.start();
    }

    public void stop(){
        ant.resetActionTickCount();
        super.stop();
    }
}
