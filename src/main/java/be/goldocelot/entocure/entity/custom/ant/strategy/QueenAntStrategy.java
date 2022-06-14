package be.goldocelot.entocure.entity.custom.ant.strategy;

import be.goldocelot.entocure.entity.custom.ant.AntEntity;
import be.goldocelot.entocure.entity.custom.ant.goal.QueenAntMadeAntHillGoal;

import java.lang.reflect.Type;

public class QueenAntStrategy implements AntStrategy {

    @Override
    public void registerGoals(AntEntity ant) {
        ant.goalSelector.addGoal(3, new QueenAntMadeAntHillGoal(ant, 1f,8, 2));
    }
}
