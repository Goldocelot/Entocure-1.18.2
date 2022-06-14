package be.goldocelot.entocure.client.model;

import be.goldocelot.entocure.Entocure;
import be.goldocelot.entocure.entity.custom.ant.CarpenterAntEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class CarpenterAntModel extends AnimatedGeoModel<CarpenterAntEntity> {
    public static final ResourceLocation MODEL_LOCATION = new ResourceLocation(Entocure.MOD_ID, "geo/carpenter_ant_queen.geo.json");
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(Entocure.MOD_ID, "textures/entity/carpenter_ant_queen.png");
    public static final ResourceLocation ANIMATION_LOCATION = new ResourceLocation(Entocure.MOD_ID, "animations/entity/carpenter_ant_queen.animation.json");

    @Override
    public ResourceLocation getModelLocation(CarpenterAntEntity object) {
        return MODEL_LOCATION;
    }

    @Override
    public ResourceLocation getTextureLocation(CarpenterAntEntity object) {
        return TEXTURE_LOCATION;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(CarpenterAntEntity animatable) {
        return ANIMATION_LOCATION;
    }

    public void setLivingAnimations(CarpenterAntEntity entity, Integer uniqueID, AnimationEvent customPredicate)
    {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("Head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX(extraData.headPitch * ((float)Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float)Math.PI / 180F));
    }
}
