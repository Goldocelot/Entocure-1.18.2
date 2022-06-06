package be.goldocelot.entocure.entity.custom.ant.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class AntPickUpGrass extends MoveToBlockGoal {
    private static final int WAIT_TICKS = 40;
    protected int ticksWaited;

    public AntPickUpGrass(PathfinderMob pMob, double pSpeedModifier, int pSearchRange, int pVerticalSearchRange) {
        super(pMob, pSpeedModifier, pSearchRange, pVerticalSearchRange);
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
        if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(mob.level, mob)) {
            BlockState blockstate = mob.level.getBlockState(this.blockPos);
            if (blockstate.is(Blocks.GRASS)) {
                this.pickGrass();
            }

        }
    }

    private void pickGrass() {
        mob.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.GRASS));
        mob.level.removeBlock(blockPos, false);
    }

    @Override
    protected boolean isValidTarget(LevelReader pLevel, BlockPos pPos) {
        BlockState blockstate = pLevel.getBlockState(pPos);
        System.out.println(blockstate.toString());
        return blockstate.is(Blocks.GRASS) || blockstate.is(Blocks.TALL_GRASS);
    }

    public boolean canUse() {
        return mob.getItemInHand(InteractionHand.MAIN_HAND).isEmpty();
    }

    public void start() {
        this.ticksWaited = 0;
        super.start();
    }
}
