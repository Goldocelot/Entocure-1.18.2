package be.goldocelot.entocure.client.renderer;

import be.goldocelot.entocure.client.model.CarpenterAntModel;
import be.goldocelot.entocure.client.model.CarpenterAntQueenModel;
import be.goldocelot.entocure.entity.custom.ant.CarpenterAntEntity;
import be.goldocelot.entocure.entity.custom.ant.CarpenterAntQueenEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CarpenterAntRenderer extends GeoEntityRenderer<CarpenterAntEntity> {
    public CarpenterAntRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CarpenterAntModel());
        this.shadowRadius = 0.2F;

    }

    @Override
    public ResourceLocation getTextureLocation(CarpenterAntEntity instance) {
        return CarpenterAntModel.TEXTURE_LOCATION;
    }

    @Override
    public void renderRecursively(GeoBone bone, PoseStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (bone.getName().equals("Item")) { // rArmRuff is the name of the bone you to set the item to attach too. Please see Note
            stack.pushPose();
            moveAndRotateMatrixToMatchBone(stack,bone);
            // Sets the scaling of the item.
            stack.scale(1.0f, 1.0f, 1.0f);
            // Change mainHand to predefined Itemstack and TransformType to what transform you would want to use.
            Minecraft.getInstance().getItemRenderer().renderStatic(mainHand, ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, packedLightIn, packedOverlayIn, stack, this.rtb,0);
            stack.popPose();
            bufferIn = rtb.getBuffer(RenderType.entityTranslucent(whTexture));
        }
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    private void moveAndRotateMatrixToMatchBone(PoseStack stack, GeoBone bone) {
        // First, let's move our render position to the pivot point...
        stack.translate(bone.getPivotX() / 16, bone.getPivotY() / 16+0.01, bone.getPivotZ() / 16);

        stack.mulPose(Vector3f.XP.rotationDegrees(105));
        stack.mulPose(Vector3f.ZP.rotationDegrees(180));

        stack.scale(0.2f,0.2f,0.2f);
    }

    @Override
    public RenderType getRenderType(CarpenterAntEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        //stack.scale(1F, 1F, 1F);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
