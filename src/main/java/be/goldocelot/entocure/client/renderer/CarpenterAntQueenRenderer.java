package be.goldocelot.entocure.client.renderer;

import be.goldocelot.entocure.Entocure;

import be.goldocelot.entocure.client.model.CarpenterAntQueenModel;
import be.goldocelot.entocure.entity.custom.CarpenterAntQueenEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.ExtendedGeoEntityRenderer;

public class CarpenterAntQueenRenderer extends ExtendedGeoEntityRenderer<CarpenterAntQueenEntity> {
    public CarpenterAntQueenRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CarpenterAntQueenModel());
        this.shadowRadius = 0.2F;

    }

    @Override
    public ResourceLocation getTextureLocation(CarpenterAntQueenEntity instance) {
        return new ResourceLocation(Entocure.MOD_ID, "textures/entity/carpenter_ant_queen.png");
    }

    @Override
    protected boolean isArmorBone(GeoBone bone) {
        return false;
    }

    @Nullable
    @Override
    protected ResourceLocation getTextureForBone(String boneName, CarpenterAntQueenEntity currentEntity) {
        return null;
    }

    @Nullable
    @Override
    protected ItemStack getHeldItemForBone(String boneName, CarpenterAntQueenEntity currentEntity) {
        if(boneName.equals("Item")) return mainHand;
        return null;
    }

    @Override
    protected ItemTransforms.TransformType getCameraTransformForItemAtBone(ItemStack boneItem, String boneName) {
        if(boneName.equals("Item")) return ItemTransforms.TransformType.GROUND;
        return null;
    }

    @Nullable
    @Override
    protected BlockState getHeldBlockForBone(String boneName, CarpenterAntQueenEntity currentEntity) {
        return null;
    }

    @Override
    protected void preRenderItem(PoseStack matrixStack, ItemStack item, String boneName, CarpenterAntQueenEntity currentEntity, IBone bone) {

    }

    @Override
    protected void preRenderBlock(BlockState block, String boneName, CarpenterAntQueenEntity currentEntity) {

    }

    @Override
    protected void postRenderItem(PoseStack matrixStack, ItemStack item, String boneName, CarpenterAntQueenEntity currentEntity, IBone bone) {

    }

    @Override
    protected void postRenderBlock(BlockState block, String boneName, CarpenterAntQueenEntity currentEntity) {

    }

    @Override
    public RenderType getRenderType(CarpenterAntQueenEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(1F, 1F, 1F);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
