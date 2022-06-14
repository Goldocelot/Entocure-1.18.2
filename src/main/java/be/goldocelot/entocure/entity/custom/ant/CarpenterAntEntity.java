package be.goldocelot.entocure.entity.custom.ant;

import be.goldocelot.entocure.entity.custom.ant.strategy.WarriorAntStrategy;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public class CarpenterAntEntity extends AntEntity {

    static {
        strategy = new WarriorAntStrategy();
    }

    public CarpenterAntEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }
}
