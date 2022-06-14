package be.goldocelot.entocure.block.entity.custom;

import be.goldocelot.entocure.block.entity.ModBlockEntities;
import be.goldocelot.entocure.entity.custom.ant.AntEntity;
import com.google.common.collect.Lists;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnthillEntity extends BlockEntity {
    private int foodLevel;

    public final static String QUEEN_TAG = "queen";
    public final static String FOOD_LEVEL = "foodLevel";
    public static final String WARRIOR_COUNT = "WarriorCount";

    private static final List<String> IGNORED_ANT_TAGS = Arrays.asList("Air", "ArmorDropChances", "ArmorItems", "Brain", "CanPickUpLoot", "DeathTime", "FallDistance", "FallFlying", "Fire", "HandDropChances", "HandItems", "HurtByTimestamp", "HurtTime", "LeftHanded", "Motion", "NoGravity", "OnGround", "PortalCooldown", "Pos", "Rotation", "CannotEnterHiveTicks", "TicksSincePollination", "CropsGrownSincePollination", "Passengers", "Leash", "UUID");

    private static final int MIN_OCCUPATION_TICKS = 1200;

    private static final int MIN_BIRTH_TICKS = 6000;

    public static final int MAX_OCCUPANTS = 15;

    public static final int FOOD_CONSUMPTION = 5;

    private int warriorCount;

    private final List<AnthillEntity.AntData> stored = Lists.newArrayList();

    private QueenData queenData;

    public AnthillEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.DIRT_ANTHILL_ENTITY.get(), pWorldPosition, pBlockState);
    }

    public void addOccupant(AntEntity entity) {
        entity.resetActionTickCount();
        entity.stopRiding();
        entity.ejectPassengers();
        CompoundTag compoundtag = new CompoundTag();
        entity.save(compoundtag);

        if(entity.isQueen()){
            queenData = new QueenData(compoundtag, 0, 0);
        }else{
            stored.add(new AntData(compoundtag, 0));
        }

        if(!entity.getMainHandItem().isEmpty()) foodLevel++;
        entity.discard();
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, AnthillEntity anthillEntity) {
        //Check if the queen is inside
        if(anthillEntity.queenData!=null){
            //If there is no warrior, queen prepare itself to go outside
            if(anthillEntity.warriorCount==0) anthillEntity.queenData.ticksInAnthill++;
            //If there is not max occupants, queen prepare itself to give birth
            if(anthillEntity.warriorCount<MAX_OCCUPANTS) anthillEntity.queenData.birthTicks++;

            if(anthillEntity.queenData.birthTicks>=MIN_BIRTH_TICKS && anthillEntity.foodLevel>=FOOD_CONSUMPTION){                                                         //If there is enough food, and queen is ready -> GiveBirth

            } else if (anthillEntity.queenData.ticksInAnthill>=MIN_OCCUPATION_TICKS && anthillEntity.warriorCount==0 && anthillEntity.foodLevel<FOOD_CONSUMPTION) {       //If there is no warrior, no food, and queen is ready -> She go outside
                if(level.random.nextInt(200)==0){
                    if(releaseOccupant(level, blockPos, blockState, anthillEntity.queenData)) anthillEntity.queenData = null;
                }
            }
        }

        for (AntData antData : new ArrayList<>(anthillEntity.stored)){
            antData.ticksInAnthill++;
            if(antData.ticksInAnthill>=MIN_OCCUPATION_TICKS){
                if(level.random.nextInt(200)==0){
                    if(releaseOccupant(level, blockPos, blockState, antData)) anthillEntity.stored.remove(antData);
                }
            }
        }
    }

    private static boolean releaseOccupant(Level level, BlockPos blockPos, BlockState blockState, AnthillEntity.AntData antData) {
        if (level.isNight()) {
            return false;
        } else {
            CompoundTag compoundtag = antData.entityData.copy();
            removeIgnoredAntTags(compoundtag);
            compoundtag.put(AntEntity.ANTHILL_POS_TAG, NbtUtils.writeBlockPos(blockPos));
            boolean flag = !level.getBlockState(blockPos.above()).getCollisionShape(level, blockPos.above()).isEmpty();
            if (flag) {
                return false;
            } else {
                Entity entity = EntityType.loadEntityRecursive(compoundtag, level, (ent) -> {
                    return ent;
                });
                if (entity != null) {

                    double d0 = (double)blockPos.above().getX() + 0.5D;
                    double d1 = (double)blockPos.above().getY() + 0.5D - (double)(entity.getBbHeight() / 2.0F);
                    double d2 = (double)blockPos.above().getZ() + 0.5;
                    entity.moveTo(d0, d1, d2, entity.getYRot(), entity.getXRot());

                    return level.addFreshEntity(entity);
                }else{
                    return false;
                }
            }
        }
    }


    public void load(CompoundTag pTag) {
        super.load(pTag);
        queenData = readQueen(pTag);
        foodLevel = pTag.getInt(FOOD_LEVEL);
        warriorCount = pTag.getInt(WARRIOR_COUNT);
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        pTag.putInt(FOOD_LEVEL, foodLevel);
        pTag.putInt(WARRIOR_COUNT, warriorCount);
        pTag.put(QUEEN_TAG, writeQueen());
    }

    public CompoundTag writeQueen() {
        if(queenData==null) return new CompoundTag();
        CompoundTag compoundtag = queenData.entityData.copy();
        compoundtag.remove("UUID");
        CompoundTag compoundtag1 = new CompoundTag();
        compoundtag1.put("EntityData", compoundtag);
        compoundtag1.putInt("ticksInAnthill", queenData.ticksInAnthill);
        compoundtag1.putInt("birthTicks", queenData.birthTicks);
        return compoundtag1;
    }

    public QueenData readQueen(CompoundTag pTag){
        CompoundTag ct = pTag.getCompound(QUEEN_TAG);
        if(ct.isEmpty()) return null;
        return new QueenData(ct.getCompound("EntityData"),ct.getInt("ticksInAnthill"),ct.getInt("birthTicks"));
    }

    public void debug(Player p){
        if(Minecraft.getInstance().isLocalServer())
        p.sendMessage(new TextComponent(QUEEN_TAG+": "+(queenData==null ? "null" : queenData.toString())), Util.NIL_UUID);
        p.sendMessage(new TextComponent(FOOD_LEVEL+": "+foodLevel), Util.NIL_UUID);
        p.sendMessage(new TextComponent(WARRIOR_COUNT+": "+warriorCount), Util.NIL_UUID);
    }

    static void removeIgnoredAntTags(CompoundTag tag) {
        for(String s : IGNORED_ANT_TAGS) {
            tag.remove(s);
        }

    }

    public void reduceWarriorCount(){
        warriorCount--;
    }

    static class QueenData extends AntData{
        int birthTicks;
        QueenData(CompoundTag pEntityData, int ticksInAnthill,int birthTicks) {
            super(pEntityData, ticksInAnthill);
            this.birthTicks = birthTicks;
        }

        public String toString(){
            return "QueenData[birthTicks: "+birthTicks+" "+super.toString()+"]";
        }
    }

    static class AntData {
        final CompoundTag entityData;
        int ticksInAnthill;

        AntData(CompoundTag pEntityData, int ticksInAnthill) {
            AnthillEntity.removeIgnoredAntTags(pEntityData);
            this.entityData = pEntityData;
            this.ticksInAnthill = ticksInAnthill;
        }

        public String toString(){
            return "AntData[ticksInAnthill: "+ticksInAnthill+"]";
        }
    }


}
