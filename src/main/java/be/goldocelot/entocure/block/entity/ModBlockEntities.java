package be.goldocelot.entocure.block.entity;

import be.goldocelot.entocure.Entocure;
import be.goldocelot.entocure.block.ModBlocks;
import be.goldocelot.entocure.block.entity.custom.AnthillEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Entocure.MOD_ID);

    public static final RegistryObject<BlockEntityType<AnthillEntity>> DIRT_ANTHILL_ENTITY =
            BLOCK_ENTITIES.register("dirt_anthill_entity", () ->
                    BlockEntityType.Builder.of(AnthillEntity::new,
                            ModBlocks.DIRT_ANTHILL.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}