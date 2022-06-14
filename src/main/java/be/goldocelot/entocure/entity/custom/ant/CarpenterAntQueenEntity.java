package be.goldocelot.entocure.entity.custom.ant;

import be.goldocelot.entocure.entity.custom.ant.strategy.AntStrategy;
import be.goldocelot.entocure.entity.custom.ant.strategy.QueenAntStrategy;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public class CarpenterAntQueenEntity extends AntEntity{

    static {
        strategy = new QueenAntStrategy();
    }

    public CarpenterAntQueenEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }
}
