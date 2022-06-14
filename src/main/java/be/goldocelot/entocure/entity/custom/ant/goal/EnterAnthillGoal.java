package be.goldocelot.entocure.entity.custom.ant.goal;

import be.goldocelot.entocure.block.entity.custom.AnthillEntity;
import be.goldocelot.entocure.entity.custom.ant.AntEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.entity.BlockEntity;

public class EnterAnthillGoal extends Goal {

    public AntEntity ant;
    public boolean pathing;

    public EnterAnthillGoal(AntEntity pant) {
        ant = pant;
    }

    public double acceptedDistance() {
        return 2D;
    }

    public void tick() {
        if (ant.blockPosition().distToCenterSqr(ant.getAnthillPos().getX()+0.5, ant.getAnthillPos().getY()+0.85, ant.getAnthillPos().getZ()+0.5)<=acceptedDistance()) {
            enterHill();
        }else{
            if(!pathing){
                pathing = pathfindDirectlyTowards(ant.getAnthillPos());
            }
        }
        super.tick();
    }

    private void enterHill() {
        BlockEntity blockEntity = ant.level.getBlockEntity(ant.getAnthillPos());
        if (blockEntity instanceof AnthillEntity) {
            AnthillEntity anthillEntity = (AnthillEntity)blockEntity;
            anthillEntity.addOccupant(ant);
        }
    }

    private boolean pathfindDirectlyTowards(BlockPos pPos) {
        ant.getNavigation().setMaxVisitedNodesMultiplier(10f);
        ant.getNavigation().moveTo(pPos.getX()+0.5, pPos.getY()+0.85, pPos.getZ()+0.5, 1.0D);
        return ant.getNavigation().getPath() != null && ant.getNavigation().getPath().canReach();
    }

    public boolean canUse() {
        return !ant.getMainHandItem().isEmpty() && ant.getActionTickCount() >= AntEntity.ACTION_DELAY && ant.hasAnthill();
    }

    public void start() {
        super.start();
        pathing = false;
    }

    public void stop(){
        ant.resetActionTickCount();
        ant.getNavigation().stop();
        ant.getNavigation().resetMaxVisitedNodesMultiplier();
        System.out.println("STOPPED");
        super.stop();
    }
}
