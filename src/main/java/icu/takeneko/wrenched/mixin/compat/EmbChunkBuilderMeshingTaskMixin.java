package icu.takeneko.wrenched.mixin.compat;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import icu.takeneko.wrenched.client.screen.WrenchScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import org.embeddedt.embeddium.api.render.chunk.BlockRenderContext;
import org.embeddedt.embeddium.impl.render.chunk.compile.ChunkBuildBuffers;
import org.embeddedt.embeddium.impl.render.chunk.compile.pipeline.BlockRenderer;
import org.embeddedt.embeddium.impl.render.chunk.compile.tasks.ChunkBuilderMeshingTask;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ChunkBuilderMeshingTask.class)
abstract class EmbChunkBuilderMeshingTaskMixin {
    @WrapOperation(
        method = "execute(Lorg/embeddedt/embeddium/impl/render/chunk/compile/ChunkBuildContext;Lorg/embeddedt/embeddium/impl/util/task/CancellationToken;)Lorg/embeddedt/embeddium/impl/render/chunk/compile/ChunkBuildOutput;",
        at = @At(
            value = "INVOKE",
            target = "Lorg/embeddedt/embeddium/impl/render/chunk/compile/pipeline/BlockRenderer;renderModel(Lorg/embeddedt/embeddium/api/render/chunk/BlockRenderContext;Lorg/embeddedt/embeddium/impl/render/chunk/compile/ChunkBuildBuffers;)V"
        )
    )
    void skipBlock(
        BlockRenderer instance,
        @NotNull BlockRenderContext context,
        ChunkBuildBuffers consumer,
        Operation<Void> original
    ) {
        BlockPos blockPos = context.pos();
        if (Minecraft.getInstance().screen instanceof WrenchScreen screen && screen.shouldSkipRebuildBlock()) {
            BlockPos blockPos1 = screen.renderingBlockPos();
            if (!blockPos1.equals(blockPos)) {
                original.call(
                    instance,
                    context,
                    consumer
                );
            }
        } else {
            original.call(
                instance,
                context,
                consumer
            );
        }
    }
}
