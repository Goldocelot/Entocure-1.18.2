package be.goldocelot.entocure.entity.custom.ant;

import be.goldocelot.entocure.block.entity.custom.AnthillEntity;
import be.goldocelot.entocure.entity.custom.ant.goal.AntPickUpGrassGoal;
import be.goldocelot.entocure.entity.custom.ant.goal.EnterAnthillGoal;
import be.goldocelot.entocure.entity.custom.ant.strategy.AntStrategy;
import be.goldocelot.entocure.entity.custom.ant.strategy.QueenAntStrategy;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class AntEntity extends PathfinderMob implements IAnimatable {

    public final static String ANTHILL_POS_TAG = "AnthillPos";
    public final static String ACTION_DELAY_TAG = "ActionDelay";
    public final static int ACTION_DELAY = 300;
    private int actionTickCount;
    private BlockPos anthillPos;

    private AnimationFactory factory = new AnimationFactory(this);

    protected static AntStrategy strategy;

    public AntEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier setAttributes(){
        return PathfinderMob.createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE,0.5)
                .add(Attributes.MAX_HEALTH,4.0)
                .add(Attributes.FOLLOW_RANGE, 3.0)
                .add(Attributes.MOVEMENT_SPEED,0.2)
                .add(Attributes.ARMOR,1)
                .add(Attributes.ARMOR_TOUGHNESS,0.2).build();
    }

    protected void registerGoals(){
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this,2));
        this.goalSelector.addGoal(2, new EnterAnthillGoal(this));
        this.goalSelector.addGoal(4, new AntPickUpGrassGoal(this, 1f,8, 2));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        strategy.registerGoals(this);
    }

    public void tick(){
        actionTickCount++;
        if(hasAnthill() && !(level.getBlockEntity(anthillPos) instanceof AnthillEntity)) anthillPos = null;
        super.tick();
    }

    public void remove(Entity.RemovalReason pReason) {
        System.out.println("REMOVED:"+ hasAnthill()+", "+pReason);
        if(hasAnthill()){
            BlockEntity blockEntity = this.level.getBlockEntity(anthillPos.above());
            System.out.println(blockEntity);
            if (blockEntity instanceof AnthillEntity) {
                AnthillEntity anthillEntity = (AnthillEntity) blockEntity;
                if (pReason == Entity.RemovalReason.KILLED && !(strategy instanceof QueenAntStrategy)) {
                    anthillEntity.reduceWarriorCount();
                }
            }
        }

        super.remove(pReason);
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

    public void checkDespawn() {
        if(!hasAnthill()) super.checkDespawn();
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (this.hasAnthill()) {
            pCompound.put(ANTHILL_POS_TAG, NbtUtils.writeBlockPos(this.anthillPos));
        }
        pCompound.putInt(ACTION_DELAY_TAG,actionTickCount);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundTag pCompound) {
        this.anthillPos = null;
        if (pCompound.contains(ANTHILL_POS_TAG)) {
            this.anthillPos = NbtUtils.readBlockPos(pCompound.getCompound(ANTHILL_POS_TAG));
        }

        super.readAdditionalSaveData(pCompound);
        this.actionTickCount = pCompound.getInt(ACTION_DELAY_TAG);
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

    public BlockPos getAnthillPos(){
        return anthillPos;
    }

    public boolean isQueen(){
        return strategy instanceof QueenAntStrategy;
    }

}
