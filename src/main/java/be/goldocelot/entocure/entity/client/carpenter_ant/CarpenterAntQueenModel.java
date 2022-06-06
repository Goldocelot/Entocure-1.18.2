package be.goldocelot.entocure.entity.client.carpenter_ant;

import be.goldocelot.entocure.Entocure;
import be.goldocelot.entocure.entity.custom.CarpenterAntQueenEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class CarpenterAntQueenModel extends AnimatedGeoModel<CarpenterAntQueenEntity> {
    @Override
    public ResourceLocation getModelLocation(CarpenterAntQueenEntity object) {
        return new ResourceLocation(Entocure.MOD_ID, "geo/carpenter_ant_queen.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(CarpenterAntQueenEntity object) {
        return new ResourceLocation(Entocure.MOD_ID, "textures/entity/carpenter_ant_queen.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(CarpenterAntQueenEntity animatable) {
        return new ResourceLocation(Entocure.MOD_ID, "animations/entity/carpenter_ant_queen.animation.json");
    }

    public void setLivingAnimations(CarpenterAntQueenEntity entity, Integer uniqueID, AnimationEvent customPredicate)
    {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("Head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX(extraData.headPitch * ((float)Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float)Math.PI / 180F));
    }
}
