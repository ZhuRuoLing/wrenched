package icu.takeneko.wrenched;

import icu.takeneko.wrenched.item.Wrench;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static icu.takeneko.wrenched.Wrenched.MODID;

public class WrenchedItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredItem<Item> WRENCH = ITEMS.register(
        "wrench",
        rl -> new Wrench(new Item.Properties())
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB = CREATIVE_MODE_TABS.register(
        "wrenched",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.wrenched"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> WRENCH.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(WRENCH.get());
            }).build()
    );

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
        CREATIVE_MODE_TABS.register(bus);
    }
}
