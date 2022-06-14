package be.goldocelot.entocure.entity.custom.ant.goal;

import be.goldocelot.entocure.entity.custom.ant.AntEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class AntPickUpGrassGoal extends MoveToBlockGoal {
    private static final int WAIT_TICKS = 40;
    protected int ticksWaited;

    private AntEntity ant;
    public AntPickUpGrassGoal(AntEntity pant, double pSpeedModifier, int pSearchRange, int pVerticalSearchRange) {
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
            } else {
                ++this.ticksWaited;
            }
        }
        super.tick();
    }

    protected void onReachedTarget() {
        if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(ant.level, ant)) {
            BlockState blockstate = ant.level.getBlockState(this.blockPos);
            if (blockstate.is(Blocks.GRASS)) {
                this.pickGrass();
            }
        }
    }

    private void pickGrass() {
        ant.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.GRASS));
        ant.level.removeBlock(blockPos, false);
    }

    @Override
    protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
        BlockState blockstate = pLevel.getBlockState(pPos);
        return blockstate.is(Blocks.GRASS) || blockstate.is(Blocks.TALL_GRASS);
    }

    public boolean canUse() {
        return ant.getMainHandItem().isEmpty() && super.canUse() && ant.getActionTickCount() >= AntEntity.ACTION_DELAY;
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
