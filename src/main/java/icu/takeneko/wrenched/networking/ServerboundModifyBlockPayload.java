package icu.takeneko.wrenched.networking;

import icu.takeneko.wrenched.Wrenched;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ServerboundModifyBlockPayload(BlockPos pos, BlockState state) implements CustomPacketPayload {
    public static final Type<ServerboundModifyBlockPayload> TYPE = new Type<>(Wrenched.of("modify_block"));

    public static final StreamCodec<? super ByteBuf, BlockState> BLOCK_STATE_STREAM_CODEC =
        StreamCodec.of(
            (buf, blockState) -> buf.writeInt(Block.getId(blockState)),
            (buf) -> Block.stateById(buf.readInt())
        );

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerboundModifyBlockPayload> STREAM_CODEC =
        StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            ServerboundModifyBlockPayload::pos,
            BLOCK_STATE_STREAM_CODEC,
            ServerboundModifyBlockPayload::state,
            ServerboundModifyBlockPayload::new
        );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Level level = context.player().level();
            if (level.isLoaded(pos)) {
                level.setBlock(pos, state, Block.UPDATE_ALL_IMMEDIATE);
            }
        });
    }
}
