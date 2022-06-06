package be.goldocelot.entocure.block.entity.custom;

import be.goldocelot.entocure.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class AnthillEntity extends BlockEntity {

    public AnthillEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.ANTHILL_ENTITY.get(), pWorldPosition, pBlockState);
    }
}
