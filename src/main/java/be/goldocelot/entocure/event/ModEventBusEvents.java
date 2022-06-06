package be.goldocelot.entocure.event;

import be.goldocelot.entocure.Entocure;
import be.goldocelot.entocure.entity.ModEntityTypes;
import be.goldocelot.entocure.entity.custom.ant.CarpenterAntQueenEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Entocure.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.CARPENTER_ANT_QUEEN.get(), CarpenterAntQueenEntity.setAttributes());
    }
}
