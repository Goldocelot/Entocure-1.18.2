package be.goldocelot.entocure.block.entity.custom;

import be.goldocelot.entocure.block.entity.ModBlockEntities;
import be.goldocelot.entocure.entity.custom.ant.AntEntity;
import be.goldocelot.entocure.entity.custom.ant.QueenAntEntity;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class AnthillEntity extends BlockEntity {

    private boolean hasQueen;
    private int foodLevel;

    public final static String HAS_QUEEN_TAG = "hasQueen";
    public final static String FOOD_LEVEL = "foodLevel";

    public AnthillEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.DIRT_ANTHILL_ENTITY.get(), pWorldPosition, pBlockState);
    }

    public void addOccupant(AntEntity entity) {
        if(entity instanceof QueenAntEntity) hasQueen = true;
        if(!entity.getMainHandItem().isEmpty()) foodLevel++;
        entity.discard();
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        hasQueen = pTag.getBoolean(HAS_QUEEN_TAG);
        foodLevel = pTag.getInt(FOOD_LEVEL);
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putBoolean(HAS_QUEEN_TAG, hasQueen);
        pTag.putInt(FOOD_LEVEL, foodLevel);
    }

    public void debug(Player p){
        if(Minecraft.getInstance().isLocalServer())
        p.sendMessage(new TextComponent(HAS_QUEEN_TAG+": "+hasQueen), Util.NIL_UUID);
        p.sendMessage(new TextComponent(FOOD_LEVEL+": "+foodLevel), Util.NIL_UUID);
    }
}
