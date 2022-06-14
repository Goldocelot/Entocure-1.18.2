package be.goldocelot.entocure.entity.custom.ant.strategy;

import be.goldocelot.entocure.entity.custom.ant.AntEntity;

import java.lang.reflect.Type;

public interface AntStrategy {
    void registerGoals(AntEntity ant);
}
