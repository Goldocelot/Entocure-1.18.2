package be.goldocelot.entocure.entity;

import be.goldocelot.entocure.Entocure;
import be.goldocelot.entocure.entity.custom.ant.CarpenterAntEntity;
import be.goldocelot.entocure.entity.custom.ant.CarpenterAntQueenEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, Entocure.MOD_ID);

    public static final RegistryObject<EntityType<CarpenterAntQueenEntity>> CARPENTER_ANT_QUEEN =
            ENTITY_TYPES.register("carpenter_ant_queen",
                    () -> EntityType.Builder.of(CarpenterAntQueenEntity::new, MobCategory.AMBIENT)
                            .sized(0.4f, 0.3f)
                            .build(new ResourceLocation(Entocure.MOD_ID, "carpenter_ant_queen").toString()));

    public static final RegistryObject<EntityType<CarpenterAntEntity>> CARPENTER_ANT =
            ENTITY_TYPES.register("carpenter_ant",
                    () -> EntityType.Builder.of(CarpenterAntEntity::new, MobCategory.AMBIENT)
                            .sized(0.4f, 0.3f)
                            .build(new ResourceLocation(Entocure.MOD_ID, "carpenter_ant").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
