package be.goldocelot.entocure.entity.custom.ant;

import be.goldocelot.entocure.block.entity.custom.AnthillEntity;
import be.goldocelot.entocure.entity.custom.ant.goal.AntPickUpGrass;
import be.goldocelot.entocure.entity.custom.ant.goal.QueenAntMadeAntHill;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class AntEntity extends PathfinderMob implements IAnimatable {
    public final static int ACTION_DELAY = 300;
    private int actionTickCount;
    private BlockPos anthillPos;

    private AnimationFactory factory = new AnimationFactory(this);

    public AntEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier setAttributes(){
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH,4.0)
                .add(Attributes.FOLLOW_RANGE, 3.0)
                .add(Attributes.MOVEMENT_SPEED,0.2)
                .add(Attributes.ARMOR,1)
                .add(Attributes.ARMOR_TOUGHNESS,0.2).build();
    }

    protected void registerGoals(){
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        //this.goalSelector.addGoal(2, new QueenAntMadeAntHill(this, 1f,8, 2)); Only queen does that
        this.goalSelector.addGoal(3, new AntPickUpGrass(this, 1f,8, 2));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    public void tick(){
        actionTickCount++;
        super.tick();
    }

    private  <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if(event.isMoving()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walking", true));
        }else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public void resetActionTickCount(){
        actionTickCount = this.level.random.nextInt(101);
    }

    public int getActionTickCount(){
        return actionTickCount;
    }

    public boolean hasAnthill(){
        return anthillPos != null;
    }

    public void setAnthill(AnthillEntity anthill){
        anthillPos = anthill.getBlockPos();
    }
}
