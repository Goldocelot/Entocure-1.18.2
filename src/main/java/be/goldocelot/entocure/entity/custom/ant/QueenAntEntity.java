package be.goldocelot.entocure.entity.custom.ant;

import be.goldocelot.entocure.entity.custom.ant.goal.QueenAntMadeAntHill;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public abstract class QueenAntEntity extends AntEntity{

    public QueenAntEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    protected void registerGoals(){
        super.registerGoals();
        this.goalSelector.addGoal(2, new QueenAntMadeAntHill(this, 1f,8, 2));
    }
}
