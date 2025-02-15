package icu.takeneko.wrenched.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import icu.takeneko.wrenched.client.screen.WrenchScreen;
import icu.takeneko.wrenched.item.Wrench;
import icu.takeneko.wrenched.util.StateUtil;
import icu.takeneko.wrenched.util.WrenchedBlockExtension;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.List;

@EventBusSubscriber(Dist.CLIENT)
public class WrenchedClientEvents {

    @SubscribeEvent
    public static void wrenchUse(PlayerInteractEvent.RightClickBlock event) {
        InteractionHand hand = event.getHand();
        if (event.getEntity().getItemInHand(hand).getItem() instanceof Wrench) {
            if (Wrench.ableToUseWrench(event.getLevel(), event.getPos(), event.getEntity())) {
                BlockState targetBlockState = event.getLevel().getBlockState(event.getPos());
                if (event.getLevel().isClientSide()) {
                    clientHandle(event, targetBlockState, hand);
                }
                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
            }
        }
    }


    private static void clientHandle(PlayerInteractEvent.RightClickBlock event, BlockState targetBlockState, InteractionHand hand) {
        Property<?> property = Wrench.findChangeableProperty(targetBlockState);
        if (!event.getEntity().isShiftKeyDown()
            && Wrench.ableToUseWrench(event.getLevel(), event.getPos(), event.getEntity())
            && property != null
        ) {
            if (targetBlockState.getBlock() instanceof WrenchedBlockExtension ext && !ext.wrenched$checkBlockState(targetBlockState))
                return;
            if (!event.getEntity().getAbilities().mayBuild)
                return;
            List<BlockState> possibleStates = StateUtil.findPossibleStatesForProperty(targetBlockState, property);
            if (!possibleStates.isEmpty()) {
                Minecraft.getInstance().setScreen(new WrenchScreen(
                    event.getPos(),
                    targetBlockState,
                    property,
                    possibleStates
                ));
            }
        }
    }

    @SubscribeEvent
    public static void onRender(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) return;
        Minecraft mc = Minecraft.getInstance();
        if (!(mc.screen instanceof WrenchScreen screen)) return;
        if (!screen.shouldRender()) return;
        BlockPos pos = screen.renderingBlockPos();
        BlockState state = screen.renderingBlockState();
        RenderType renderType = screen.renderType();
        PoseStack poseStack = event.getPoseStack();
        poseStack.pushPose();
        Camera camera = event.getCamera();
        Vec3 cameraPos = camera.getPosition();
        poseStack.translate(
            pos.getX() - cameraPos.x - 0.0005,
            pos.getY() - cameraPos.y - 0.0005,
            pos.getZ() - cameraPos.z - 0.0005
        );
        poseStack.scale(1.001f, 1.001f, 1.001f);
        BakedModel model = mc.getBlockRenderer().getBlockModel(state);
        ModelBlockRenderer renderer = mc.getBlockRenderer().getModelRenderer();
        MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();
        VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);
        renderer.renderModel(
            poseStack.last(),
            vertexConsumer,
            state,
            model,
            1f,
            1f,
            1f,
            LightTexture.FULL_BLOCK,
            OverlayTexture.NO_OVERLAY
        );
        poseStack.popPose();
    }
}
