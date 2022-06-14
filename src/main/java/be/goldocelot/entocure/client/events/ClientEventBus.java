package be.goldocelot.entocure.client.events;

import be.goldocelot.entocure.Entocure;
import be.goldocelot.entocure.client.renderer.CarpenterAntQueenRenderer;
import be.goldocelot.entocure.client.renderer.CarpenterAntRenderer;
import be.goldocelot.entocure.entity.ModEntityTypes;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Entocure.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBus {
    @SubscribeEvent
    public static void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        //LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().options);
        EntityRenderers.register(ModEntityTypes.CARPENTER_ANT_QUEEN.get(), CarpenterAntQueenRenderer::new);
        EntityRenderers.register(ModEntityTypes.CARPENTER_ANT.get(), CarpenterAntRenderer::new);
    }
}
