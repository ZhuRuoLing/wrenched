package icu.takeneko.wrenched;

import icu.takeneko.wrenched.item.Wrench;
import icu.takeneko.wrenched.util.StateUtil;
import icu.takeneko.wrenched.util.WrenchedBlockExtension;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.List;

@EventBusSubscriber
public class WrenchedEvents {
    @SubscribeEvent
    public static void wrenchUse(PlayerInteractEvent.RightClickBlock event) {
        if (event.getEntity().level().isClientSide) return;
        InteractionHand hand = event.getHand();
        if (event.getEntity().getItemInHand(hand).getItem() instanceof Wrench && !event.getEntity().isShiftKeyDown()) {
            if (Wrench.ableToUseWrench(event.getLevel(), event.getPos(), event.getEntity())
                && checkWrench(event, event.getLevel().getBlockState(event.getPos()), hand)
            ) {
                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
            }
        }
    }

    private static boolean checkWrench(PlayerInteractEvent.RightClickBlock event, BlockState targetBlockState, InteractionHand hand) {
        Property<?> property = Wrench.findModifyableProperty(targetBlockState);
        if (!event.getEntity().isShiftKeyDown()
            && Wrench.ableToUseWrench(event.getLevel(), event.getPos(), event.getEntity())
            && property != null
        ) {
            if (targetBlockState.getBlock() instanceof WrenchedBlockExtension ext && !ext.wrenched$checkBlockState(targetBlockState))
                return false;
            if (!event.getEntity().getAbilities().mayBuild)
                return false;
            List<BlockState> possibleStates = StateUtil.findPossibleStatesForProperty(targetBlockState, property);
            return !possibleStates.isEmpty();
        }
        return false;
    }
}
