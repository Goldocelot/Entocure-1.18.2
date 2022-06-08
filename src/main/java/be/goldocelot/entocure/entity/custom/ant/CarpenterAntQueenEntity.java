package be.goldocelot.entocure.entity.custom.ant;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public class CarpenterAntQueenEntity extends QueenAntEntity{

    public CarpenterAntQueenEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }
}
