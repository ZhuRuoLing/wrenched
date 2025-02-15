package icu.takeneko.wrenched.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import icu.takeneko.wrenched.client.screen.WrenchScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.chunk.SectionCompiler;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SectionCompiler.class)
public abstract class SectionCompilerMixin {
    @WrapOperation(
        method = "compile(Lnet/minecraft/core/SectionPos;Lnet/minecraft/client/renderer/chunk/RenderChunkRegion;Lcom/mojang/blaze3d/vertex/VertexSorting;Lnet/minecraft/client/renderer/SectionBufferBuilderPack;Ljava/util/List;)Lnet/minecraft/client/renderer/chunk/SectionCompiler$Results;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/block/BlockRenderDispatcher;renderBatched(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/BlockAndTintGetter;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;ZLnet/minecraft/util/RandomSource;Lnet/neoforged/neoforge/client/model/data/ModelData;Lnet/minecraft/client/renderer/RenderType;)V"
        )
    )
    void skipBlockAt(
        BlockRenderDispatcher instance,
        BlockState state,
        BlockPos pos,
        BlockAndTintGetter blockAndTintGetter,
        PoseStack poseStack,
        VertexConsumer vertexConsumer,
        boolean checkSides,
        RandomSource randomSource,
        ModelData modelData,
        RenderType renderType,
        Operation<Void> original
    ) {
        if (Minecraft.getInstance().screen instanceof WrenchScreen screen && screen.shouldSkipRebuildBlock()) {
            BlockPos blockPos1 = screen.renderingBlockPos();
            if (!blockPos1.equals(pos)) {
                original.call(
                    instance,
                    state,
                    pos,
                    blockAndTintGetter,
                    poseStack,
                    vertexConsumer,
                    checkSides,
                    randomSource,
                    modelData,
                    renderType
                );
            }
        } else {
            original.call(
                instance,
                state,
                pos,
                blockAndTintGetter,
                poseStack,
                vertexConsumer,
                checkSides,
                randomSource,
                modelData,
                renderType
            );
        }
    }
}
